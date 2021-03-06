package com.pwr.bzapps.plwordnetmobile.database.access.parse;

import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedLexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseRelationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;

import org.json.*;

import java.util.ArrayList;

public class JSONParser {

    public static  <T extends Entity> T parseJSONqueryResponse(String json, Class<T> clazz){
        try {
            JSONObject obj = new JSONObject(json);
            return parseJSONtoT(obj,clazz);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<SenseEntity> parseJSONqueryArrayResponse(String json){
        try{
            JSONObject obj = new JSONObject(json);
            JSONArray senseArr = obj.getJSONArray("content");
            return parseJSONtoSensesList(senseArr);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<SenseEntity>();
        }
    }
    public static  <T extends Entity> ArrayList<T> parseJSONqueryArrayResponse(String json, Class<T> clazz){
        try{
            JSONObject obj = new JSONObject(json);
            JSONArray entityArr = obj.getJSONArray("content");
            return parseJSONtoTList(entityArr,clazz);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

    public static ArrayList<SenseEntity> parseJSONtoSensesList(JSONArray jsonSenseArr){
        try {
            ArrayList<SenseEntity> result = new ArrayList<SenseEntity>();
            for (int i = 0; i < jsonSenseArr.length(); i++)
            {
                JSONObject jsonSense = jsonSenseArr.getJSONObject(i);
                result.add(parseJSONtoSenseEntity(jsonSense));
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<SenseEntity>();
        }
    }

    public static <T extends Entity> T parseJSONtoT(JSONObject jsonT, Class<T> clazz){
        switch(clazz.getSimpleName()){
            case "ApplicationLocalisedStringEntity":
                return ((T)parseJSONtoApplicationLocalisedStringEntity(jsonT));
            case "DictionaryEntity":
                return ((T)parseJSONtoDictionaryEntity(jsonT));
            case "DomainEntity":
                return ((T)parseJSONtoDomainEntity(jsonT));
            case "LexiconEntity":
                return ((T)parseJSONtoLexiconEntity(jsonT));
            case "EmotionalAnnotationEntity":
                return ((T)parseJSONtoEmotionalAnnotationEntity(jsonT));
            case "PartOfSpeechEntity":
                return ((T)parseJSONtoPartOfSpeechEntity(jsonT));
            case "WordEntity":
                return ((T)parseJSONtoWordEntity(jsonT));
            case "RelationTypeAllowedLexiconEntity":
                return ((T)parseJSONtoRelationTypeAllowedLexiconEntity(jsonT));
            case "RelationTypeAllowedPartOfSpeechEntity":
                return ((T)parseJSONtoRelationTypeAllowedPartOfSpeechEntity(jsonT));
            case "RelationTypeEntity":
                return ((T)parseJSONtoRelationTypeEntity(jsonT));
            case "SenseAttributeEntity":
                return ((T)parseJSONtoSenseAttributeEntity(jsonT));
            case "SenseEntity":
                return ((T)parseJSONtoSenseEntity(jsonT));
            case "SenseExampleEntity":
                return ((T)parseJSONtoSenseExampleEntity(jsonT));
            case "SenseRelationEntity":
                return ((T)parseJSONtoSenseRelationEntity(jsonT));
            case "SynsetAttributeEntity":
                return ((T)parseJSONtoSynsetAttributeEntity(jsonT));
            case "SynsetEntity":
                return ((T)parseJSONtoSynsetEntity(jsonT));
            case "SynsetExampleEntity":
                return ((T)parseJSONtoSynsetExampleEntity(jsonT));
            case "SynsetRelationEntity":
                return ((T)parseJSONtoSynsetRelationEntity(jsonT));
        }
        return null;

    }

    public static <T extends Entity> ArrayList<T> parseJSONtoTList(JSONArray jsonTArr, Class<T> clazz){
        try {
            ArrayList<T> result = new ArrayList<T>();
            for (int i = 0; i < jsonTArr.length(); i++)
            {
                JSONObject jsonT = jsonTArr.getJSONObject(i);
                switch(clazz.getSimpleName()){
                    case "ApplicationLocalisedStringEntity":
                        result.add((T)parseJSONtoApplicationLocalisedStringEntity(jsonT));
                        break;
                    case "DictionaryEntity":
                        result.add((T)parseJSONtoDictionaryEntity(jsonT));
                        break;
                    case "DomainEntity":
                        result.add((T)parseJSONtoDomainEntity(jsonT));
                        break;
                    case "LexiconEntity":
                        result.add((T)parseJSONtoLexiconEntity(jsonT));
                        break;
                    case "EmotionalAnnotationEntity":
                        result.add((T)parseJSONtoEmotionalAnnotationEntity(jsonT));
                        break;
                    case "PartOfSpeechEntity":
                        result.add((T)parseJSONtoPartOfSpeechEntity(jsonT));
                        break;
                    case "WordEntity":
                        result.add((T)parseJSONtoWordEntity(jsonT));
                        break;
                    case "RelationTypeAllowedLexiconEntity":
                        result.add((T)parseJSONtoRelationTypeAllowedLexiconEntity(jsonT));
                        break;
                    case "RelationTypeAllowedPartOfSpeechEntity":
                        result.add((T)parseJSONtoRelationTypeAllowedPartOfSpeechEntity(jsonT));
                        break;
                    case "RelationTypeEntity":
                        result.add((T)parseJSONtoRelationTypeEntity(jsonT));
                        break;
                    case "SenseAttributeEntity":
                        result.add((T)parseJSONtoSenseAttributeEntity(jsonT));
                        break;
                    case "SenseEntity":
                        result.add((T)parseJSONtoSenseEntity(jsonT));
                        break;
                    case "SenseExampleEntity":
                        result.add((T)parseJSONtoSenseExampleEntity(jsonT));
                        break;
                    case "SenseRelationEntity":
                        result.add((T)parseJSONtoSenseRelationEntity(jsonT));
                        break;
                    case "SynsetAttributeEntity":
                        result.add((T)parseJSONtoSynsetAttributeEntity(jsonT));
                        break;
                    case "SynsetEntity":
                        result.add((T)parseJSONtoSynsetEntity(jsonT));
                        break;
                    case "SynsetExampleEntity":
                        result.add((T)parseJSONtoSynsetExampleEntity(jsonT));
                        break;
                    case "SynsetRelationEntity":
                        result.add((T)parseJSONtoSynsetRelationEntity(jsonT));
                        break;
                }
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }

    }

    public static SenseEntity parseJSONtoSenseEntity(JSONObject jsonSense){
        if(jsonSense==null){
            return null;
        }
        try{
            if(EntityManager.contains("Se:"+jsonSense.getInt("id"))){
                return (SenseEntity)EntityManager.getEntity("Se:"+jsonSense.getInt("id"));
            }
            else {
                SenseEntity senseEntity = new SenseEntity();
                senseEntity.setSenseId(jsonSense.getLong("id"));
                senseEntity.setStatusId(handleNullInIntegerField(jsonSense.optInt("statusId",-1)));
                //senseEntity.setSynsetAttributeId(handleNullInIntegerField(jsonSense.optInt("synset_id",-1)));
                senseEntity.setSynsetPosition(handleNullInIntegerField(jsonSense.optInt("synsetPosition",-1)));
                senseEntity.setVariant(handleNullInIntegerField(jsonSense.optInt("variant",-1)));

                JSONObject jsonSynset = jsonSense.getJSONObject("synsetId");
                senseEntity.setSynsetId(parseJSONtoSynsetEntity(jsonSynset));

                JSONObject jsonDomain = jsonSense.getJSONObject("domainId");
                senseEntity.setDomainId(parseJSONtoDomainEntity(jsonDomain));

                JSONObject jsonLexicon = jsonSense.getJSONObject("lexiconId");
                senseEntity.setLexiconId(parseJSONtoLexiconEntity(jsonLexicon));

                JSONObject jsonWord = jsonSense.getJSONObject("wordId");
                senseEntity.setWordId(parseJSONtoWordEntity(jsonWord));

                JSONObject jsonPartOfSpeech = jsonSense.getJSONObject("partOfSpeechId");
                senseEntity.setPartOfSpeechId(parseJSONtoPartOfSpeechEntity(jsonPartOfSpeech));

                JSONArray jsonArraySenseAttributes = jsonSense.getJSONArray("senseAttributes");
                senseEntity.setSenseAttributes(parseJSONtoTList(jsonArraySenseAttributes,SenseAttributeEntity.class));

                JSONArray jsonArrayEmotionalAnnotations = jsonSense.getJSONArray("emotionalAnnotations");
                senseEntity.setEmotionalAnnotations(parseJSONtoTList(jsonArrayEmotionalAnnotations,EmotionalAnnotationEntity.class));
                //JSONArray jsonRelationChild = jsonSense.getJSONArray("relationChild");
                //senseEntity.setRelationChild(parseJSONtoTList(jsonRelationChild,SenseRelationEntity.class));
                //JSONArray jsonRelationParent = jsonSense.getJSONArray("relationParent");
                //senseEntity.setRelationParent(parseJSONtoTList(jsonRelationParent,SenseRelationEntity.class));

                EntityManager.putEntity(senseEntity);
                return senseEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DictionaryEntity parseJSONtoDictionaryEntity(JSONObject jsonDictionary){
        if(jsonDictionary==null){
            return null;
        }
        try{
            if(EntityManager.contains("Di:"+jsonDictionary.getInt("id"))){
                return (DictionaryEntity) EntityManager.getEntity("Di:"+jsonDictionary.getInt("id"));
            }
            else{
                DictionaryEntity dictionaryEntity = new DictionaryEntity();
                dictionaryEntity.setDictionaryId(jsonDictionary.getLong("id"));
                dictionaryEntity.setDtype(jsonDictionary.optString("dtype",null));
                dictionaryEntity.setTag(jsonDictionary.optString("tag",null));
                dictionaryEntity.setValue(jsonDictionary.optLong("value"));

                dictionaryEntity.setNameId(handleNullInIntegerField(jsonDictionary.optInt("nameId",-1)));

                dictionaryEntity.setDescriptionId(handleNullInIntegerField(jsonDictionary.optInt("descriptionId",-1)));

                EntityManager.putEntity(dictionaryEntity);
                return dictionaryEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static EmotionalAnnotationEntity parseJSONtoEmotionalAnnotationEntity(JSONObject jsonEmotionalAnnotation){
        if(jsonEmotionalAnnotation==null){
            return null;
        }
        try{
            if(EntityManager.contains("EA:"+jsonEmotionalAnnotation.getInt("id"))){
                return (EmotionalAnnotationEntity) EntityManager.getEntity("EA:"+jsonEmotionalAnnotation.getInt("id"));
            }
            else{
                EmotionalAnnotationEntity emotionalAnnotationEntity = new EmotionalAnnotationEntity();
                emotionalAnnotationEntity.setEmotionalAnnotationId(jsonEmotionalAnnotation.getLong("id"));
                emotionalAnnotationEntity.setEmotions(jsonEmotionalAnnotation.optString("emotions",null));
                emotionalAnnotationEntity.setHasEmotionalCharacteristic(jsonEmotionalAnnotation.getBoolean("hasEmotionalCharacteristic"));
                emotionalAnnotationEntity.setMarkedness(jsonEmotionalAnnotation.optString("markedness",null));
                emotionalAnnotationEntity.setSuperAnotation(jsonEmotionalAnnotation.getBoolean("superAnotation"));
                emotionalAnnotationEntity.setValuations(jsonEmotionalAnnotation.optString("valuations",null));
                emotionalAnnotationEntity.setExample1(jsonEmotionalAnnotation.optString("example1",null));
                emotionalAnnotationEntity.setExample2(jsonEmotionalAnnotation.optString("example2",null));
                emotionalAnnotationEntity.setSenseId(handleNullInIntegerField(jsonEmotionalAnnotation.optLong("senseId",-1)));

                EntityManager.putEntity(emotionalAnnotationEntity);
                return emotionalAnnotationEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RelationTypeAllowedLexiconEntity parseJSONtoRelationTypeAllowedLexiconEntity(JSONObject jsonRelationTypeAllowedLexicon){
        if(jsonRelationTypeAllowedLexicon==null){
            return null;
        }
        try{
            RelationTypeAllowedLexiconEntity relationTypeAllowedLexiconEntity = new RelationTypeAllowedLexiconEntity();
            JSONObject jsonLexicon = jsonRelationTypeAllowedLexicon.getJSONObject("lexiconId");
            relationTypeAllowedLexiconEntity.setLexiconId(parseJSONtoLexiconEntity(jsonLexicon));
            JSONObject jsonRelationType = jsonRelationTypeAllowedLexicon.getJSONObject("relationTypeId");
            relationTypeAllowedLexiconEntity.setRelationTypeId(parseJSONtoRelationTypeEntity(jsonRelationType));

            if(EntityManager.contains(relationTypeAllowedLexiconEntity.getEntityID())){
                return (RelationTypeAllowedLexiconEntity) EntityManager.getEntity(relationTypeAllowedLexiconEntity.getEntityID());
            }
            else{
                EntityManager.putEntity(relationTypeAllowedLexiconEntity);
                return relationTypeAllowedLexiconEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RelationTypeAllowedPartOfSpeechEntity parseJSONtoRelationTypeAllowedPartOfSpeechEntity(JSONObject jsonRelationTypeAllowedPartOfSpeech){
        if(jsonRelationTypeAllowedPartOfSpeech==null){
            return null;
        }
        try{
            RelationTypeAllowedPartOfSpeechEntity relationTypeAllowedPartOfSpeechEntity = new RelationTypeAllowedPartOfSpeechEntity();
            JSONObject jsonPartOfSpeech = jsonRelationTypeAllowedPartOfSpeech.getJSONObject("partOfSpeechId");
            relationTypeAllowedPartOfSpeechEntity.setPartOfSpeechId(parseJSONtoPartOfSpeechEntity(jsonPartOfSpeech));
            JSONObject jsonRelationType = jsonRelationTypeAllowedPartOfSpeech.getJSONObject("relationTypeId");
            relationTypeAllowedPartOfSpeechEntity.setRelationTypeId(parseJSONtoRelationTypeEntity(jsonRelationType));

            if(EntityManager.contains(relationTypeAllowedPartOfSpeechEntity.getEntityID())){
                return (RelationTypeAllowedPartOfSpeechEntity) EntityManager.getEntity(relationTypeAllowedPartOfSpeechEntity.getEntityID());
            }
            else{
                EntityManager.putEntity(relationTypeAllowedPartOfSpeechEntity);
                return relationTypeAllowedPartOfSpeechEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RelationTypeEntity parseJSONtoRelationTypeEntity(JSONObject jsonRelationType){
        if(jsonRelationType==null){
            return null;
        }
        try{
            if(EntityManager.contains("RT:"+jsonRelationType.getInt("id"))){
                return (RelationTypeEntity) EntityManager.getEntity("RT:"+jsonRelationType.getInt("id"));
            }
            else{
                RelationTypeEntity relationTypeEntity = new RelationTypeEntity();
                relationTypeEntity.setRelationTypeId(jsonRelationType.getLong("id"));
                relationTypeEntity.setPriority(handleNullInIntegerField(jsonRelationType.optInt("priority",-1)));
                relationTypeEntity.setNodePosition(jsonRelationType.optString("nodePosition",null));
                relationTypeEntity.setColor(jsonRelationType.optString("color",null));
                relationTypeEntity.setRelationArgument(jsonRelationType.optString("relationArgument",null));
                relationTypeEntity.setMultilingual(jsonRelationType.getBoolean("multilingual"));
                relationTypeEntity.setAutoReverse(jsonRelationType.getBoolean("autoReverse"));
                relationTypeEntity.setReverseRelationTypeId(handleNullInIntegerField(jsonRelationType.optLong("reverseRelationTypeId",-1)));

                relationTypeEntity.setNameId(handleNullInIntegerField(jsonRelationType.optLong("nameId",-1)));
                relationTypeEntity.setDescriptionId(handleNullInIntegerField(jsonRelationType.optLong("descriptionId",-1)));
                relationTypeEntity.setDisplayTextId(handleNullInIntegerField(jsonRelationType.optLong("displayTextId",-1)));
                relationTypeEntity.setShortDisplayTextId(handleNullInIntegerField(jsonRelationType.optLong("shortDisplayTextId",-1)));
                relationTypeEntity.setParentRelationTypeId(handleNullInIntegerField(jsonRelationType.optLong("parentRelationTypeId",-1)));

                EntityManager.putEntity(relationTypeEntity);
                return relationTypeEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SenseExampleEntity parseJSONtoSenseExampleEntity(JSONObject jsonSenseExample){
        if(jsonSenseExample==null){
            return null;
        }
        try{
            if(EntityManager.contains("SeEx:"+jsonSenseExample.getInt("id"))){
                return (SenseExampleEntity) EntityManager.getEntity("SeEx:"+jsonSenseExample.getInt("id"));
            }
            else{
                SenseExampleEntity senseExampleEntity = new SenseExampleEntity();
                senseExampleEntity.setSenseExampleId(jsonSenseExample.getLong("id"));
                senseExampleEntity.setExample(jsonSenseExample.optString("example",null));
                senseExampleEntity.setType(jsonSenseExample.optString("type",null));

                senseExampleEntity.setSenseAttributeId(handleNullInIntegerField(jsonSenseExample.optLong("senseAttributeId",-1)));

                EntityManager.putEntity(senseExampleEntity);
                return senseExampleEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SenseRelationEntity parseJSONtoSenseRelationEntity(JSONObject jsonSenseRelation){
        if(jsonSenseRelation==null){
            return null;
        }
        try{
            if(EntityManager.contains("SeR:"+jsonSenseRelation.getInt("id"))){
                return (SenseRelationEntity) EntityManager.getEntity("SeR:"+jsonSenseRelation.getInt("id"));
            }
            else{
                SenseRelationEntity senseRelationEntity = new SenseRelationEntity();
                senseRelationEntity.setSenseRelationId(jsonSenseRelation.getLong("id"));
                senseRelationEntity.setParentSenseId(handleNullInIntegerField(jsonSenseRelation.optLong("parentSenseId",-1)));
                senseRelationEntity.setChildSenseId(handleNullInIntegerField(jsonSenseRelation.optLong("childSenseId",-1)));

                JSONObject jsonRelationType = jsonSenseRelation.getJSONObject("relationTypeId");
                senseRelationEntity.setRelationTypeId(parseJSONtoRelationTypeEntity(jsonRelationType));

                EntityManager.putEntity(senseRelationEntity);
                return senseRelationEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SynsetAttributeEntity parseJSONtoSynsetAttributeEntity(JSONObject jsonSynsetAttribute){
        if(jsonSynsetAttribute==null){
            return null;
        }
        try{
            if(EntityManager.contains("SyA:"+jsonSynsetAttribute.getInt("synsetId"))){
                return (SynsetAttributeEntity) EntityManager.getEntity("SyA:"+jsonSynsetAttribute.getInt("synsetId"));
            }
            else{
                SynsetAttributeEntity synsetAttributeEntity = new SynsetAttributeEntity();
                synsetAttributeEntity.setSynsetAttributeId(jsonSynsetAttribute.getLong("synsetId"));
                synsetAttributeEntity.setDefinition(jsonSynsetAttribute.optString("definition",null));
                synsetAttributeEntity.setComment(jsonSynsetAttribute.optString("comment",null));
                synsetAttributeEntity.setErrorComment(jsonSynsetAttribute.optString("errorComment",null));
                synsetAttributeEntity.setIliId(jsonSynsetAttribute.optString("iliId",null));
                synsetAttributeEntity.setOwnerId(handleNullInIntegerField(jsonSynsetAttribute.optInt("ownerId",-1)));
                synsetAttributeEntity.setPrincetonId(jsonSynsetAttribute.optString("princetonId",null));

                JSONArray jsonArraySynsetExamples = jsonSynsetAttribute.getJSONArray("synsetExamples");
                synsetAttributeEntity.setSynsetExamples(parseJSONtoTList(jsonArraySynsetExamples,SynsetExampleEntity.class));

                EntityManager.putEntity(synsetAttributeEntity);
                return synsetAttributeEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SynsetEntity parseJSONtoSynsetEntity(JSONObject jsonSynset){
        if(jsonSynset==null){
            return null;
        }
        try{
            if(EntityManager.contains("Sy:"+jsonSynset.getInt("id"))){
                return (SynsetEntity) EntityManager.getEntity("Sy:"+jsonSynset.getInt("id"));
            }
            else{
                SynsetEntity synsetEntity = new SynsetEntity();
                synsetEntity.setSynsetId(jsonSynset.getLong("id"));
                synsetEntity.setSplit(handleNullInIntegerField(jsonSynset.optInt("split",-1)));
                synsetEntity.setStatusId(handleNullInIntegerField(jsonSynset.optInt("statusId",-1)));
                synsetEntity.setAbstract(handleNullInIntegerField(jsonSynset.optInt("abstract",-1)).shortValue());

                JSONObject jsonLexicon = jsonSynset.getJSONObject("lexiconId");
                synsetEntity.setLexiconId(parseJSONtoLexiconEntity(jsonLexicon));
                JSONArray jsonArrayRelationParent = jsonSynset.getJSONArray("relationParent");
                synsetEntity.setRelationParent(parseJSONtoTList(jsonArrayRelationParent, SynsetRelationEntity.class));
                JSONArray jsonArrayRelationChild = jsonSynset.getJSONArray("relationChild");
                synsetEntity.setRelationChild(parseJSONtoTList(jsonArrayRelationChild, SynsetRelationEntity.class));
                JSONArray jsonArraySynsetAttributes = jsonSynset.getJSONArray("synsetAttributes");
                synsetEntity.setSynsetAttributes(parseJSONtoTList(jsonArraySynsetAttributes,SynsetAttributeEntity.class));

                EntityManager.putEntity(synsetEntity);
                return synsetEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SynsetExampleEntity parseJSONtoSynsetExampleEntity(JSONObject jsonSynsetExample){
        if(jsonSynsetExample==null){
            return null;
        }
        try{
            if(EntityManager.contains("SynEx:"+jsonSynsetExample.getInt("id"))){
                return (SynsetExampleEntity) EntityManager.getEntity("SynEx:"+jsonSynsetExample.getInt("id"));
            }
            else{
                SynsetExampleEntity synsetExampleEntity = new SynsetExampleEntity();
                synsetExampleEntity.setSynsetExampleId(jsonSynsetExample.getLong("id"));
                synsetExampleEntity.setExample(jsonSynsetExample.optString("example",null));
                synsetExampleEntity.setType(jsonSynsetExample.optString("type",null));
                synsetExampleEntity.setSynsetAttributeId(handleNullInIntegerField(jsonSynsetExample.optLong("synsetAttributesId",-1)));

                //JSONObject jsonSynsetAttributes = jsonSynsetExample.getJSONObject("synsetAttributesId");
                //synsetExampleEntity.setSynsetAttributeId(parseJSONtoSynsetAttributeEntity(jsonSynsetAttributes));

                EntityManager.putEntity(synsetExampleEntity);
                return synsetExampleEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SynsetRelationEntity parseJSONtoSynsetRelationEntity(JSONObject jsonSynsetRelation){
        if(jsonSynsetRelation==null){
            return null;
        }
        try{
            if(EntityManager.contains("SyR:"+jsonSynsetRelation.getInt("id"))){
                return (SynsetRelationEntity) EntityManager.getEntity("SyR:"+jsonSynsetRelation.getInt("id"));
            }
            else{
                SynsetRelationEntity synsetRelationEntity = new SynsetRelationEntity();
                synsetRelationEntity.setSYnsetRelationId(jsonSynsetRelation.getLong("id"));
                synsetRelationEntity.setParentSynsetId(handleNullInIntegerField(jsonSynsetRelation.optLong("parentSynsetId",-1)));
                synsetRelationEntity.setChildSynsetId(handleNullInIntegerField(jsonSynsetRelation.optLong("childSynsetId",-1)));

                JSONObject jsonRelationType = jsonSynsetRelation.getJSONObject("synsetRelationTypeId");
                synsetRelationEntity.setSynsetRelationTypeId(parseJSONtoRelationTypeEntity(jsonRelationType));

                EntityManager.putEntity(synsetRelationEntity);
                return synsetRelationEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SenseAttributeEntity parseJSONtoSenseAttributeEntity(JSONObject jsonSenseAttribute){
        if(jsonSenseAttribute==null){
            return null;
        }
        try{
            if(EntityManager.contains("SeA:"+jsonSenseAttribute.getInt("senseId"))){
                return (SenseAttributeEntity) EntityManager.getEntity("SeA:"+jsonSenseAttribute.getInt("senseId"));
            }
            else{
                SenseAttributeEntity senseAttributeEntity = new SenseAttributeEntity();
                senseAttributeEntity.setSenseAttributeId(jsonSenseAttribute.getLong("senseId"));
                senseAttributeEntity.setProperName(jsonSenseAttribute.getBoolean("properName"));
                senseAttributeEntity.setErrorComment(jsonSenseAttribute.optString("errorComment",null));
                senseAttributeEntity.setUserId(handleNullInIntegerField(jsonSenseAttribute.optInt("userId",-1)));
                senseAttributeEntity.setAspectId(handleNullInIntegerField(jsonSenseAttribute.optInt("aspectId",-1)));
                senseAttributeEntity.setRegisterId(handleNullInIntegerField(jsonSenseAttribute.optInt("registerId",-1)));
                senseAttributeEntity.setLink(jsonSenseAttribute.optString("link",null));
                senseAttributeEntity.setDefinition(jsonSenseAttribute.optString("definition",null));
                senseAttributeEntity.setComment(jsonSenseAttribute.optString("comment",null));

                JSONArray jsonSenseExamples = jsonSenseAttribute.getJSONArray("senseExamples");
                senseAttributeEntity.setSenseExamples(parseJSONtoTList(jsonSenseExamples,SenseExampleEntity.class));

                EntityManager.putEntity(senseAttributeEntity);
                return senseAttributeEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PartOfSpeechEntity parseJSONtoPartOfSpeechEntity(JSONObject jsonPartOfSpeech){
        if(jsonPartOfSpeech==null){
            return null;
        }
        try{
            if(EntityManager.contains("POS:"+jsonPartOfSpeech.getInt("id"))){
                return (PartOfSpeechEntity) EntityManager.getEntity("POS:"+jsonPartOfSpeech.getInt("id"));
            }
            else{
                PartOfSpeechEntity partOfSpeechEntity = new PartOfSpeechEntity();
                partOfSpeechEntity.setPartOfSpeechId(jsonPartOfSpeech.getLong("id"));
                partOfSpeechEntity.setColor(jsonPartOfSpeech.optString("color",null));

                partOfSpeechEntity.setNameId(handleNullInIntegerField(jsonPartOfSpeech.optLong("nameId",-1)));

                EntityManager.putEntity(partOfSpeechEntity);
                return partOfSpeechEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static WordEntity parseJSONtoWordEntity(JSONObject jsonWord){
        if(jsonWord==null){
            return null;
        }
        try{
            if(EntityManager.contains("Wo:"+jsonWord.getInt("id"))){
                return (WordEntity) EntityManager.getEntity("Wo:"+jsonWord.getInt("id"));
            }
            else{
                WordEntity wordEntity = new WordEntity();
                wordEntity.setWordId(jsonWord.getLong("id"));
                wordEntity.setWord(jsonWord.optString("word",null));

                EntityManager.putEntity(wordEntity);
                return wordEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LexiconEntity parseJSONtoLexiconEntity(JSONObject jsonLexicon){
        if(jsonLexicon==null){
            return null;
        }
        try{
            if(EntityManager.contains("Le:"+jsonLexicon.getInt("id"))){
                return (LexiconEntity) EntityManager.getEntity("Le:"+jsonLexicon.getInt("id"));
            }
            else{
                LexiconEntity lexiconEntity = new LexiconEntity();
                lexiconEntity.setLexiconId(jsonLexicon.getLong("id"));
                lexiconEntity.setName(jsonLexicon.optString("name",null));
                lexiconEntity.setIdentifier(jsonLexicon.optString("identifier",null));
                lexiconEntity.setLanguageName(jsonLexicon.optString("languageName",null));
                lexiconEntity.setLexiconVersion(jsonLexicon.optString("lexiconVersion",null));

                EntityManager.putEntity(lexiconEntity);
                return lexiconEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DomainEntity parseJSONtoDomainEntity(JSONObject jsonDomain){
        if(jsonDomain==null){
            return null;
        }
        try{
            if(EntityManager.contains("Do:"+jsonDomain.getInt("id"))){
                return (DomainEntity) EntityManager.getEntity("Do:"+jsonDomain.getInt("id"));
            }
            else{
                DomainEntity domainEntity = new DomainEntity();
                domainEntity.setDomainId(jsonDomain.getLong("id"));


                domainEntity.setNameId(handleNullInIntegerField(jsonDomain.optInt("nameId",-1)));
                domainEntity.setDescriptionId(handleNullInIntegerField(jsonDomain.optInt("descriptionId",-1)));

                EntityManager.putEntity(domainEntity);
                return domainEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ApplicationLocalisedStringEntity parseJSONtoApplicationLocalisedStringEntity(JSONObject jsonLocalisedString){
        if(jsonLocalisedString==null){
            return null;
        }
        try{
            if(EntityManager.contains("ALS:"+jsonLocalisedString.getInt("id"))){
                return (ApplicationLocalisedStringEntity)EntityManager.getEntity("ALS:"+jsonLocalisedString.getInt("id"));
            }
            else{
                ApplicationLocalisedStringEntity applicationLocalisedStringEntity = new ApplicationLocalisedStringEntity();
                applicationLocalisedStringEntity.setApplicationLocalisedStringId(jsonLocalisedString.getLong("id"));
                applicationLocalisedStringEntity.setLanguage(jsonLocalisedString.optString("language",null));
                applicationLocalisedStringEntity.setValue(jsonLocalisedString.optString("value",null));

                EntityManager.putEntity(applicationLocalisedStringEntity);
                return applicationLocalisedStringEntity;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Integer handleNullInIntegerField(int value){
        return (value>-1 ? value : null);
    }

    private static Long handleNullInIntegerField(long value){
        return (value>-1 ? value : null);
    }
}
