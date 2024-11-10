package tgg.securityblog;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tgg.securityblog.entity.User;
import tgg.securityblog.entity.UserAuthority;
import tgg.securityblog.entity.UserAuthorityMapping;
import tgg.securityblog.service.UserAuthorityMappingService;
import tgg.securityblog.service.UserAuthorityService;
import tgg.securityblog.service.UserService;

@Component
@RequiredArgsConstructor
public class UserAndUserAuthorityInitializer {
    private final UserService userService;
    private final UserAuthorityService userAuthorityService;
    private final UserAuthorityMappingService userAuthorityMappingService;
    @PostConstruct
    public void method(){
        User user = User.builder()
                .username("scie429")
                .password("12345")
                .nickname("tgg")
                .build();

        User user2 = User.builder()
                .username("scie")
                .password("12345")
                .nickname("tgghuhu")
                .build();

        User savedUser = userService.saveUser(user);
        User savedUser2 = userService.saveUser(user2);

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

    }
}
