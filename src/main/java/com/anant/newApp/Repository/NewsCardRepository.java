package com.anant.newApp.Repository;

import com.anant.newApp.Entity.NewsCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCardRepository extends JpaRepository<NewsCardEntity, String> {
}
