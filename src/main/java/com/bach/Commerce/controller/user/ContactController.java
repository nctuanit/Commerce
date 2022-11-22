package com.bach.Commerce.controller.user;

import com.bach.Commerce.repo.jpa.CategoryRepository;
import com.bach.Commerce.service.LoginService;
import com.bach.Commerce.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ContactController {


    final UserService userService;

    final CategoryRepository categoryRepository;
    @GetMapping("/contact")
    public String contact(Model model) {

        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
            model.addAttribute("cate", categoryRepository.findAll());
        } catch (Exception e) {
            e.getStackTrace();
        }

        return "contact";
    }
}
