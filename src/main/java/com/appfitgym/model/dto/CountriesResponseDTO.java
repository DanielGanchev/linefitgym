package com.appfitgym.model.dto;

import java.util.List;

public class CountriesResponseDTO {
    private boolean error;
    private String msg;
    private List<CountryDTO> data;

    public boolean isError() {
        return error;
    }

    public CountriesResponseDTO setError(boolean error) {
        this.error = error;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public CountriesResponseDTO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<CountryDTO> getData() {
        return data;
    }

    public CountriesResponseDTO setData(List<CountryDTO> data) {
        this.data = data;
        return this;
    }
}