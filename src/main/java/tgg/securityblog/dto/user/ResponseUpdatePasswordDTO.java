package tgg.securityblog.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseUpdatePasswordDTO {
    private String password;
    private String updatePassword;
}
