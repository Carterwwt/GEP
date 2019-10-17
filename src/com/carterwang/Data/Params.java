package com.carterwang.Data;

import java.util.HashMap;
import java.util.Map;

public class Params {

    //选择范围
    public static double SelectionRange = 1000;

    //精度
    public static double Precision = 1100;

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
    static int T_Size = 10;

    //终点集
    public static char[] T;

    //进化代数
    public static int NumberOfGenerations = 8000;

    //种群大小
    public static int PopulationSize = 100;

    //适应度样本数据大小
    public static int NumberOfCases = 0;

    //基因头部长度
    public static int HeadLength = 6;

    //基因尾部长度
    public static int TailLength = HeadLength * (map.get('+') - 1) + 1;

    //基因长度
    public static int GeneLength = HeadLength + TailLength;

    //每条染色体的基因数量
    public static int NumberOfGenes = 4;

    //染色体长度
    public static int ChromosomeLength = NumberOfGenes * GeneLength;

    //变异率
    public static double MutationRate = 0.3;

    //单点重组概率
    public static double Onepoint_Recombination_Rate = 0.4;

    //两点重组概率
    public static double Towpoint_Recombination_Rate = 0.2;

    //基因重组概率
    public static double Gene_Recombination_Rate = 0.1;

    //插入序列元素转座的概率
    public static double IS_Transposition_Rate = 0.2;

    //根转座的概率
    public static double RIS_Transposition_Rate = 0.2;

    //基因转座概率
    public static double Gene_Transposition_Rate = 0.1;

    //插入序列元素的长度
    public static int IS_Elements_Length = 3;

    //根转座元素的长度
    public static int RIS_Elements_Length = 3;

    public static char[] getT() {
        char[] T_All = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] c = new char[T_Size];
        for(int i=0;i<T_Size;i++) {
            c[i] = T_All[i];
        }
        return c;
    }
}


