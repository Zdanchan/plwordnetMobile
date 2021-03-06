package com.pwr.bzapps.plwordnetmobile.service.database.repository.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationTypeRepository extends CrudRepository<RelationTypeEntity, Long> {
    @Query(value = "SELECT CONCAT(rt.id,','" +
            ",IF(rt.auto_reverse=1,1,0),','" +
            ",IF(rt.multilingual=1,1,0),','" +
            ",IF(rt.description_id IS NULL, 'null', rt.description_id),','" +
            ",IF(rt.display_text_id IS NULL, 'null', rt.display_text_id),','" +
            ",IF(rt.name_id IS NULL, 'null', rt.name_id),','" +
            ",IF(rt.parent_relation_type_id IS NULL, 'null', rt.parent_relation_type_id),','" +
            ",IF(rt.relation_argument IS NULL, 'null', CONCAT('\"',rt.relation_argument,'\"')),','" +
            ",IF(rt.reverse_relation_type_id IS NULL, 'null', rt.reverse_relation_type_id),','" +
            ",IF(rt.short_display_text_id IS NULL, 'null', rt.short_display_text_id),','" +
            ",IF(rt.color IS NULL, 'null', CONCAT('\"',rt.color,'\"')),','" +
            ",IF(rt.node_position IS NULL, 'null',CONCAT('\"',rt.node_position,'\"')),','" +
            ",IF(rt.priority IS NULL, 'null', rt.priority)) FROM relation_type rt", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT(rt.id,','" +
            ",IF(rt.auto_reverse=1,1,0),','" +
            ",IF(rt.multilingual=1,1,0),','" +
            ",IF(rt.description_id IS NULL, 'null', rt.description_id),','" +
            ",IF(rt.display_text_id IS NULL, 'null', rt.display_text_id),','" +
            ",IF(rt.name_id IS NULL, 'null', rt.name_id),','" +
            ",IF(rt.parent_relation_type_id IS NULL, 'null', rt.parent_relation_type_id),','" +
            ",IF(rt.relation_argument IS NULL, 'null', CONCAT('\"',rt.relation_argument,'\"')),','" +
            ",IF(rt.reverse_relation_type_id IS NULL, 'null', rt.reverse_relation_type_id),','" +
            ",IF(rt.short_display_text_id IS NULL, 'null', rt.short_display_text_id),','" +
            ",IF(rt.color IS NULL, 'null', CONCAT('\"',rt.color,'\"')),','" +
            ",IF(rt.node_position IS NULL, 'null',CONCAT('\"',rt.node_position,'\"')),','" +
            ",IF(rt.priority IS NULL, 'null', rt.priority)) FROM relation_type rt" +
            " WHERE rt.id>=:begin AND rt.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM relation_type", nativeQuery = true)
    public Long getMaxIndex();
}
