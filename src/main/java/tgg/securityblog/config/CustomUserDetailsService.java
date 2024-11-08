package tgg.securityblog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tgg.securityblog.entity.User;
import tgg.securityblog.service.UserAuthorityService;
import tgg.securityblog.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final UserAuthorityService userAuthorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userService.findUserByUsername(username);
        List<GrantedAuthority> grantedAuthorities = userAuthorityService.findAllUserAuthorityByUserId(findUser.getUserId())
                .stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        UserDetails user = new org.springframework.security.core.userdetails.User(findUser.getUsername(), findUser.getPassword(), grantedAuthorities);
        return user;
    }
}
