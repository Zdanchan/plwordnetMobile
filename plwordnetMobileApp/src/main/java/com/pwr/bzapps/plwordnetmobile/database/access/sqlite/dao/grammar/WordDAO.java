package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;

import java.util.List;

public class WordDAO {

    public static WordEntity checkTable(){
        return new Select()
                .from(WordEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<WordEntity> getAll(){
        return new Select()
                .from(WordEntity.class)
                .execute();
    }

    public static WordEntity findById(Long id){
        return new Select()
                .from(WordEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
