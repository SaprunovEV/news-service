package by.sapra.newsservice.services.models;

import by.sapra.newsservice.models.errors.UserError;
import by.sapra.newsservice.storages.models.StorageUserItem;

import java.util.Optional;
import java.util.function.Function;

public class UserApplicationModel implements ApplicationModel<UserItemModel, UserError> {

    private final Optional<StorageUserItem> model;
    private final Function<StorageUserItem, UserItemModel> mapper;
    private final String message;

    private UserItemModel result;

    UserApplicationModel(Optional<StorageUserItem> model, Function<StorageUserItem, UserItemModel> mapper, String message) {
        this.model = model;
        this.mapper = mapper;
        this.message = message;
        this.result = null;
    }

    public static UserApplicationModelBuilder builder() {
        return new UserApplicationModelBuilder();
    }

    @Override
    public UserItemModel getData() {
        if (result == null) {
            result = mapper.apply(model.get());
            return result;
        }
        return result;
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

    public static class UserApplicationModelBuilder {
        private Optional<StorageUserItem> model;
        private Function<StorageUserItem, UserItemModel> mapper;
        private String message;

        UserApplicationModelBuilder() {
        }

        public UserApplicationModelBuilder model(Optional<StorageUserItem> model) {
            this.model = model;
            return this;
        }

        public UserApplicationModelBuilder mapper(Function<StorageUserItem, UserItemModel> mapper) {
            this.mapper = mapper;
            return this;
        }

        public UserApplicationModelBuilder message(String message) {
            this.message = message;
            return this;
        }

        public UserApplicationModel build() {
            return new UserApplicationModel(model, mapper, message);
        }

        public String toString() {
            return "UserApplicationModel.UserApplicationModelBuilder(model=" + this.model + ", mapper=" + this.mapper + ", message=" + this.message + ")";
        }
    }
}
