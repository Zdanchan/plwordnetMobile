package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;

import java.util.List;

public class SenseExampleDAO {

    public static SenseExampleEntity checkTable(){
        return new Select()
                .from(SenseExampleEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SenseExampleEntity> getAll(){
        return new Select()
                .from(SenseExampleEntity.class)
                .execute();
    }

    public static SenseExampleEntity findById(Long id){
        return new Select()
                .from(SenseExampleEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }

    public static List<SenseExampleEntity> findAllForSenseAttribute(Long sense_attribute_id){
        return new Select()
                .from(SenseExampleEntity.class)
                .where("sense_attribute_id = ?",sense_attribute_id)
                .execute();
    }
}
