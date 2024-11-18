package tgg.securityblog;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tgg.securityblog.entity.*;
import tgg.securityblog.repository.UserRepository;
import tgg.securityblog.service.*;

@Component
@RequiredArgsConstructor
public class UserAndUserAuthorityInitializer {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserAuthorityService userAuthorityService;
    private final UserAuthorityMappingService userAuthorityMappingService;
    private final ImageFileService imageFileService;
    private final PostService postService;
    @PostConstruct
    public void method(){
        User user = User.builder()
                .username("scie429")
                .password("12345")
                .nickname("tgg")
                .email("hello@hello.com")
                .build();

        User user2 = User.builder()
                .username("scie")
                .password("12345")
                .nickname("tgghuhu")
                .email("hi@hello.com")
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        user2.setPassword(passwordEncoder.encode(user2.getPassword()));
        User savedUser2 = userRepository.save(user2);

        UserAuthority userAuthority01 = new UserAuthority("ROLE_USER");
        UserAuthority userAuthority02 = new UserAuthority("ROLE_ADMIN");
        UserAuthority savedUserAuthority01 = userAuthorityService.saveUserAuthority(userAuthority01);
        UserAuthority savedUserAuthority02 = userAuthorityService.saveUserAuthority(userAuthority02);

        UserAuthorityMapping userAuthorityMapping = new UserAuthorityMapping();
        userAuthorityMapping.setUser(savedUser);
        userAuthorityMapping.setUserAuthority(savedUserAuthority01);
        userAuthorityMappingService.saveUserAuthorityMapping(userAuthorityMapping);

        userAuthorityMapping = new UserAuthorityMapping();
        userAuthorityMapping.setUser(savedUser);
        userAuthorityMapping.setUserAuthority(savedUserAuthority02);
        userAuthorityMappingService.saveUserAuthorityMapping(userAuthorityMapping);

        userAuthorityMapping = new UserAuthorityMapping();
        userAuthorityMapping.setUser(savedUser2);
        userAuthorityMapping.setUserAuthority(savedUserAuthority01);
        userAuthorityMappingService.saveUserAuthorityMapping(userAuthorityMapping);

        userAuthorityMapping = new UserAuthorityMapping();
        userAuthorityMapping.setUser(savedUser2);
        userAuthorityMapping.setUserAuthority(savedUserAuthority02);
        userAuthorityMappingService.saveUserAuthorityMapping(userAuthorityMapping);

        ImageFile imageFile = ImageFile.builder()
                .savedFileName("user.png")
                .uploadFileName("basic image")
                .build();

        imageFile.setUser(savedUser);
        imageFileService.saveImageFile(imageFile);

        ImageFile imageFile2 = ImageFile.builder()
                .savedFileName("user.png")
                .uploadFileName("basic image")
                .build();

        imageFile2.setUser(savedUser2);
        imageFileService.saveImageFile(imageFile2);

        for(int i=0;i<101;i++){
            Post post1 = Post.builder()
                    .title("tgg의 " + (i+1) + " 번째 제목")
                    .content("tgg의" + (i+1) + " 번째 내용")
                    .nickname(user.getNickname())
                    .build();

            Post post2 = Post.builder()
                    .title("tgghuhu의 " + (i+1) + " 번째 제목")
                    .content("tgghuhu의" + (i+1) + " 번째 내용")
                    .nickname(user2.getNickname())
                    .build();

            post1.setUser(user);
            post2.setUser(user2);

            postService.savePost(post1);
            postService.savePost(post2);
        }
    }
}
