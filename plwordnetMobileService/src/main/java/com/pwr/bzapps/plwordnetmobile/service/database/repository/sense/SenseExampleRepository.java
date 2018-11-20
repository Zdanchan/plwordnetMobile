package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseExampleRepository extends CrudRepository<SenseExampleEntity, Integer> {
    @Query("SELECT se FROM SenseExampleEntity se WHERE sense_attribute_id IN (:ids)")
    public List<SenseExampleEntity> findMultipleBySenseAttributeId(@Param("ids") Integer[] ids);
}
