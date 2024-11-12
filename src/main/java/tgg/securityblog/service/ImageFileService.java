package tgg.securityblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tgg.securityblog.entity.ImageFile;
import tgg.securityblog.entity.User;
import tgg.securityblog.repository.ImageFileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageFileService {

    @Value("${file.dir}")
    private String fileDir;

    private final ImageFileRepository imageFileRepository;

    public String getFullPath(String savedFilename){
        return fileDir + savedFilename;
    }

    public String getSavedFilename(String username){
        return imageFileRepository.getSavedFilenameByUsername(username);
    }

    public ImageFile saveImageFile(ImageFile imageFile){
        return imageFileRepository.save(imageFile);
    }

    public ImageFile makeImageFile(MultipartFile multipartFile, User user) {

        String uploadFilename = null;
        String savedFilename = null;

        if(multipartFile.isEmpty()){
            uploadFilename = "basic image";
            savedFilename = "user.png";
        }
        else{
            try{
                uploadFilename = multipartFile.getOriginalFilename();
                int index = uploadFilename.lastIndexOf(".");
                savedFilename = UUID.randomUUID() + uploadFilename.substring(index);

                multipartFile.transferTo(new File(getFullPath(savedFilename)));
            }catch (IOException e){
                log.error("파일 저장중 문제 발생 = ", e);
            }
        }

        ImageFile imageFile = ImageFile.builder()
                .uploadFileName(uploadFilename)
                .savedFileName(savedFilename)
                .build();

        imageFile.setUser(user);

        return imageFile;
    }
}
