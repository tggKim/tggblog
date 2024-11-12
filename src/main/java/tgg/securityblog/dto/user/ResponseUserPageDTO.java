package tgg.securityblog.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgg.securityblog.entity.User;

@Getter @Setter
@NoArgsConstructor
public class ResponseUserPageDTO {

    public ResponseUserPageDTO(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

    private String username;
    private String nickname;
    private String email;
}
