package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.web.v1.models.UpsertUserRequest;
import by.sapra.newsservice.web.v1.models.UserItemResponse;
import by.sapra.newsservice.web.v1.models.UserListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface UserResponseMapper {
    UserListResponse userListModelToUserListResponse(UserListModel usersListModel);

    UserItemResponse serviceUserItemToUserItemResponse(UserItemModel serviceResponse);

    UserItemModel requestToUserItemModel(UpsertUserRequest request);

    @Mapping(source = "id", target = "id")
    UserItemModel requestToUserItemModelWithId(Long id, UpsertUserRequest request);
}
