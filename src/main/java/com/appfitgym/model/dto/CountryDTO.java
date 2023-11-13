package com.appfitgym.model.dto;


import java.util.List;

public class CountryDTO  {

    private String code;
    private List<String> currencyCodes;
    private String name;
    private String wikiDataId;


    public String getName() {
        return name;
    }

    public CountryDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public CountryDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public List<String> getCurrencyCodes() {
        return currencyCodes;
    }

    public CountryDTO setCurrencyCodes(List<String> currencyCodes) {
        this.currencyCodes = currencyCodes;
        return this;
    }

    public String getWikiDataId() {
        return wikiDataId;
    }

    public CountryDTO setWikiDataId(String wikiDataId) {
        this.wikiDataId = wikiDataId;
        return this;
    }
}
