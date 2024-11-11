package tgg.securityblog.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import tgg.securityblog.dto.message.AlertMessageDTO;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && !authentication.getName().equals("anonymousUser")){
            AlertMessageDTO alertMessageDTO = new AlertMessageDTO("로그아웃이 필요합니다", "/");
            request.setAttribute("message", alertMessageDTO);
            request.getRequestDispatcher("/message").forward(request,response);
            return false;
        }
        return true;
    }
}
