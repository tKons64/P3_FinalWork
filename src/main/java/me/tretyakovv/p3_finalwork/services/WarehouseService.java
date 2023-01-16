package me.tretyakovv.p3_finalwork.services;

import me.tretyakovv.p3_finalwork.model.Color;
import me.tretyakovv.p3_finalwork.model.Size;
import me.tretyakovv.p3_finalwork.model.Sock;

import java.util.HashMap;

public interface WarehouseService {


    boolean postSocks(Sock sock, int quantity);

    boolean writeOffSocks(Sock sock, int quantity);

    HashMap<Sock, Integer> getListSocks();

    int currenBalanceByParameters(Color color, Size size, int cottonMin, int cottonMax);
}
