package tgg.securityblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tgg.securityblog.entity.UserAuthority;
import tgg.securityblog.entity.UserAuthorityMapping;

import java.util.List;

@Repository
public interface UserAuthorityMappingRepository extends JpaRepository<UserAuthorityMapping, Long> {

}
