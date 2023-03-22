package com.hyuryu.blog.provider;

import com.hyuryu.blog.model.BlogSearchResult;
import com.hyuryu.blog.model.NaverBlogSearchItem;
import com.hyuryu.blog.model.NaverBlogSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class NaverBlogSearchProviderTest {
    private static final String API_URL = "https://openapi.naver.com/v1/search/blog.json";

    @Autowired
    private NaverBlogSearchProvider naverBlogSearchProvider;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("네이버 블로그 검색 API 호출 테스트")
    void searchTest() {
        // Given
        RestTemplate restTemplate = mock(RestTemplate.class);
        ResponseEntity<NaverBlogSearchResponse> response = ResponseEntity.ok(
                new NaverBlogSearchResponse(
                        List.of(new NaverBlogSearchItem("title", "link", "bloggername", "postdate"))
                )
        );
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(NaverBlogSearchResponse.class)))
                .thenReturn(response);

        NaverBlogSearchProvider provider = new NaverBlogSearchProvider();
        provider.setRestTemplate(restTemplate);

        // When
        List<BlogSearchResult> results = provider.search("keyword", "accuracy", 1, 10);

        // Then
        assertNotNull(results);
    }
}