package by.sapra.newsservice.services;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CategoryServiceConf.class)
class CategoryServiceTest  {
    @Autowired
    CategoryService service;

    @Test
    void shouldNotReturnNull() throws Exception {
        CategoryFilter filter = new CategoryFilter();
        filter.setPageSize(3);
        filter.setPageNumber(0);

        List<Category> actual = service.findAll(filter);

        assertNotNull(actual);
    }
}