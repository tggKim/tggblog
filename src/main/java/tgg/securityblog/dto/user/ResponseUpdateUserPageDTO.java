package tgg.securityblog.dto.user;

import lombok.Getter;
import lombok.Setter;
import tgg.securityblog.entity.User;

@Getter @Setter
public class ResponseUpdateUserPageDTO {
    public ResponseUpdateUserPageDTO(User user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

    private String nickname;
    private String email;

}
