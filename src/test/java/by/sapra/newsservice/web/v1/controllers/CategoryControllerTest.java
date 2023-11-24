package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.web.v1.AbstractErrorControllerTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest extends AbstractErrorControllerTest {

    @Test
    void whenFindAll_thenReturnOk() throws Exception {
        String pageNumber = "0";
        String pageSize = "3";
        mockMvc.perform(getWithPagination(pageNumber, pageSize))
                .andExpect(status().isOk());
    }


    @NotNull
    private MockHttpServletRequestBuilder getWithPagination(String pageNumber, String pageSize) {
        return get(getUrl()).param("pageNumber", pageNumber).param("pageSize", pageSize);
    }

    @Override
    public String getUrl() {
        return "/api/v1/categories";
    }
}