package tgg.securityblog.dto.post;

import lombok.Getter;
import lombok.Setter;
import tgg.securityblog.entity.Post;

import java.time.LocalDateTime;
@Getter @Setter
public class HomePostsResponseDTO {

    public HomePostsResponseDTO(Post post, String nickname){
        this.postId = post.getPostId();
        this.viewCount = post.getViewCount();
        this.title = post.getTitle();
        this.createdDate = post.getCreatedDate();
        this.nickname = nickname;
    }

    private String nickname;

    private Long postId;

    private Long viewCount;

    private String title;

    private LocalDateTime createdDate;
}
