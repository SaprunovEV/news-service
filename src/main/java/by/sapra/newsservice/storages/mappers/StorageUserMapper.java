package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface StorageUserMapper {

    default StorageUserList entityListToStorageUserList(List<UserEntity> users) {
        return StorageUserList.builder()
                .users(entityListToStorageUserItemList(users))
                .build();
    }
    List<StorageUserItem> entityListToStorageUserItemList(List<UserEntity> content);

    StorageUserItem entityToStorageUserItem(UserEntity item);

    UserEntity storageUserEntityToEntity(StorageUserItem mapperResponse);
}
