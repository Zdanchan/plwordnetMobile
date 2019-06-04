package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface SynsetRelationDAO {

    @Query("SELECT * FROM synset_relation AS sr LIMIT 1")
    public SynsetRelationEntity checkTable();

    @Query("SELECT * FROM synset_relation AS sr")
    public List<SynsetRelationEntity> getAll();

    @Query("SELECT * FROM synset_relation AS sr WHERE sr.id = :id")
    public SynsetRelationEntity findById(Long id);

    @Query("SELECT * FROM synset_relation AS sr WHERE sr.parent_synset_id = :parent_synset_id")
    public List<SynsetRelationEntity> findChildrenByParentId(Long parent_synset_id);

    @Query("SELECT * FROM synset_relation AS sr WHERE sr.child_synset_id = :child_synset_id")
    public List<SynsetRelationEntity> findParentsByChildId(Long child_synset_id);
}
