package me.tretyakovv.p3_finalwork.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    static HashMap<Sock, Integer> tableBalance = new HashMap<>();

    private long lastId;

    private final FilesService filesService;

    public WarehouseServiceImpl(FilesService filesService) {
        this.filesService = filesService;
        this.mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeySerializer(Sock.class, new SocksKeySerializer());
        simpleModule.addKeyDeserializer(Sock.class, new SocksKeyDeserializer());
        this.mapper.registerModule(simpleModule);
    }

    @PostConstruct
    private void init() throws IOException {
        readFromFile();
    }
    @Override
    public boolean postSocks(Sock sock, int quantity) {
        if (!sock.fillingСorrectly() && quantity > 0) {
            return false;
        }

        Transactions transaction = new Transactions(TypeOpearation.POST, sock, quantity);
        listTransactions.put(lastId++, transaction);
        tableBalance.put(sock, currenBalance(sock) + quantity);
        saveToFile();
        return true;
    }

    @Override
    public boolean writeOffSocks(Sock sock, int quantity) {
        if (!sock.fillingСorrectly() && quantity > 0) {
            return false;
        }

        int balance = currenBalance(sock);
        if (balance >= quantity) {
            Transactions transaction = new Transactions(TypeOpearation.RELEASE, sock, quantity);
            listTransactions.put(lastId++, transaction);
            tableBalance.put(sock, currenBalance(sock) - quantity);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public String getListSocksInStock() {
        String listSocks = "";
        for (Sock sock : tableBalance.keySet()) {
            int balance = tableBalance.get(sock);
            if (balance <= 0) {
                continue;
            }
            listSocks = listSocks + sock + " в наличии: " + balance + " шт." + "\n";
        }
        return listSocks;
    }
    @Override
    public int currenBalanceByParameters(Color color, Size size, int cottonMin, int cottonMax) {
        int balance = 0;
        for (Sock sock: tableBalance.keySet()) {
            if (sock.getColor() == color && sock.getSize() == size
                && sock.getCottonPart() >= cottonMin && sock.getCottonPart() <= cottonMax) {
                balance = balance + currenBalance(sock);
            }
        }
        return balance;
    }

    private int currenBalance(Sock sock) {
        return tableBalance.getOrDefault(sock, 0);
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
            tableBalance = mapper.readValue(jsonTableBalance, new TypeReference<HashMap<Sock, Integer>>() {
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
