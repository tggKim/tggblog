package tgg.securityblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tgg.securityblog.entity.ImageFile;
import tgg.securityblog.entity.User;
import tgg.securityblog.repository.ImageFileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
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

    @Transactional
    public void updateImageFile(String username, MultipartFile multipartFile){
        ImageFile findImageFile = imageFileRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("유저에 프로필 이미지가 없습니다."));

        String uploadFilename = null;
        String savedFilename = null;

        try{
            uploadFilename = multipartFile.getOriginalFilename();
            int index = uploadFilename.lastIndexOf(".");
            savedFilename = UUID.randomUUID() + uploadFilename.substring(index);

            //기존의 프로필이 기본 이미지가 아니라면 이미지 삭제
            if(!findImageFile.getSavedFilename().equals("user.png")){
                File deleteFile = new File(getFullPath(findImageFile.getSavedFilename()));
                deleteFile.delete();
            }

            //새로운 파일 저장
            multipartFile.transferTo(new File(getFullPath(savedFilename)));

            findImageFile.setUploadFilename(uploadFilename);
            findImageFile.setSavedFilename(savedFilename);
        }catch (IOException e){
            log.error("파일 저장중 문제 발생 = ", e);
        }
    }
}
