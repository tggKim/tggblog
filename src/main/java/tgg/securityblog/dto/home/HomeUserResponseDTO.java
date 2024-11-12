package tgg.securityblog.dto.home;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgg.securityblog.entity.User;

@Getter @Setter
@NoArgsConstructor
public class HomeUserResponseDTO {

    public HomeUserResponseDTO(String nickname, String savedFilename){
        this.nickname = nickname;
        this.savedFilename = savedFilename;
    }

    private String nickname;
    private String savedFilename;
}
