package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsListModel {
    private long count;
    private List<NewsModel> news;
}
