package com.bach.Commerce.controller.user;

import com.bach.Commerce.model.dto.BlogDTO;
import com.bach.Commerce.model.dto.CommentBlogDTO;
import com.bach.Commerce.repo.dao.CommentBlogDAO;
import com.bach.Commerce.repo.jpa.CategoryRepository;
import com.bach.Commerce.service.LoginService;
import com.bach.Commerce.service.base.*;
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
import java.util.List;

@Controller
@AllArgsConstructor
public class BlogController {


    final BlogService blogService;


    final UserService userService;


    final TagService tagService;


    final CategoryForBlogService categoryForBlogService;


    final CommentBlogService commentBlogService;

    final CommentBlogDAO commentBlogDao;

    final CategoryRepository categoryRepository;
    @GetMapping("/blog")
    public String blog(Model model, HttpServletRequest request) {

        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }

        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

        Integer page = request.getParameter("page") == null ? 0 : Integer.valueOf(request.getParameter("page"));

        Integer size = request.getParameter("size") == null ? 4 : Integer.valueOf(request.getParameter("size"));

        String category = request.getParameter("category") == null ? "" : request.getParameter("category");

        String month = request.getParameter("month") == null ? "" : request.getParameter("month");

        String tag = request.getParameter("tag") == null ? "" : request.getParameter("tag");

        long count = blogService.count();

        long pageTotal = (long) Math.ceil((double) count / size);

        List<BlogDTO> blogs = blogService.getBlogForBlogPage(keyword, category, month, tag, page, size);
        model.addAttribute("blog", blogs);

        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("categoryForBlog", categoryForBlogService.getAllCategoryForBlog());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("month", month);
        model.addAttribute("tag", tag);
        model.addAttribute("totalBlog", count);
        model.addAttribute("cate", categoryRepository.findAll());
        return "blog";
    }

    @GetMapping("/blog-detail")
    public String blogDetail(@RequestParam(name = "id") int id, Model model, HttpServletRequest request) {

        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.getUserById(principal.getId()));
        } catch (Exception e) {
            e.getStackTrace();
        }

        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

        String category = request.getParameter("category") == null ? "" : request.getParameter("category");

        String month = request.getParameter("month") == null ? "" : request.getParameter("month");

        String tag = request.getParameter("tag") == null ? "" : request.getParameter("tag");

        long count = blogService.count();
        model.addAttribute("blog", blogService.getOneBlog(id));

        model.addAttribute("totalBlog", count);
        model.addAttribute("categoryForBlog", categoryForBlogService.getAllCategoryForBlog());
        model.addAttribute("category", category);
        model.addAttribute("month", month);
        model.addAttribute("keyword", keyword);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("comments", commentBlogService.getComment(id));
        model.addAttribute("commentTotal", commentBlogDao.countById(id));
        model.addAttribute("cate", categoryRepository.findAll());
        return "blog-detail";
    }

    @PostMapping("/blog-detail/review")
    public String commentBlog(Model model, @ModelAttribute CommentBlogDTO commentBlogDTO, @RequestParam(name = "blogId", required = false) int blogId,
                              @RequestParam("name") String name,
                              @RequestParam("mail") String mail,
                              @RequestParam("comment") String comment) {

        //int check = 0;
        List<CommentBlogDTO> list = commentBlogService.getComment(blogId);

        try {
            LoginService loginService = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (loginService != null) {
                commentBlogDTO.setName(loginService.getName());
                commentBlogDTO.setEmail(loginService.getUsername());
                commentBlogDTO.setComment(comment);
                BlogDTO blogDTO = new BlogDTO();
                blogDTO.setId(blogId);
                commentBlogDTO.setBlogDTO(blogDTO);
                commentBlogService.add(commentBlogDTO);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }

        commentBlogDTO.setName(name);
        commentBlogDTO.setEmail(mail);
        commentBlogDTO.setComment(comment);
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(blogId);
        commentBlogDTO.setBlogDTO(blogDTO);
        commentBlogService.add(commentBlogDTO);

        return "redirect:/blog-detail?id=" + blogId;
    }
}
