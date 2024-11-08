package tgg.securityblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgg.securityblog.entity.UserAuthority;
import tgg.securityblog.repository.UserAuthorityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthorityService {
    private final UserAuthorityRepository userAuthorityRepository;

    public UserAuthority saveUserAuthority(UserAuthority userAuthority){
        return userAuthorityRepository.save(userAuthority);
    }

    public List<String> findAllUserAuthorityByUserId(Long userId){
        return userAuthorityRepository.findAllUserAuthorityByUserId(userId);
    }
}
