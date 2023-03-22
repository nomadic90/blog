package com.hyuryu.blog.provider;

import com.hyuryu.blog.model.BlogSearchResult;
import com.hyuryu.blog.model.NaverBlogSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class NaverBlogSearchProvider implements BlogSearchProvider {
    private static final String API_URL = "https://openapi.naver.com/v1/search/blog";

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Override
    public List<BlogSearchResult> search(String query, String sort, int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = API_URL + "?query=" + query + "&display=" + size + "&start=" + (page - 1) * size + "&sort=" + sort;

        ResponseEntity<NaverBlogSearchResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, NaverBlogSearchResponse.class);
        NaverBlogSearchResponse responseBody = response.getBody();

        if (responseBody != null) {
            return responseBody.getItems().stream().map(item ->
                    new BlogSearchResult(item.getTitle(), item.getLink(), item.getBloggername(), item.getPostdate())
            ).collect(Collectors.toList());
        } else {
            return List.of();
        }
    }
}
