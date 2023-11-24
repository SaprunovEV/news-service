package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.models.CategoryFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category V1", description = "Category API version V1")
public class CategoryController {
    @GetMapping
    public ResponseEntity<?> handleFindAll(@Valid CategoryFilter filter) {
        return null;
    }
}
