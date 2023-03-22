package com.hyuryu.blog.web;

import com.hyuryu.blog.base.model.CodeResponse;
import com.hyuryu.blog.provider.KakaoBlogSearchProvider;
import com.hyuryu.blog.provider.NaverBlogSearchProvider;
import com.hyuryu.blog.service.BlogSearchCountService;
import com.hyuryu.blog.service.BlogSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class BlogSearchController {
    @Autowired
    private BlogSearchCountService blogSearchCountService;

    @Autowired
    private KakaoBlogSearchProvider kakaoBlogSearchProvider;

    @Autowired
    private NaverBlogSearchProvider naverBlogSearchProvider;

    private BlogSearchService blogSearchService;

    @PostConstruct
    public void init() {
        this.blogSearchService = new BlogSearchService(List.of(kakaoBlogSearchProvider, naverBlogSearchProvider));
    }

    @GetMapping("/api/v1/search")
    public CodeResponse search(@RequestParam String query,
                               @RequestParam(required = false, defaultValue = "accuracy") String sort,
                               @RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "10") int size) {
        blogSearchCountService.incrementSearchCount(query);
        return CodeResponse.successResult(blogSearchService.search(query, sort, page, size));
    }

    @GetMapping("/api/v1/top-keywords")
    public CodeResponse getTopKeyowrds() {
        return CodeResponse.successResult(blogSearchCountService.getTop10Keywords());
    }
}
