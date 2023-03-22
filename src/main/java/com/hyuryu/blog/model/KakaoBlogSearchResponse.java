package com.hyuryu.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter()
public class KakaoBlogSearchResponse {
    @JsonProperty("documents")
    private List<KakaoBlogDocument> documents;

    @Getter()
    public static class KakaoBlogDocument {
        @JsonProperty("title")
        private String title;

        @JsonProperty("url")
        private String url;

        @JsonProperty("thumbnail")
        private String thumbnail;

        @JsonProperty("datetime")
        private String datetime;
    }
}
