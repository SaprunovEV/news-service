package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.services.CategoryService;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.services.models.CategoryWithNews;
import by.sapra.newsservice.web.v1.annotations.*;
import by.sapra.newsservice.web.v1.mappers.CategoryMapper;
import by.sapra.newsservice.web.v1.models.CategoryId;
import by.sapra.newsservice.web.v1.models.UpsertCategoryRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category V1", description = "Category API version V1")
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping
    @FindAllCAtegoryDock
    public ResponseEntity<?> handleFindAll(@Valid CategoryFilter filter) {
        return ResponseEntity.ok(mapper.categoryItemListToCategoryListResponse(service.findAll(filter)));
    }

    @GetMapping("/{id}")
    @FindCategoryByIdDock
    public ResponseEntity<?> handleFindById(@Valid CategoryId id) {
        ApplicationModel<CategoryWithNews, CategoryError> model = service.findById(id.getId());

        if (model.hasError()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model.getError());
        }
        return ResponseEntity.ok(mapper.categoryToCategoryResponse(model.getData()));
    }

    @PostMapping
    @CreateNewCategoryDock
    public ResponseEntity<?> handleSaveCategory(@RequestBody @Valid UpsertCategoryRequest request) {
        ApplicationModel<CategoryWithNews, CategoryError> model =
                service.saveCategory(mapper.requestToCategoryWithNews(request));

        if (model.hasError())
            return ResponseEntity.badRequest().body(model.getError());

        return ResponseEntity.status(CREATED).body(mapper.categoryToCategoryResponse(model.getData()));
    }

    @PutMapping("/{id}")
    @UpdateCategoryDock
    public ResponseEntity<?> handleUpdateCategory(@Valid CategoryId id, @RequestBody @Valid UpsertCategoryRequest request) {
        ApplicationModel<CategoryWithNews, CategoryError> model =
                service.updateCategory(mapper.requestWithIdToCategoryWithNews(request, id.getId()));

        if (model.hasError()) {
            return ResponseEntity.badRequest().body(model.getError());
        }
        return ResponseEntity.ok(mapper.categoryToCategoryResponse(model.getData()));
    }

    @DeleteMapping("/{id}")
    @DeleteCategoryDock
    public ResponseEntity<Void> handleDeleteCategory(@Valid CategoryId id) {
        service.deleteCategory(id.getId());
        return ResponseEntity.noContent().build();
    }
}
