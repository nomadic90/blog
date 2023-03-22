package com.hyuryu.blog.provider;

import com.hyuryu.blog.model.BlogSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RestClientTest(KakaoBlogSearchProvider.class)
class KakaoBlogSearchProviderTest {
    private static final String API_URL = "https://dapi.kakao.com/v2/search/blog";
    private static final int PAGE = 1;
    private static final int SIZE = 10;
    private static final String QUERY = "springboot";
    private static final String SORT = "accuracy";

    private MockRestServiceServer mockServer;
    private KakaoBlogSearchProvider provider;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplateBuilder(new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {
                restTemplate.setUriTemplateHandler(new RootUriTemplateHandler(API_URL));
            }
        }).build();

        provider = new KakaoBlogSearchProvider();
        provider.setRestTemplate(restTemplate);
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @DisplayName("카카오 블로그 검색 API 호출 테스트")
    void testSearch() {
        // given
        String responseBody = "{\"documents\":[{\"title\":\"Spring Boot Tutorial\",\"url\":\"https://www.example.com\",\"thumbnail\":\"https://www.example.com/thumb.jpg\",\"datetime\":\"2022-03-22T11:32:00\"}],\"meta\":{\"total_count\":1,\"pageable_count\":1,\"is_end\":true}}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        mockServer.expect(MockRestRequestMatchers.requestTo(API_URL + "?query=" + QUERY + "&sort=" + SORT + "&page=" + PAGE + "&size=" + SIZE))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(responseBody, MediaType.APPLICATION_JSON));

        // when
        List<BlogSearchResult> result = provider.search(QUERY, SORT, PAGE, SIZE);

        // then
        assertThat(result).hasSize(1);
        BlogSearchResult searchResult = result.get(0);
        assertThat(searchResult.getTitle()).isEqualTo("Spring Boot Tutorial");
        assertThat(searchResult.getUrl()).isEqualTo("https://www.example.com");
        assertThat(searchResult.getThumbnail()).isEqualTo("https://www.example.com/thumb.jpg");
        assertThat(searchResult.getDatetime()).isEqualTo("2022-03-22T11:32:00");
        mockServer.verify();
    }
}