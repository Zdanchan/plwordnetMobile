package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedLexiconEntity;

import java.util.List;

public class RelationTypeAllowedLexiconDAO {

    public static RelationTypeAllowedLexiconEntity checkTable(){
        return new Select()
                .from(RelationTypeAllowedLexiconEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<RelationTypeAllowedLexiconEntity> getAll(){
        return new Select()
                .from(RelationTypeAllowedLexiconEntity.class)
                .execute();
    }

    public static RelationTypeAllowedLexiconEntity findById(Long relation_type_id, Long lexicon_id){
        return new Select()
                .from(RelationTypeAllowedLexiconEntity.class)
                .where("relation_type_id = ?",relation_type_id)
                .and("lexicon_id = ?",lexicon_id)
                .executeSingle();
    }
}
