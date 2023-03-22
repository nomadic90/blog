package com.hyuryu.blog.repository;

import com.hyuryu.blog.entity.SearchCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchCountRepository extends JpaRepository<SearchCount, Long> {
    SearchCount findByKeyword(String keyword);

    @Query("SELECT s FROM SearchCount s ORDER BY s.count DESC")
    List<SearchCount> findTop10ByOrderByCountDesc();
}
