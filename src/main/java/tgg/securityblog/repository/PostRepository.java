package tgg.securityblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tgg.securityblog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT u.nickname FROM Post p JOIN p.user u WHERE p.postId = :postId")
    public String getNicknameByPostId(@Param(value = "postId") Long postId);

    @Modifying
    @Query(value = "UPDATE post p SET p.nickname = :nickname WHERE p.user_id = :userId", nativeQuery = true)
    public void updatePostsNickname(@Param(value = "userId")Long userId, @Param(value = "nickname")String nickname);
}
