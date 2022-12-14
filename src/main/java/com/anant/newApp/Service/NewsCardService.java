package com.anant.newApp.Service;

import com.anant.newApp.Entity.NewsCardEntity;
import com.anant.newApp.Repository.NewsCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NewsCardService {

    @Autowired
    private NewsCardRepository repo;

    public List<NewsCardEntity> findAll(){
        return repo.findAll();
    }

    public int save(NewsCardEntity entity){
       var articleToSave  = repo.findById(entity.getUrl());
       if(articleToSave.isPresent()) {
           return articleToSave.get().getId();
       }
           NewsCardEntity savedEntity = repo.save(entity);
           return savedEntity.getId();
    }

    public Optional<NewsCardEntity> findById(String url){
        return repo.findById(url);
    }

    public boolean deleteSavedArticle(String url){
        Optional<NewsCardEntity> cardToDelete = findById(url);
        if(cardToDelete.isPresent()){
            repo.deleteById(url);
            return true;
        }
       return false;
    }
}
