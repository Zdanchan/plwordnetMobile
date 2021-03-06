package com.pwr.bzapps.plwordnetmobile.database.entity.relation;




import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
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
@Table(name = "relation_type", id = "id")
public class RelationTypeEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
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
//    @Column(name = "reverse_relation_type_id")
    //private RelationTypeEntity reverseRelationType;
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

    public Long getRelationTypeId() {
        return id;
    }

    public void setRelationTypeId(Long id) {
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
        this.relationArgument = "null".equals(relationArgument) ? null : relationArgument;
    }

    //public RelationTypeEntity getReverseRelationType() {
    //    return reverseRelationType;
    //}

    //public void setReverseRelationTypeId(RelationTypeEntity reverseRelationType) {
    //    this.reverseRelationType = reverseRelationType;
    //}


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
        return "RT:" + getRelationTypeId();
    }
}
