package com.bach.Commerce.controller.admin;

import com.bach.Commerce.entity.Categories;
import com.bach.Commerce.model.dto.CategoriesDTO;
import com.bach.Commerce.model.dto.CategoriesForBlogDTO;
import com.bach.Commerce.repo.jpa.CategoryRepository;
import com.bach.Commerce.service.base.CategoryForBlogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AdminCategoryController {


    final  CategoryRepository categoryRepository;


    final  CategoryForBlogService categoriesForBlogService;

    @GetMapping("/admin/categories")
    public String listCategories(Model model) {

        model.addAttribute("categories", categoryRepository.findAll());

        model.addAttribute("categoriesForBlog", categoriesForBlogService.getAllCategoryForBlog());

        return "/admin/viewCategory";
    }

    @GetMapping("/admin/categories/new")
    public String addCategories(Model model) {

        model.addAttribute("category", new CategoriesDTO());

        return "/admin/addCategories";
    }

    @PostMapping("/admin/categories/save")
    public String addCategoriesNew(@ModelAttribute("category") Categories categories) {

        categoryRepository.save(categories);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {

        categoryRepository.delete(categoryRepository.getById(id));

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/edit/{id}")
    public String editCateGet(Model model, @PathVariable(name = "id") int id) {

        Categories categories = categoryRepository.getById(id);

        model.addAttribute("categories", categories);

        return "/admin/editCategories";
    }

    @PostMapping("/admin/categories/edit")
    public String editCatePost(@ModelAttribute(name = "categories") Categories categories) {

        categoryRepository.save(categories);

        return "redirect:/admin/categories";
    }

    //==============================================================================//

    @GetMapping("/admin/categoriesforblog/new")
    public String addCategoriesForBlog(Model model) {

        model.addAttribute("category", new CategoriesForBlogDTO());

        return "/admin/addCategoriesForBlog";
    }

    @PostMapping("/admin/categoriesforblog/save")
    public String addCategoriesForBlogNew(@ModelAttribute("category") CategoriesForBlogDTO categoriesForBlogDTO) {

        categoriesForBlogService.add(categoriesForBlogDTO);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categoriesforblog/delete/{id}")
    public String deleteCategoriesForBlog(@PathVariable(name = "id") int id) {

        categoriesForBlogService.delete(id);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categoriesforblog/edit")
    public String editCategoriesForBlog(Model model, int id) {

        model.addAttribute("categoriesForBlogDTO", categoriesForBlogService.getOne(id));

        return "/admin/editCategoriesForBlog";
    }

    @PostMapping("/admin/categoriesforblog/edit")
    public String editCategoriesForBlogSave(@ModelAttribute("categoriesForBlogDTO") CategoriesForBlogDTO categoriesForBlogDTO) {

        categoriesForBlogService.edit(categoriesForBlogDTO);

        return "redirect:/admin/categories";
    }

}
