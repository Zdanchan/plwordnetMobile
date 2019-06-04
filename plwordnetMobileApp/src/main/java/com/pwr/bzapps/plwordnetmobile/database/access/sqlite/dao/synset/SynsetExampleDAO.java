package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.Collection;
import java.util.List;

@Dao
public interface SynsetExampleDAO {

    @Query("SELECT * FROM synset_examples AS se LIMIT 1")
    public SynsetExampleEntity checkTable();

    @Query("SELECT * FROM synset_examples AS se")
    public List<SynsetExampleEntity> getAll();

    @Query("SELECT * FROM synset_examples AS se WHERE se.id = :id")
    public SynsetExampleEntity findById(Long id);

    @Query("SELECT * FROM synset_examples AS se WHERE se.synset_attributes_id = :synset_attribute_id")
    public List<SynsetExampleEntity> findAllForSynsetAttribute(Long synset_attribute_id);
}
