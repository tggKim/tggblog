package tgg.securityblog.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import tgg.securityblog.entity.User;
import tgg.securityblog.entity.UserAuthority;
import tgg.securityblog.entity.UserAuthorityMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserAuthorityService userAuthorityService;

    @Autowired
    UserAuthorityMappingService userAuthorityMappingService;
    @Autowired
    PasswordEncoder passwordEncoder;

    //UserService 테스트
    @Test
    public void test(){
        User user = User.builder()
                .username("scie")
                .password("sukim2919@")
                .nickname("tgg")
                .build();

        userService.saveUser(user);

        User findUser = userService.findUserByUsername("scie");

        Assertions.assertThat(findUser.getUsername()).isEqualTo("scie");
        Assertions.assertThat(findUser.getNickname()).isEqualTo("tgg");
        Assertions.assertThat(passwordEncoder.matches("sukim2919@", findUser.getPassword())).isEqualTo(true);


        Assertions.assertThat(userService.existByUsername("scie")).isEqualTo(true);

        userService.updateUserNickname("scie","tgghuhu");
        findUser = userService.findUserByUsername("scie");
        Assertions.assertThat(findUser.getNickname()).isEqualTo("tgghuhu");

        userService.updateUserPassword("scie", "12345");
        findUser = userService.findUserByUsername("scie");
        Assertions.assertThat(passwordEncoder.matches("12345", findUser.getPassword()));

        //deleteUserById 하면 UserAndUserAuthorityInitializer 떄문에 2L임 ㅇㅇ
        userService.deleteUserByUsername("scie");

        Assertions.assertThat(userService.existByUsername("scie")).isEqualTo(false);

    }


    //UserAuthorityService 에서 findAllUserAuthorities() 테스트
    @Test
    public void test02(){
        User user = User.builder()
                .username("scie")
                .password("sukim2919@")
                .nickname("tgg")
                .build();

        User savedUser = userService.saveUser(user);

        UserAuthority userAuthority1 = new UserAuthority("ROLE_USER");
        UserAuthority userAuthority2 = new UserAuthority("ROLE_ADMIN");

        UserAuthority savedUserAuthority01 = userAuthorityService.saveUserAuthority(userAuthority1);
        UserAuthority savedUserAuthority02 = userAuthorityService.saveUserAuthority(userAuthority2);

        UserAuthorityMapping userAuthorityMapping = new UserAuthorityMapping();
        userAuthorityMapping.setUser(savedUser);
        userAuthorityMapping.setUserAuthority(savedUserAuthority01);
        userAuthorityMappingService.saveUserAuthorityMapping(userAuthorityMapping);

        userAuthorityMapping = new UserAuthorityMapping();
        userAuthorityMapping.setUser(savedUser);
        userAuthorityMapping.setUserAuthority(savedUserAuthority02);
        userAuthorityMappingService.saveUserAuthorityMapping(userAuthorityMapping);

        User findUser = userService.findUserByUsername("scie");

        List<String> roles = userAuthorityService.findAllUserAuthorityByUserId(findUser.getUserId());
        for(String role : roles){
            System.out.println(role);
        }
    }
}