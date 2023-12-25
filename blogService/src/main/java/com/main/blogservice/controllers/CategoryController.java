package com.main.blogservice.controllers;

import com.main.blogservice.domain.Category;
import com.main.blogservice.exceptions.CategoryException;
import com.main.blogservice.model.CategoryDto;
import com.main.blogservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategoryHandler(@RequestBody String category) throws CategoryException {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(category));
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategoryHandler(@PathVariable(name = "categoryId") Integer categoryId, @RequestBody String categoryName) throws CategoryException{
        categoryService.updateCategory(categoryId, categoryName);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteCategoryHandler(@PathVariable(name = "categoryId") Integer categoryId) throws CategoryException{
        categoryService.deleteCategory(categoryId);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAllCategoriesHandler(){
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @PutMapping("/{categoryId}/{order}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategoryOrder(@PathVariable(name = "categoryId") Integer categoryId, @PathVariable("order") Integer orderId) throws CategoryException{
        categoryService.updateCategoryOrder(categoryId, orderId);
    }
}