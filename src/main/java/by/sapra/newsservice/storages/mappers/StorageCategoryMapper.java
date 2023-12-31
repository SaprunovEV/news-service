package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;

import static org.mapstruct.ReportingPolicy.IGNORE;
@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, uses = {StorageCategory2NewsMapper.class})
public interface StorageCategoryMapper {
    default CategoryListModel entityListToCategoryListModel(List<CategoryEntity> expected, Map<Long, Long> countMap) {
        return CategoryListModel.builder()
                .count(countMap.keySet().size())
                .categories(expected.stream().map(entity -> entityToCategoryModel(entity, countMap.get(entity.getId()))).toList())
                .build();
    }

    @Mapping(source = "count", target = "newsCount")
    CategoryModel entityToCategoryModel(CategoryEntity categoryEntity, long count);
    @Mapping(source = "category2News", target = "news")
    FullCategoryModel entityToFullCategory(CategoryEntity expected);

    @Mapping(target = "category2News", source = "news")
    CategoryEntity fullCategoryModelToEntity(FullCategoryModel categoryToSave);
}
