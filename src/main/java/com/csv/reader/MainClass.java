package com.csv.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.csv.config.Config;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainClass
{
    public static void main(String Args[]) throws IOException{
    //        Date date = new Date();
    //        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");
    //        Config config = new Config();
    //        PrintStream o = new PrintStream(new File("out"+ formatter.format(date) +".csv"));
    //        System.setOut(o);
    //
    //        FileInputStream file = new FileInputStream(new File(config.getOriginFile()));
    //        XSSFWorkbook workbook = new XSSFWorkbook(file);
    //        XSSFSheet sheet = workbook.getSheetAt(0);
    //
    //        Iterator<Row> rowIterator = sheet.iterator();
    //        while (rowIterator.hasNext())
    //        {
    //            Row row = rowIterator.next();
    //            Cell cell = row.getCell(1);
    //            String value = cell.getStringCellValue();
    //            lookOFSI(value, config);
    //            LookOFACSDN(value, config);
    //        }
    //        file.close();
    //        System.out.println("Completed");

    System.out.println(StringChallenge("57888888882339999"));
    }

    public static String StringChallenge(String str) {
        // code goes here
        String result = "false";
        LinkedList<String> l = new LinkedList<>();

        for(int i = 0; i < str.length(); i++) {
            if(l.size() != 0 && String.valueOf(l.get(l.size() - 1).charAt(0)).equals(String.valueOf(str.charAt(i)))){
                l.set(l.size() - 1, l.get(l.size() - 1) + str.charAt(i));
            }else {
                l.add(String.valueOf(str.charAt(i)));
            }
        }

        List fl = l.stream().filter( n-> compareValues(n.length(), Character.getNumericValue(n.charAt(0)))).collect(Collectors.toList());

        if(fl.size() != 0) {
            result = "true";
        }
        return result;
    }

    private static boolean compareValues(int n, int m){
        return n == m;
    }

    public static String MatrixChallenge(String[] strArr) {
        // code goes here
        String result = "not found";

        LinkedList<String> vowels = new LinkedList();
        vowels.add("a");
        vowels.add("e");
        vowels.add("i");
        vowels.add("o");
        vowels.add("u");

        for(int i = 0; i < strArr.length - 1; i ++){
            for(int n = 0; n < strArr[i].length() - 1; n ++) {
                if(vowels.contains(testValue(strArr[i].charAt(n)))
                        && vowels.contains(testValue(strArr[i].charAt(n + 1)))
                        && vowels.contains(testValue(strArr[i+1].charAt(n)))
                        && vowels.contains(testValue(strArr[i+1].charAt(n+1)))
                ) {
                    result = i + "-" + n;
                    break;
                }
            }
            if(!result.equals("not found")){
                break;
            }
        }
        Character.getNumericValue('1');
        return result;

    }

    private static String testValue(char value) {
        System.out.println(value);
        return String.valueOf(value);
    }

    private static void lookOFSI(String value, Config config) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(config.getOFSICSVFile()));
        reader.lines().filter(n-> n.replace(",", " ").contains(value)).forEach(n -> System.out.println("OFSI ConList File," + n));
        reader.close();
    }

    private static void LookOFACSDN(String value, Config config) throws IOException{
        BufferedReader sdn = new BufferedReader(new FileReader(config.getOFACSDNFileName()));
        BufferedReader alt = new BufferedReader(new FileReader(config.getOFACAlterFileName()));

        Stream<String> list =Stream.concat(sdn.lines().map(n-> "SDN File," + n), alt.lines().map(n-> "ALT File," + n));

        list.filter(n -> n.contains(value)).forEach(n ->{
            try{
                System.out.println(n);
                getAddress(n.split(",")[1], config);
                if(n.contains("SDN")) {
                    getALT(n.split(",")[1], alt.lines());
                }else if (n.contains("ALT")){
                    getSDN(n.split(",")[1], config);
                }
            }catch(IOException e){
                System.out.println(e.getMessage() + e.getCause());
            }
        });

        sdn.close();
        alt.close();

    }

    private static void getAddress(String value, Config config) throws IOException{
        BufferedReader add = new BufferedReader(new FileReader(config.getOFACAddressFileName()));
        add.lines().filter(n-> n.split(",")[0].equals(value)).forEach(n->System.out.println("ADD File," + n));
        add.close();
    }

    private static void getALT(String id, Stream<String> alts){
        alts.filter(n -> id.equals(n.split(",")[0])).forEach(n -> System.out.println("ALT File," + n));
    }

    private static void getSDN(String id, Config config) throws FileNotFoundException{
        BufferedReader sdn = new BufferedReader(new FileReader(config.getOFACSDNFileName()));
        sdn.lines().filter(n -> id.equals(n.split(",")[0])).forEach(n -> System.out.println("SDN File," + n));
    }
}
