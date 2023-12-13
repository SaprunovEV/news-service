package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.web.v1.annotations.FindAllUsersDock;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "users V1", description = "User API version V1")
public class UserController {

    @GetMapping
    @FindAllUsersDock
    public ResponseEntity<?> handleFindAll(@Valid UserFilter filter) {
        return ResponseEntity.ok("");
    }
}
