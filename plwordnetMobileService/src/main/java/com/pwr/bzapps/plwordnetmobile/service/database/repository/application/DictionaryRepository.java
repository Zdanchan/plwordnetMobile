package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DictionaryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Integer> {
    @Query(value = "SELECT CONCAT('\"',de.dtype,'\"',',',de.id,',',IF(de.description_id IS NULL,'null', de.description_id),',',de.name_id,',',IF(de.tag IS NULL, 'null', CONCAT('\"',de.tag,'\"')),',',IF(de.value IS NULL,'null',de.value)) FROM dictionaries de", nativeQuery = true)
    public List<String> findAllAndParseString();
}
