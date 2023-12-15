package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.models.errors.UserError;
import by.sapra.newsservice.services.UserService;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.web.v1.annotations.CreateUserDock;
import by.sapra.newsservice.web.v1.annotations.FindAllUsersDock;
import by.sapra.newsservice.web.v1.annotations.FindUserByIdDock;
import by.sapra.newsservice.web.v1.mappers.UserResponseMapper;
import by.sapra.newsservice.web.v1.models.UpsertUserRequest;
import by.sapra.newsservice.web.v1.models.UserId;
import by.sapra.newsservice.web.v1.models.UserItemResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    @FindUserByIdDock
    public ResponseEntity<?> handleFindById(@Valid UserId id) {
        ApplicationModel<UserItemModel, UserError> serviceResponse = service.findUserById(id.getId());
        if (serviceResponse.hasError())
            return ResponseEntity.status(NOT_FOUND).body(serviceResponse.getError());
        return ResponseEntity.ok(
                mapper.serviceUserItemToUserItemResponse(serviceResponse.getData())
        );
    }

    @PostMapping
    @CreateUserDock
    public ResponseEntity<?> handleCreationUser(@RequestBody @Valid UpsertUserRequest request) {
        UserItemModel user2save = mapper.requestToUserItemModel(request);
        ApplicationModel<UserItemModel, UserError> model = service.createUser(user2save);
        if (model.hasError()) {
            return ResponseEntity.badRequest().body(model.getError());
        }
        UserItemResponse response = mapper.serviceUserItemToUserItemResponse(model.getData());
        return ResponseEntity.status(CREATED).body(response);
    }
}
