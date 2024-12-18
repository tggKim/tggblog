package tgg.securityblog.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import tgg.securityblog.entity.User;

@Getter @Setter
public class RequestSignupUser {

    private String username;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;

    @NotEmpty(message = "닉네임를 입력해 주세요")
    private String nickname;

    @NotEmpty(message = "이메일은 비어 있을 수 없습니다")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message = "유효한 이메일 주소를 입력해 주세요")
    private String email;

    private MultipartFile image;

    public User getUser(){
        return User.builder()
                .username(this.username)
                .password(this.password)
                .nickname(this.nickname)
                .email(this.email)
                .build();
    }
}
