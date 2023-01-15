package me.tretyakovv.p3_finalwork.services;

import me.tretyakovv.p3_finalwork.model.Color;
import me.tretyakovv.p3_finalwork.model.Size;
import me.tretyakovv.p3_finalwork.model.Socks;

import java.util.HashMap;

public interface WarehouseService {

    boolean postSocks(Socks socks);

    boolean releaseSocks(Socks socks);

    HashMap<Integer, Socks> getListSocks();

    int currenBalanceByParameters(Color color, Size size, int cottonMin, int cottonMax);
}
