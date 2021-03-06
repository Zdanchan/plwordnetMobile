package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseAttributeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseAttributeRepository extends CrudRepository<SenseAttributeEntity, Long> {
    @Query("SELECT sa FROM SenseAttributeEntity sa WHERE sa.senseId IN (:senseIds)")
    public List<SenseAttributeEntity> findMultipleBySenseId(@Param("senseIds") Long[] senseIds);
    @Query("SELECT sa.id FROM SenseAttributeEntity sa WHERE sa.senseId IN (:senseIds)")
    public List<Long> findIdsMultipleBySenseId(@Param("senseIds") Long[] senseIds);

    @Query(value = "SELECT CONCAT(" +
            "sa.sense_id,','," +
            "IF(sa.comment IS NULL,'null',CONCAT('\"',REPLACE(sa.comment,'\"','####'),'\"')),','," +
            "IF(sa.definition IS NULL,'null',CONCAT('\"',REPLACE(sa.definition,'\"','####'),'\"')),','," +
            "IF(sa.link IS NULL,'null',CONCAT('\"',REPLACE(sa.link,'\"','####'),'\"')),','," +
            "IF(sa.register_id IS NULL,'null',sa.register_id),','," +
            "IF(sa.aspect_id IS NULL,'null',sa.aspect_id),','," +
            "IF(sa.user_id IS NULL,'null',sa.user_id),','," +
            "IF(sa.error_comment IS NULL,'null',CONCAT('\"',REPLACE(sa.error_comment,'\"','####'),'\"')),','," +
            "IF(sa.proper_name=1,1,0)" +
            ")FROM sense_attributes sa", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "sa.sense_id,','," +
            "IF(sa.comment IS NULL,'null',CONCAT('\"',REPLACE(sa.comment,'\"','####'),'\"')),','," +
            "IF(sa.definition IS NULL,'null',CONCAT('\"',REPLACE(sa.definition,'\"','####'),'\"')),','," +
            "IF(sa.link IS NULL,'null',CONCAT('\"',REPLACE(sa.link,'\"','####'),'\"')),','," +
            "IF(sa.register_id IS NULL,'null',sa.register_id),','," +
            "IF(sa.aspect_id IS NULL,'null',sa.aspect_id),','," +
            "IF(sa.user_id IS NULL,'null',sa.user_id),','," +
            "IF(sa.error_comment IS NULL,'null',CONCAT('\"',REPLACE(sa.error_comment,'\"','####'),'\"')),','," +
            "IF(sa.proper_name=1,1,0)" +
            ")FROM sense_attributes sa " +
            "JOIN sense s ON sa.sense_id = s.id " +
            "WHERE s.lexicon_id IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForSensesAndParseString(@Param("lexiconIds") Long[] lexiconIds);
    @Query(value = "SELECT CONCAT(" +
            "sa.sense_id,','," +
            "IF(sa.comment IS NULL,'null',CONCAT('\"',REPLACE(sa.comment,'\"','####'),'\"')),','," +
            "IF(sa.definition IS NULL,'null',CONCAT('\"',REPLACE(sa.definition,'\"','####'),'\"')),','," +
            "IF(sa.link IS NULL,'null',CONCAT('\"',REPLACE(sa.link,'\"','####'),'\"')),','," +
            "IF(sa.register_id IS NULL,'null',sa.register_id),','," +
            "IF(sa.aspect_id IS NULL,'null',sa.aspect_id),','," +
            "IF(sa.user_id IS NULL,'null',sa.user_id),','," +
            "IF(sa.error_comment IS NULL,'null',CONCAT('\"',REPLACE(sa.error_comment,'\"','####'),'\"')),','," +
            "IF(sa.proper_name=1,1,0)" +
            ")FROM sense_attributes sa" +
            " WHERE sa.sense_id>=:begin AND sa.sense_id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT CONCAT(" +
            "sa.sense_id,','," +
            "IF(sa.comment IS NULL,'null',CONCAT('\"',REPLACE(sa.comment,'\"','####'),'\"')),','," +
            "IF(sa.definition IS NULL,'null',CONCAT('\"',REPLACE(sa.definition,'\"','####'),'\"')),','," +
            "IF(sa.link IS NULL,'null',CONCAT('\"',REPLACE(sa.link,'\"','####'),'\"')),','," +
            "IF(sa.register_id IS NULL,'null',sa.register_id),','," +
            "IF(sa.aspect_id IS NULL,'null',sa.aspect_id),','," +
            "IF(sa.user_id IS NULL,'null',sa.user_id),','," +
            "IF(sa.error_comment IS NULL,'null',CONCAT('\"',REPLACE(sa.error_comment,'\"','####'),'\"')),','," +
            "IF(sa.proper_name=1,1,0)" +
            ")FROM sense_attributes sa " +
            "JOIN sense s ON sa.sense_id = s.id " +
            "WHERE sa.sense_id>=:begin AND sa.sense_id<:end " +
            "AND s.lexicon_id IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForSensesAndParseStringBatch(@Param("lexiconIds") Long[] lexiconIds, @Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT MAX(sense_id) FROM sense_attributes", nativeQuery = true)
    public Long getMaxIndex();
}
