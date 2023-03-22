package com.hyuryu.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverBlogSearchResponse {
    @Setter
    private List<NaverBlogSearchItem> items;
}
