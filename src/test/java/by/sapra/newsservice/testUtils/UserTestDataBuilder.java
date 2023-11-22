package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.UserEntity;

public class UserTestDataBuilder implements TestDataBuilder<UserEntity> {
    private String name = "Test Name";

    private UserTestDataBuilder() {}

    private UserTestDataBuilder(String name) {
        this.name = name;
    }

    public static UserTestDataBuilder aUser() {
        return new UserTestDataBuilder();
    }

    public UserTestDataBuilder withName(String name) {
        return this.name == name ? this : new UserTestDataBuilder(name);
    }

    @Override
    public UserEntity build() {
        UserEntity entity = new UserEntity();
        entity.setName(this.name);
        return entity;
    }
}
