package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.CategoryEntity;

public class CategoryTestDataBuilder implements TestDataBuilder<CategoryEntity>{
    private String name = "testCategoryName";

    private CategoryTestDataBuilder() {}

    public CategoryTestDataBuilder(String name) {
        this.name = name;
    }

    public static CategoryTestDataBuilder aCategory() {
        return new CategoryTestDataBuilder();
    }

    public CategoryTestDataBuilder withName(String name) {
        return this.name == name ? this : new CategoryTestDataBuilder(name);
    }

    @Override
    public CategoryEntity build() {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(name);
        return entity;
    }
}
