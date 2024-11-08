package tgg.securityblog.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgg.securityblog.entity.UserAuthority;
import tgg.securityblog.entity.UserAuthorityMapping;
import tgg.securityblog.repository.UserAuthorityMappingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthorityMappingService {
    private final UserAuthorityMappingRepository userAuthorityMappingRepository;

    public UserAuthorityMapping saveUserAuthorityMapping(UserAuthorityMapping userAuthorityMapping){
        return userAuthorityMappingRepository.save(userAuthorityMapping);
    }

}
