package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import android.content.Context;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
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
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;

public class SQLiteConnector {

    private static Context context;

    public static void reloadDatabaseInstance(){
        reloadDatabaseInstance(context);
    }

    public static void reloadDatabaseInstance(Context context){
        if(Cache.isInitialized()) {
            ActiveAndroid.dispose();
        }
        if(SQLiteConnector.context==null)
            SQLiteConnector.context=context;
        if(!SQLiteDBFileManager.getInstance().getSqliteDbFile(Settings.getDbType()).exists()) {
            return;
        }
        try {
            Configuration.Builder builder =
                    new Configuration.Builder(SQLiteConnector.context)
                            .setDatabaseName(
                                    SQLiteDBFileManager.getInstance().getSqliteDbFile(Settings.getDbType()).getAbsolutePath());

            builder.addModelClass(ApplicationLocalisedStringEntity.class);
            builder.addModelClass(DictionaryEntity.class);
            builder.addModelClass(DomainEntity.class);
            builder.addModelClass(LexiconEntity.class);
            builder.addModelClass(EmotionalAnnotationEntity.class);
            builder.addModelClass(PartOfSpeechEntity.class);
            builder.addModelClass(WordEntity.class);
            builder.addModelClass(RelationTypeAllowedLexiconEntity.class);
            builder.addModelClass(RelationTypeAllowedPartOfSpeechEntity.class);
            builder.addModelClass(RelationTypeEntity.class);
            builder.addModelClass(SenseAttributeEntity.class);
            builder.addModelClass(SenseEntity.class);
            builder.addModelClass(SenseExampleEntity.class);
            builder.addModelClass(SenseRelationEntity.class);
            builder.addModelClass(SynsetAttributeEntity.class);
            builder.addModelClass(SynsetEntity.class);
            builder.addModelClass(SynsetExampleEntity.class);
            builder.addModelClass(SynsetRelationEntity.class);
            Configuration dbConfiguration = builder.create();
            ActiveAndroid.initialize(dbConfiguration);
        }catch (Exception e){
            Settings.setOfflineMode(false);
        }

    }

    public static void setContext(Context context) {
        SQLiteConnector.context = context;
    }
}
