package com.pwr.bzapps.plwordnetmobile.database.entity.sense;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.io.Serializable;
import java.util.Collection;

/**
 *   `sense_id` bigint(20) NOT NULL,
 *   `comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `definition` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `register_id` bigint(20) DEFAULT NULL,
 *   `aspect_id` bigint(20) DEFAULT NULL,
 *   `user_id` bigint(20) DEFAULT NULL,
 *   `error_comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `proper_name` bit(1) NOT NULL DEFAULT b'0',
 *
 * */
public class SenseAttributeEntity implements Entity, Serializable {
    private Integer sense_id;
    private String comment;
    private String definition;
    private String link;
    private Integer register_id;
    private Integer aspect_id;
    private Integer user_id;
    private String error_comment;
    private boolean proper_name;

    private Collection<SenseExampleEntity> sense_examples;

    public Integer getSense_id() {
        return sense_id;
    }

    public void setSense_id(Integer sense_id) {
        this.sense_id = sense_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = "null".equals(comment) ? null : comment;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = "null".equals(definition) ? null : definition;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = "null".equals(link) ? null : link;
    }

    public Integer getRegister_id() {
        return register_id;
    }

    public void setRegister_id(Integer register_id) {
        this.register_id = register_id;
    }

    public Integer getAspect_id() {
        return aspect_id;
    }

    public void setAspect_id(Integer aspect_id) {
        this.aspect_id = aspect_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getError_comment() {
        return error_comment;
    }

    public void setError_comment(String error_comment) {
        this.error_comment = "null".equals(error_comment) ? null : error_comment;
    }

    public boolean isProper_name() {
        return proper_name;
    }

    public void setProper_name(boolean proper_name) {
        this.proper_name = proper_name;
    }

    public Collection<SenseExampleEntity> getSense_examples() {
        return sense_examples;
    }

    public void setSense_examples(Collection<SenseExampleEntity> sense_examples) {
        this.sense_examples = sense_examples;
    }

    @Override
    public String getEntityID() {
        return "SeAE:"+getSense_id();
    }
}