package me.tretyakovv.p3_finalwork.services;

import java.io.File;

public interface FilesService {
    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);

    boolean cleanDataFile(String dataFileName);

    File getDataFile(String dataFileName);
}
