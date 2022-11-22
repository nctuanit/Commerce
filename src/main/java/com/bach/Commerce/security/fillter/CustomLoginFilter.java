package com.bach.Commerce.security.fillter;

import com.bach.Commerce.common.exception.InvalidReCaptchaTokenException;
import com.bach.Commerce.security.captcha.RecaptchaV3Handler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    public CustomLoginFilter(String loginURL, String httpMethod) {

        //Sử dụng để chặn một yêu cầu khớp với đường dẫn #loginURL và có phương thức là #httpMethod
        setUsernameParameter("username");
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginURL, httpMethod));
    }

    //Phương thức được kiểm tra trước khi xác thực bởi spring authentication
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String recaptchaFormResponse = request.getParameter("g-recaptcha-response");

        System.out.println("Before Processing Authentication");

        RecaptchaV3Handler handler = new RecaptchaV3Handler();

        try {
            float score = handler.verify(recaptchaFormResponse);

            if (score < 0.5) {
                request.getRequestDispatcher("otp-login").forward(request, response);
            }

        } catch (InvalidReCaptchaTokenException | ServletException | IOException e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }
}
