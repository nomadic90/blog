package com.hyuryu.blog.provider;

import com.hyuryu.blog.model.BlogSearchResult;
import com.hyuryu.blog.model.KakaoBlogSearchResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KakaoBlogSearchProvider implements BlogSearchProvider {
    private final String kakaoAppKey = "5a974f2ab0be9781d788bacd9aff4184";
    private String apiUrl = "https://dapi.kakao.com/v2/search/blog";
    private RestTemplate restTemplate = new RestTemplate();

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<BlogSearchResult> search(String query, String sort, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoAppKey);
        String url = apiUrl + "?query=" + query + "&sort=" + sort + "&page=" + page + "&size=" + size;

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoBlogSearchResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoBlogSearchResponse.class);

        return response.getBody().getDocuments()
                .stream()
                .map(doc -> new BlogSearchResult(doc.getTitle(), doc.getUrl(), doc.getThumbnail(), doc.getDatetime()))
                .collect(Collectors.toList());
    }
}
