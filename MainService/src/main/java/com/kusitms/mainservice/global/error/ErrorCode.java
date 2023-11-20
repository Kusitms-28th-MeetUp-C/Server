package com.kusitms.mainservice.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_PLATFORM_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 플랫폼 타입입니다."),
    INVALID_ROADMAP_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 로드맵 타입입니다."),
    INVALID_TEMPLATE_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 회의록 타입입니다."),
    INVALID_SHARED_TYPE(HttpStatus.BAD_REQUEST,"유효하지않은 공유된 템플릿,로드맵 타입 입니다."),
    INVALID_TEAM_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 팀 타입입니다."),
    INVALID_TEAM_SPACE_SIZE(HttpStatus.BAD_REQUEST, "유효하지 않은 팀 스페이스 크기입니다."),
    INVALID_USER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 유저 타입니다."),
    EMPTY_TEAM_SPACE(HttpStatus.BAD_REQUEST, "요청에 팀 스페이스 정보가 없습니다."),

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 형식이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "일치하지 않는 리프레시 토큰입니다."),


    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다."),

    /**
     * 404 Not Found
     */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "엔티티를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 팀입니다."),
    TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회의록입니다."),
    TEMPLATE_CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회의록 내용입니다."),
    TEMPLATE_DOWNLOAD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 다운로드 회의록 내용입니다."),
    ROADMAP_DOWNLOAD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 로드맵 다운로드 기록입니다."),
    ROADMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 로드맵입니다."),
    ROADMAP_SPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 로드맵 딘계입니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    DUPLICATE_TEAM(HttpStatus.CONFLICT, "이미 존재하는 팀입니다."),


    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    JSON_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Json 으로 변환할 수 없는 String 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
