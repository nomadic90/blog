package com.hyuryu.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="search_count")
@NoArgsConstructor
public class SearchCount {
    public SearchCount(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    @Setter
    private int count;
}

