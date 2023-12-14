package by.sapra.newsservice.services.impl;

import by.sapra.newsservice.services.UserService;
import by.sapra.newsservice.services.mappers.UserServiceMapper;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final UserStorage storage;
    private final UserServiceMapper mapper;

    @Override
    public UserListModel findAllUsers(UserFilter filter) {
        return mapper.storageUserListToUserListModel(storage.findAll(filter));
    }

    @Override
    public UserItemModel findUserById(long id) {
        return null;
    }
}
