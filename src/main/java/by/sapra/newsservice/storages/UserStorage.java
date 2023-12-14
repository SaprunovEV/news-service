package by.sapra.newsservice.storages;

import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.models.StorageUserList;

public interface UserStorage {
    StorageUserList findAll(UserFilter filter);
}
