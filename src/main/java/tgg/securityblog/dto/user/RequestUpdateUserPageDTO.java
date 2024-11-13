package tgg.securityblog.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class RequestUpdateUserPageDTO {

    @NotEmpty(message = "이메일은 비어 있을 수 없습니다")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message = "유효한 이메일 주소를 입력해 주세요")
    private String email;

    @NotEmpty(message = "닉네임를 입력해 주세요")
    private String nickname;

    private MultipartFile imageFile;

}
