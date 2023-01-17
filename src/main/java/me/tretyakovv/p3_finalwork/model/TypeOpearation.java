package me.tretyakovv.p3_finalwork.model;

public enum TypeOpearation {
    POST ("Приемка"),
    RELEASE ("Выдача"),
    DELETE ("Списание");

    private String title;

    TypeOpearation(String title) {
        this.title = title;
    }
}
