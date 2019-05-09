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
    private boolean auto_reverse;
    @Column(name = "multilingual")
    private boolean multilingual;
    @Column(name = "description_id")
    private Long description_id;
    @Column(name = "display_text_id")
    private Long display_text_id;
    @Column(name = "name_id")
    private Long name_id;
    @Column(name = "parent_relation_type_id")
    private Long parent_relation_type_id;
    @Column(name = "relation_argument")
    private String relation_argument;
    @Column(name = "reverse_relation_type_id")
    private Long reverse_relation_type_id;
    @Column(name = "short_display_text_id")
    private Long short_display_text_id;
    @Column(name = "color")
    private String color;
    @Column(name = "node_position")
    private String node_position;
    @Column(name = "priority")
    private Integer priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getDescription_id() {
        return description_id;
    }

    public void setDescription_id(Long description_id) {
        this.description_id = description_id;
    }

    public Long getDisplay_text_id() {
        return display_text_id;
    }

    public void setDisplay_text_id(Long display_text_id) {
        this.display_text_id = display_text_id;
    }

    public Long getName_id() {
        return name_id;
    }

    public void setName_id(Long name_id) {
        this.name_id = name_id;
    }

    public Long getParent_relation_type_id() {
        return parent_relation_type_id;
    }

    public void setParent_relation_type_id(Long parent_relation_type_id) {
        this.parent_relation_type_id = parent_relation_type_id;
    }

    public String getRelation_argument() {
        return relation_argument;
    }

    public void setRelation_argument(String relation_argument) {
        this.relation_argument = relation_argument;
    }

    public Long getReverse_relation_type_id() {
        return reverse_relation_type_id;
    }

    public void setReverse_relation_type_id(Long reverse_relation_type_id) {
        this.reverse_relation_type_id = reverse_relation_type_id;
    }

    public Long getShort_display_text_id() {
        return short_display_text_id;
    }

    public void setShort_display_text_id(Long short_display_text_id) {
        this.short_display_text_id = short_display_text_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNode_position() {
        return node_position;
    }

    public void setNode_position(String node_position) {
        this.node_position = node_position;
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
        string+="auto_reverse:" + auto_reverse + ";";
        string+="multilingual:" + multilingual + ";";
        string+="description_id:" + description_id.toString() + ";";
        string+="display_text_id:" + display_text_id.toString() + ";";
        string+="name_id:" + name_id.toString() + ";";
        string+="parent_relation_type_id:" + parent_relation_type_id + ";";
        string+="relation_argument:" + relation_argument + ";";
        string+="reverse_relation_type_id:" + reverse_relation_type_id + ";";
        string+="short_display_text_id:" + short_display_text_id.toString() + ";";
        string+="color:" + color + ";";
        string+="node_position:" + node_position + ";";
        string+="priority:" + priority;
        string+="}RTE";

        return string;
    }
}
