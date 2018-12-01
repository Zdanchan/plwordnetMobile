package com.pwr.bzapps.plwordnetmobile.service.database.repository.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeAllowedLexiconEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationTypeAllowedLexiconRepository extends CrudRepository<RelationTypeAllowedLexiconEntity, Integer> {
    @Query(value = "SELECT CONCAT(rtal.relation_type_id.id,',',rtal.lexicon_id.id) FROM RelationTypeAllowedLexiconEntity rtal", nativeQuery = true)
    public List<String> findAllAndParseString();
}
