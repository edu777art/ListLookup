package com.csv.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Config
{
    private final String originFile;
    private final String OFSICSVFile;
    private final String OFACSDNFileName;
    private final String OFACAddressFileName;
    private final String OFACAlterFileName;

    public Config() throws IOException{
        //Charge all configs from a config.txt file
        String file ="config.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));

        Map<String, String> map = reader.lines().collect(Collectors.toMap(n-> n.split("=")[0], o -> o.split("=")[1]));

        originFile = map.get("mainDocument");
        OFSICSVFile = map.get("OFSICSVFile");
        OFACSDNFileName = map.get("OFACSDNFileName");
        OFACAddressFileName = map.get("OFACAddressFileName");
        OFACAlterFileName = map.get("OFACAlterFileName");

        reader.close();
    }

    public String getOriginFile(){
        return originFile;
    }

    public String getOFSICSVFile(){
        return OFSICSVFile;
    }


    public String getOFACSDNFileName(){
        return OFACSDNFileName;
    }

    public String getOFACAddressFileName(){
        return OFACAddressFileName;
    }

    public String getOFACAlterFileName(){
        return OFACAlterFileName;
    }
}
