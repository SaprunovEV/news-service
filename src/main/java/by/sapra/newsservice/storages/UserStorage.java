package by.sapra.newsservice.storages;

import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;

import java.util.Optional;

public interface UserStorage {
    StorageUserList findAll(UserFilter filter);

    Optional<StorageUserItem> findById(long id);

    Optional<StorageUserItem> createNewUser(StorageUserItem mapperResponse);

    Optional<StorageUserItem> updateUser(StorageUserItem mapperResponse);
}
