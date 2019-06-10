package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import javax.persistence.*;
import java.util.Collection;

/**
 *   `synset_id` bigint(20) NOT NULL,
 *   `comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `definition` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `princeton_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'External original Princeton Id',
 *   `owner_id` bigint(20) DEFAULT NULL COMMENT 'Synset owner',
 *   `error_comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `ili_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'OMW id',
 * */
@Entity
@Table(name = "synset_attributes")
public class SynsetAttributeEntity {
    @Id
    @Column(name = "synset_id")
    private Long synsetId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "definition")
    private String definition;
    @Column(name = "princeton_id")
    private String princetonId;
    @Column(name = "owner_id")
    private Integer ownerId;
    @Column(name = "error_comment")
    private String errorComment;
    @Column(name = "ili_id")
    private String iliId;

    @OneToMany(mappedBy = "synsetAttributeId")
    private Collection<SynsetExampleEntity> synsetExamples;

    public Long getSynsetId() {
        return synsetId;
    }

    public void setSynsetId(Long synsetId) {
        this.synsetId = synsetId;
    }

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

    public String getPrincetonId() {
        return princetonId;
    }

    public void setPrincetonId(String princetonId) {
        this.princetonId = princetonId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getErrorComment() {
        return errorComment;
    }

    public void setErrorComment(String errorComment) {
        this.errorComment = errorComment;
    }

    public String getIliId() {
        return iliId;
    }

    public void setIliId(String iliId) {
        this.iliId = iliId;
    }

    public Collection<SynsetExampleEntity> getSynsetExamples() {
        return synsetExamples;
    }

    public void setSynsetExamples(Collection<SynsetExampleEntity> synsetExamples) {
        this.synsetExamples = synsetExamples;
    }

    public String toString(){
        String string = "";
        string+="SyAE{";
        string+="synsetId:" + synsetId + ";";
        //string+="synset: " + synset.toString() + ";";
        string+="comment:" + comment + ";";
        string+="definition:" + definition + ";";
        string+="princetonId:" + princetonId + ";";
        string+="ownerId:" + ownerId + ";";
        string+="errorComment:" + errorComment + ";";
        string+="iliId:" + iliId + ";";
        string+="synsetExamples:" + synsetExamples.toString();
        string+="}SyAE";

        return string;
    }
}
