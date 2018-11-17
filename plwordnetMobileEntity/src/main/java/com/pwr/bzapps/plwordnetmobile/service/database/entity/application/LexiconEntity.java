package com.pwr.bzapps.plwordnetmobile.service.database.entity.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `identifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Short identification string representing lexicon',
 *   `language_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Language of lexicon',
 *   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 *   `lexicon_version` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Lexicon name',
 * */
@Entity
@Table(name = "lexicon")
public class LexiconEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "language_name")
    private String language_name;
    @Column(name = "name")
    private String name;
    @Column(name = "lexicon_version")
    private String lexicon_version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = "null".equals(identifier) ? null : identifier;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = "null".equals(language_name) ? null : language_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = "null".equals(name) ? null : name;
    }

    public String getLexicon_version() {
        return lexicon_version;
    }

    public void setLexicon_version(String lexicon_version) {
        this.lexicon_version = "null".equals(lexicon_version) ? null : lexicon_version;
    }

    @Override
    public String getEntityID() {
        return "Le:" + getId();
    }
}
