package com.main.blogservice.repositories;

import com.main.blogservice.domain.Category;
import com.main.blogservice.model.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String categoryName);

    boolean existsByOrder(int orderNumber);

    @Query("Select new com.main.blogservice.model.CategoryDto(c.id, c.name , c.order, count(b.id)) FROM  Category c LEFT JOIN  c.blogs" +
            " b GROUP BY c.id, c.name, c.order")
    List<CategoryDto> findAllCategoryDto();
}
