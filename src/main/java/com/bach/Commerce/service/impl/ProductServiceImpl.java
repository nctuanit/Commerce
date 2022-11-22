package com.bach.Commerce.service.impl;

import com.bach.Commerce.entity.Product;
import com.bach.Commerce.model.dto.CategoriesDTO;
import com.bach.Commerce.model.dto.ProductDTO;
import com.bach.Commerce.repo.dao.ProductDAO;
import com.bach.Commerce.repo.jpa.ProductRepository;
import com.bach.Commerce.service.base.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    final ProductDAO productDAO;

    final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> listProduct = productDAO.getAllProduct();

        List<ProductDTO> dtos = new ArrayList<ProductDTO>();

        for (Product p : listProduct) {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setPrice(p.getPrice());
            productDTO.setDescription(p.getDescription());
            productDTO.setImg_main(p.getImg_main());
            productDTO.setImg_hover(p.getImg_hover());
            productDTO.setImg_sub(p.getImg_sub());


            dtos.add(productDTO);
        }

        return dtos;
    }

    @Override
    public ProductDTO getProductById(int id) {
        ProductDTO productDTO = new ProductDTO();

        Product p = productDAO.getProductById(id);

        productDTO.setId(p.getId());
        productDTO.setName(p.getName());
        productDTO.setPrice(p.getPrice());
        productDTO.setDescription(p.getDescription());
        productDTO.setImg_main(p.getImg_main());
        productDTO.setImg_hover(p.getImg_hover());
        productDTO.setImg_sub(p.getImg_sub());

        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductByCatagories(int categories) {

        List<Product> listProduct = productDAO.getProductByCategories(categories);

        List<ProductDTO> dtos = new ArrayList<ProductDTO>();

        for (Product p : listProduct) {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setPrice(p.getPrice());

            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId_cate(p.getCategory().getId());
            categoriesDTO.setType(p.getCategory().getType());
            productDTO.setCategory(categoriesDTO);

            productDTO.setDescription(p.getDescription());
            productDTO.setImg_main(p.getImg_main());
            productDTO.setImg_hover(p.getImg_hover());
            productDTO.setImg_sub(p.getImg_sub());


            dtos.add(productDTO);
        }

        return dtos;
    }

    @Override
    public List<ProductDTO> getAll(int page, int sizePerPage) {

        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

        Pageable pageable = (Pageable) PageRequest.of(page, sizePerPage, Sort.by("id").descending());

        Page<Product> productEntities = productRepository.findAll(pageable);

        for (Product p : productEntities) {
            ProductDTO productDTO = new ProductDTO();

            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setPrice(p.getPrice());

            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId_cate(p.getCategory().getId());
            categoriesDTO.setType(p.getCategory().getType());
            productDTO.setCategory(categoriesDTO);

            productDTO.setDescription(p.getDescription());
            productDTO.setImg_main(p.getImg_main());
            productDTO.setImg_hover(p.getImg_hover());
            productDTO.setImg_sub(p.getImg_sub());

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public List<ProductDTO> search(String keyword, String category, int page, int sizePerPage) {
        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

        Pageable pageable = (Pageable) PageRequest.of(page, sizePerPage, Sort.by("id").descending());

        List<Product> productEntities = productDAO.findAll(keyword, category, pageable);

        for (Product p : productEntities) {
            ProductDTO productDTO = new ProductDTO();

            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setPrice(p.getPrice());

            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId_cate(p.getCategory().getId());
            categoriesDTO.setType(p.getCategory().getType());
            productDTO.setCategory(categoriesDTO);

            productDTO.setDescription(p.getDescription());
            productDTO.setImg_main(p.getImg_main());
            productDTO.setImg_hover(p.getImg_hover());
            productDTO.setImg_sub(p.getImg_sub());

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    @Override
    public List<ProductDTO> getProductForProductPage(String findName, long priceStart, long priceEnd, int start, int length) {

        List<Product> listProducts = productDAO.getProductForProductPage(findName, priceStart, priceEnd, start, length);

        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

        for (Product p : listProducts) {
            ProductDTO productDTO = new ProductDTO();

            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setPrice(p.getPrice());

            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId_cate(p.getCategory().getId());
            categoriesDTO.setType(p.getCategory().getType());
            productDTO.setCategory(categoriesDTO);

            productDTO.setDescription(p.getDescription());
            productDTO.setImg_main(p.getImg_main());
            productDTO.setImg_hover(p.getImg_hover());
            productDTO.setImg_sub(p.getImg_sub());

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    @Override
    public List<ProductDTO> getProductForProductPagePriceHigh(String sort) {

        List<Product> listProducts = productDAO.getProductPriceLowtoHigh(sort);

        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

        for (Product p : listProducts) {
            ProductDTO productDTO = new ProductDTO();

            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setPrice(p.getPrice());

            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId_cate(p.getCategory().getId());
            categoriesDTO.setType(p.getCategory().getType());
            productDTO.setCategory(categoriesDTO);

            productDTO.setDescription(p.getDescription());
            productDTO.setImg_main(p.getImg_main());
            productDTO.setImg_hover(p.getImg_hover());
            productDTO.setImg_sub(p.getImg_sub());

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }
}
