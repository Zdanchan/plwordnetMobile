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
            "'\\'',sa.comment,'\\'',','," +
            "'\\'',sa.definition,'\\'',','," +
            "'\\'',sa.link,'\\'',','," +
            "sa.register_id,','," +
            "sa.aspect_id,','," +
            "sa.user_id,','," +
            "'\\'',sa.error_comment,'\\'',','," +
            "sa.proper_name,','" +
            ")FROM SenseAttributeEntity sa", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "sa.sense_id,','," +
            "'\\'',sa.comment,'\\'',','," +
            "'\\'',sa.definition,'\\'',','," +
            "'\\'',sa.link,'\\'',','," +
            "sa.register_id,','," +
            "sa.aspect_id,','," +
            "sa.user_id,','," +
            "'\\'',sa.error_comment,'\\'',','," +
            "sa.proper_name,','" +
            ")FROM SenseAttributeEntity sa WHERE sa.sense_id IN (:sense_ids)", nativeQuery = true)
    public List<String> findAllForSensesAndParseString(@Param("sense_ids") Integer[] sense_ids);
}
