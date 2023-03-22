package com.hyuryu.blog.service;

import com.hyuryu.blog.entity.SearchCount;
import com.hyuryu.blog.repository.SearchCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BlogSearchCountServiceTest {
    private BlogSearchCountService blogSearchCountService;
    private SearchCountRepository searchCountRepository;

    @BeforeEach
    public void setUp() {
        searchCountRepository = mock(SearchCountRepository.class);
        blogSearchCountService = new BlogSearchCountService(searchCountRepository);
    }

    @Test
    public void incrementSearchCountTest() {
        SearchCount searchCount = new SearchCount("query", 1);
        Mockito.when(searchCountRepository.findByKeyword(anyString())).thenReturn(null);
        Mockito.when(searchCountRepository.save(any(SearchCount.class))).thenReturn(searchCount);

        blogSearchCountService.incrementSearchCount("query");

        verify(searchCountRepository).findByKeyword("query");
        verify(searchCountRepository).save(searchCount);
    }

    @Test
    public void getTop10KeywordsTest() {
        List<SearchCount> expectedResult = Arrays.asList(
                new SearchCount("query1", 5),
                new SearchCount("query2", 3)
        );
        Mockito.when(searchCountRepository.findTop10ByOrderByCountDesc()).thenReturn(expectedResult);

        List<SearchCount> actualResult = blogSearchCountService.getTop10Keywords();

        // Assert
        assertEquals(expectedResult, actualResult);
        verify(searchCountRepository).findTop10ByOrderByCountDesc();
    }
}