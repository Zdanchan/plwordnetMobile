package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

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
    private boolean autoReverse;
    private boolean multilingual;
    private Integer descriptionId;
    private Integer displayTextId;
    private Integer nameId;
    private Integer parentRelationTypeId;
    private String relationArgument;
    //private RelationTypeEntity reverseRelationType;
    private Integer reverseRelationTypeId;
    private Integer shortDisplayTextId;
    private String color;
    private String nodePosition;
    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAutoReverse() {
        return autoReverse;
    }

    public void setAutoReverse(boolean autoReverse) {
        this.autoReverse = autoReverse;
    }

    public boolean isMultilingual() {
        return multilingual;
    }

    public void setMultilingual(boolean multilingual) {
        this.multilingual = multilingual;
    }

    public Integer getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Integer descriptionId) {
        this.descriptionId = descriptionId;
    }

    public Integer getDisplayTextId() {
        return displayTextId;
    }

    public void setDisplayTextId(Integer displayTextId) {
        this.displayTextId = displayTextId;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }

    public Integer getParentRelationTypeId() {
        return parentRelationTypeId;
    }

    public void setParentRelationTypeId(Integer parentRelationTypeId) {
        this.parentRelationTypeId = parentRelationTypeId;
    }

    public String getRelationArgument() {
        return relationArgument;
    }

    public void setRelationArgument(String relationArgument) {
        this.relationArgument = "null".equals(relationArgument) ? null : relationArgument;
    }

    //public RelationTypeEntity getReverseRelationType() {
    //    return reverseRelationType;
    //}

    //public void setReverseRelationTypeId(RelationTypeEntity reverseRelationType) {
    //    this.reverseRelationType = reverseRelationType;
    //}


    public Integer getReverseRelationTypeId() {
        return reverseRelationTypeId;
    }

    public void setReverseRelationTypeId(Integer reverseRelationTypeId) {
        this.reverseRelationTypeId = reverseRelationTypeId;
    }

    public Integer getShortDisplayTextId() {
        return shortDisplayTextId;
    }

    public void setShortDisplayTextId(Integer shortDisplayTextId) {
        this.shortDisplayTextId = shortDisplayTextId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = "null".equals(color) ? null : color;
    }

    public String getNodePosition() {
        return nodePosition;
    }

    public void setNodePosition(String nodePosition) {
        this.nodePosition = "null".equals(nodePosition) ? null : nodePosition;
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
