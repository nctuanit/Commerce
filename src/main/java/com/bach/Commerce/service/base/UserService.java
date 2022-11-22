package com.bach.Commerce.service.base;

import com.bach.Commerce.common.enums.AuthProvider;
import com.bach.Commerce.entity.User;
import com.bach.Commerce.model.dto.CartItemDTO;
import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.model.models.UserOder;
import org.springframework.security.core.Authentication;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface UserService {

    public UserDTO getUserById(int id);

    public List<UserDTO> getAllUser();

    public User addUser(UserDTO userDTO);

    public UserDTO getUserByMail(String mail);

    public void registerNewUserAfterOAuthLoginSuccess(String email, String name, AuthProvider facebook);

    public User getCurrentlyLoggedInUser(Authentication authentication);

    public User editUser(UserDTO userDTO);

    public void deleteUser(int id);
    public void sendEmailOrder(Map<Integer, CartItemDTO> cartItemDTOMap, UserOder user) throws UnsupportedEncodingException, MessagingException;
    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;
}
