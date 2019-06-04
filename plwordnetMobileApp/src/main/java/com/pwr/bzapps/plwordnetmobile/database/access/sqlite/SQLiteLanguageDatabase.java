package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.ApplicationLocalisedStringDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.DictionaryDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.DomainDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.LexiconDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.EmotionalAnnotationDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.PartOfSpeechDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.WordDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation.RelationTypeAllowedLexiconDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation.RelationTypeAllowedPartOfSpeechDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation.RelationTypeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseAttributeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseRelationDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetAttributeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetRelationDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedLexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseRelationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;

@Database(entities = {ApplicationLocalisedStringEntity.class,
        DictionaryEntity.class,
        DomainEntity.class,
        LexiconEntity.class,
        EmotionalAnnotationEntity.class,
        PartOfSpeechEntity.class,
        WordEntity.class,
        RelationTypeAllowedLexiconEntity.class,
        RelationTypeAllowedPartOfSpeechEntity.class,
        RelationTypeEntity.class,
        SenseAttributeEntity.class,
        SenseEntity.class,
        SenseExampleEntity.class,
        SenseRelationEntity.class,
        SynsetAttributeEntity.class,
        SynsetEntity.class,
        SynsetExampleEntity.class,
        SynsetRelationEntity.class
        },
        version = 1,
        exportSchema = false)
public abstract class SQLiteLanguageDatabase extends RoomDatabase {
    public abstract ApplicationLocalisedStringDAO applicationLocalisedStringDAO();
    public abstract DictionaryDAO dictionaryDAO();
    public abstract DomainDAO domainDAO();
    public abstract LexiconDAO lexiconDAO();
    public abstract EmotionalAnnotationDAO emotionalAnnotationDAO();
    public abstract PartOfSpeechDAO partOfSpeechDAO();
    public abstract WordDAO wordDAO();
    public abstract RelationTypeAllowedLexiconDAO relationTypeAllowedLexiconDAO();
    public abstract RelationTypeAllowedPartOfSpeechDAO relationTypeAllowedPartOfSpeechDAO();
    public abstract RelationTypeDAO relationTypeDAO();
    public abstract SenseAttributeDAO senseAttributeDAO();
    public abstract SenseDAO senseDAO();
    public abstract SenseExampleDAO senseExampleDAO();
    public abstract SenseRelationDAO senseRelationDAO();
    public abstract SynsetAttributeDAO synsetAttributeDAO();
    public abstract SynsetDAO synsetDAO();
    public abstract SynsetExampleDAO synsetExampleDAO();
    public abstract SynsetRelationDAO synsetRelationDAO();
}
