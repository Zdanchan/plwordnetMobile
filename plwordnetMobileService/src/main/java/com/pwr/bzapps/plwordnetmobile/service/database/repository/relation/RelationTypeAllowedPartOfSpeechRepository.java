package com.pwr.bzapps.plwordnetmobile.service.database.repository.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationTypeAllowedPartOfSpeechRepository extends CrudRepository<RelationTypeAllowedPartOfSpeechEntity, Integer> {
    @Query(value = "SELECT CONCAT(rtapos.relation_type_id,',',rtapos.part_of_speech_id) FROM relation_type_allowed_parts_of_speech rtapos", nativeQuery = true)
    public List<String> findAllAndParseString();
}
