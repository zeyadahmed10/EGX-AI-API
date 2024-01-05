package org.egx.recommendation.repos;

import org.egx.recommendation.entity.NewsEmbedding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsEmbeddingRepository extends JpaRepository<NewsEmbedding, Integer> {
    String KNearestNeighborsQuery = "select * from news_embedding ne order by ne.embedding <-> " +
            "(select us.embedding from user_history us where us.email= :emailParam ) LIMIT :kParam OFFSET :pageParam * :kParam";
    Page<NewsEmbedding> findAllByOrderByHitsDesc(Pageable pageable);
    @Query(nativeQuery = true, value = KNearestNeighborsQuery)
    List<NewsEmbedding> findKNearestNeighbors(@Param("emailParam")String userEmail, @Param("kParam") int k, @Param("pageParam")int page);
}