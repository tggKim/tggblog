package tgg.securityblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgg.securityblog.dto.home.HomeUserResponseDTO;
import tgg.securityblog.entity.User;
import tgg.securityblog.service.ImageFileService;
import tgg.securityblog.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ImageFileService imageFileService;

    @GetMapping("/")
    public String showHome(Authentication authentication, Model model){
        ifAuthenticatedSetResponseDTO(authentication, model);
        return "home/home";
    }

    private void ifAuthenticatedSetResponseDTO(Authentication authentication, Model model){
        if(authentication != null && !authentication.getName().equals("anonymousUser") && authentication.isAuthenticated()) {
            User findUser = userService.findUserByUsername(authentication.getName());
            String savedFilename = imageFileService.getSavedFilename(findUser.getUsername());
            HomeUserResponseDTO user = new HomeUserResponseDTO(findUser.getNickname(), savedFilename);
            model.addAttribute("user", user);
        }
    }
}
