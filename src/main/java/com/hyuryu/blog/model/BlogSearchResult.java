package com.hyuryu.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogSearchResult {
    private String title;
    private String url;
    private String thumbnail;
    private String datetime;
}
