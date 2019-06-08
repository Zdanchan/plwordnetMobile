package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;

import java.util.List;

public class PartOfSpeechDAO {

    public static PartOfSpeechEntity checkTable(){
        return new Select()
                .from(PartOfSpeechEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<PartOfSpeechEntity> getAll(){
        return new Select()
                .from(PartOfSpeechEntity.class)
                .execute();
    }

    public static PartOfSpeechEntity findById(Long id){
        return new Select()
                .from(PartOfSpeechEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
