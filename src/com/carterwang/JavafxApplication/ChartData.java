package com.carterwang.JavafxApplication;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;


public class ChartData {

    private static List<Double> fitAndGeneration = new ArrayList<>();
    private static List<Double> bestFit = new ArrayList<>();

    public static List<Double> getFitAndGeneration() {
        return fitAndGeneration;
    }

    public static List<Double> getBestFit() {
        return bestFit;
    }

    public static List<XYChart.Data<Number, Number>> getAvgData() {
        List<XYChart.Data<Number, Number>> data = new ArrayList<>();
        for(int i=0;i<fitAndGeneration.size();i++) {
            data.add(new XYChart.Data<>(i, fitAndGeneration.get(i)));
        }
        return data;
    }

    public static List<XYChart.Data<Number, Number>> getBestData() {
        List<XYChart.Data<Number, Number>> data = new ArrayList<>();
        for(int i=0;i<bestFit.size();i++) {
            data.add(new XYChart.Data<>(i, bestFit.get(i)));
        }
        return data;
    }
}
