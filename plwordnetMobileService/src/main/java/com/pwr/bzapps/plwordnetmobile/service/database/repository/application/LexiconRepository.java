package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LexiconRepository extends CrudRepository<LexiconEntity, Integer> {
    @Query("SELECT le FROM LexiconEntity le WHERE LOWER(le.language_name) LIKE LOWER(:language)")
    public List<LexiconEntity> getAllLexiconsForLanguage(@Param("language") String language);
}
