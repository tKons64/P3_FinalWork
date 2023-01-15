package me.tretyakovv.p3_finalwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {

    private Color color;

    private Size size;

    private int cottonPart;

    public boolean fillingСorrectly() {
        return (color != null && size != null && cottonPart >= 0 && cottonPart <= 100);
    }
}
