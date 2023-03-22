package com.hyuryu.blog.provider;

import com.hyuryu.blog.model.BlogSearchResult;

import java.util.List;

public interface BlogSearchProvider {
    List<BlogSearchResult> search(String query, String sort, int page, int size);
}
