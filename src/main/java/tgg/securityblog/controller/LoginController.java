package tgg.securityblog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tgg.securityblog.dto.LoginRequestDTO;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false, value = "logout") String logout, Model model){

        if(logout != null){
            model.addAttribute("logoutMessage", "로그아웃 성공");
            System.out.println();
        }

        model.addAttribute("loginRequest", new LoginRequestDTO());

        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginRequest") LoginRequestDTO loginRequestDTO, BindingResult bindingResult, HttpServletRequest request){

        AuthenticationException exception = (AuthenticationException)request.getAttribute("exception");
        if(exception != null){
            if(exception instanceof SessionAuthenticationException){
                bindingResult.reject("multipleLogin");
            }
            else{
                bindingResult.reject("loginError");
            }
        }

        if(bindingResult.hasErrors()){
            return "login/login";
        }

        return "forward:/login_validated";
    }

}
