package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;

import java.util.List;

public class SenseAttributeDAO {


    public static SenseAttributeEntity checkTable(){
        return new Select()
                .from(SenseAttributeEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SenseAttributeEntity> getAll(){
        return new Select()
                .from(SenseAttributeEntity.class)
                .execute();
    }

    public static SenseAttributeEntity findById(Integer id){
        return new Select()
                .from(SenseAttributeEntity.class)
                .where("sense_id = ?",id)
                .executeSingle();
    }

    public static List<SenseAttributeEntity> findAllForSenseId(Long sense_id){
        return new Select()
                .from(SenseAttributeEntity.class)
                .where("sense_id = ?",sense_id)
                .execute();
    }
}
