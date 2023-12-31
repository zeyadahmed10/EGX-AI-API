package org.egx.recommendation.repos;

import org.egx.recommendation.entity.NewsEmbedding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsEmbeddingRepository extends JpaRepository<NewsEmbedding, Integer> {

    Page<NewsEmbedding> findAllByHitsDesc(Pageable pageable);
}