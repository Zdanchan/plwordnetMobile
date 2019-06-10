package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.util.List;


public class RelationTypeDAO {

    public static RelationTypeEntity checkTable(){
        return new Select()
                .from(RelationTypeEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<RelationTypeEntity> getAll(){
        return new Select()
                .from(RelationTypeEntity.class)
                .execute();
    }

    public static RelationTypeEntity findById(Long id){
        return new Select()
                .from(RelationTypeEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
