package by.sapra.newsservice.services.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserItemModel {
    private Long id;
    private String name;
}
