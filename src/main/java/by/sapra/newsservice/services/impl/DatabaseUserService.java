package by.sapra.newsservice.services.impl;

import by.sapra.newsservice.models.errors.UserError;
import by.sapra.newsservice.services.UserService;
import by.sapra.newsservice.services.mappers.UserServiceMapper;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.UserApplicationModel;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.UserStorage;
import by.sapra.newsservice.storages.models.StorageUserItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final UserStorage storage;
    private final UserServiceMapper mapper;

    @Override
    public UserListModel findAllUsers(UserFilter filter) {
        return mapper.storageUserListToUserListModel(storage.findAll(filter));
    }

    @Override
    public ApplicationModel<UserItemModel, UserError> findUserById(long id) {
        Optional<StorageUserItem> optional = storage.findById(id);

        return UserApplicationModel.builder()
                .model(optional)
                .message(MessageFormat.format("Пользователь с ID {0} не найден!", id))
                .mapper(mapper::storageUserItemToUserItemModel)
                .build();
    }

    @Override
    public ApplicationModel<UserItemModel, UserError> createUser(UserItemModel request) {
        StorageUserItem userToSave = mapper.userItemModelToStorageUserItem(request);
        Optional<StorageUserItem> optional = storage.createNewUser(userToSave);

        return UserApplicationModel.builder()
                .model(optional)
                .mapper(mapper::storageUserItemToUserItemModel)
                .message(MessageFormat.format("Пользователь с именем {0} уже существует!", request.getName()))
                .build();
    }

    @Override
    public ApplicationModel<UserItemModel, UserError> updateUser(UserItemModel model) {
        StorageUserItem userToUpdate = mapper.userItemModelToStorageUserItem(model);
        Optional<StorageUserItem> optional = storage.updateUser(userToUpdate);
        return UserApplicationModel.builder()
                .model(optional)
                .mapper(mapper::storageUserItemToUserItemModel)
                .message(MessageFormat.format("Пользователь с ID {0} не найден!", model.getId()))
                .build();
    }

    @Override
    public void deleteUser(long id) {
        storage.deleteUser(id);
    }
}
