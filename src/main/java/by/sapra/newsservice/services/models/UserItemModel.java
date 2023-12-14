package by.sapra.newsservice.services.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserItemModel {
    private long id;
    private String name;
}
