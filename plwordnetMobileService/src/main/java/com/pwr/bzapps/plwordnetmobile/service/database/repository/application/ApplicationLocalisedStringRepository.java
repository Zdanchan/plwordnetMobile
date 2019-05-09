package com.pwr.bzapps.plwordnetmobile.service.database.repository.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationLocalisedStringRepository extends CrudRepository<ApplicationLocalisedStringEntity, Long> {

    @Query("SELECT als FROM ApplicationLocalisedStringEntity als WHERE id = :id AND language = :language")
    public ApplicationLocalisedStringEntity findByIdAndLang(@Param("id") Long id, @Param("language") String language);

    @Query(value = "SELECT CONCAT(als.id,',','\"',' ','\"',',','\"',als.language,'\"') FROM application_localised_string als", nativeQuery = true)
    public List<String> findAllAndParseString();

    @Query(value = "SELECT CONCAT(als.id,',','\"',' ','\"',',','\"',als.language,'\"') FROM application_localised_string als WHERE language = :language", nativeQuery = true)
    public List<String> findAllByLanguageAndParseString(@Param("language") String language);

    @Query(value = "SELECT CONCAT(als.id,',','\"',' ','\"',',','\"',als.language,'\"') FROM application_localised_string als" +
            " WHERE als.id>=:begin AND als.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT CONCAT(als.id,',','\"',' ','\"',',','\"',als.language,'\"') FROM application_localised_string als WHERE language = :language" +
            " AND als.id>=:begin AND als.id<:end", nativeQuery = true)
    public List<String> findAllByLanguageAndParseStringBatch(@Param("language") String language, @Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM application_localised_string", nativeQuery = true)
    public Long getMaxIndex();
}
