package by.sapra.newsservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Column name in category should not be empty!")
    private String name;

    public void setName(String name) {
        this.name = name != null ? name.toLowerCase() : name;
    }
}
