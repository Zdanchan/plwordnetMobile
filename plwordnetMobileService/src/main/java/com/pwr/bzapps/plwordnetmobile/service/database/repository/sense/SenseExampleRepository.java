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
            "'\"',se.type,'\"') FROM sense_examples se WHERE se.sense_attribute_id IN (:sense_attribute_ids)", nativeQuery = true)
    public List<String> findAllForSenseAttributesAndParseString(@Param("sense_attribute_ids") Integer[] sense_attribute_ids);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') FROM sense_examples se" +
            " WHERE se.id>=:begin AND se.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.sense_attribute_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "'\"',se.type,'\"') FROM sense_examples se WHERE se.sense_attribute_id IN (:sense_attribute_ids)" +
            " AND se.sense_attribute_id>=:begin AND se.sense_attribute_id<:end", nativeQuery = true)
    public List<String> findAllForSenseAttributesAndParseStringBatch(@Param("sense_attribute_ids") Integer[] sense_attribute_ids,
                                                                     @Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT MAX(id) FROM sense_examples", nativeQuery = true)
    public Integer getMaxIndex();

}
