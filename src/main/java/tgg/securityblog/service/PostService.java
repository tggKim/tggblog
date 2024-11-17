package tgg.securityblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tgg.securityblog.dto.post.HomePostsResponseDTO;
import tgg.securityblog.entity.Post;
import tgg.securityblog.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post savePost(Post post){
        return postRepository.save(post);
    }

    public Page<HomePostsResponseDTO> findAllPosts(Pageable pageable) {
        Page<HomePostsResponseDTO> posts = postRepository.findAll(pageable).map(post -> new HomePostsResponseDTO(post, postRepository.getNicknameByPostId(post.getPostId())));
        return posts;
    }
}
