package com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `word` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@Entity
@Table(name = "word")
public class WordEntity {
    @Id
    private Integer id;

    @Column(name = "word")
    private String word;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String toString(){
        String string = "";
        string+="WoE{";
        string+="id:" + id + ";";
        string+="word:" + word;
        string+="}WoE";

        return string;
    }
}
