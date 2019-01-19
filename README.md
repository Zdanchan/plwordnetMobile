# plWordNet GO!
(aka plwordnetMobile) This is source code of specialized mobile application which provides access to plWordNet contents, allowing to search senses for words and browse through its relations, while being online and offline. Also provides special graph visualization of wordnet relations (only online), which until this moment was only available through plWordNet desktop and web applications. Project contains source code of mobile application and also web service in Spring framework used for communication with MySQL database. 

*Project uses JavaScript graph visualization code created by plWordNet development team.

## What should be done after adding new lexicon into database

**1.** Lexicon language added into database must be named in english (to stay consistent in whole system) for example:

id | identifier | language_name | name | lexicon_version
------------ | ------------- | ------------- | ------------- | -------------
1 | PLWN | Polish | Słowosieć | 3.1

**2.** Put new locale flag icon into *plwordnetMobile/plwordnetMobileApp/src/main/res/drawable/* and call it by template:
```
flag_<language_name_in_english>.png
```

**3.** Rebuild project

## How to add new localization to application
**1.** In locales.xml add new entry to:
```xml 
<string-array name="locales_symbols">
  <item>en_EN</item>
  <item>pl_PL</item>
  ...
  <item>New_Locale_Symbol</item>
</string-array>
```
    
Symbols according to Andriod locales list (https://github.com/Zdanchan/plwordnetMobile/blob/master/available_locale_symbols.txt)

**2.** Also in locales.xml add new name in:
```xml 
    <!--Language names-->
    <string name="lang_en_EN" translatable="false">English</string>
    <string name="lang_pl_PL" translatable="false">Polski</string>
    ...
    <string name="lang_New_Locale_Symbol" translatable="false">Localised language name</string>
    <!---->
```

**3.** Also in locales.xml add new locale id in:
```xml 
    <!--Locale ID-->
    <string name="locale_en_EN" translatable="false">english</string>
    <string name="locale_pl_PL" translatable="false">polish</string>
    ...
    <string name="locale_New_Locale_Symbol" translatable="false">language_name_in_english</string>
    <!---->
```

**4.** Put new locale flag icon into *plwordnetMobile/plwordnetMobileApp/src/main/res/drawable/* and call it by template:
```
flag_<language_name_in_english>.png
```

**5.** In *plwordnetMobile/plwordnetMobileApp/src/main/res/* create new directory by template:
```
values-<first 2 letters of symbol>
```

**6.** Copy all files from: *plwordnetMobile/plwordnetMobileApp/src/main/res/values-en*

**7.** Paste them into your new directory *plwordnetMobile/plwordnetMobileApp/src/main/res/values-<first 2 letters of symbol>*

**8.** Translate all copied files into desired language (do not change entries names, only values)

**9.** Rebuild project

## How to enable new offline language pack

### 1. On server side (doesn't require application rebuild)

  **1.1.** Lexicon language added into database must be named in english (to stay consistent in whole system)
  
  id | identifier | language_name | name | lexicon_version
  ------------ | ------------- | ------------- | ------------- | -------------
  1 | PLWN | Polish | Słowosieć | 3.1
  
  **1.2.** In *%SERVICE LOCATION%/service_configuration.cfg* file append language english name in line
  
```
conf.language_packs={all,polish,english,...,new_language_pack_name};
```

### 2. On application side (requires application rebuild)
  **2.1.** Add new entry in *plwordnetMobile/plwordnetMobileApp/src/main/res/values/data_languages.xml*

```xml
    <string-array name="data_languages">
        <item>polish</item>
        <item>english</item>
        ...
        <item>language_name_in_english</item>
    </string-array>
```

  **2.2.** Add new language pack name in *plwordnetMobile/plwordnetMobileApp/src/main/res/values/language_pack_names.xml*
  
 ```xml
  <resources>
    <string name="dictionary_polish">Polish</string>
    <string name="dictionary_english">English</string>
    ...
    <string name="dictionary_%LANGUAGE NAME IN ENGLISH%">Localized language name</string>
  </resources>
 ```

  **2.3.** Repeat step **2.2.** for language_pack_names.xml in all *plwordnetMobile/plwordnetMobileApp/src/main/res/values-<first 2 letters of locale symbol>* directories

  **2.4.** Rebuild project

## How to add new relation type
**1.** Add new entry in *plwordnetMobile/plwordnetMobileApp/src/main/res/values/relation_types.xml*
```xml
<resources>
  <string name="rel_type_10">hyponyms</string>
  <string name="rel_type_11">hypernyms</string>
  <string name="rel_type_12">antonyms</string>
  <string name="rel_type_13">converses</string>
  ...
  <string name="rel_type_%RELATION ID%">relation name</string>
</resources>
```

**2.** Repeat step **1** to **relation_types.xml** files in all *plwordnetMobile/plwordnetMobileApp/src/main/res/values-<first 2 letters of locale symbol>* directories

**3.** Rebuild project
