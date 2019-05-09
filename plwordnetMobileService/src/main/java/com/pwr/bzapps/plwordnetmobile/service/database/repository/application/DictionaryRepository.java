package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DictionaryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Long> {
    @Query(value = "SELECT CONCAT('\"',de.dtype,'\"',',',de.id,',',IF(de.description_id IS NULL,'null', de.description_id),',',de.name_id,',',IF(de.tag IS NULL, 'null', CONCAT('\"',de.tag,'\"')),',',IF(de.value IS NULL,'null',de.value)) FROM dictionaries de", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT('\"',de.dtype,'\"',',',de.id,',',IF(de.description_id IS NULL,'null', de.description_id),',',de.name_id,',',IF(de.tag IS NULL, 'null', CONCAT('\"',de.tag,'\"')),',',IF(de.value IS NULL,'null',de.value)) FROM dictionaries de" +
            " WHERE de.id>=:begin AND de.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM dictionaries", nativeQuery = true)
    public Long getMaxIndex();
}
