package tgg.securityblog.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestUpdatePasswordDTO {

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;

    private String updatePassword;
}
