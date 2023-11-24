package by.sapra.newsservice.web.v1;

import by.sapra.newsservice.testUtils.StringTestUtils;
import net.javacrumbs.jsonunit.JsonAssert;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractErrorControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    void whenPageSizeNotSpecified_thenReturnError() throws Exception {
        String pageNumber = "0";
        MockHttpServletResponse response = mockMvc.perform(
                        get(getUrl())
                                .param("pageNumber", pageNumber)
                )
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/errors/pagination_size_not_specified_error_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenPageSizeIsNegative_thenReturnError() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(getWithPagination(getUrl(), "0", "-1"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/errors/pagination_size_negative_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenPageNumberNotSpecified_thenReturnError() throws Exception {
        String pageSize = "3";
        MockHttpServletResponse response = mockMvc.perform(
                        get(getUrl())
                                .param("pageSize", pageSize)
                )
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/errors/pagination_number_not_specified_error_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenPageNumberIsNegative_thenReturnError() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(getWithPagination(getUrl(), "-1", "4"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/errors/pagination_number_negative_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    public abstract String getUrl();

    @NotNull
    private MockHttpServletRequestBuilder getWithPagination(String url, String pageNumber, String pageSize) {
        return get(url).param("pageNumber", pageNumber).param("pageSize", pageSize);
    }
}
