package com.bach.Commerce.security.fillter;

import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Component
public class BeforeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    UserService userService;

    public BeforeAuthenticationFilter() {
        super.setUsernameParameter("username");
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    /*
     * Phương thức này sẽ được gọi lại bởi chính spring security ngay trước qua trình xác thực
     * vì cậy chúng ta sẽ viết code trong method này để xác định xem đâu là một yêu cầu đáng ngờ (bad request)
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email = request.getParameter("username");
        logger.info("attemptAuthentication email: " + email);

        UserDTO userDTO = userService.getUserByMail(email);

        if (userDTO != null) {
            float spamScore = getGoogleRecaptchaScore();
            if (spamScore < 0.5) {

                if (userDTO.isOTPRequired()) {
                    return super.attemptAuthentication(request, response);
                }

                //generate OTP and send email
                try {
                    userService.generateOneTimePassword(userDTO);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new AuthenticationServiceException("Error while sending OTP to client emnail.");
                }
                throw new InsufficientAuthenticationException("OTP");
            }
        }

        System.out.println("attemptAuthentication");
        return super.attemptAuthentication(request, response);
    }

    private float getGoogleRecaptchaScore() {
        return 0.43f;
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    //Xử lí nếu quá trình xác thực thành công
    @Autowired
    @Qualifier("loginSuccessHandler")
    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }

    // Xử lý nếu quá trình xác thực lỗi
    @Autowired
    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }
}
