package com.pwr.bzapps.plwordnetmobile.service.database.repository.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationTypeRepository extends CrudRepository<RelationTypeEntity, Integer> {
    @Query(value = "SELECT CONCAT(rt.id,',',rt.auto_reverse,',',rt.multilingual,',',rt.description_id,',',rt.display_text_id,',',rt.name_id,','," +
            "rt.parent_relation_type_id,',','\\'',rt.relation_argument,'\\'',',',rt.reverse_relation_type_id,',',rt.short_display_text_id,',','\\'',rt.color,'\\'',','," +
            "'\\'',rt.node_position,'\\''',',rt.priority) FROM RelationTypeEntity rt", nativeQuery = true)
    public List<String> findAllAndParseString();
}
