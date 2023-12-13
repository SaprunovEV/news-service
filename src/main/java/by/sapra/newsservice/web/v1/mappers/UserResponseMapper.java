package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.web.v1.models.UserListResponse;

public interface UserResponseMapper {
    UserListResponse userListModelToUserListResponse(UserListModel usersListModel);
}
