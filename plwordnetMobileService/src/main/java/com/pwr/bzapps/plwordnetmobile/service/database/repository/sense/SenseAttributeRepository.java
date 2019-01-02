package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseAttributeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseAttributeRepository extends CrudRepository<SenseAttributeEntity, Integer> {
    @Query("SELECT sa FROM SenseAttributeEntity sa WHERE sa.sense_id IN (:sense_ids)")
    public List<SenseAttributeEntity> findMultipleBySenseId(@Param("sense_ids") Integer[] sense_ids);
    @Query("SELECT sa.id FROM SenseAttributeEntity sa WHERE sa.sense_id IN (:sense_ids)")
    public List<Integer> findIdsMultipleBySenseId(@Param("sense_ids") Integer[] sense_ids);
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
            ")FROM sense_attributes sa WHERE sa.sense_id IN (:sense_ids)", nativeQuery = true)
    public List<String> findAllForSensesAndParseString(@Param("sense_ids") Integer[] sense_ids);
}
