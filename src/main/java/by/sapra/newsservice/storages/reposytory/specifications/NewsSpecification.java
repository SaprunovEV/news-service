package by.sapra.newsservice.storages.reposytory.specifications;

import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {
    static Specification<NewsEntity> withFilter(NewsFilter filter) {
        return Specification.where(byCategoryId(filter.getCategory()));
    }

    static Specification<NewsEntity> byCategoryId(Long category) {
        return (root, qe, cb) -> {
            if (category==null) {
                return null;
            }
            return cb.equal(root.get("category2News").get("category").get("id"), category);
        };
    }
}
