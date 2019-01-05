package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SenseDAO {
    static final String HEADER = "SELECT s.id, s.status_id, s.synset_position, s.variant, s.synset_id, s.domain_id, " +
            "s.lexicon_id, s.word_id, s.part_of_speech_id FROM " + SQLiteTablesConstNames.SENSE_NAME + " AS s  ";

    public Collection<SenseEntity> getAll(){
        String query = HEADER;
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class);
        return results;
    }

    public SenseEntity findById(Integer id){
        if(EntityManager.contains("Se:"+id)){
            return (SenseEntity)EntityManager.getEntity("Se:"+id);
        }
        String query = HEADER
                + " WHERE s.id = " + id;
        SenseEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,SenseEntity.class);
        return result;
    }

    public Collection<SenseEntity> findMultipleByIds(Integer[] ids){
        if(ids.length == 0)
            return new ArrayList<SenseEntity>();
        String query = HEADER
                + " WHERE s.id IN (" + StringUtil.parseIntegerArrayToString(ids) + ")";
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class);
        return results;
    }

    public Collection<SenseEntity> findBySynsetId(Integer id){
        String query = HEADER
                + " WHERE s.synset_id = " + id;
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class);
        return results;
    }

    public Collection<SenseEntity> findMultipleBySynsetIds(Integer[] ids){
        if(ids.length == 0)
            return new ArrayList<SenseEntity>();
        String query = HEADER
                + " WHERE s.synset_id IN (" + StringUtil.parseIntegerArrayToString(ids) + ")";
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class);
        return results;
    }

    public Collection<SenseEntity> findByWord(String word){
        if("".equals(word))
            return new ArrayList<SenseEntity>();
        String query = HEADER
                + " JOIN word AS w ON s.word_id = w.id"
                + " WHERE LOWER(w.word) LIKE LOWER('%" + word + "%')"
                + " ORDER BY w.word, variant, part_of_speech_id, lexicon_id ASC";
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class);
        return results;
    }

    public Collection<SenseEntity> findByWord(String word, int resultLimit){
        if("".equals(word))
            return new ArrayList<SenseEntity>();
        String query = HEADER
                + " JOIN word AS w ON s.word_id = w.id"
                + " WHERE LOWER(w.word) LIKE LOWER('%" + word + "%')"
                + " ORDER BY w.word, s.variant, s.lexicon_id, s.part_of_speech_id ASC";
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class,resultLimit);
        return results;
    }

    public Collection<SenseEntity> findRelatedForWord(String word){
        String query = HEADER
                + " JOIN word AS w ON s.word_id = w.id"
                + " WHERE w.word= '" + word + "'";
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class);
        return results;
    }

    public Collection<SenseEntity> findRelatedForWordAndLanguage(String word, String language){
        String query = HEADER
                + " JOIN word AS w ON s.word_id = w.id JOIN lexicon AS l ON l.id = s.lexicon_id"
                + " WHERE w.word= '" + word + "'"
                + " AND LOWER(l.language_name) LIKE LOWER('" + language + "')";
        Collection<SenseEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseEntity.class);
        return results;
    }
}
