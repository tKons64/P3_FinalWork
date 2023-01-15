package me.tretyakovv.p3_finalwork.services;

import me.tretyakovv.p3_finalwork.model.Color;
import me.tretyakovv.p3_finalwork.model.Size;
import me.tretyakovv.p3_finalwork.model.Socks;

import java.util.HashMap;

public interface WarehouseService {


    boolean postSocks(Socks socks, int quantity);

    boolean writeOffSocks(Socks socks, int quantity);

    HashMap<Socks, Integer> getListSocks();

    int currenBalanceByParameters(Color color, Size size, int cottonMin, int cottonMax);
}
