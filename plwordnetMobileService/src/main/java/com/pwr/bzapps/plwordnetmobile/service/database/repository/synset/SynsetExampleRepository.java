package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynsetExampleRepository extends CrudRepository<SynsetExampleEntity, Integer> {
    @Query("SELECT se FROM SynsetExampleEntity se WHERE synset_attributes_id IN (:ids)")
    public List<SynsetExampleEntity> findMultipleBySynsetAttributeId(@Param("ids") Integer[] ids);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.synset_attributes_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "IF(se.type IS NULL,'null',CONCAT('\"',se.type,'\"'))" +
            ") FROM synset_examples se", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.synset_attributes_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "IF(se.type IS NULL,'null',CONCAT('\"',se.type,'\"'))" +
            ") FROM synset_examples se " +
            "JOIN synset s ON se.id = s.id " +
            "WHERE s.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSynsetAttributesAndParseString(@Param("lexicon_ids") Integer[] lexicon_ids);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.synset_attributes_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "IF(se.type IS NULL,'null',CONCAT('\"',se.type,'\"'))" +
            ") FROM synset_examples se" +
            " WHERE se.id>=:begin AND se.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.synset_attributes_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "IF(se.type IS NULL,'null',CONCAT('\"',se.type,'\"'))" +
            ") FROM synset_examples se " +
            "JOIN synset s ON se.id = s.id " +
            "WHERE se.synset_attributes_id>=:begin AND se.synset_attributes_id<:end " +
            "AND s.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSynsetAttributesAndParseStringBatch(@Param("lexicon_ids") Integer[] lexicon_ids, @Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT MAX(id) FROM synset_examples", nativeQuery = true)
    public Integer getMaxIndex();
}