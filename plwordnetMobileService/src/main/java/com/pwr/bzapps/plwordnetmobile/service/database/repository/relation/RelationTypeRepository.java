package com.pwr.bzapps.plwordnetmobile.service.database.repository.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationTypeRepository extends CrudRepository<RelationTypeEntity, Integer> {
    @Query(value = "SELECT CONCAT(rt.id,','" +
            ",rt.auto_reverse,','" +
            ",rt.multilingual,','" +
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
}
