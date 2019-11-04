package com.carterwang.Population;

import com.carterwang.Data.Params;
import com.carterwang.Repo.PopulationRepo;
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
        this.headLength = Params.HEAD_LENGTH;
        this.numberOfGenes = Params.GENE_NUM;
        this.populationSize = Params.POPULATION_SIZE;
        this.tailLength = Params.TAIL_LENGTH;
    }


    /**
     * 生成初代种群
     * @return 一个String类型的ArrayList,包含种群的所有个体
     */
    public void generatePopulation() {
        ArrayList<Individual> individuals = new ArrayList<>();
        for(int i=0;i<populationSize;i++) {
            individuals.add(new Individual(generateIndividual(),i));
        }
        Population population = new Population(individuals);
        PopulationRepo.setPopulation(population);
        PopulationRepo.setBest(population.getAllIndividuals().get(0));
    }

    /**
     * 单个个体的生成
     * @return 一个String，装有个体的染色体结构
     */
    private String generateIndividual() {
        StringBuilder res = new StringBuilder();
        int index;
        for(int i=0;i<numberOfGenes;i++) {
            int k = headLength / 2;
            //生成基因头部
            for(int j=0;j<k;j++) {
                //选中函数集
                res.append(Params.F[RandomUtility.indexOfFunction()]);
            }
            for(int j=0;j<headLength - k;j++) {
                if(Math.random() < 0.5)
                    res.append(Params.F[RandomUtility.indexOfFunction()]);
                else
                    res.append(Params.T[RandomUtility.indexOfTerminal()]);
            }
            //生成基因尾部
            for(int j=0;j<tailLength;j++) {
                index = RandomUtility.indexOfTerminal();
                res.append(Params.T[index]);
            }
        }
        return res.toString();
    }
}
