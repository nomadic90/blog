package com.hyuryu.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NaverBlogSearchResponse {
    @Setter
    private List<NaverBlogSearchItem> items;
}
