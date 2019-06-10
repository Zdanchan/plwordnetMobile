package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.util.List;


public class LexiconDAO {

    public static LexiconEntity checkTable(){
        return new Select()
                .from(LexiconEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<LexiconEntity> getAll(){
        return new Select()
                .from(LexiconEntity.class)
                .execute();
    }

    public static LexiconEntity findById(Long id){
        return new Select()
                .from(LexiconEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
