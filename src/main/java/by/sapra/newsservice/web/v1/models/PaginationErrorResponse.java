package by.sapra.newsservice.web.v1.models;

import lombok.Data;
@Data
public class PaginationErrorResponse implements ErrorResponse {
    private String message;
}
