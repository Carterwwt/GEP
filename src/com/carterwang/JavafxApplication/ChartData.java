package com.carterwang.JavafxApplication;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;


public class ChartData {

    private static List<XYChart.Data<Number, Number>> fitAndGenData = new ArrayList<>();
    private static List<XYChart.Data<Number, Number>> bestFitData = new ArrayList<>();

    public static void addDataToFitAndGen(double fitness) {
        fitAndGenData.add(new XYChart.Data<>(fitAndGenData.size(), fitness));
    }

    public static void addDataToBestFit(double fitness) {
        bestFitData.add(new XYChart.Data<>(bestFitData.size(), fitness));
    }

    public static List<XYChart.Data<Number, Number>> getAvgData() {
        return fitAndGenData;
    }

    public static List<XYChart.Data<Number, Number>> getBestData() {
        return bestFitData;
    }
}
