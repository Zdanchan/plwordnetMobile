package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynsetExampleRepository extends CrudRepository<SynsetExampleEntity, Integer> {
    @Query("SELECT se FROM SynsetExampleEntity se WHERE synset_attributes_id IN (:ids)")
    public List<SynsetExampleEntity> findMultipleBySynsetAttributeId(@Param("ids") Integer[] ids);
}
