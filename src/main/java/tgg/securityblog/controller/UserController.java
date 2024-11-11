package tgg.securityblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tgg.securityblog.dto.user.RequestSignupUser;
import tgg.securityblog.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signupPage(Model model){
        RequestSignupUser signupUser = new RequestSignupUser();
        model.addAttribute("signupUser", signupUser);
        return "user/signupPage";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute("signupUser") RequestSignupUser signupUser, BindingResult bindingResult){

        validateSignupUser(signupUser, bindingResult);

        if(bindingResult.hasErrors()){
            return "user/signupPage";
        }

        userService.saveUser(signupUser.getUser());

        return "redirect:/login";
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
