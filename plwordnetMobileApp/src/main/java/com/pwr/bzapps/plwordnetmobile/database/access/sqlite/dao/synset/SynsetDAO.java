package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;

import java.util.List;

public class SynsetDAO {

    public static SynsetEntity checkTable(){
        return new Select()
                .from(SynsetEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SynsetEntity> getAll(){
        return new Select()
                .from(SynsetEntity.class)
                .execute();
    }

    public static SynsetEntity findById(Long id){
        return new Select()
                .from(SynsetEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
