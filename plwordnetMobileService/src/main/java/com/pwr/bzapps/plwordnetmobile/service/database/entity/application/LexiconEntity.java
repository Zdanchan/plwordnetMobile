package com.pwr.bzapps.plwordnetmobile.service.database.entity.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `identifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Short identification string representing lexicon',
 *   `language_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Language of lexicon',
 *   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 *   `lexicon_version` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL COMMENT 'Lexicon name',
 * */
@Entity
@Table(name = "lexicon")
public class LexiconEntity {
    @Id
    private Long id;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "language_name")
    private String languageName;
    @Column(name = "name")
    private String name;
    @Column(name = "lexicon_version")
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
        this.identifier = identifier;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLexiconVersion() {
        return lexiconVersion;
    }

    public void setLexiconVersion(String lexiconVersion) {
        this.lexiconVersion = lexiconVersion;
    }

    public String toString(){
        String string = "";
        string+="LeE{";
        string+="id:" + id + ";";
        string+="identifier:" + identifier + ";";
        string+="languageName:" + languageName + ";";
        string+="name:" + name + ";";
        string+="lexiconVersion:" + lexiconVersion;
        string+="}LeE";

        return string;
    }
}
