package com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.WordEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordRepository extends CrudRepository<WordEntity, Integer> {
    @Query(value = "SELECT CONCAT(w.id,',','\\'',w.word,'\\'') FROM WordEntity w", nativeQuery = true)
    public List<String> findAllAndParseString();
}
