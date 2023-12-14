package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.UserService;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.web.v1.annotations.FindAllUsersDock;
import by.sapra.newsservice.web.v1.mappers.UserResponseMapper;
import by.sapra.newsservice.web.v1.models.UserId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "users V1", description = "User API version V1")
public class UserController {
    private final UserService service;
    private final UserResponseMapper mapper;
    @GetMapping
    @FindAllUsersDock
    public ResponseEntity<?> handleFindAll(@Valid UserFilter filter) {
        return ResponseEntity.ok(mapper.userListModelToUserListResponse(service.findAllUsers(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> handleFindById(@Valid UserId id) {
        return ResponseEntity.ok(
                mapper.serviceUserItemToUserItemResponse(service.findUserById(id.getId()))
        );
    }
}
