package com.pwr.bzapps.plwordnetmobile.service.database.entity.relation;

import javax.persistence.*;

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
@Entity
@Table(name = "relation_type")
public class RelationTypeEntity {
    @Id
    private Long id;
    @Column(name = "auto_reverse")
    private boolean autoReverse;
    @Column(name = "multilingual")
    private boolean multilingual;
    @Column(name = "description_id")
    private Long descriptionId;
    @Column(name = "display_text_id")
    private Long displayTextId;
    @Column(name = "name_id")
    private Long nameId;
    @Column(name = "parent_relation_type_id")
    private Long parentRelationTypeId;
    @Column(name = "relation_argument")
    private String relationArgument;
    @Column(name = "reverse_relation_type_id")
    private Long reverseRelationTypeId;
    @Column(name = "short_display_text_id")
    private Long shortDisplayTextId;
    @Column(name = "color")
    private String color;
    @Column(name = "node_position")
    private String nodePosition;
    @Column(name = "priority")
    private Integer priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public Long getDisplayTextId() {
        return displayTextId;
    }

    public void setDisplayTextId(Long displayTextId) {
        this.displayTextId = displayTextId;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public Long getParentRelationTypeId() {
        return parentRelationTypeId;
    }

    public void setParentRelationTypeId(Long parentRelationTypeId) {
        this.parentRelationTypeId = parentRelationTypeId;
    }

    public String getRelationArgument() {
        return relationArgument;
    }

    public void setRelationArgument(String relationArgument) {
        this.relationArgument = relationArgument;
    }

    public Long getReverseRelationTypeId() {
        return reverseRelationTypeId;
    }

    public void setReverseRelationTypeId(Long reverseRelationTypeId) {
        this.reverseRelationTypeId = reverseRelationTypeId;
    }

    public Long getShortDisplayTextId() {
        return shortDisplayTextId;
    }

    public void setShortDisplayTextId(Long shortDisplayTextId) {
        this.shortDisplayTextId = shortDisplayTextId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNodePosition() {
        return nodePosition;
    }

    public void setNodePosition(String nodePosition) {
        this.nodePosition = nodePosition;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String toString(){
        String string = "";
        string+="RTE{";
        string+="id:" + id + ";";
        string+="autoReverse:" + autoReverse + ";";
        string+="multilingual:" + multilingual + ";";
        string+="descriptionId:" + descriptionId.toString() + ";";
        string+="displayTextId:" + displayTextId.toString() + ";";
        string+="nameId:" + nameId.toString() + ";";
        string+="parentRelationTypeId:" + parentRelationTypeId + ";";
        string+="relationArgument:" + relationArgument + ";";
        string+="reverseRelationTypeId:" + reverseRelationTypeId + ";";
        string+="shortDisplayTextId:" + shortDisplayTextId.toString() + ";";
        string+="color:" + color + ";";
        string+="nodePosition:" + nodePosition + ";";
        string+="priority:" + priority;
        string+="}RTE";

        return string;
    }
}
