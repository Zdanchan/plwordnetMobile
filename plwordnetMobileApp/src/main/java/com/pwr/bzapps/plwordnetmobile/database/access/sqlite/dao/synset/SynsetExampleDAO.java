package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;

import java.util.List;

public class SynsetExampleDAO {

    public static SynsetExampleEntity checkTable(){
        return new Select()
                .from(SynsetExampleEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SynsetExampleEntity> getAll(){
        return new Select()
                .from(SynsetExampleEntity.class)
                .execute();
    }

    public static SynsetExampleEntity findById(Long id){
        return new Select()
                .from(SynsetExampleEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }

    public static List<SynsetExampleEntity> findAllForSynsetAttribute(Long synset_attribute_id){
        return new Select()
                .from(SynsetExampleEntity.class)
                .where("synset_attributes_id = ?",synset_attribute_id)
                .execute();
    }
}
