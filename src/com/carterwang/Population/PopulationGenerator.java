package com.carterwang.Population;

import com.carterwang.Data.Params;
import com.carterwang.Utility.RandomUtility;

import java.util.ArrayList;


/**
 * 初代种群生成器
 */
public class PopulationGenerator {
    //基因尾部长度
    private int tailLength;

    //基因头部长度
    private int headLength;

    //每个染色体中的基因数量
    private int numberOfGenes;

    //种群大小
    private int populationSize;

    public PopulationGenerator() {
        this.headLength = Params.HeadLength;
        this.numberOfGenes = Params.NumberOfGenes;
        this.populationSize = Params.PopulationSize;
        this.tailLength = Params.TailLength;
    }


    /**
     * 生成初代种群
     * @return 一个String类型的ArrayList,包含种群的所有个体
     */
    public Population generatePopulation() {
        ArrayList<Individual> individuals = new ArrayList<>();
        for(int i=0;i<populationSize;i++) {
            individuals.add(new Individual(generateIndividual(),i));
        }
        return new Population(individuals);
    }

    /**
     * 单个个体的生成
     * @return 一个String，装有个体的染色体结构
     */
    private String generateIndividual() {
        String res = "";
        int index;
        for(int i=0;i<numberOfGenes;i++) {
            res += Params.F[RandomUtility.indexOfFunction()];
            //生成基因头部
            for(int j=0;j<headLength - 1;j++) {
                if(Math.random() < 0.5) {
                    //选中函数集
                    res += Params.F[RandomUtility.indexOfFunction()];
                } else {
                    //选中终点集
                    res += Params.T[RandomUtility.indexOfTerminal()];
                }
            }
            //生成基因尾部
            for(int j=0;j<tailLength;j++) {
                index = RandomUtility.indexOfTerminal();
                res += Params.T[index];
            }
        }
        return res;
    }
}
