package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseExampleEntity;

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
    private Integer synset_id;
    //@ManyToOne
    //@JoinColumn(name = "synset", referencedColumnName = "id")
    //private SynsetEntity synset;
    @Column(name = "comment")
    private String comment;
    @Column(name = "definition")
    private String definition;
    @Column(name = "princeton_id")
    private String princeton_id;
    @Column(name = "owner_id")
    private Integer owner_id;
    @Column(name = "error_comment")
    private String error_comment;
    @Column(name = "ili_id")
    private String ili_id;

    @OneToMany(mappedBy = "synset_attributes_id")
    private Collection<SynsetExampleEntity> synset_examples;

    public Integer getSynset_id() {
        return synset_id;
    }

    public void setSynset_id(Integer synset_id) {
        this.synset_id = synset_id;
    }

    //public SynsetEntity getSynset() {
    //    return synset;
    //}
//
    //public void setSynset(SynsetEntity synset) {
    //    this.synset = synset;
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

    public String getPrinceton_id() {
        return princeton_id;
    }

    public void setPrinceton_id(String princeton_id) {
        this.princeton_id = princeton_id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public String getError_comment() {
        return error_comment;
    }

    public void setError_comment(String error_comment) {
        this.error_comment = error_comment;
    }

    public String getIli_id() {
        return ili_id;
    }

    public void setIli_id(String ili_id) {
        this.ili_id = ili_id;
    }

    public Collection<SynsetExampleEntity> getSynset_examples() {
        return synset_examples;
    }

    public void setSynset_examples(Collection<SynsetExampleEntity> synset_examples) {
        this.synset_examples = synset_examples;
    }

    public String toString(){
        String string = "";
        string+="SyAE{";
        string+="synset_id:" + synset_id + ";";
        //string+="synset: " + synset.toString() + ";";
        string+="comment:" + comment + ";";
        string+="definition:" + definition + ";";
        string+="princeton_id:" + princeton_id + ";";
        string+="owner_id:" + owner_id + ";";
        string+="error_comment:" + error_comment + ";";
        string+="ili_id:" + ili_id + ";";
        string+="synset_examples:" + synset_examples.toString();
        string+="}SyAE";

        return string;
    }
}
