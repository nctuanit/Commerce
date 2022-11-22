package com.bach.Commerce.security.oauth;

import com.bach.Commerce.common.enums.AuthProvider;
import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@AllArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    final UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getEmail();

        System.out.println("User's Email : " + email);

        UserDTO userDTO = new UserDTO();

        String name = oAuth2User.getFullName();

        try {
            userDTO = userService.getUserByMail(email);

        } catch (Exception e) {
            // TODO: handle exception
        }

        System.out.println("Ton tai : " + userDTO);

        if (userDTO == null) {

            userService.registerNewUserAfterOAuthLoginSuccess(email, name, AuthProvider.FACEBOOKGOOGLE);

        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
