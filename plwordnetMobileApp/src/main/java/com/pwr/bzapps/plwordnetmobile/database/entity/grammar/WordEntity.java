package com.pwr.bzapps.plwordnetmobile.database.entity.grammar;

import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `word` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
public class WordEntity implements Entity, Serializable {
    private Integer id;
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
        this.word = "null".equals(word) ? null : word;
    }

    @Override
    public String getEntityID() {
        return "Wo:" + getId();
    }
}
