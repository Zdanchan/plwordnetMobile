package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseExampleRepository extends CrudRepository<SenseExampleEntity, Integer> {
    @Query("SELECT se FROM SenseExampleEntity se WHERE se.sense_attribute_id IN (:sense_attribute_ids)")
    public List<SenseExampleEntity> findMultipleBySenseAttributeId(@Param("sense_attribute_ids") Integer[] sense_attribute_ids);
}
