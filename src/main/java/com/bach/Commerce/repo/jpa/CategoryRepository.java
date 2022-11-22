package com.bach.Commerce.repo.jpa;

import com.bach.Commerce.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {

}
