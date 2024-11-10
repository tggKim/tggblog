package tgg.securityblog.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import tgg.securityblog.handler.CustomAuthenticationFailureHandler;
import tgg.securityblog.handler.CustomAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(ahr -> ahr
                .requestMatchers("/test").authenticated()
                .requestMatchers("/login","/error","/","/message","/css/**").permitAll());

        httpSecurity.csrf(c->c.disable());

        httpSecurity.formLogin(fl -> fl
                .loginPage("/login")
                .loginProcessingUrl("/login_validated")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .failureHandler(new CustomAuthenticationFailureHandler()));
        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.logout(lo -> lo
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID"));

        httpSecurity.sessionManagement(session -> session
                .invalidSessionUrl("/")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true));

        return httpSecurity.build();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
