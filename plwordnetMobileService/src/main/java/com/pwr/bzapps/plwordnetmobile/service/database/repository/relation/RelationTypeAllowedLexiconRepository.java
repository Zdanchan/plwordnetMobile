package com.pwr.bzapps.plwordnetmobile.service.database.repository.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeAllowedLexiconEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationTypeAllowedLexiconRepository extends CrudRepository<RelationTypeAllowedLexiconEntity, Long> {
    @Query(value = "SELECT CONCAT(rtal.relation_type_id,',',rtal.lexicon_id) FROM relation_type_allowed_lexicons rtal", nativeQuery = true)
    public List<String> findAllAndParseString();
}
