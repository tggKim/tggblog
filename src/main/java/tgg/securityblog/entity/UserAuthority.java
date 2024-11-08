package tgg.securityblog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class UserAuthority {

    public UserAuthority(String role){
        this.role = role;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    private String role;

    @OneToMany(mappedBy = "userAuthority")
    private List<UserAuthorityMapping> userAuthorityMappings = new ArrayList<>();

    public void addUserAuthorityMapping(UserAuthorityMapping userAuthorityMapping){
        userAuthorityMappings.add(userAuthorityMapping);
    }
}
