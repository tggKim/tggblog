package tgg.securityblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import tgg.securityblog.dto.user.RequestSignupUser;
import tgg.securityblog.dto.user.ResponseUserPageDTO;
import tgg.securityblog.entity.ImageFile;
import tgg.securityblog.entity.User;
import tgg.securityblog.service.ImageFileService;
import tgg.securityblog.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ImageFileService imageFileService;

    @GetMapping("/signup")
    public String signupPage(Model model){
        RequestSignupUser signupUser = new RequestSignupUser();
        model.addAttribute("signupUser", signupUser);
        return "user/signupPage";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute("signupUser") RequestSignupUser signupUser, BindingResult bindingResult){

        isImageFile(signupUser.getImage(), bindingResult);

        validateSignupUser(signupUser, bindingResult);

        if(bindingResult.hasErrors()){
            return "user/signupPage";
        }

        User savedUser = userService.saveUser(signupUser.getUser());
        saveImageFile(savedUser, signupUser.getImage());

        return "redirect:/";
    }

    @GetMapping("/user")
    public String userPage(Authentication authentication, Model model){
        User findUser = userService.findUserByUsername(authentication.getName());
        ResponseUserPageDTO user = new ResponseUserPageDTO(findUser);
        model.addAttribute("user", user);
        return "user/userPage";
    }

    private void saveImageFile(User user,  MultipartFile image){
        ImageFile imageFile = imageFileService.makeImageFile(image, user);
        imageFileService.saveImageFile(imageFile);
    }

    private void isImageFile(MultipartFile image, BindingResult bindingResult){

        if(!image.isEmpty() && !image.getContentType().startsWith("image/")){
            bindingResult.reject("notImage");
        }

    }

    private void validateSignupUser(RequestSignupUser signupUser, BindingResult bindingResult){
        if(userService.existByUsername(signupUser.getUsername())){
            bindingResult.rejectValue("username", "duplicateUsername", "중복된 아이디 입니다.");
        }

        if(userService.existByNickname(signupUser.getNickname())){
            bindingResult.rejectValue("nickname", "duplicateNickname", "중복된 닉네임 입니다.");
        }
    }
}
