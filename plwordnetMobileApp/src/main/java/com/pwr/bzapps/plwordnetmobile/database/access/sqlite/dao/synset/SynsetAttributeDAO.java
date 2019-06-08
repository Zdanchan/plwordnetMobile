package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;

import java.util.List;

public class SynsetAttributeDAO {


    public static SynsetAttributeEntity checkTable(){
        return new Select()
                .from(SynsetAttributeEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SynsetAttributeEntity> getAll(){
        return new Select()
                .from(SynsetAttributeEntity.class)
                .execute();
    }

    public static SynsetAttributeEntity findById(Long id){
        return new Select()
                .from(SynsetAttributeEntity.class)
                .where("synset_id = ?",id)
                .executeSingle();
    }

    public static List<SynsetAttributeEntity> findAllForSynsetId(Long synset_id){
        return new Select()
                .from(SynsetAttributeEntity.class)
                .where("synset_id = ?",synset_id)
                .execute();
    }
}
