package com.carterwang.Data;

import java.util.ArrayList;

public class SampleData {
    private ArrayList<DataRow> dataRows;

    public SampleData(ArrayList<DataRow> dataRows) {
        this.dataRows = dataRows;
    }

    public ArrayList<DataRow> getDataRows() {
        return dataRows;
    }

    @Override
    public String toString() {
        String output = "";
        for(DataRow dataRow : dataRows) {
            output += dataRow.toString();
        }
        return output;
    }
}
