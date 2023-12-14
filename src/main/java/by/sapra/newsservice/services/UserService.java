package by.sapra.newsservice.services;

import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;

public interface UserService {
    UserListModel findAllUsers(UserFilter filter);

    UserItemModel findUserById(long id);
}
