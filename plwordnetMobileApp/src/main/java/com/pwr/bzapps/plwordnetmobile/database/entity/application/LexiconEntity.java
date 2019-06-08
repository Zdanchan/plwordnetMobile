package com.pwr.bzapps.plwordnetmobile.database.entity.application;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `identifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Short identification string representing lexicon',
 *   `language_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Language of lexicon',
 *   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 *   `lexicon_version` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Lexicon name',
 * */
@Table(name = "lexicon", id = "id")
public class LexiconEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "language_name")
    private String languageName;
    @Column(name = "name")
    private String name;
    @Column(name = "lexicon_version")
    private String lexiconVersion;

    public Long getLexiconId() {
        return id;
    }

    public void setLexiconId(Long id) {
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
        return "Le:" + getLexiconId();
    }
}
