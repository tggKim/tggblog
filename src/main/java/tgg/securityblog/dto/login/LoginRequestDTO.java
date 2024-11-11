package tgg.securityblog.dto.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;

@Getter @Setter
public class LoginRequestDTO {

    @NotEmpty(message = "아이디를 입력해 주세요")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;
}
