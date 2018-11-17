package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynsetRepository extends CrudRepository<SynsetEntity, Integer> {

    @Query("SELECT s FROM SynsetEntity s WHERE id IN (:ids)")
    public List<SynsetEntity> findMultipleByIds(@Param("ids") Integer[] ids);

    @Query("SELECT s FROM SynsetEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<SynsetEntity> findAllForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query("SELECT s.id FROM SynsetEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<Integer> findIdsForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);
}
