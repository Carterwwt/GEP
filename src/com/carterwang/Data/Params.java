package com.carterwang.Data;

import java.util.HashMap;
import java.util.Map;

public class Params {

    //文件路径
    public static String src = "src/data2.txt";

    //选择范围
    public static double SELECTION_RANGE = 1000;

    //精度
    public static double PRECISION = 0;

    //函数集
    public static char[] F = {'+','-','*','/'};

    //函数的参数个数
    public static Map<Character, Integer> map = new HashMap<Character, Integer>() {{
        put('+',2);
        put('-',2);
        put('*',2);
        put('/',2);
    }};

    //终点集参数个数
    public static int T_SIZE = 0;

    //终点集
    public static char[] T;

    //进化代数
    public static int GENERATIONS = 50;

    //种群大小
    public static int POPULATION_SIZE = 100;

    //适应度样本数据大小
    public static int CASES_NUM = 0;

    //基因头部长度
    public static int HEAD_LENGTH = 6;

    //基因尾部长度
    public static int TAIL_LENGTH = HEAD_LENGTH * (map.get('+') - 1) + 1;

    //基因长度
    public static int GENE_LENGTH = HEAD_LENGTH + TAIL_LENGTH;

    //每条染色体的基因数量
    public static int GENE_NUM = 4;

    //染色体长度
    public static int CHROMOSOME_LENGTH = GENE_NUM * GENE_LENGTH;

    //变异率
    public static double MUTATION_RATE = 0.0385;

    //单点重组概率
    public static double ONE_RECOM_RATE = 0.3;

    //两点重组概率
    public static double TWO_RECOM_RATE = 0.3;

    //基因重组概率
    public static double GENE_RECOM_RATE = 0.1;

    //插入序列元素转座的概率
    public static double IS_RATE = 0.1;

    //根转座的概率
    public static double RIS_RATE = 0.1;

    //基因转座概率
    public static double GENE_TRANS_RATE = 0.1;

    //插入序列元素的长度
    public static int IS_LENGTH = 3;

    //根转座元素的长度
    public static int RIS_LENGTH = 3;

    public static char[] getT() {
        char[] T_All = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] c = new char[T_SIZE];
        for(int i = 0; i< T_SIZE; i++) {
            c[i] = T_All[i];
        }
        return c;
    }
}


