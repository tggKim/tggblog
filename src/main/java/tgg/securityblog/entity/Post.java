package tgg.securityblog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity @Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Post {

    @Builder
    public Post(String title, String content, String nickname){
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private Long viewCount = 0L;

    private String title;

    private String content;

    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void setUser(User user){
        this.user = user;
        user.addPost(this);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setViewCount(Long viewCount){
        this.viewCount = viewCount;
    }

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}
