package com.pwr.bzapps.plwordnetmobile.database.entity.application;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `identifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Short identification string representing lexicon',
 *   `language_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Language of lexicon',
 *   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 *   `lexicon_version` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Lexicon name',
 * */
@android.arch.persistence.room.Entity(tableName = "lexicon")
public class LexiconEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "identifier")
    private String identifier;
    @ColumnInfo(name = "language_name")
    private String languageName;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "lexicon_version")
    private String lexiconVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = "null".equals(identifier) ? null : identifier;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = "null".equals(languageName) ? null : languageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = "null".equals(name) ? null : name;
    }

    public String getLexiconVersion() {
        return lexiconVersion;
    }

    public void setLexiconVersion(String lexiconVersion) {
        this.lexiconVersion = "null".equals(lexiconVersion) ? null : lexiconVersion;
    }

    @Override
    public String getEntityID() {
        return "Le:" + getId();
    }
}
