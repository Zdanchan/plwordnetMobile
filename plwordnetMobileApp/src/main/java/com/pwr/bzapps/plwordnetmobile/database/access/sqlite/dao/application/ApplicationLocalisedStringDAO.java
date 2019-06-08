package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.List;

public class ApplicationLocalisedStringDAO {

    public static ApplicationLocalisedStringEntity checkTable(){
        return new Select()
                .from(ApplicationLocalisedStringEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<ApplicationLocalisedStringEntity> getAll(){
        return new Select()
                .from(ApplicationLocalisedStringEntity.class)
                .execute();
    }

    public static ApplicationLocalisedStringEntity findById(Long id){
        return new Select()
                .from(ApplicationLocalisedStringEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }

    public static List<ApplicationLocalisedStringEntity> findByMultipleIds(Long[] ids){
        return new Select()
                .from(ApplicationLocalisedStringEntity.class)
                .where("id IN (" + StringUtil.parseLongArrayToString(ids) + ")")
                .execute();
    }
}
