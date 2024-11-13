package tgg.securityblog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ImageFile {

    @Builder
    public ImageFile(String uploadFileName, String savedFileName){
        this.uploadFilename = uploadFileName;
        this.savedFilename = savedFileName;
    }

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String uploadFilename;

    private String savedFilename;

    public void setUser(User user){
        this.user = user;
        user.addImageFile(this);
    }
}
