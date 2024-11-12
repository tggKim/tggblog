package tgg.securityblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tgg.securityblog.entity.ImageFile;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

    @Query("SELECT i.savedFilename " +
            "FROM ImageFile i " +
            "JOIN i.user u WHERE " +
            "u.username = :username")
    public String getSavedFilenameByUsername(@Param("username") String username);
}
