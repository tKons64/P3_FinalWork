package me.tretyakovv.p3_finalwork.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import me.tretyakovv.p3_finalwork.model.*;
import me.tretyakovv.p3_finalwork.services.FilesService;
import me.tretyakovv.p3_finalwork.serializer.SocksKeyDeserializer;
import me.tretyakovv.p3_finalwork.serializer.SocksKeySerializer;
import me.tretyakovv.p3_finalwork.services.WarehouseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Value("${name.of.file.transactions}")
    private String dataFileTransactions;

    @Value("${name.of.file.tableBalance}")
    private String dataFileTableBalance;

    private ObjectMapper mapper;

    static HashMap<Long, Transactions> listTransactions = new HashMap<>();

    @JsonSerialize(keyUsing = SocksKeySerializer.class)
    @JsonDeserialize(keyUsing = SocksKeyDeserializer.class)
    static HashMap<Socks, Integer> tableBalance = new HashMap<>();

    private long lastId;

    private final FilesService filesService;

    public WarehouseServiceImpl(FilesService filesService) {
        this.filesService = filesService;
        this.mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeySerializer(Socks.class, new SocksKeySerializer());
        simpleModule.addKeyDeserializer(Socks.class, new SocksKeyDeserializer());
        this.mapper.registerModule(simpleModule);
    }

    @PostConstruct
    private void init() throws IOException {
        readFromFile();
    }
    @Override
    public boolean postSocks(Socks socks, int quantity) {
        if (!socks.fillingСorrectly() && quantity > 0) {
            return false;
        }

        Transactions transaction = new Transactions(TypeOpearation.POST, socks, quantity);
        listTransactions.put(lastId++, transaction);
        tableBalance.put(socks, currenBalance(socks) + quantity);
        saveToFile();
        return true;
    }

    @Override
    public boolean writeOffSocks(Socks socks, int quantity) {
        if (!socks.fillingСorrectly() && quantity > 0) {
            return false;
        }

        int balance = currenBalance(socks);
        if (balance >= quantity) {
            Transactions transaction = new Transactions(TypeOpearation.RELEASE, socks, quantity);
            listTransactions.put(lastId++, transaction);
            tableBalance.put(socks, currenBalance(socks) - quantity);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public HashMap<Socks, Integer> getListSocks() {
        return tableBalance;
    }
    @Override
    public int currenBalanceByParameters(Color color, Size size, int cottonMin, int cottonMax) {
        int balance = 0;
        for (Socks socks: tableBalance.keySet()) {
            if (socks.getColor() == color && socks.getSize() == size
                && socks.getCottonPart() >= cottonMin && socks.getCottonPart() <= cottonMax) {
                balance = balance + currenBalance(socks);
            }
        }
        return balance;
    }

    private int currenBalance(Socks socks) {
        return tableBalance.getOrDefault(socks, 0);
    }

    private void saveToFile() {
        try {
            String jsonTableBalance = mapper.writeValueAsString(tableBalance);
            String jsonTransactions = mapper.writeValueAsString(listTransactions);
            filesService.saveToFile(jsonTableBalance, dataFileTableBalance);
            filesService.saveToFile(jsonTransactions, dataFileTransactions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void readFromFile() throws IOException {
        String jsonTableBalance = filesService.readFromFile(dataFileTableBalance);
        String jsonTransactions = filesService.readFromFile(dataFileTransactions);
        try {
            tableBalance = mapper.readValue(jsonTableBalance, new TypeReference<HashMap<Socks, Integer>>() {
            });
            listTransactions = mapper.readValue(jsonTransactions, new TypeReference<HashMap<Long, Transactions>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (Long aLong : listTransactions.keySet()) {
            if (lastId < aLong) {
                lastId = aLong;
            }
        }
        if (lastId > 0L) {
            lastId++;
        }
    }
}
