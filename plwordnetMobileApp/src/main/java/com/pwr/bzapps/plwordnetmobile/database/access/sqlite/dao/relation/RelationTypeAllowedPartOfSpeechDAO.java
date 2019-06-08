package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;

import java.util.List;

public class RelationTypeAllowedPartOfSpeechDAO {

    public static RelationTypeAllowedPartOfSpeechEntity checkTable(){
        return new Select()
                .from(RelationTypeAllowedPartOfSpeechEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<RelationTypeAllowedPartOfSpeechEntity> getAll(){
        return new Select()
                .from(RelationTypeAllowedPartOfSpeechEntity.class)
                .execute();
    }

    public static RelationTypeAllowedPartOfSpeechEntity findById(Long relation_type_id, Long part_of_speech_id){
        return new Select()
                .from(RelationTypeAllowedPartOfSpeechEntity.class)
                .where("relation_type_id = ?",relation_type_id)
                .and("part_of_speech_id = ?",part_of_speech_id)
                .executeSingle();
    }
}
