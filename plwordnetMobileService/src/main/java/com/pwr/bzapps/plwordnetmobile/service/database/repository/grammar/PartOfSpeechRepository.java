package com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.PartOfSpeechEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartOfSpeechRepository extends CrudRepository<PartOfSpeechEntity, Long> {
    @Query(value = "SELECT CONCAT(pos.id,',',pos.name_id,',','\"',pos.color,'\"') FROM part_of_speech pos", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT(pos.id,',',pos.name_id,',','\"',pos.color,'\"') FROM part_of_speech pos" +
            " WHERE pos.id>=:begin AND pos.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM emotional_annotations", nativeQuery = true)
    public Long getMaxIndex();
}
