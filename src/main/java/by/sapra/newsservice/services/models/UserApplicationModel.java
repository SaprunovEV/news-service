package by.sapra.newsservice.services.models;

import by.sapra.newsservice.models.errors.UserError;
import by.sapra.newsservice.storages.models.StorageUserItem;
import lombok.Builder;

import java.util.Optional;
import java.util.function.Function;

@Builder
public class UserApplicationModel implements ApplicationModel<UserItemModel, UserError> {

    private Optional<StorageUserItem> model;
    private Function<StorageUserItem, UserItemModel> mapper;
    private String message;

    @Override
    public UserItemModel getData() {
        return mapper.apply(model.get());
    }

    @Override
    public UserError getError() {
        return UserError.builder()
                .message(message)
                .build();
    }

    @Override
    public boolean hasError() {
        return model.isEmpty();
    }
}
