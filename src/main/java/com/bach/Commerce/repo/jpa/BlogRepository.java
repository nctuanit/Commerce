package com.bach.Commerce.repo.jpa;

import com.bach.Commerce.entity.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query("SELECT b FROM Blog b JOIN b.tags t WHERE b.title LIKE %?1% AND b.categoriesForBlog.type LIKE %?2% AND b.created_month LIKE %?3% AND t.type LIKE %?4%")
    public List<Blog> findAll(String keyword, String categoryForBlog, String month, String tag, Pageable pageable);

}
