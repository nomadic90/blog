package com.hyuryu.blog.provider;

import com.hyuryu.blog.model.BlogSearchResult;
import com.hyuryu.blog.model.NaverBlogSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NaverBlogSearchProvider implements BlogSearchProvider {
    private static final String API_URL = "https://openapi.naver.com/v1/search/blog.json";

    private String clientId = "RkibhwwNPb3HRtaB7hAR";
    private String clientSecret = "3SyFZh8sgw";

    private RestTemplate restTemplate = new RestTemplate();

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<BlogSearchResult> search(String query, String sort, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        if (sort.equals("accuracy")) {
            sort = "sim";
        } else {
            sort = "date";
        }

        String url = API_URL + "?query=" + query + "&display=" + size + "&start=" + page + "&sort=" + sort;

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
