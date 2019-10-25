package com.carterwang.Client;

import com.carterwang.Data.Params;
import com.carterwang.Data.UIParams;
import com.carterwang.EvolutionController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    //用于显示参数名字的标签
    private Label[] labels;

    //用于接收参数输入的输入框
    private TextField[] textFields;

    //根面板
    private HBox root;

    //网格面板
    private GridPane gridPane;

    //进化任务
    Task evolutionTask;

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
            setupRoot();
            setupGridPane();

            //将所有面板加入根面板中
            root.getChildren().add(gridPane);

            //显示scene
            Scene scene = new Scene(root, UIParams.width, UIParams.height);
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
     * 配置网格面板
     */
    private void setupGridPane() {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,20,20,20));
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        labels = new Label[LabelIndex.length];
        textFields = new TextField[LabelIndex.length];
        for(int i=0;i<LabelIndex.length;i++) {
            labels[i] = new Label(LabelIndex.labelName[i]);
            labels[i].setFont(new Font(15));
            textFields[i] = new TextField(LabelIndex.defaultValue[i]);
        }
        gridPane.addColumn(0, labels);
        gridPane.addColumn(1, textFields);

        //开始按钮
        Button startBtn = new Button("Start");
        startBtn.setOnAction(event -> {
            startEvolution();
        });
        GridPane.setHalignment(startBtn, HPos.RIGHT);
        gridPane.add(startBtn, 1, LabelIndex.length);

        //停止按钮
        Button endBtn = new Button("Stop");
        endBtn.setOnAction(event -> {
            endEvolution();
        });
        GridPane.setHalignment(endBtn, HPos.LEFT);
        gridPane.add(endBtn, 0, LabelIndex.length);
    }

    /**
     * 设置响应事件
     */
    private void startEvolution() {
        Params.src = textFields[LabelIndex.FILE_PATH].getText();
        Params.SELECTION_RANGE = Double.parseDouble(textFields[LabelIndex.SELECTION_RANGE].getText());
        Params.PRECISION = Double.parseDouble(textFields[LabelIndex.PRECISION].getText());
        //TODO: CONVERT TEXT TO FUNCTION
        Params.GENERATIONS = Integer.parseInt(textFields[LabelIndex.GENERATIONS].getText());
        Params.PopulationSize = Integer.parseInt(textFields[LabelIndex.POPULATION_SIZE].getText());
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
        evolutionTask = new Task() {
            @Override
            protected Object call() {
                new EvolutionController().start();
                return null;
            }
            @Override
            protected void running() {
                updateMessage("running...");
            }
            @Override
            protected void succeeded() {
                updateMessage("Done!");
            }
            @Override
            protected void cancelled() {
                updateMessage("Cancelled!");
            }
            @Override
            protected void failed() {
                updateMessage("Failed!");
            }
        };

        evolutionTask.messageProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        new Thread(evolutionTask).start();
    }

    private void endEvolution() {

    }

}
