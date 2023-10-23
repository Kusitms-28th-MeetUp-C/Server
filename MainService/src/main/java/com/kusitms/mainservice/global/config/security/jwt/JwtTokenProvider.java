package com.kusitms.mainservice.global.config.security.jwt;


import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.config.security.jwt.entity.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import io.jsonwebtoken.security.Keys;
@Slf4j
@Component
public class JwtTokenProvider {

    // 안전한 256 비트 크기의 키 생성
    private final static byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();


    private final static String baseSecretKey="aBcDeFgHiJkLmNoPqRsTuVwXyZ0123456789AbCdEfGhIjKlMnOpQrStUvWxYz";
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Autowired
    private UserRepository userRepository;
    @Value("${security.jwt.token.access-length}")
    private long ACCESS_TOKEN_VALID_TIME; //day
    @Value("${security.jwt.token.refresh-length}")
    private long REFRESH_TOKEN_VALID_TIME; //week

    public TokenInfo createAccessToken(Long id) {
        return createToken(id , ACCESS_TOKEN_VALID_TIME);
    }

    public TokenInfo createRefreshToken(Long id) {
        return createToken(id, REFRESH_TOKEN_VALID_TIME);
    }

    // 토큰 생성
    public TokenInfo createToken(Long id, long validTime) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(id));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validTime); // 유효기간 계산 (지금으로부터 + 유효시간)
        logger.info("now: {}", now);
        logger.info("validity: {}", validity);

        String token = Jwts.builder()
                .setClaims(claims) // sub 설정
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, keyBytes) // 암호화방식?
                .compact();

        token = "Bearer " + token;
        return new TokenInfo(token, validity.getTime()- now.getTime());
    }

    // 토큰에서 값 추출
    public Long getSubject(String token) {
        return Long.valueOf(Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }
    public Claims parseClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(baseSecretKey));
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
    public String resolveToken(String token) {
        if(token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }
        return null;
    }
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        try {
            claims.get("email");
        } catch(Exception e) {
            try {
                throw new AuthenticationException("Jwt 토큰에 이메일이 존재하지 않습니다.");
            } catch (AuthenticationException ex) {
                ex.printStackTrace();
            }
        }
        Collection<GrantedAuthority> authorities =
                Arrays.stream(claims.get("ROLE_").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails userDetails = new User(claims.get("email").toString(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    // 유효한 토큰인지 확인
    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(keyBytes).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }
    public TokenInfo createAccessTokenFromRefreshToken(String refreshToken) {
        Claims claims = parseClaims(refreshToken);

        Long userId = Long.valueOf(claims.getSubject());

        // 유저 정보를 불러와서 새로운 Access Token을 생성합니다.
        com.kusitms.mainservice.domain.user.domain.User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없음"));

        // Access Token 생성 (유효기간은 ACCESS_TOKEN_VALID_TIME을 사용)
        String newAccessToken = createToken(user.getId(), ACCESS_TOKEN_VALID_TIME).getToken();
        return new TokenInfo(newAccessToken, ACCESS_TOKEN_VALID_TIME);
    }
}
