package tgg.securityblog.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgg.securityblog.entity.User;
import tgg.securityblog.entity.UserAuthority;
import tgg.securityblog.entity.UserAuthorityMapping;
import tgg.securityblog.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAuthorityService userAuthorityService;
    private final UserAuthorityMappingService userAuthorityMappingService;
    private final PasswordEncoder passwordEncoder;

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당 Id의 유저가 존재하지 않습니다"));
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 아이디의 유저가 존재하지 않습니다"));
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        UserAuthority userAuthority = userAuthorityService.findUserAuthorityById(1L);

        UserAuthorityMapping userAuthorityMapping = new UserAuthorityMapping();
        userAuthorityMapping.setUser(savedUser);
        userAuthorityMapping.setUserAuthority(userAuthority);
        userAuthorityMappingService.saveUserAuthorityMapping(userAuthorityMapping);

        return savedUser;
    }

    public boolean isPasswordCorrect(String username, String password){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 아이디의 유저가 존재하지 않습니다"));
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Transactional
    public User updateUserNickname(User user, String nickname){
        user.setNickname(nickname);
        return user;
    }

    @Transactional
    public User updateUserPassword(User user, String password){
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        return user;
    }

    @Transactional
    public User updateUserEmail(User user, String email){
        user.setEmail(email);
        return user;
    }

    @Transactional
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserByUsername(String username){
        userRepository.deleteByUsername(username);
    }

    public boolean existByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existByNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public boolean existByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
