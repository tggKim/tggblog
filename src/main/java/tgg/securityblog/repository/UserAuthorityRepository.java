package tgg.securityblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tgg.securityblog.entity.UserAuthority;

import java.util.List;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

//    @Query("SELECT ua.role "+
//    "FROM User u "+
//    "JOIN u.userAuthorityMappings uam "+
//    "JOIN uam.userAuthority ua "+
//    "WHERE u.userId = :userId")

    @Query("SELECT ua.role " +
            "FROM UserAuthority ua " +
            "JOIN ua.userAuthorityMappings uam " +
            "JOIN uam.user u " +
            "WHERE u.userId = :userId"
    )
    List<String> findAllUserAuthorityByUserId(@Param("userId") Long userId);
}
