package com.hyuryu.blog.web;

import com.hyuryu.blog.base.model.CodeResponse;
import com.hyuryu.blog.entity.SearchCount;
import com.hyuryu.blog.model.BlogSearchResult;
import com.hyuryu.blog.service.BlogSearchCountService;
import com.hyuryu.blog.service.BlogSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BlogSearchControllerTest {
    @Mock
    private BlogSearchCountService blogSearchCountService;

    @Mock
    private BlogSearchService blogSearchService;

    @InjectMocks
    private BlogSearchController blogSearchController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearch() {
        // given
        String query = "spring boot";
        String sort = "date";
        int page = 1;
        int size = 10;

        // when
        when(blogSearchService.search(query, sort, page, size)).thenReturn(List.of(
                new BlogSearchResult("title1", "url1", "thumbnail1", "datetime1"),
                new BlogSearchResult("title2", "url2", "thumbnail2", "datetime2")
        ));

        CodeResponse expected = CodeResponse.successResult(List.of(
                new BlogSearchResult("title1", "url1", "thumbnail1", "datetime1"),
                new BlogSearchResult("title2", "url2", "thumbnail2", "datetime2")
        ));

        CodeResponse actual = blogSearchController.search(query, sort, page, size);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testGetTopKeywords() {
        // given
        when(blogSearchCountService.getTop10Keywords()).thenReturn(List.of(
                new SearchCount("query1", 10),
                new SearchCount("query2", 8),
                new SearchCount("query3", 5)
        ));

        CodeResponse expected = CodeResponse.successResult(List.of(
                new SearchCount("query1", 10),
                new SearchCount("query2", 8),
                new SearchCount("query3", 5)
        ));

        // when
        CodeResponse actual = blogSearchController.getTopKeyowrds();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}