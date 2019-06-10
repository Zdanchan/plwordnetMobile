package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseExampleRepository extends CrudRepository<SenseExampleEntity, Long> {
    @Query("SELECT se FROM SenseExampleEntity se WHERE senseAttributeId IN (:ids)")
    public List<SenseExampleEntity> findMultipleBySenseAttributeId(@Param("ids") Long[] ids);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') FROM sense_examples se", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') FROM sense_examples se " +
            "JOIN sense s ON se.id = s.id " +
            "WHERE s.lexicon_id IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForSenseAttributesAndParseString(@Param("lexiconIds") Long[] lexiconIds);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') " +
            "FROM sense_examples se" +
            " WHERE se.id>=:begin AND se.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') " +
            "FROM sense_examples se " +
            "JOIN sense s ON se.id = s.id " +
            "WHERE se.sense_attribute_id>=:begin AND se.sense_attribute_id<:end " +
            "AND s.lexicon_id IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForSenseAttributesAndParseStringBatch(@Param("lexiconIds") Long[] lexiconIds,
                                                                     @Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT MAX(id) FROM sense_examples", nativeQuery = true)
    public Long getMaxIndex();

}
