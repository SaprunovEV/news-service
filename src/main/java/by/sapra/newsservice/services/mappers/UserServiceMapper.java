package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.storages.models.StorageUserList;

public interface UserServiceMapper {
    UserListModel storageUserListToUserListModel(StorageUserList storageUserList);
}
