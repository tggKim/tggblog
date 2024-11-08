package tgg.securityblog.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserAuthorityMapping {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAuthorityMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorityId")
    private UserAuthority userAuthority;

    public void setUser(User user){
        this.user = user;
        user.addUserAuthorityMapping(this);
    }

    public void setUserAuthority(UserAuthority userAuthority){
        this.userAuthority = userAuthority;
        userAuthority.addUserAuthorityMapping(this);
    }
}
