package com.pwr.bzapps.plwordnetmobile.service.database.entity.sense;

import javax.persistence.*;
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
@Entity
@Table(name = "sense_attributes")
public class SenseAttributeEntity {
    @Id
    @Column(name = "sense_id")
    private Long senseId;
    //@ManyToOne
    //@JoinColumn(name = "sense", referencedColumnName = "id")
    //private SenseEntity sense;
    @Column(name = "comment")
    private String comment;
    @Column(name = "definition")
    private String definition;
    @Column(name = "link")
    private String link;
    @Column(name = "register_id")
    private Long registerId;
    @Column(name = "aspect_id")
    private Long aspectId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "error_comment")
    private String errorComment;
    @Column(name = "proper_name")
    private boolean properName;

    @OneToMany(mappedBy = "senseAttributeId")
    private Collection<SenseExampleEntity> senseExamples;

    public Long getSenseId() {
        return senseId;
    }

    public void setSenseId(Long senseId) {
        this.senseId = senseId;
    }

    //public SenseEntity getSense() {
    //    return sense;
    //}
//
    //public void setSense(SenseEntity sense) {
    //    this.sense = sense;
    //}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public Long getAspectId() {
        return aspectId;
    }

    public void setAspectId(Long aspectId) {
        this.aspectId = aspectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getErrorComment() {
        return errorComment;
    }

    public void setErrorComment(String errorComment) {
        this.errorComment = errorComment;
    }

    public boolean isProperName() {
        return properName;
    }

    public void setProperName(boolean properName) {
        this.properName = properName;
    }

    public Collection<SenseExampleEntity> getSenseExamples() {
        return senseExamples;
    }

    public void setSenseExamples(Collection<SenseExampleEntity> senseExamples) {
        this.senseExamples = senseExamples;
    }

    public String toString(){
        String string = "";
        string+="SeAE{";
        string+="senseId:" + senseId + ";";
        string+="comment:" + comment + ";";
        string+="definition:" + definition + ";";
        string+="link:" + link + ";";
        string+="registerId:" + registerId + ";";
        string+="aspectId:" + aspectId + ";";
        string+="userId:" + userId + ";";
        string+="errorComment:" + errorComment + ";";
        string+="properName:" + properName;
        //string+="sensesExamples: " + senseExamples;
        string+="}SeAE";

        return string;
    }
}
