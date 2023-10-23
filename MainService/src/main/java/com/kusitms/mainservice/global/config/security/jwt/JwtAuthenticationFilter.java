package com.kusitms.mainservice.global.config.security.jwt;


import com.kusitms.mainservice.global.config.security.jwt.entity.TokenInfo;
import com.kusitms.mainservice.global.config.security.jwt.exception.EmptyTokenException;
import com.kusitms.mainservice.global.config.security.jwt.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_PREFIX = "Bearer RefreshToken ";
    private static final String ACCESS_TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken != null) {
            if (bearerToken.startsWith(ACCESS_TOKEN_PREFIX)) {
                // accessToken이 있는 경우: 인증 수행
                String jwtToken = jwtTokenProvider.resolveToken(bearerToken);

                if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new InvalidTokenException("Access 토큰이 유효하지 않습니다.");
                }
            } else if (bearerToken.startsWith(REFRESH_TOKEN_PREFIX)) {
                // refreshToken이 있는 경우: 새로운 accessToken 생성 및 인증 수행
                String refreshToken = bearerToken.substring(REFRESH_TOKEN_PREFIX.length());

                if (jwtTokenProvider.validateToken(refreshToken)) {
                    TokenInfo newAccessToken = jwtTokenProvider.createAccessTokenFromRefreshToken(refreshToken);

                    if (newAccessToken != null) {
                        // 생성한 새로운 accessToken을 Response Header에 추가 (선택적)
                        response.addHeader(AUTHORIZATION_HEADER, ACCESS_TOKEN_PREFIX + newAccessToken);

                        // 새로운 accessToken으로 인증 수행
                        Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken.getToken());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new InvalidTokenException("새로운 Access 토큰 생성 실패");
                    }
                } else {
                    throw new InvalidTokenException("Refresh 토큰이 유효하지 않습니다.");
                }
            }
        } else {
            throw new EmptyTokenException("Authorization 헤더에 토큰이 없습니다.");
        }

        filterChain.doFilter(request, response);
    }
}
