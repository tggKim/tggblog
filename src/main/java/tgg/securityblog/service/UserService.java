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
    private final PasswordEncoder passwordEncoder;

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당 Id의 유저가 존재하지 않습니다"));
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 아이디의 유저가 존재하지 않습니다"));
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUserNickname(String username, String nickname){
        User findUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 아이디의 유저가 존재하지 않습니다"));
        findUser.setNickname(nickname);
        return findUser;
    }

    @Transactional
    public User updateUserPassword(String username, String password){
        String encodePassword = passwordEncoder.encode(password);
        User findUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 아이디의 유저가 존재하지 않습니다"));
        findUser.setPassword(encodePassword);
        return findUser;
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
}
