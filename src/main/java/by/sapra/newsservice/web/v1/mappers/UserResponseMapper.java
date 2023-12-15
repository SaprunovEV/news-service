package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.web.v1.models.UpsertUserRequest;
import by.sapra.newsservice.web.v1.models.UserItemResponse;
import by.sapra.newsservice.web.v1.models.UserListResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface UserResponseMapper {
    UserListResponse userListModelToUserListResponse(UserListModel usersListModel);

    UserItemResponse serviceUserItemToUserItemResponse(UserItemModel serviceResponse);

    UserItemModel requestToUserItemModel(UpsertUserRequest request);
}
