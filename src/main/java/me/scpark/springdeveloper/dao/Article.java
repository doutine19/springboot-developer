package me.scpark.springdeveloper.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Article {

    /*
        id (bigint, not null, primary key)
        title (varchar(255), not null)
        content (varchar(255), not null)
     */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

