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
            ",'\"',ea.emotions,'\"',','" +
            ",'\"',ea.valuations,'\"',','" +
            ",'\"',ea.markedness,'\"',','" +
            ",'\"',ea.example1,'\"',','" +
            ",'\"',ea.example2,'\"') FROM emotional_annotations ea", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT(ea.id,','" +
            ",ea.sense_id,','" +
            ",IF(ea.has_emotional_characteristic=1,1,0),','" +
            ",IF(ea.super_anotation=1,1,0),','" +
            ",'\"',ea.emotions,'\"',','" +
            ",'\"',ea.valuations,'\"',','" +
            ",'\"',ea.markedness,'\"',','" +
            ",'\"',ea.example1,'\"',','" +
            ",'\"',ea.example2,'\"') FROM emotional_annotations ea" +
            " WHERE ea.id>=:begin AND ea.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Integer begin, @Param("end") Integer end);

    @Query(value = "SELECT MAX(id) FROM emotional_annotations", nativeQuery = true)
    public Integer getMaxIndex();
}
