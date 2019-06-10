package com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.WordEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends CrudRepository<WordEntity, Long> {
    @Query(value = "SELECT CONCAT(w.id,',','\"',REPLACE(w.word,'\"','####'),'\"') FROM word w", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT(w.id,',','\"',REPLACE(w.word,'\"','####'),'\"') FROM word w" +
            " WHERE w.id>=:begin AND w.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM word", nativeQuery = true)
    public Long getMaxIndex();
}
