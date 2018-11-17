package com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.WordEntity;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<WordEntity, Integer> {
}
