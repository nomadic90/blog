package com.hyuryu.blog.service;

import com.hyuryu.blog.entity.SearchCount;
import com.hyuryu.blog.repository.SearchCountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BlogSearchCountServiceTest {
    @Mock
    private SearchCountRepository searchCountRepository;

    @InjectMocks
    private BlogSearchCountService blogSearchCountService;

    @Test
    @DisplayName("검색어 검색 횟수 증가 테스트")
    void incrementSearchCountTest() {
        // given
        String keyword = "test";
        given(searchCountRepository.findByKeyword(keyword)).willReturn(new SearchCount(keyword, 1));

        // when
        blogSearchCountService.incrementSearchCount(keyword);

        // then
        SearchCount searchCount = searchCountRepository.findByKeyword(keyword);
        assertThat(searchCount.getCount()).isEqualTo(2);
        assertThat(searchCount.getKeyword()).isEqualTo(keyword);
    }

    @Test
    @DisplayName("상위 10개 검색어 조회 테스트")
    void getTop10KeywordsTest() {
        // given
        List<SearchCount> searchCounts = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new SearchCount("test" + i, i))
                .collect(Collectors.toList());
        given(searchCountRepository.findTop10ByOrderByCountDesc()).willReturn(searchCounts);

        // when
        List<SearchCount> top10Keywords = blogSearchCountService.getTop10Keywords();

        // then
        assertThat(top10Keywords.size()).isEqualTo(20);
        assertThat(top10Keywords.get(0).getKeyword()).isEqualTo("test1");
        assertThat(top10Keywords.get(0).getCount()).isEqualTo(1);
        assertThat(top10Keywords.get(9).getKeyword()).isEqualTo("test10");
        assertThat(top10Keywords.get(9).getCount()).isEqualTo(10);
    }
}