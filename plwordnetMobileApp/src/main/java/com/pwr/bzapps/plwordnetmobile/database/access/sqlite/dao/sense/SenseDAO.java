package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.activeandroid.query.Select;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.List;

public class SenseDAO {

    public static SenseEntity checkTable(){
        return new Select()
                .from(SenseEntity.class)
                .limit("1")
                .executeSingle();
    }

    public static List<SenseEntity> getAll(){
        return new Select()
                .from(SenseEntity.class)
                .execute();
    }

    public static SenseEntity findById(Long id){
        return new Select()
                .from(SenseEntity.class)
                .where("id = ?",id)
                .executeSingle();
    }

    public static List<SenseEntity> findMultipleByIds(Long[] ids){
        return new Select()
                .from(SenseEntity.class)
                .where("id IN (" +StringUtil.parseLongArrayToString(ids)+ ")")
                .execute();
    }

    public static List<SenseEntity> findBySynsetId(Long id){
        return new Select()
                .from(SenseEntity.class)
                .where("synset_id = ?",id)
                .execute();
    }

    public static List<SenseEntity> findMultipleBySynsetIds(Long[] ids){
        return new Select()
                .from(SenseEntity.class)
                .where("synset_id IN (" +StringUtil.parseLongArrayToString(ids)+ ")")
                .execute();
    }

    public static List<SenseEntity> findByWord(String word){
        return new Select()
                .from(SenseEntity.class)
                .join(WordEntity.class)
                .as("w")
                .on("w.id = word_id")
                .where("LOWER(w.word) LIKE LOWER(?)","%"+word+"%")
                .orderBy("LENGTH(w.word), w.word, variant, part_of_speech_id, lexicon_id ASC")
                .execute();
    }

    public static List<SenseEntity> findByWord(String word, int resultLimit){
        return new Select()
                .from(SenseEntity.class)
                .join(WordEntity.class)
                .as("w")
                .on("w.id = word_id")
                .where("LOWER(w.word) LIKE LOWER(?)","%"+word+"%")
                .orderBy("LENGTH(w.word), w.word, variant, part_of_speech_id, lexicon_id ASC")
                .limit("" + resultLimit)
                .execute();
    }

    public static List<SenseEntity> findRelatedForWord(String word){
        return new Select()
                .from(SenseEntity.class)
                .join(WordEntity.class)
                .as("w")
                .on("w.id = word_id")
                .where("w.word = ?", word)
                .execute();
    }

    public static List<SenseEntity> findRelatedForWordAndLanguage(String word, String language){
        return new Select()
                .from(SenseEntity.class)
                .join(WordEntity.class)
                .as("w")
                .on("w.id = word_id")
                .join(LexiconEntity.class)
                .as("l")
                .on("l.id = lexicon_id")
                .where("w.word = ?", word)
                .and("LOWER(l.language_name) LIKE LOWER(?)",language)
                .execute();
    }

    public static List<SenseEntity> findRelatedForWordLanguageAndPartOfSpeech(String word,
                                                                             String language,
                                                                       Long part_of_speech){
        return new Select()
                .from(SenseEntity.class)
                .join(WordEntity.class)
                .as("w")
                .on("w.id = word_id")
                .join(LexiconEntity.class)
                .as("l")
                .on("l.id = lexicon_id")
                .where("w.word = ?", word)
                .and("LOWER(l.language_name) LIKE LOWER(?)",language)
                .and("part_of_speech_id = ?",part_of_speech)
                .execute();
    }
}
