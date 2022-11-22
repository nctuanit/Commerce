package com.bach.Commerce.security.login;

import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    final UserService userService;

    //Phương thức sẽ gọi lại vào spring security nếu xác thực (bad request) không thành công
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("username");
        System.out.println("onAuthenticationFailure: " + email);

        String failureRedirectURL = "/dang-nhap?error&email=" + email;
        if (exception.getMessage().contains("OTP")) {
            failureRedirectURL = "/dang-nhap?otp=true&email=" + email;
        } else {
            UserDTO userDTO = userService.getUserByMail(email);
            if (userDTO != null && userDTO.isOTPRequired()) {
                failureRedirectURL = "/dang-nhap?otp=true&email=" + email;
            }
        }

        super.setDefaultFailureUrl(failureRedirectURL);

        super.onAuthenticationFailure(request, response, exception);
    }
}
