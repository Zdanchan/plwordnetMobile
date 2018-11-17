package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationLocalisedStringRepository extends CrudRepository<ApplicationLocalisedStringEntity, Integer> {

    @Query("SELECT als FROM ApplicationLocalisedStringEntity als WHERE id = :id AND language = :language")
    public ApplicationLocalisedStringEntity findByIdAndLang(@Param("id") Integer id, @Param("language") String language);
}
