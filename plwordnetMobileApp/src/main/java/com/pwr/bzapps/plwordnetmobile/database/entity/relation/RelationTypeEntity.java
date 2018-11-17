package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `auto_reverse` bit(1) NOT NULL DEFAULT b'0' COMMENT 'On true application will create automatically reversed relation',
 *   `multilingual` bit(1) NOT NULL DEFAULT b'0' COMMENT 'Relation between two lexicons',
 *   `description_id` bigint(20) DEFAULT NULL,
 *   `display_text_id` bigint(20) DEFAULT NULL,
 *   `name_id` bigint(20) DEFAULT NULL,
 *   `parent_relation_type_id` bigint(20) DEFAULT NULL,
 *   `relation_argument` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'Describes type of relation',
 *   `reverse_relation_type_id` bigint(20) DEFAULT NULL,
 *   `short_display_text_id` bigint(20) DEFAULT NULL COMMENT 'Text displayed on visualisation',
 *   `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'Color of displayed relation',
 *   `node_position` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'Position in node LEFT,TOP,RIGHT,BOTTOM',
 *   `priority` int(11) DEFAULT NULL,
 * */
public class RelationTypeEntity implements Entity, Serializable {
    private Integer id;
    private boolean auto_reverse;
    private boolean multilingual;
    private Integer description_id;
    private Integer display_text_id;
    private Integer name_id;
    private Integer parent_relation_type_id;
    private String relation_argument;
    //private RelationTypeEntity reverse_relation_type;
    private Integer reverse_relation_type_id;
    private Integer short_display_text_id;
    private String color;
    private String node_position;
    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAuto_reverse() {
        return auto_reverse;
    }

    public void setAuto_reverse(boolean auto_reverse) {
        this.auto_reverse = auto_reverse;
    }

    public boolean isMultilingual() {
        return multilingual;
    }

    public void setMultilingual(boolean multilingual) {
        this.multilingual = multilingual;
    }

    public Integer getDescription_id() {
        return description_id;
    }

    public void setDescription_id(Integer description_id) {
        this.description_id = description_id;
    }

    public Integer getDisplay_text_id() {
        return display_text_id;
    }

    public void setDisplay_text_id(Integer display_text_id) {
        this.display_text_id = display_text_id;
    }

    public Integer getName_id() {
        return name_id;
    }

    public void setName_id(Integer name_id) {
        this.name_id = name_id;
    }

    public Integer getParent_relation_type_id() {
        return parent_relation_type_id;
    }

    public void setParent_relation_type_id(Integer parent_relation_type_id) {
        this.parent_relation_type_id = parent_relation_type_id;
    }

    public String getRelation_argument() {
        return relation_argument;
    }

    public void setRelation_argument(String relation_argument) {
        this.relation_argument = "null".equals(relation_argument) ? null : relation_argument;
    }

    //public RelationTypeEntity getReverse_relation_type() {
    //    return reverse_relation_type;
    //}

    //public void setReverse_relation_type_id(RelationTypeEntity reverse_relation_type) {
    //    this.reverse_relation_type = reverse_relation_type_;
    //}


    public Integer getReverse_relation_type_id() {
        return reverse_relation_type_id;
    }

    public void setReverse_relation_type_id(Integer reverse_relation_type_id) {
        this.reverse_relation_type_id = reverse_relation_type_id;
    }

    public Integer getShort_display_text_id() {
        return short_display_text_id;
    }

    public void setShort_display_text_id(Integer short_display_text_id) {
        this.short_display_text_id = short_display_text_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = "null".equals(color) ? null : color;
    }

    public String getNode_position() {
        return node_position;
    }

    public void setNode_position(String node_position) {
        this.node_position = "null".equals(node_position) ? null : node_position;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String getEntityID() {
        return "RT:" + getId();
    }
}
