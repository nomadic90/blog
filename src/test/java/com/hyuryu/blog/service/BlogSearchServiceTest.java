package com.hyuryu.blog.service;

import com.hyuryu.blog.model.BlogSearchResult;
import com.hyuryu.blog.provider.BlogSearchProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

class BlogSearchServiceTest {
    private BlogSearchService blogSearchService;
    private BlogSearchProvider blogSearchProvider;

    @BeforeEach
    public void setUp() {
        blogSearchProvider = Mockito.mock(BlogSearchProvider.class);
        blogSearchService = new BlogSearchService(Arrays.asList(blogSearchProvider));
    }

    @Test
    public void searchTest() {
        List<BlogSearchResult> expectedResult = Arrays.asList(
                new BlogSearchResult("Title 1", "URL 1", "t1", "d2"),
                new BlogSearchResult("Title 2", "URL 2", "t1", "d2")
        );
        Mockito.when(blogSearchProvider.search(anyString(), anyString(), anyInt(), anyInt())).thenReturn(expectedResult);

        List<BlogSearchResult> actualResult = blogSearchService.search("query", "accuracy", 1, 10);

        // Assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(blogSearchProvider).search("query", "accuracy", 1, 10);
    }
}