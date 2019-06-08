package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;

import java.util.List;

public class DictionaryDAO {

    public static DictionaryEntity checkTable(){
        return new Select()
                .from(DictionaryEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<DictionaryEntity> getAll(){
        return new Select()
                .from(DictionaryEntity.class)
                .execute();
    }

    public static DictionaryEntity findById(Long id){
        return new Select()
                .from(DictionaryEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
