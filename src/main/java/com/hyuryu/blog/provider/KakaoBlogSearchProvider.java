package com.hyuryu.blog.provider;

import com.hyuryu.blog.model.BlogSearchResult;
import com.hyuryu.blog.model.KakaoBlogSearchResponse;
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
public class KakaoBlogSearchProvider implements BlogSearchProvider {
//    private final String kakaoAppKey = "5a974f2ab0be9781d788bacd9aff4184";
    @Value("${kakao.app.key}")
    private String kakaoAppKey;
    private final String apiUrl = "https://dapi.kakao.com/v2/search/blog";
    private final RestTemplate restTemplate = new RestTemplate();

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
