package com.bach.Commerce.service.impl;

import com.bach.Commerce.common.enums.AuthProvider;
import com.bach.Commerce.entity.User;
import com.bach.Commerce.model.dto.CartItemDTO;
import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.model.models.UserOder;
import com.bach.Commerce.repo.dao.UserDAO;
import com.bach.Commerce.repo.jpa.UserRepository;
import com.bach.Commerce.security.oauth.CustomOAuth2User;
import com.bach.Commerce.service.LoginService;
import com.bach.Commerce.service.base.UserService;
import com.bach.Commerce.utils.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.var;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final UserDAO userDAO;

    final UserRepository userRepo;

    final JavaMailSender javaMailSender;

    @Override
    public User addUser(UserDTO userDTO) {

        User user = new User();

        user.setId(userDTO.getId());

        user.setName(userDTO.getName());
        //Lấy password người dùng nhập, mã hóa về dạng BCrypt r lưu database
        BCryptPasswordEncoder endcoder = new BCryptPasswordEncoder();
        String rawPassword = userDTO.getPassword();
        String endcodedPassword = endcoder.encode(rawPassword);

        System.out.println(endcodedPassword);
        user.setPassword(endcodedPassword);
        user.setUsername(userDTO.getUsername());
        user.setCountry_id(0);
        user.setAvatar(userDTO.getAvatar());

        user.setCreated_time(new Date());

        user.setRole("ROLE_USER");
        user.setEnabled(true);

        userDAO.addUser(user);

        return user;

    }

    @Override
    public User editUser(UserDTO userDTO) {

        User user = userDAO.getUserById(userDTO.getId());

        System.out.println(user);

        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setState(userDTO.getState());
        user.setAddress(userDTO.getAddress());
        user.setCity(userDTO.getState());
        user.setCountry_id(userDTO.getCountry_id());
        user.setPhone(userDTO.getPhone());
        user.setAvatar(userDTO.getAvatar());

        System.out.println("In ra cho vui : " + userDTO.getAvatar());

        user.setPostal_code(userDTO.getPostal_code());

        userDAO.editUser(user);

        return user;
    }

    @Override
    public UserDTO getUserByMail(String mail) {

        User user = userDAO.getUserByMail(mail);

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        ;
        userDTO.setName(user.getName());
        userDTO.setPhone(user.getPhone());
        userDTO.setCity(user.getCity());
        userDTO.setAddress(user.getAddress());
        userDTO.setState(user.getState());
        userDTO.setCountry_id(user.getCountry_id());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setOneTimePassword(user.getOneTimePassword());
        userDTO.setOtpRequestedTime(user.getOtpRequestedTime());

        return userDTO;
    }

    @Override
    public void registerNewUserAfterOAuthLoginSuccess(String email, String name, AuthProvider authProvider) {

        User user = new User();

        user.setUsername(email);
        user.setName(name);
        user.setAuthProvider(authProvider);
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setCountry_id(0);
        userDAO.addUser(user);

    }


    @Override
    public User getCurrentlyLoggedInUser(Authentication authentication) {

        if (authentication == null) return null;

        User user = null;
        Object principal = authentication.getPrincipal();

        if (principal instanceof LoginService) {
            user = ((LoginService) principal).getUser();

        } else if (principal instanceof CustomOAuth2User) {
            String email = ((CustomOAuth2User) principal).getEmail();
            user = userRepo.getUserByUsername(email);
        }

        return user;
    }

    @Override
    public void deleteUser(int id) {
        User user = userDAO.getUserById(id);

        if (user != null) {
            userDAO.deleteUser(user);
        }

    }

    @Override
    public void sendEmailOrder(Map<Integer, CartItemDTO> cartItemDTOMap, UserOder userOder) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("chautuans77s@gmail.com", "Commerce Support");
        String subject = "ĐƠN hàng Mới từ Commerce";
        helper.setTo("chautuan190@gmail.com");
        String content ="";
        Long total = 0L;
        for (CartItemDTO cartItemDTO : cartItemDTOMap.values()) {
            total += cartItemDTO.getQuantity() * cartItemDTO.getProduct().getPrice();
            content += cartItemDTO.getProduct().getName() + " - Số lượng: " + cartItemDTO.getQuantity() + " - Giá: " + NumberUtils.toVnd(cartItemDTO.getProduct().getPrice()) + "<br>";
        }
        content += "Tổng tiền: " + NumberUtils.toVnd(total) + "<br>";
        content += "Tên khách hàng: " + userOder.getCustomerName() + "<br>";
        content += "Số điện thoại: " + userOder.getPhone() + "<br>";
        content += "Địa chỉ: " + userOder.getAddress() + "<br>";
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    @Override
    public void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException {

        User user = userDAO.getUserById(userDTO.getId());

        String OTP = RandomString.make(8);
        System.out.println("OTP: " + OTP);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String endcodeOTP = passwordEncoder.encode(OTP);

        user.setOneTimePassword(endcodeOTP);
        user.setOtpRequestedTime(new Date());

        userDAO.editUser(user);

        sendOTPEmail(user, OTP);

        System.out.println("Email was sent.");
    }

    private void sendOTPEmail(User user, String OTP) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("chautuans77s@gmail.com", "Commerce Support");
        helper.setTo(user.getUsername());

        String subject = "Đây là mã One-Time Password (OTP) của bạn - Hết hiệu lực trong 5 phút.";
        String content = "<p>Xin chào " + user.getName() + ",</p>"
                + "<p>Vì lí do bảo mật, chúng tôi yêu cầu bạn sử dụng mã OTP bên dưới để đăng nhập: </p>"
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p> Chú ý : Mã OTP này chỉ có hiệu lực trong 5 phút. </p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    @Override
    public UserDTO getUserById(int id) {
        User user = userDAO.getUserById(id);
        System.out.println(user);

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        //userDTO.setRole(user.getRole());
        userDTO.setName(user.getName());
        userDTO.setPhone(user.getPhone());
        userDTO.setCity(user.getCity());
        userDTO.setPostal_code(user.getPostal_code());
        userDTO.setAddress(user.getAddress());
        userDTO.setState(user.getState());
        userDTO.setCountry_id(user.getCountry_id());
        userDTO.setAvatar(user.getAvatar());

        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepo.findAll();

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());
            userDTO.setPassword(user.getPassword());
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());
            userDTO.setName(user.getName());
            userDTO.setPhone(user.getPhone());
            userDTO.setCity(user.getCity());
            userDTO.setAddress(user.getAddress());
            userDTO.setState(user.getState());
            userDTO.setCountry_id(user.getCountry_id());
            userDTO.setAvatar(user.getAvatar());

            userDTOs.add(userDTO);
        }

        return userDTOs;


    }
}
