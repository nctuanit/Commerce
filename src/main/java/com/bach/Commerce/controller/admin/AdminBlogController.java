package com.bach.Commerce.controller.admin;

import com.bach.Commerce.entity.Blog;
import com.bach.Commerce.model.dto.BlogDTO;
import com.bach.Commerce.repo.jpa.BlogRepository;
import com.bach.Commerce.service.base.BlogService;
import com.bach.Commerce.service.base.CategoryForBlogService;
import com.bach.Commerce.service.base.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Controller
@AllArgsConstructor
public class AdminBlogController {

    final BlogService blogService;

    final TagService tagService;

    final BlogRepository blogRepository;

    final CategoryForBlogService categoryForBlogService;

    @GetMapping("/admin/blog")
    public String adminGetBlog(Model model) {

        model.addAttribute("blogs", blogService.getAllBlogs());
        model.addAttribute("categoryForBlog", categoryForBlogService.getAllCategoryForBlog());

        return "/admin/viewBlog";
    }

    @GetMapping("/admin/blog/new")
    public String newBlog(Model model) {

        model.addAttribute("blog", new BlogDTO());

        model.addAttribute("categoryForBlog", categoryForBlogService.getAllCategoryForBlog());

        model.addAttribute("listTags", tagService.getAllTag());

        System.out.println(categoryForBlogService.getAllCategoryForBlog());

        return "/admin/addBlog";
    }

    @PostMapping("/admin/blog/new")
    public String saveNewBlog(@ModelAttribute(name = "blog") BlogDTO blogDTO,
                              @RequestParam(name = "blogImage") MultipartFile file)
            throws IOException {
        //Loi map doi tuong model
        String blogImage = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());

        if (blogImage != "") {
            blogDTO.setImageBlog(blogImage);

            Blog blog = blogService.addNewBlog(blogDTO);

            String uploadDir = "./blog-images/" + blog.getId();

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePathMain = uploadPath.resolve(blogImage);
                System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());

                Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not save upload file : " + blogImage);
            }
        } else {
            blogService.addNewBlog(blogDTO);
        }

        return "redirect:/admin/blog";
    }

    @GetMapping("/admin/blog/delete/{id}")
    public String deleteBlog(@PathVariable(name = "id") int id) throws IOException {

        blogService.deleteBlog(id);

        return "redirect:/admin/blog";
    }

    @GetMapping("/admin/blog/edit")
    public String editBlog(Model model, int id) {

        BlogDTO blogDTO = blogService.getOneBlog(id);

        model.addAttribute("listTags", tagService.getAllTag());
        model.addAttribute("blog", blogDTO);
        model.addAttribute("categoryForBlog", categoryForBlogService.getAllCategoryForBlog());

        return "/admin/editBlog";
    }

    @PostMapping("/admin/blog/edit")
    public String editBlogSave(@ModelAttribute("blog") BlogDTO blogDTO, @RequestParam(value = "blogImage") MultipartFile file,
                               @RequestParam(name = "id") int id) throws IOException {

        String blogImage = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());

        if (blogImage != "") {

            System.out.println("12345");

            blogDTO.setImageBlog(blogImage);

            System.out.println("BlogDTO" + blogDTO.getImageBlog());

            Blog blog = blogService.editBlog(blogDTO);

            String uploadDir = "./blog-images/" + blog.getId();

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePathMain = uploadPath.resolve(blogImage);
                System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());

                Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not save upload file : " + blogImage);
            }
        } else {

            //Điều này sẽ chắc chắn rằng nếu không thêm file ảnh chỉnh sửa
            //Nó sẽ vẫn lấy file ảnh cũ
            blogImage = blogService.getOneBlog(id).getImageBlog();

            blogDTO.setImageBlog(blogImage);

            blogService.editBlog(blogDTO);

        }

        return "redirect:/admin/blog";
    }
}
