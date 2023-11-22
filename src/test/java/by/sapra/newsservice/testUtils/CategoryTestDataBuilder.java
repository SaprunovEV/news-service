package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.models.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoryTestDataBuilder implements TestDataBuilder<CategoryEntity>{
    private String name = "testCategoryName";
    private List<TestDataBuilder<Category2News>> category2News = new ArrayList<>();

    private CategoryTestDataBuilder() {}

    public CategoryTestDataBuilder(String name, List<TestDataBuilder<Category2News>> category2News) {
        this.name = name;
        this.category2News = category2News;
    }

    public static CategoryTestDataBuilder aCategory() {
        return new CategoryTestDataBuilder();
    }

    public CategoryTestDataBuilder withName(String name) {
        return this.name == name ? this : new CategoryTestDataBuilder(name, this.category2News);
    }

    public CategoryTestDataBuilder withCategory2News(List<TestDataBuilder<Category2News>> category2News) {
        return this.category2News == category2News ? this : new CategoryTestDataBuilder(this.name, category2News);
    }

    @Override
    public CategoryEntity build() {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(name);
        entity.setCategory2News(getEntityCollection(category2News, c2n -> c2n.setCategory(entity)));
        return entity;
    }
}
