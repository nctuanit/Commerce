package com.bach.Commerce.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class ApiCarItemController {
    @GetMapping("/")
    public String getCart(HttpSession session) {
        return session.getId();
    }
}
