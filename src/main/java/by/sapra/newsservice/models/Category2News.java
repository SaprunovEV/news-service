package by.sapra.newsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "category_news_link")
@Getter
@Setter
public class Category2News {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @ToString.Exclude
    private NewsEntity news;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private CategoryEntity category;
}
