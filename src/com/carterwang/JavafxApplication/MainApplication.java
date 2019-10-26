package com.carterwang.JavafxApplication;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;
import com.carterwang.Utility.SelectionUtility;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * MainApplication中的Label和TextField都是以数组的形式存在
 * LabelIndex类提供索引，能直观地获取到指定的某个组件
 */
class LabelIndex {
    private static int base = 0;
    static int FILE_PATH = base++;
    static int SELECTION_RANGE = base++;
    static int PRECISION = base++;
    static int FUNCTION = base++;
    static int GENERATIONS = base++;
    static int POPULATION_SIZE = base++;
    static int HEAD_LENGTH = base++;
    static int GENE_NUM = base++;
    static int MUTATION_RATE = base++;
    static int ONE_POINT_RATE = base++;
    static int TWO_POINT_RATE = base++;
    static int GENE_RECOM_RATE = base++;
    static int IS_RATE = base++;
    static int RIS_RATE = base++;
    static int GENE_TRANS_RATE = base++;
    static int IS_LENGTH = base++;
    static int RIS_LENGTH = base++;

    static String[] labelName = {
            "文件路径",
            "选择范围",
            "精度",
            "函数集",
            "进化代数",
            "种群大小",
            "基因头部长度",
            "基因数量",
            "变异率",
            "单点重组概率",
            "两点重组概率",
            "基因重组概率",
            "插入元素序列转座概率",
            "根转座概率",
            "基因转座概率",
            "插入元素序列长度",
            "根转座元素长度"
    };

    static String[] defaultValue = {
            "src/data2.txt",
            "1000",
            "0",
            "",
            "1000",
            "100",
            "6",
            "4",
            "0.0385",
            "0.3",
            "0.3",
            "0.1",
            "0.1",
            "0.1",
            "0.1",
            "3",
            "3"
    };

    static int length = labelName.length;
}

/**
 * MainApplication类为JavaFx应用的基类，管理所有图形界面
 */
public class MainApplication extends Application {

    private Label[] labels;
    private TextField[] textFields;
    private TextField chromosomeField;
    private TextField fitnessField;
    private TextField generationField;
    private HBox root;
    private GridPane leftRoot;
    private VBox rightRoot;
    private GridPane resultPane;
    private LineChart<Number, Number> lineChart;

    private double width = 1100;
    private double height = width * 10 / 16;

    //进化进程
    private EvolutionTask evolutionTask;

    /**
     * 启动应用
     * @param args 参数
     */
    public void launchApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //初始化面板
            setupRoot();
            setupLeft();
            setupRight();
            setupResultPane();
            setupLineChart();

            //将所有面板加入根面板中
            root.getChildren().add(leftRoot);
            root.getChildren().add(rightRoot);
            rightRoot.getChildren().add(resultPane);

            //显示scene
            Scene scene = new Scene(root, width, height);
            primaryStage.setTitle("GEP");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置根面板
     */
    private void setupRoot() {
        root = new HBox();
        root.setPadding(new Insets(5,5,5,5));
    }

    /**
     * 配置左半部分的面板
     */
    private void setupLeft() {
        leftRoot = new GridPane();
        leftRoot.setPrefSize(width * 2 / 5, height);
        leftRoot.setPadding(new Insets(10,20,20,20));
        leftRoot.setHgap(20);
        leftRoot.setVgap(10);
        labels = new Label[LabelIndex.length];
        textFields = new TextField[LabelIndex.length];
        for(int i=0;i<LabelIndex.length;i++) {
            labels[i] = new Label(LabelIndex.labelName[i]);
            labels[i].setFont(new Font(15));
            textFields[i] = new TextField(LabelIndex.defaultValue[i]);
        }
        leftRoot.addColumn(0, labels);
        leftRoot.addColumn(1, textFields);

        //开始按钮
        Button startBtn = new Button("开始");
        startBtn.setPrefSize(100,30);
        startBtn.setOnAction(event -> {
            startEvolution();
        });
        GridPane.setHalignment(startBtn, HPos.RIGHT);
        leftRoot.add(startBtn, 1, LabelIndex.length);

        //停止按钮
        Button endBtn = new Button("停止");
        endBtn.setPrefSize(100,30);
        endBtn.setOnAction(event -> {
            endEvolution();
        });
        GridPane.setHalignment(endBtn, HPos.LEFT);
        leftRoot.add(endBtn, 0, LabelIndex.length);
    }

    /**
     * 配置右半部分的面板,是一个VBox
     */
    private void setupRight() {
        rightRoot = new VBox();
        rightRoot.setAlignment(Pos.TOP_CENTER);
        rightRoot.setPadding(new Insets(10,50,20,20));
        rightRoot.setSpacing(10);
        rightRoot.setPrefWidth(width * 3 / 5);

//        Label title = new Label("Best Individual");
//        title.setFont(new Font(18));
//        rightRoot.getChildren().add(title);

    }

