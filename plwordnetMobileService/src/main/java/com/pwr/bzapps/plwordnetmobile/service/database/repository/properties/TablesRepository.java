package com.pwr.bzapps.plwordnetmobile.service.database.repository.properties;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.properties.TableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TablesRepository extends CrudRepository<TableEntity, Integer> {

    @Query("SELECT t FROM TableEntity t WHERE table_schema = 'wordnet' order by update_time desc")
    public List<TableEntity> getTablesOrdered();
}
