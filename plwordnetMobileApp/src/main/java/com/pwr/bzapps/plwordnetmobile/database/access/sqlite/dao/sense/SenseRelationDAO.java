package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseRelationEntity;

import java.util.List;

public class SenseRelationDAO {

    public static SenseRelationEntity checkTable(){
        return new Select()
                .from(SenseRelationEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SenseRelationEntity> getAll(){
        return new Select()
                .from(SenseRelationEntity.class)
                .execute();
    }

    public static SenseRelationEntity findById(Long id){
        return new Select()
                .from(SenseRelationEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
