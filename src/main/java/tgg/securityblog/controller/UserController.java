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
import tgg.securityblog.dto.user.*;
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
        String savedFilename = imageFileService.getSavedFilename(findUser.getUsername());
        ResponseUserPageDTO user = new ResponseUserPageDTO(findUser, savedFilename);
        model.addAttribute("user", user);
        return "user/userPage";
    }

    @GetMapping("/userUpdate")
    public String userUpdatePage(Authentication authentication, Model model){
        User findUser = userService.findUserByUsername(authentication.getName());
        ResponseUpdateUserPageDTO user = new ResponseUpdateUserPageDTO(findUser);
        model.addAttribute("updateUser", user);
        return "user/userUpdatePage";
    }

    @PostMapping("/userUpdate")
    public String userUpdate(@Validated @ModelAttribute("updateUser") RequestUpdateUserPageDTO updateUser, BindingResult bindingResult, Authentication authentication){

        User user = userService.findUserByUsername(authentication.getName());

        String updateOrNot = validateEmailAndNicknameForUpdateUserDTO(user, updateUser, bindingResult);

        isImageFile(updateUser.getImageFile(), bindingResult);

        if(bindingResult.hasErrors()){
            return "user/userUpdatePage";
        }

        if(updateOrNot.contains("nickname")){
            userService.updateUserNickname(user, updateUser.getNickname());
        }
        if(updateOrNot.contains("email")){
            userService.updateUserEmail(user, updateUser.getEmail());
        }

        if(!updateUser.getImageFile().isEmpty()){
            imageFileService.updateImageFile(user.getUsername(), updateUser.getImageFile());
        }

        return "redirect:/user";
    }

    @GetMapping("/passwordUpdate")
    public String showPasswordUpdatePage(Model model){
        ResponseUpdatePasswordDTO updatePassword = new ResponseUpdatePasswordDTO();
        model.addAttribute("updatePassword", updatePassword);
        return "user/passwordUpdatePage";
    }

    @PostMapping("/passwordUpdate")
    public String passwordUpdate(@Validated @ModelAttribute("updatePassword") RequestUpdatePasswordDTO updatePassword, BindingResult bindingResult, Authentication authentication){

        User findUser = userService.findUserByUsername(authentication.getName());
        if(!userService.isPasswordCorrect(findUser.getUsername(), updatePassword.getPassword())){
            bindingResult.rejectValue("password", "incorrectPassword", "비밀번호가 틀렸습니다");
        }

        if(bindingResult.hasErrors()){
            return "user/passwordUpdatePage";
        }

        userService.updateUserPassword(findUser, updatePassword.getUpdatePassword());

        return "redirect:/user";
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

    private String validateEmailAndNicknameForUpdateUserDTO(User user, RequestUpdateUserPageDTO updateUser, BindingResult bindingResult){

        String updateOrNot = "";

        if(!user.getNickname().equals(updateUser.getNickname())){
            validateDuplicateNickname(updateUser.getNickname(), bindingResult);
            updateOrNot += "nickname";
        }

        if(!user.getEmail().equals(updateUser.getEmail())){
            validateDuplicateEmail(updateUser.getEmail(), bindingResult);
            updateOrNot += "email";
        }

        return updateOrNot;
    }

    private void validateDuplicateUsername(String username, BindingResult bindingResult){
        if(userService.existByUsername(username)){
            bindingResult.rejectValue("username", "duplicateUsername", "중복된 아이디 입니다.");
        }
    }

    private void validateDuplicateNickname(String nickname, BindingResult bindingResult){
        if(userService.existByNickname(nickname)){
            bindingResult.rejectValue("nickname", "duplicateNickname", "중복된 닉네임 입니다.");
        }
    }

    private void validateDuplicateEmail(String email, BindingResult bindingResult){
        if(userService.existByEmail(email)){
            bindingResult.rejectValue("email", "duplicateEmail", "중복된 이메일 입니다.");
        }
    }

    private void validateSignupUser(RequestSignupUser signupUser, BindingResult bindingResult){
        validateDuplicateUsername(signupUser.getUsername(), bindingResult);

        validateDuplicateNickname(signupUser.getNickname(), bindingResult);

        validateDuplicateEmail(signupUser.getEmail(), bindingResult);
    }
}
