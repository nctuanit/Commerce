package com.bach.Commerce.service.base;

import com.bach.Commerce.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    public List<ProductDTO> getAllProduct();

    public ProductDTO getProductById(int id);

    public List<ProductDTO> getProductByCatagories(int categories);

    public List<ProductDTO> getAll(int page, int sizePerPage);

    public List<ProductDTO> search(String keyword, String category, int page, int sizePerPage);

    public long count();

    public List<ProductDTO> getProductForProductPage(String findName, long priceStart, long priceEnd, int start, int length);

    public List<ProductDTO> getProductForProductPagePriceHigh(String sort);
}
