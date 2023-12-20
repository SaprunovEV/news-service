package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface UserServiceMapper {
    UserListModel storageUserListToUserListModel(StorageUserList storageUserList);

    UserItemModel storageUserItemToUserItemModel(StorageUserItem storageUser);

    StorageUserItem userItemModelToStorageUserItem(UserItemModel expected);
}
