package me.tretyakovv.p3_finalwork.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public enum Size {

    @JsonProperty("38")
    S38 ("38"),
    @JsonProperty("39")
    S39("39"),
    @JsonProperty("40")
    S40("40"),
    @JsonProperty("41")
    S41("41"),
    @JsonProperty("42")
    S42("42"),
    @JsonProperty("43")
    S43("43");

    private String title;

    Size(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
