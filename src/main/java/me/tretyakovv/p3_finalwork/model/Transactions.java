package me.tretyakovv.p3_finalwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    private TypeOpearation typeOpearation;

    private String dateTimeOperation;

    private Sock sock;

    private int quantity;

    public Transactions(TypeOpearation typeOpearation, Sock sock, int quantity) {
        this.typeOpearation = typeOpearation;
        this.dateTimeOperation = LocalDateTime.now().toString();
        this.sock = sock;
        this.quantity = quantity;
    }
}