    /**
     * 顾名思义
     */
    private void setupResultPane() {
        resultPane = new GridPane();
        resultPane.setVgap(15);
        resultPane.setHgap(10);

        Label label1 = new Label("染色体结构");
        Label label2 = new Label("适应度");
        Label label3 = new Label("代数");
        label1.setFont(new Font(15));
        label2.setFont(new Font(15));
        label3.setFont(new Font(15));
        resultPane.add(label1, 0, 0);
        resultPane.add(label2, 0, 1);
        resultPane.add(label3, 0, 2);

        chromosomeField = new TextField();
        chromosomeField.setFont(new Font(15));
        chromosomeField.setEditable(false);
        resultPane.add(chromosomeField, 1, 0);
        GridPane.setHgrow(chromosomeField, Priority.ALWAYS);

        fitnessField = new TextField();
        fitnessField.setFont(new Font(15));
        fitnessField.setEditable(false);
        resultPane.add(fitnessField, 1, 1);
        GridPane.setHgrow(fitnessField, Priority.ALWAYS);

        generationField = new TextField();
        generationField.setFont(new Font(15));
        generationField.setEditable(false);
        resultPane.add(generationField, 1, 2);
        GridPane.setHgrow(generationField, Priority.ALWAYS);
    }

    private XYChart.Series<Number, Number> fitnessSeries;
    private XYChart.Series<Number, Number> bestFitSeries;

    private void setupLineChart() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("适应度折线图");
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        xAxis.setLabel("进化代数");
        //平均适应度折线
        fitnessSeries = new XYChart.Series<>();
        fitnessSeries.setName("平均适应度");
        lineChart.getData().add(fitnessSeries);
        //最佳适应度折线
        bestFitSeries = new XYChart.Series<>();
        bestFitSeries.setName("最佳个体适应度");
        lineChart.getData().add(bestFitSeries);
        rightRoot.getChildren().add(lineChart);
    }

    private void refreshLineChart() {
        List<XYChart.Data<Number, Number>> avgData = ChartData.getAvgData();
        fitnessSeries.getData().clear();
        fitnessSeries.getData().addAll(avgData);
        List<XYChart.Data<Number, Number>> bestData = ChartData.getBestData();
        bestFitSeries.getData().clear();
        bestFitSeries.getData().addAll(bestData);
    }

    /**
     * 开始按钮的响应事件
     */
    private void startEvolution() {
        Params.src = textFields[LabelIndex.FILE_PATH].getText();
        Params.SELECTION_RANGE = Double.parseDouble(textFields[LabelIndex.SELECTION_RANGE].getText());
        Params.PRECISION = Double.parseDouble(textFields[LabelIndex.PRECISION].getText());
        //TODO: CONVERT TEXT TO FUNCTION
        Params.GENERATIONS = Integer.parseInt(textFields[LabelIndex.GENERATIONS].getText());
        Params.POPULATION_SIZE = Integer.parseInt(textFields[LabelIndex.POPULATION_SIZE].getText());
        Params.HEAD_LENGTH = Integer.parseInt(textFields[LabelIndex.HEAD_LENGTH].getText());
        Params.GENE_NUM = Integer.parseInt(textFields[LabelIndex.GENE_NUM].getText());
        Params.MUTATION_RATE = Double.parseDouble(textFields[LabelIndex.MUTATION_RATE].getText());
        Params.ONE_RECOM_RATE = Double.parseDouble(textFields[LabelIndex.ONE_POINT_RATE].getText());
        Params.TWO_RECOM_RATE = Double.parseDouble(textFields[LabelIndex.TWO_POINT_RATE].getText());
        Params.GENE_RECOM_RATE = Double.parseDouble(textFields[LabelIndex.GENE_RECOM_RATE].getText());
        Params.IS_RATE = Double.parseDouble(textFields[LabelIndex.IS_RATE].getText());
        Params.RIS_RATE = Double.parseDouble(textFields[LabelIndex.RIS_RATE].getText());
        Params.GENE_TRANS_RATE = Double.parseDouble(textFields[LabelIndex.GENE_TRANS_RATE].getText());
        Params.IS_LENGTH = Integer.parseInt(textFields[LabelIndex.IS_LENGTH].getText());
        Params.RIS_LENGTH = Integer.parseInt(textFields[LabelIndex.RIS_LENGTH].getText());
        System.out.println("设置完毕");

        //多线程处理，进化耗时较长，避免阻塞UI线程
        evolutionTask = new EvolutionTask(this);
        evolutionTask.progressProperty().addListener((observable, oldValue, newValue) -> {
            refreshLineChart();
        });

        //清空上一次的数据
        ChartData.getFitAndGeneration().clear();
        Thread th = new Thread(evolutionTask);
        th.setDaemon(true);
        th.start();
    }

    /**
     * 停止按钮的响应事件
     */
    private void endEvolution() {
        if(evolutionTask != null)
            evolutionTask.cancel();
    }

    void setResult() {
        Individual best = SelectionUtility.selectBestIndividual();
        chromosomeField.setText(best.getChromosome());
        fitnessField.setText(String.valueOf(best.getFitness()));
        generationField.setText(String.valueOf(evolutionTask.controller.getGeneration()));
        refreshLineChart();
    }

    void setGeneration(int generation) {
        generationField.setText(String.valueOf(generation));
    }
}
