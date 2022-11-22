package com.bach.Commerce.controller.user;

import com.bach.Commerce.model.dto.CategoriesDTO;
import com.bach.Commerce.model.dto.ProductDTO;
import com.bach.Commerce.model.dto.ReviewDTO;
import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.repo.dao.ReviewDAO;
import com.bach.Commerce.repo.jpa.CategoryRepository;
import com.bach.Commerce.repo.jpa.ProductRepository;
import com.bach.Commerce.repo.jpa.ReviewRepository;
import com.bach.Commerce.service.LoginService;
import com.bach.Commerce.service.base.CategoryService;
import com.bach.Commerce.service.base.ProductService;
import com.bach.Commerce.service.base.ReviewService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {


    final ProductService productService;


    final ProductRepository productRepository;


    final CategoryRepository categoryRepository;


    final CategoryService categoryService;


    final ReviewService reviewService;


    final UserService userService;


    final ReviewDAO reviewDao;


    final ReviewRepository reviewRepo;

    @GetMapping(value = "/trang-chu")
    public String getAllProduct(Model model, HttpServletRequest request, @ModelAttribute("categories") CategoriesDTO category, HttpSession session) {

        Object object = session.getAttribute("cart");// Tạo ngay lập tức một session 'cart' ngay cả khi khách hàng chưa thêm giỏ hàng để tránh bị null
        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }
        System.out.println( productRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("cate", categoryRepository.findAll());

        return "index";
    }
    @GetMapping(value = "/")
    public String getAllProduct2(Model model, HttpServletRequest request, @ModelAttribute("categories") CategoriesDTO category, HttpSession session) {

        Object object = session.getAttribute("cart");// Tạo ngay lập tức một session 'cart' ngay cả khi khách hàng chưa thêm giỏ hàng để tránh bị null
        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }
        System.out.println( productRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("cate", categoryRepository.findAll());

        return "index";
    }
    @GetMapping("/product-detail")
    public String getProductById(Model model, @RequestParam(name = "id", required = false) int id,
                                 HttpSession session) {

        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }

        long numberOfReview = reviewDao.countById(id);

        model.addAttribute("numberOfReview", numberOfReview);

        System.out.println(numberOfReview);

        model.addAttribute("reviews", reviewService.find(id));

        model.addAttribute("product", productRepository.getById(id));
        model.addAttribute("cate", categoryRepository.findAll());
        return "product-detail";
    }

    @PostMapping(value = "/member/product-detail/review")
    public String review(HttpServletRequest request, @ModelAttribute ReviewDTO reviewDTO, @RequestParam(name = "productId", required = false) Integer productId,
                         @RequestParam(name = "starNumber") int starNumber, @RequestParam(name = "review") String review) {

        LoginService loginService = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int check = 0;
        List<ReviewDTO> list = reviewService.find(productId);

        if (list.isEmpty()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(loginService.getName());
            userDTO.setId(loginService.getId());
            reviewDTO.setUserDTO(userDTO);
            reviewDTO.setReview(review);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(productId);
            reviewDTO.setProductDTO(productDTO);
            reviewDTO.setStarNumber(starNumber);
            reviewService.add(reviewDTO);
        }
        for (ReviewDTO reviewDTO2 : list) {// kiem tra de moi nguoi dung chi comment dc  1 laan
            if (reviewDTO2.getUserDTO().getId() == loginService.getId()) {
                check = 1;
                break;

            } else {
                check = 2;
            }
        }
        if (check == 2) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(loginService.getName());
            userDTO.setId(loginService.getId());
            reviewDTO.setUserDTO(userDTO);
            reviewDTO.setReview(review);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(productId);
            reviewDTO.setProductDTO(productDTO);
            reviewDTO.setStarNumber(starNumber);
            reviewService.add(reviewDTO);
        }


        return "redirect:/product-detail?id=" + productId;
    }




    @GetMapping(value = "/product")
    public String getAllProductForProductPage(Model model, HttpServletRequest request, HttpSession session) {

        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }

        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

        Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

        Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
                : Long.valueOf(request.getParameter("priceStart"));

        Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
                : Long.valueOf(request.getParameter("priceEnd"));

        //List<ProductDTO> listProducts = productService.getProductForProductPage(keyword ,priceStart, priceEnd, 0, page * 8);

        String lowtohigh = request.getParameter("lowtohigh");
        model.addAttribute("cate", categoryRepository.findAll());
        if (lowtohigh != null && lowtohigh != "") {
            model.addAttribute("products", productService.getProductForProductPagePriceHigh(lowtohigh));
        } else {
            model.addAttribute("products", productService.getAll(0,99));
        }

        request.setAttribute("page", page);
        request.setAttribute("priceStart", priceStart);
        request.setAttribute("priceEnd", priceEnd);
        request.setAttribute("keyword", keyword);

        model.addAttribute("cate", categoryService.getAll());

        return "product";
    }
}
