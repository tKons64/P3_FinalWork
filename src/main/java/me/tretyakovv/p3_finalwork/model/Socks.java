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

    private int stockBalance;

    public boolean fillingСorrectly() {
        return (color != null && size != null && cottonPart >= 0 && cottonPart <= 100 && stockBalance > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Socks socks = (Socks) o;

        if (cottonPart != socks.cottonPart) return false;
        if (color != socks.color) return false;
        return size == socks.size;
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + cottonPart;
        return result;
    }
}
