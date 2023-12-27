package org.egx.recommendation.repos;

import org.egx.recommendation.entity.NewsEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsEmbeddingRepository extends JpaRepository<NewsEmbedding, Integer> {
}