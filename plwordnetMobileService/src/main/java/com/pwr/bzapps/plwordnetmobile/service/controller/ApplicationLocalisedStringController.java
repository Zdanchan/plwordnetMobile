package com.pwr.bzapps.plwordnetmobile.service.controller;

import com.pwr.bzapps.plwordnetmobile.service.controller.response.Response;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.id.ApplicationLocalisedStringId;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.export.SQLExporter;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.application.ApplicationLocalisedStringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/ApplicationLocalisedStringsController")
public class ApplicationLocalisedStringController {
    private Logger log = LoggerFactory.getLogger(ApplicationLocalisedStringController.class);

    @Autowired
    private ApplicationLocalisedStringRepository repository;

    @GetMapping(path="/findById")
    private @ResponseBody ApplicationLocalisedStringEntity findById(@RequestParam Long id, @RequestParam String language){
        ApplicationLocalisedStringEntity result = repository.findByIdAndLang(id,language);
        return (result);
    }

    @GetMapping(path="/findAll")
    private @ResponseBody Response<Iterable<ApplicationLocalisedStringEntity>> findSenseAll(){
        return new Response<Iterable<ApplicationLocalisedStringEntity>>(repository.findAll());
    }
}
