package com.carterwang.Utility;

import com.carterwang.Data.DataRow;
import com.carterwang.Data.Params;
import com.carterwang.Data.SampleData;
import com.carterwang.Repo.SampleDataRepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtility {

    private FileUtility() {}

    public static void readData() {
        File file = new File("src/data1.txt");
        ArrayList<DataRow> dataRows = new ArrayList<>();
        try {
            FileReader in = new FileReader(file);
            BufferedReader reader = new BufferedReader(in);
            String line;
            while((line = reader.readLine()) != null) {
                String[] res = line.split(",");
                double[] terminal = new double[Params.T.length];
                for(int i=0;i<res.length - 1;i++) {
                    terminal[i] = Double.parseDouble(res[i]);
                }
                dataRows.add(new DataRow(terminal,Double.parseDouble(res[res.length - 1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Params.NumberOfCases = dataRows.size();
        SampleDataRepo.setSampleData(new SampleData(dataRows));
    }
}