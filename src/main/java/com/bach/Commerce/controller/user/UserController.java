package com.bach.Commerce.controller.user;

import com.bach.Commerce.entity.User;
import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.repo.jpa.CategoryRepository;
import com.bach.Commerce.repo.jpa.UserRepository;
import com.bach.Commerce.service.LoginService;
import com.bach.Commerce.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Controller
@AllArgsConstructor
public class UserController {

    final UserRepository userRepo;

    final UserService userService;

    final CategoryRepository categoryRepository;
    @GetMapping("/member/user")
    public String memberUser(Model model, int id) {

        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }

        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("cate", categoryRepository.findAll());
        return "/informationUser";
    }
    @PostMapping("/member/user/edit")
    public String editInfomationMember(@ModelAttribute(name = "user") UserDTO userDTO, @RequestParam(name = "avatarImage") MultipartFile file) throws IOException {

        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());

        userDTO.setAvatar(fileName);

        User user = userService.editUser(userDTO);

        String uploadDir = "./avatar-images/" + user.getId();

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePathMain = uploadPath.resolve(fileName);
            System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());
            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save upload file : " + fileName);
        }

        return "redirect:/trang-chu";
    }

}
