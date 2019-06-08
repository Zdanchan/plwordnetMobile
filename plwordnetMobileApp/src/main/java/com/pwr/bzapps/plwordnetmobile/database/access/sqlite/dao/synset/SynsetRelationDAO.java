package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;

import java.util.List;

public class SynsetRelationDAO {

    public static SynsetRelationEntity checkTable(){
        return new Select()
                .from(SynsetRelationEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SynsetRelationEntity> getAll(){
        return new Select()
                .from(SynsetRelationEntity.class)
                .execute();
    }

    public static SynsetRelationEntity findById(Long id){
        return new Select()
                .from(SynsetRelationEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }

    public static List<SynsetRelationEntity> findChildrenByParentId(Long parent_synset_id){
        return new Select()
                .from(SynsetRelationEntity.class)
                .where("parent_synset_id = ?",parent_synset_id)
                .execute();
    }

    public static List<SynsetRelationEntity> findParentsByChildId(Long child_synset_id){
        return new Select()
                .from(SynsetRelationEntity.class)
                .where("child_synset_id = ?",child_synset_id)
                .execute();
    }
}
