package by.sapra.newsservice.storages.reposytory;

import by.sapra.newsservice.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
