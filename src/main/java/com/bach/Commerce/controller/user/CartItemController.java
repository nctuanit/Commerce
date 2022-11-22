package com.bach.Commerce.controller.user;

import com.bach.Commerce.model.dto.CartItemDTO;
import com.bach.Commerce.model.dto.CouponDTO;
import com.bach.Commerce.model.dto.ProductDTO;
import com.bach.Commerce.model.models.UserOder;
import com.bach.Commerce.repo.jpa.CategoryRepository;
import com.bach.Commerce.service.LoginService;
import com.bach.Commerce.service.base.CouponService;
import com.bach.Commerce.service.base.ProductService;
import com.bach.Commerce.service.base.UserService;
import com.bach.Commerce.utils.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Controller
@AllArgsConstructor
public class CartItemController {

    final ProductService productService;
    final CouponService CouponService;
    final UserService userService;

    final CategoryRepository categoryRepository;
    @GetMapping("/cart")
    public String getAllCartItem(Model model, @AuthenticationPrincipal Authentication authentication, HttpSession session) {

        Object object = session.getAttribute("cart");

        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }

        return "shoping-cart";
    }

    @PostMapping("/add-to-cart")
    public String AddToCart(@RequestParam(name = "id") int id, HttpSession session, HttpServletRequest request, Model model,
                            @RequestParam(name = "num-product") int numproduct) throws IOException {

        ProductDTO product = productService.getProductById(id); // lay thong tin san pham
        Object object = session.getAttribute("cart"); //lay session neu co , neu chua co tao 1 session moi la cart
        int totalOfCart = 0;
        double totalPrice = 0;
        double totalPriceAfterApplyCoupon = 0;

        if (object == null) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setProduct(product);
            cartItemDTO.setQuantity(numproduct);
            Map<Integer, CartItemDTO> map = new HashMap<>();
            map.put(id, cartItemDTO);
            session.setAttribute("cart", map);

            totalOfCart += numproduct;
            totalPrice = (numproduct * map.get(id).getProduct().getPrice());
            totalPriceAfterApplyCoupon = totalPrice;


        } else {
            Map<Integer, CartItemDTO> map = (Map<Integer, CartItemDTO>) object;// lay ra map
            CartItemDTO cartItemDTO = map.get(id);

            if (cartItemDTO == null) {  //neu chua co sp trong map thi lay tt sp va sl sp =1
                cartItemDTO = new CartItemDTO();
                cartItemDTO.setProduct(product);
                cartItemDTO.setQuantity(numproduct);
                map.put(id, cartItemDTO);

                Set<Integer> set = map.keySet();
                for (Integer key : set) {

                    totalOfCart += map.get(key).getQuantity();
                    totalPrice += map.get(key).getProduct().getPrice() * map.get(key).getQuantity();
                    totalPriceAfterApplyCoupon = totalPrice;

                }


            } else { // neu co sp trong map roi thi tang sl cua sp len
                cartItemDTO.setQuantity(cartItemDTO.getQuantity() + numproduct);

                Set<Integer> set = map.keySet();
                for (Integer key : set) {

                    totalOfCart += map.get(key).getQuantity();
                    totalPrice += map.get(key).getProduct().getPrice() * map.get(key).getQuantity();
                    totalPriceAfterApplyCoupon = totalPrice;

                }
            }
        }

        session.setAttribute("totalPriceAfterApplyCoupon", totalPriceAfterApplyCoupon);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("totalOfCart", totalOfCart);
        model.addAttribute("cate", categoryRepository.findAll());
        return "redirect:/product-detail?id=" + id;
    }

    @PostMapping("/update-cart")
    public String updateCart(@RequestParam(name = "coupon") String coupon, Model model, HttpServletRequest req) {

        HttpSession session = req.getSession();
        double totalPrice = (double) session.getValue("totalPrice");

        CouponDTO couponDTO = CouponService.findCouponByCode(coupon);

        if (couponDTO != null) {
            session.setAttribute("totalPriceAfterApplyCoupon", totalPrice - (totalPrice * (couponDTO.getDiscount() / 100)));
        } else {
            session.setAttribute("totalPriceAfterApplyCoupon", totalPrice);
        }

        System.out.println(couponDTO.getDiscount());

        return "redirect:/cart";
    }


    @GetMapping(value = "/delete-from-cart")
    public String Deletefromtocart(HttpServletRequest req, @RequestParam(name = "key", required = true) int key) {
        HttpSession session = req.getSession();
        Object object = session.getAttribute("cart");
        int totalOfCart = (int) session.getValue("totalOfCart");
        double totalPrice = (double) session.getValue("totalPrice");
        double totalPriceAfterApplyCoupon = (double) session.getValue("totalPriceAfterApplyCoupon");
        if (object != null) {
            Map<Integer, CartItemDTO> map = (Map<Integer, CartItemDTO>) object;
            session.setAttribute("totalOfCart", totalOfCart - map.get(key).getQuantity());
            session.setAttribute("totalPrice", NumberUtils.toVnd(totalPrice - map.get(key).getQuantity() * map.get(key).getProduct().getPrice()));

            session.setAttribute("totalPriceAfterApplyCoupon", NumberUtils.toVnd(totalPrice - map.get(key).getQuantity() * map.get(key).getProduct().getPrice()));
            map.remove(key);
            session.setAttribute("cart", map);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart")
    public String Order(HttpServletRequest req) throws MessagingException, UnsupportedEncodingException {
        HttpSession session = req.getSession();
        Object object = session.getAttribute("cart");
        var user = UserOder.builder()
                .shipName(req.getParameter("shipname"))
                .address(req.getParameter("address"))
                .phone(req.getParameter("phone"))
                .customerName(req.getParameter("fullname")).build();

        if (object != null) {
            Map<Integer, CartItemDTO> map = (Map<Integer, CartItemDTO>) object;
            userService.sendEmailOrder(map, user);
            session.setAttribute("cart", null);
        }

        return "redirect:/cart";
    }
}

