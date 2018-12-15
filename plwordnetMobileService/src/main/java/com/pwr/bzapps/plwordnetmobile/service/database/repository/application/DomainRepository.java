package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DomainEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `description_id` bigint(20) DEFAULT NULL,
 *   `name_id` bigint(20) DEFAULT NULL,
 * */
public interface DomainRepository extends CrudRepository<DomainEntity, Integer>{
    @Query(value = "SELECT CONCAT(do.id,',',do.description_id,',',do.name_id) FROM domain do ", nativeQuery = true)
    public List<String> findAllAndParseString();
}
