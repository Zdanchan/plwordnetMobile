package com.pwr.bzapps.plwordnetmobile.database.entity.grammar;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `word` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@Table(name = "word", id = "id")
public class WordEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "word")
    private String word;

    public Long getWordId() {
        return id;
    }

    public void setWordId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = "null".equals(word) ? null : word;
    }

    @Override
    public String getEntityID() {
        return "Wo:" + getWordId();
    }
}
