package com.carterwang.JavafxApplication;

import com.carterwang.Calculator.GRCM;
import com.carterwang.Data.DataRow;
import com.carterwang.Data.Params;
import com.carterwang.Data.SampleData;
import com.carterwang.Population.Individual;
import com.carterwang.Repo.PopulationRepo;
import com.carterwang.Repo.SampleDataRepo;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;


public class ChartData {

    private static List<XYChart.Data<Number, Number>> avgData = new ArrayList<>();
    private static List<XYChart.Data<Number, Number>> bestFitData = new ArrayList<>();
    private static List<XYChart.Data<Number, Number>> modelData = new ArrayList<>();

    public static void addDataToFitAndGen(double fitness) {
        avgData.add(new XYChart.Data<>(avgData.size(), fitness));
    }

    public static void addDataToBestFit(double fitness) {
        bestFitData.add(new XYChart.Data<>(bestFitData.size(), fitness));
    }

    public static List<XYChart.Data<Number, Number>> getAvgData() {
        return avgData;
    }

    public static List<XYChart.Data<Number, Number>> getBestData() {
        return bestFitData;
    }

    public static List<XYChart.Data<Number, Number>> getModelData() {
        modelData.clear();
        Individual best = PopulationRepo.getBest();
        SampleData sampleData = SampleDataRepo.getSampleData();
        int sum = 0, geneLength = Params.GENE_LENGTH;
        for(int i=0;i<sampleData.getDataRows().size();i++) {
            sum = 0;
            for(int j=0;j< Params.GENE_NUM;j++) {
                sum += GRCM.compute(best.getChromosome().substring(j * geneLength, j * geneLength + geneLength), sampleData.getDataRows().get(i));
            }
            modelData.add(new XYChart.Data<>(i+1, sum));
        }
        return modelData;
    }

    public static List<XYChart.Data<Number, Number>> getTargetData() {
        List<XYChart.Data<Number, Number>> data = new ArrayList<>();
        SampleData sampleData = SampleDataRepo.getSampleData();
        ArrayList<DataRow> dataRows = sampleData.getDataRows();
        for(int i=0;i<dataRows.size();i++) {
            data.add(new XYChart.Data<>(i+1, dataRows.get(i).getValue()));
        }
        return data;
    }
}
