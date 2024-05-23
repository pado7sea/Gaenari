package com.gaenari.backend.global.format.response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@Component
public class FilterResponse {

    private void sendJsonResponse(HttpServletResponse response, int status, String statusMessage, String message, Optional<String> actionRequired)
        throws IOException {
        log.info("Sending JSON response - Status: {}, Message: {}", statusMessage, message);
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String actionRequiredJson = actionRequired.map(action -> ", \"actionRequired\": \"" + action + "\"").orElse("");
        String body = String.format("{\n" +
                                        "  \"status\": \"%s\",\n" +
                                        "  \"message\": \"%s\"%s\n" +
                                        "}", statusMessage, message, actionRequiredJson);
        response.getWriter().write(body);
    }

    public void sendTokenInvalidResponse(HttpServletResponse response) throws IOException {
        log.error("Token invalid. Prompting re-login.");
        sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "FAILED",
                         "토큰이 유효하지 않습니다. 다시 로그인 해주십시오.", Optional.empty());
    }

    public void sendTokenReissueResponse(HttpServletResponse response) throws IOException {
        log.warn("Session expired. Advising token refresh.");
        sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED",
                         "세션이 만료되었습니다. 토큰을 새로 갱신해주십시오.", Optional.of("REFRESH_TOKEN"));
    }

    public void sendLoginRequiredResponse(HttpServletResponse response) throws IOException {
        log.warn("Service requires login.");
        sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "FAILED",
                         "해당 서비스는 로그인이 필요합니다. 로그인 해주십시오.", Optional.empty());
    }

    public void sendEmailAuthFailureResponse(HttpServletResponse response) throws IOException {
        log.error("Email authentication required for registration.");
        sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "FAILED",
                         "회원가입을 위해서는 이메일 인증이 필요합니다.", Optional.empty());
    }

    public void sendTokenInvalidRetryWithNewTokenResponse(HttpServletResponse response) throws IOException {
        log.error("Token invalid. Advising retry with a new token.");
        sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "FAILED",
                         "토큰이 유효하지 않습니다. 새로운 토큰으로 다시 시도해주세요.", Optional.empty());
    }
}


