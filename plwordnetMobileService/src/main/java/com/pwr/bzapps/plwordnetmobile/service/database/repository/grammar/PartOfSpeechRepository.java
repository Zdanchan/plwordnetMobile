package com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.PartOfSpeechEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartOfSpeechRepository extends CrudRepository<PartOfSpeechEntity, Integer> {
    @Query(value = "SELECT CONCAT(pos.id,',',pos.name_id,',','\\'',pos.color'\\'') FROM PartOfSpeechEntity pos", nativeQuery = true)
    public List<String> findAllAndParseString();
}
