package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetAttributeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynsetAttributeRepository extends CrudRepository<SynsetAttributeEntity, Integer> {
    @Query("SELECT sa FROM SynsetAttributeEntity sa WHERE sa.synset_id IN (:synset_ids)")
    public List<SynsetAttributeEntity> findMultipleBySynsetId(@Param("synset_ids") Integer[] synset_ids);
    @Query("SELECT sa.id FROM SynsetAttributeEntity sa WHERE sa.synset_id IN (:synset_ids)")
    public List<Integer> findIdsMultipleBySynsetId(@Param("synset_ids") Integer[] synset_ids);
}
