package tgg.securityblog.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgg.securityblog.entity.User;

@Getter @Setter
@NoArgsConstructor
public class ResponseUserPageDTO {

    public ResponseUserPageDTO(User user, String savedFilename){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.savedFilename = savedFilename;
    }

    private String username;
    private String nickname;
    private String email;
    private String savedFilename;
}
