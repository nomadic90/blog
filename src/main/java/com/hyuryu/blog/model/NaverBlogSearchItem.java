package com.hyuryu.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NaverBlogSearchItem {
    private String title;
    private String link;
    private String bloggername;
    private String postdate;
}
