package tgg.securityblog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgg.securityblog.dto.AlertMessageDTO;

@Controller
public class AlertMessageController {
    @GetMapping("/message")
    public String showMessage(HttpServletRequest request, Model model){
        AlertMessageDTO message = (AlertMessageDTO)request.getAttribute("message");
        model.addAttribute("message", message);
        return "message/message";
    }
}
