package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DictionaryEntity;
import org.springframework.data.repository.CrudRepository;

public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Integer> {
}
