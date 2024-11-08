package tgg.securityblog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Builder
    public User(String username, String password, String nickname){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<UserAuthorityMapping> userAuthorityMappings = new ArrayList<>();

    public void addUserAuthorityMapping(UserAuthorityMapping userAuthorityMapping){
        userAuthorityMappings.add(userAuthorityMapping);
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}