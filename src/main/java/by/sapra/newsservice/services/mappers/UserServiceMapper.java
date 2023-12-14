package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.storages.models.StorageUserList;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface UserServiceMapper {
    UserListModel storageUserListToUserListModel(StorageUserList storageUserList);
}
