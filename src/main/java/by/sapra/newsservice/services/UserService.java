package by.sapra.newsservice.services;

import by.sapra.newsservice.models.errors.UserError;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;

public interface UserService {
    UserListModel findAllUsers(UserFilter filter);

    ApplicationModel<UserItemModel, UserError> findUserById(long id);

    ApplicationModel<UserItemModel, UserError> createUser(UserItemModel request);
}
