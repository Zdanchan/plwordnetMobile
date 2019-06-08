package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;
import java.util.List;

public class DomainDAO {

    public static DomainEntity checkTable(){
        return new Select()
                .from(DomainEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<DomainEntity> getAll(){
        return new Select()
                .from(DomainEntity.class)
                .execute();
    }

    public static DomainEntity findById(Long id){
        return new Select()
                .from(DomainEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }
}
