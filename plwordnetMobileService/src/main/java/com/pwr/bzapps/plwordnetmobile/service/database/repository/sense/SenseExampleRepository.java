package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseExampleRepository extends CrudRepository<SenseExampleEntity, Integer> {
    @Query("SELECT se FROM SenseExampleEntity se WHERE sense_attribute_id IN (:ids)")
    public List<SenseExampleEntity> findMultipleBySenseAttributeId(@Param("ids") Integer[] ids);
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
            "WHERE s.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSenseAttributesAndParseString(@Param("lexicon_ids") Integer[] lexicon_ids);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') " +
            "FROM sense_examples se" +
            " WHERE se.id>=:begin AND se.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') " +
            "FROM sense_examples se " +
            "JOIN sense s ON se.id = s.id " +
            "WHERE se.sense_attribute_id>=:begin AND se.sense_attribute_id<:end " +
            "AND s.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSenseAttributesAndParseStringBatch(@Param("lexicon_ids") Integer[] lexicon_ids,
                                                                     @Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT MAX(id) FROM sense_examples", nativeQuery = true)
    public Integer getMaxIndex();

}
