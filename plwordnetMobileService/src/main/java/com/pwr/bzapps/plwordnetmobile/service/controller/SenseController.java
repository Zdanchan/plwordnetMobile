package com.pwr.bzapps.plwordnetmobile.service.controller;

import com.pwr.bzapps.plwordnetmobile.service.configuration.ConfigurationReader;
import com.pwr.bzapps.plwordnetmobile.service.controller.response.Response;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/sense")
public class SenseController {

    private Logger log = LoggerFactory.getLogger(SenseController.class);

    @Autowired
    private SenseRepository repository;

    @GetMapping(path="/findById")
    private @ResponseBody SenseEntity findSenseById(@RequestParam Integer id){
        SenseEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping(path="/findSynonymsBySynsetId")
    private @ResponseBody Response<List<SenseEntity>> findSynonymsBySynsetId(@RequestParam Integer id){
        List<SenseEntity> result = repository.findSynonymsBySynsetId(id);
        return new Response<List<SenseEntity>>(result);
    }

    @GetMapping(path="/findMultipleByIds")
    private @ResponseBody Response<List<SenseEntity>> findMultipleSensesByIds(@RequestParam Integer[] ids){
        List<SenseEntity> result = repository.findMultipleByIds(ids);
        return new Response<List<SenseEntity>>(result);
    }

    @GetMapping(path="/findMultipleBySynsetIds")
    private @ResponseBody Response<List<SenseEntity>> findMultipleBySynsetIds(@RequestParam Integer[] ids){
        List<SenseEntity> result = repository.findMultipleBySynsetIds(ids);
        return new Response<List<SenseEntity>>(result);
    }

    @GetMapping(path="/findByWord")
    private @ResponseBody Response<List<SenseEntity>> findSenseByWord(@RequestParam String word){
        List<SenseEntity> result = repository.findByWord(word, new PageRequest(0,ConfigurationReader.getSenseQueryResultLimit()));
        return new Response<List<SenseEntity>>(result);
    }

    @GetMapping(path="/findRelatedSensesByWord")
    private @ResponseBody Response<Iterable<SenseEntity>> findRelatedSensesByWord(@RequestParam String word){
        return new Response<Iterable<SenseEntity>>(repository.findRelatedSensesByWord(word));
    }

    @GetMapping(path="/findRelatedSensesByWordAndLanguage")
    private @ResponseBody Response<Iterable<SenseEntity>> findRelatedSensesByWordAndLanguage(@RequestParam String word,
                                                                                  @RequestParam String language){
        return new Response<Iterable<SenseEntity>>(repository.findRelatedSensesByWord(word,language));
    }

    @GetMapping(path="/findRelatedSensesByWordLanguageAndPartOfSpeech")
    private @ResponseBody Response<Iterable<SenseEntity>> findRelatedSensesByWordLanguageAndPartOfSpeech(@RequestParam String word,
                                                                                             @RequestParam String language,
                                                                                             @RequestParam Integer part_of_speech){
        return new Response<Iterable<SenseEntity>>(repository.findRelatedSensesByWord(word,language,part_of_speech));
    }

    @GetMapping(path="/findAll")
    private @ResponseBody Response<Iterable<SenseEntity>> findSenseAll(){
        return new Response<Iterable<SenseEntity>>(repository.findAll());
    }
}
