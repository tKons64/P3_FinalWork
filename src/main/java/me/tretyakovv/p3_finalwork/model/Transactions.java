package me.tretyakovv.p3_finalwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    private TypeOpearation typeOpearation;

    private String dateTimeOperation;

    private Socks socks;

    private int quantity;

    public Transactions(TypeOpearation typeOpearation, Socks socks, int quantity) {
        this.typeOpearation = typeOpearation;
        this.dateTimeOperation = LocalDateTime.now().toString();
        this.socks = socks;
        this.quantity = quantity;
    }
}
