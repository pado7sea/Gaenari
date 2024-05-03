package com.gaenari.backend.domain.member.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RequestLogin {
    @NotEmpty(message = "이메일은 필수 정보입니다.")
    @Email(message = "올바른 형식의 이메일 주소를 입력해 주십시오.")
    @Schema(description = "이메일", example = "ssafy123@naver.com")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 정보입니다.")
    @Schema(description = "비밀번호", example = "ssafy123")
    private String password;
}
