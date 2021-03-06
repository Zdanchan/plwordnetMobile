package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynsetExampleRepository extends CrudRepository<SynsetExampleEntity, Long> {
    @Query("SELECT se FROM SynsetExampleEntity se WHERE synsetAttributesId IN (:ids)")
    public List<SynsetExampleEntity> findMultipleBySynsetAttributeId(@Param("ids") Long[] ids);
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
            "WHERE s.lexicon_id IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForSynsetAttributesAndParseString(@Param("lexiconIds") Long[] lexiconIds);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.synset_attributes_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "IF(se.type IS NULL,'null',CONCAT('\"',se.type,'\"'))" +
            ") FROM synset_examples se" +
            " WHERE se.id>=:begin AND se.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT CONCAT(" +
            "se.id,','," +
            "se.synset_attributes_id,','," +
            "IF(se.example IS NULL,'null',CONCAT('\"',REPLACE(se.example,'\"','####'),'\"')),','," +
            "IF(se.type IS NULL,'null',CONCAT('\"',se.type,'\"'))" +
            ") FROM synset_examples se " +
            "JOIN synset s ON se.id = s.id " +
            "WHERE se.synset_attributes_id>=:begin AND se.synset_attributes_id<:end " +
            "AND s.lexicon_id IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForSynsetAttributesAndParseStringBatch(@Param("lexiconIds") Long[] lexiconIds,
                                                                      @Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT MAX(id) FROM synset_examples", nativeQuery = true)
    public Long getMaxIndex();
}