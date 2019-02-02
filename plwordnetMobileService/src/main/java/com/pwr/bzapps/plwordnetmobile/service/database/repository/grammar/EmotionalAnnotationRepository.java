package com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.EmotionalAnnotationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmotionalAnnotationRepository extends CrudRepository<EmotionalAnnotationEntity, Integer> {
    @Query(value = "SELECT CONCAT(ea.id,','" +
            ",ea.sense_id,','" +
            ",IF(ea.has_emotional_characteristic=1,1,0),','" +
            ",IF(ea.super_anotation=1,1,0),','" +
            ",IF(ea.emotions IS NULL,'null',CONCAT('\"',REPLACE(ea.emotions,'\"','####'),'\"')),','" +
            ",IF(ea.valuations IS NULL,'null',CONCAT('\"',REPLACE(ea.valuations,'\"','####'),'\"')),','" +
            ",IF(ea.markedness IS NULL,'null',CONCAT('\"',REPLACE(ea.markedness,'\"','####'),'\"')),','" +
            ",IF(ea.example1 IS NULL,'null',CONCAT('\"',REPLACE(ea.example1,'\"','####'),'\"')),','" +
            ",IF(ea.example2 IS NULL,'null',CONCAT('\"',REPLACE(ea.example2,'\"','####'),'\"'))) " +
            " FROM emotional_annotations ea", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT(ea.id,','" +
            ",ea.sense_id,','" +
            ",IF(ea.has_emotional_characteristic=1,1,0),','" +
            ",IF(ea.super_anotation=1,1,0),','" +
            ",IF(ea.emotions IS NULL,'null',CONCAT('\"',REPLACE(ea.emotions,'\"','####'),'\"')),','" +
            ",IF(ea.valuations IS NULL,'null',CONCAT('\"',REPLACE(ea.valuations,'\"','####'),'\"')),','" +
            ",IF(ea.markedness IS NULL,'null',CONCAT('\"',REPLACE(ea.markedness,'\"','####'),'\"')),','" +
            ",IF(ea.example1 IS NULL,'null',CONCAT('\"',REPLACE(ea.example1,'\"','####'),'\"')),','" +
            ",IF(ea.example2 IS NULL,'null',CONCAT('\"',REPLACE(ea.example2,'\"','####'),'\"'))) " +
            " FROM emotional_annotations ea " +
            "JOIN sense s ON ea.sense_id = s.id " +
            "WHERE s.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSenseIdsAndParseString(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query(value = "SELECT CONCAT(ea.id,','" +
            ",ea.sense_id,','" +
            ",IF(ea.has_emotional_characteristic=1,1,0),','" +
            ",IF(ea.super_anotation=1,1,0),','" +
            ",IF(ea.emotions IS NULL,'null',CONCAT('\"',REPLACE(ea.emotions,'\"','####'),'\"')),','" +
            ",IF(ea.valuations IS NULL,'null',CONCAT('\"',REPLACE(ea.valuations,'\"','####'),'\"')),','" +
            ",IF(ea.markedness IS NULL,'null',CONCAT('\"',REPLACE(ea.markedness,'\"','####'),'\"')),','" +
            ",IF(ea.example1 IS NULL,'null',CONCAT('\"',REPLACE(ea.example1,'\"','####'),'\"')),','" +
            ",IF(ea.example2 IS NULL,'null',CONCAT('\"',REPLACE(ea.example2,'\"','####'),'\"'))) " +
            "FROM emotional_annotations ea " +
            "WHERE ea.id>=:begin AND ea.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Integer begin, @Param("end") Integer end);

    @Query(value = "SELECT CONCAT(ea.id,','" +
            ",ea.sense_id,','" +
            ",IF(ea.has_emotional_characteristic=1,1,0),','" +
            ",IF(ea.super_anotation=1,1,0),','" +
            ",IF(ea.emotions IS NULL,'null',CONCAT('\"',REPLACE(ea.emotions,'\"','####'),'\"')),','" +
            ",IF(ea.valuations IS NULL,'null',CONCAT('\"',REPLACE(ea.valuations,'\"','####'),'\"')),','" +
            ",IF(ea.markedness IS NULL,'null',CONCAT('\"',REPLACE(ea.markedness,'\"','####'),'\"')),','" +
            ",IF(ea.example1 IS NULL,'null',CONCAT('\"',REPLACE(ea.example1,'\"','####'),'\"')),','" +
            ",IF(ea.example2 IS NULL,'null',CONCAT('\"',REPLACE(ea.example2,'\"','####'),'\"'))) " +
            "FROM emotional_annotations ea " +
            "JOIN sense s ON ea.sense_id = s.id " +
            "WHERE ea.sense_id>=:begin AND ea.sense_id<:end " +
            "AND s.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSenseIdsAndParseStringBatch(@Param("lexicon_ids") Integer[] lexicon_ids,
                                                              @Param("begin") Integer begin, @Param("end") Integer end);

    @Query(value = "SELECT MAX(id) FROM emotional_annotations", nativeQuery = true)
    public Integer getMaxIndex();
}
