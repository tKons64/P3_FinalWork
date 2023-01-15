package me.tretyakovv.p3_finalwork.services.impl;

import me.tretyakovv.p3_finalwork.model.Color;
import me.tretyakovv.p3_finalwork.model.Size;
import me.tretyakovv.p3_finalwork.model.Socks;
import me.tretyakovv.p3_finalwork.services.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WarehouseServiceImpl implements WarehouseService {


    static HashMap<Integer, Socks> listSocks = new HashMap<>();

    @Override
    public boolean postSocks(Socks socks) {
        if (!socks.fillingСorrectly()) {
            return false;
        }

        socks.setStockBalance(currenBalance(socks) + socks.getStockBalance());
        listSocks.put(socks.hashCode(), socks);
        return true;
    }

    @Override
    public boolean releaseSocks(Socks socks) {
        if (!socks.fillingСorrectly()) {
            return false;
        }

        int balance = currenBalance(socks);
        if (balance >= socks.getStockBalance()) {
            socks.setStockBalance(balance - socks.getStockBalance());
            listSocks.put(socks.hashCode(), socks);
            return true;
        }
        return false;
    }

    @Override
    public HashMap<Integer, Socks> getListSocks() {
        return listSocks;
    }
    @Override
    public int currenBalanceByParameters(Color color, Size size, int cottonMin, int cottonMax) {
        int balance = 0;
        for (Socks socks: listSocks.values()) {
            if (socks.getColor() == color && socks.getSize() == size
                && socks.getCottonPart() >= cottonMin && socks.getCottonPart() <= cottonMax) {
                balance = balance + socks.getStockBalance();
            }
        }
        return balance;
    }

    private int currenBalance(Socks socks) {
        Socks stockSocks = listSocks.get(socks.hashCode());
        if (stockSocks != null) {
            return stockSocks.getStockBalance();
        }
        return 0;
    }
}
