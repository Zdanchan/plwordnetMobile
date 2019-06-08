package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;

import java.util.List;


public class EmotionalAnnotationDAO {

    public static EmotionalAnnotationEntity checkTable(){
        return new Select()
                .from(EmotionalAnnotationEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<EmotionalAnnotationEntity> getAll(){
        return new Select()
                .from(EmotionalAnnotationEntity.class)
                .execute();
    }

    public static EmotionalAnnotationEntity findById(Long id){
        return new Select()
                .from(EmotionalAnnotationEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }

    public static List<EmotionalAnnotationEntity> findAllForSenseId(Long sense_id){
        return new Select()
                .from(EmotionalAnnotationEntity.class)
                .where("sense_id = ?",sense_id)
                .execute();
    }
}
