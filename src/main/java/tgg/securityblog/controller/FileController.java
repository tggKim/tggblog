package tgg.securityblog.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import tgg.securityblog.service.ImageFileService;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final ImageFileService imageFileService;

    @ResponseBody
    @GetMapping("/image/{savedFilename}")
    public Resource getImage(@PathVariable("savedFilename") String savedFilename) throws MalformedURLException {
        return new UrlResource("file:" + imageFileService.getFullPath(savedFilename));
    }
}
