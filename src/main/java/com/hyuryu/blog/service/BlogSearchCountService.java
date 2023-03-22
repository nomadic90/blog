package com.hyuryu.blog.service;

import com.hyuryu.blog.entity.SearchCount;
import com.hyuryu.blog.repository.SearchCountRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogSearchCountService {
    private SearchCountRepository searchCountRepository;

    private static final long CACHE_EXPIRATION = 1000;

    private List<SearchCount> keywords;
    private long cacheExpireTime;

    private final Object lock = new Object();

    public BlogSearchCountService(SearchCountRepository searchCountRepository) {
        this.searchCountRepository = searchCountRepository;
    }

    @Async
    public void incrementSearchCount(String keyword) {
        synchronized (lock) {
            SearchCount searchCount = searchCountRepository.findByKeyword(keyword);
            if (searchCount == null) {
                searchCount = new SearchCount(keyword, 1);
            } else {
                searchCount.setCount(searchCount.getCount()+1);
            }

            searchCountRepository.save(searchCount);
        }
    }

    public List<SearchCount> getTop10Keywords() {
        synchronized (lock) {
            if (keywords == null || keywords.size() == 0 || System.currentTimeMillis() > cacheExpireTime) {
                keywords = searchCountRepository.findTop10ByOrderByCountDesc();
                cacheExpireTime = System.currentTimeMillis() + CACHE_EXPIRATION;
            }

            return keywords;
        }
    }
}
