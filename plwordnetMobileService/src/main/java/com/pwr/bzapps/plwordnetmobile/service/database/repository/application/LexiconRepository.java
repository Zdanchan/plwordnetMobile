package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LexiconRepository extends CrudRepository<LexiconEntity, Long> {

    @Query(value = "SELECT CONCAT(le.id,',','\"',le.identifier,'\"',',','\"',le.language_name,'\"',',','\"',le.name,'\"',',','\"',le.lexicon_version,'\"') FROM wordnet.lexicon le", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT(le.id,',','\"',le.identifier,'\"',',','\"',le.language_name,'\"',',','\"',le.name,'\"',',','\"',le.lexicon_version,'\"') FROM wordnet.lexicon le" +
            " WHERE le.id>=:begin AND le.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);

    @Query("SELECT le FROM LexiconEntity le WHERE LOWER(le.language_name) LIKE LOWER(:language)")
    public List<LexiconEntity> getAllLexiconsForLanguage(@Param("language") String language);

    @Query(value = "SELECT le.id FROM lexicon le WHERE LOWER(le.language_name) LIKE LOWER(:language)", nativeQuery = true)
    public Long[] getAllLexiconsIdsForLanguage(@Param("language") String language);

    @Query(value = "SELECT MAX(id) FROM lexicon", nativeQuery = true)
    public Long getMaxIndex();
}
