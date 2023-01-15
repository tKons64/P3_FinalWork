package me.tretyakovv.p3_finalwork.model;

public enum Color {

    RED ("Красный"),
    BLACK ("Черный"),
    WHITE ("Белый"),
    GREEN ("Зеленый"),
    YELLOW ("Желтый");

    private String title;

    Color(String title) {
        this.title = title;
    }
}
