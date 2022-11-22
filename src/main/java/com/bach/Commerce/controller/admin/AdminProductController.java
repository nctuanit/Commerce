package com.bach.Commerce.controller.admin;

import com.bach.Commerce.entity.Categories;
import com.bach.Commerce.entity.Product;
import com.bach.Commerce.model.dto.ProductDTO;
import com.bach.Commerce.repo.jpa.CategoryRepository;
import com.bach.Commerce.repo.jpa.ProductRepository;
import com.bach.Commerce.service.base.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminProductController {


    final ProductService productService;


    final ProductRepository productRepository;


    final CategoryRepository categoryRepository;

    @GetMapping("/admin/product")
    public String viewProduct(Model model, @RequestParam(name = "page", required = false) Integer page,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name = "keyword", required = false) String keyword,
                              @RequestParam(name = "category", required = false) String category) {

        if (keyword == null) keyword = "";

        if (category == null) category = "";

        if (page == null) page = 0;

        if (size == null) size = 5;

        long count = productService.count();

        long pageTotal = (long) Math.ceil((double) count / size);

        List<ProductDTO> products = productService.search(keyword, category, page, size);

        model.addAttribute("products", products);

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        System.out.println(products);

        List<Categories> listCategories = categoryRepository.findAll();

        model.addAttribute("listCategories", listCategories);

        return "/admin/viewProduct";
    }

    @GetMapping("/admin/product/new")
    public String addProduct(Model model) {

        List<Categories> listCategories = categoryRepository.findAll();

        model.addAttribute("product", new Product());

        model.addAttribute("listCategories", listCategories);

        return "/admin/addProduct";
    }

    @PostMapping("/admin/product/save")
    public String addProductSave(@ModelAttribute("product") Product product, @RequestParam(name = "primaryImage") MultipartFile fileMain,
                                 @RequestParam(name = "img_sub1") MultipartFile[] fileSub) throws IOException {

        String fileMainName = StringUtils.cleanPath(fileMain.getOriginalFilename());

        int count = 0;
        for (MultipartFile sub : fileSub) {
            String fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());
            if (count == 0) product.setImg_hover(fileSubName);
            if (count == 1) product.setImg_sub(fileSubName);

            count++;
        }

        Product savedProduct = productRepository.save(product);

        String uploadDir = "./product-images/" + savedProduct.getId();

        //Lưu file ảnh chính
        Path uploadPathMain = Paths.get(uploadDir);

        if (!Files.exists(uploadPathMain)) {
            Files.createDirectories(uploadPathMain);
        }

        try (InputStream inputStream = fileMain.getInputStream()) {

            Path filePathMain = uploadPathMain.resolve(fileMainName);
            System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());

            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new IOException("Could not save upload file : " + fileMainName);
        }

        //Lưu file ảnh con
        for (MultipartFile sub : fileSub) {

            String fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = sub.getInputStream()) {

                Path filePath = uploadPath.resolve(fileSubName);
                System.out.println("Check : " + filePath.toFile().getAbsolutePath());

                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                throw new IOException("Could not save upload file : " + fileSubName);
            }

        }

        product.setImg_main(fileMainName);

        productRepository.save(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {

        productRepository.deleteById(id);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String editProduct(@PathVariable(name = "id") int id, Model model) {

        model.addAttribute("product", productRepository.getById(id));

        List<Categories> listCategories = categoryRepository.findAll();

        model.addAttribute("listCategories", listCategories);

        return "/admin/editProduct";
    }

    @PostMapping("/admin/product/edit")
    public String saveEditProduct(@RequestParam(name = "id") int id, @ModelAttribute("product") Product product, @RequestParam(name = "primaryImage") MultipartFile fileMain,
                                  @RequestParam(name = "img_sub1") MultipartFile[] fileSub) throws IOException {

        String fileMainName = StringUtils.cleanPath(fileMain.getOriginalFilename());

        if (fileMainName != "") {

            product.setImg_hover(productService.getProductById(id).getImg_hover());
            product.setImg_sub(productService.getProductById(id).getImg_sub());
            product.setImg_main(fileMainName);

            Product savedProduct = productRepository.save(product);

            String uploadDir = "./product-images/" + savedProduct.getId();

            //Lưu file ảnh chính
            Path uploadPathMain = Paths.get(uploadDir);

            if (!Files.exists(uploadPathMain)) {
                Files.createDirectories(uploadPathMain);
            }

            try (InputStream inputStream = fileMain.getInputStream()) {

                Path filePathMain = uploadPathMain.resolve(fileMainName);
                System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());

                Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                throw new IOException("Could not save upload file : " + fileMainName);
            }
        } else {

            /*
             * product.setImg_hover(productService.getProductById(id).getImg_hover());
             * product.setImg_sub(productService.getProductById(id).getImg_sub());
             * product.setImg_main(productService.getProductById(id).getImg_main());
             *
             * System.out.println("Co product tim that" +
             * productService.getProductById(id).getImg_hover());
             *
             * productRepository.save(product);
             */

        }

        String fileSubName = "";

        int count = 0;
        for (MultipartFile sub : fileSub) {
            fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());

            if (fileSubName != "") {
                if (count == 0) product.setImg_hover(fileSubName);
                if (count == 1) product.setImg_sub(fileSubName);
            }
            count++;
        }

        if (fileSubName != "") {

            Product savedProduct = productRepository.save(product);

            String uploadDir = "./product-images/" + savedProduct.getId();

            //Lưu file ảnh con
            for (MultipartFile sub : fileSub) {

                fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());

                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = sub.getInputStream()) {

                    Path filePath = uploadPath.resolve(fileSubName);
                    System.out.println("Check : " + filePath.toFile().getAbsolutePath());

                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    throw new IOException("Could not save upload file : " + fileSubName);
                }

            }

        } else {

            product.setImg_hover(productService.getProductById(id).getImg_hover());
            product.setImg_sub(productService.getProductById(id).getImg_sub());
            product.setImg_main(productService.getProductById(id).getImg_main());

            productRepository.save(product);
        }

        return "redirect:/admin/product";
    }

}
