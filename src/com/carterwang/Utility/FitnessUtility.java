package com.carterwang.Utility;

import com.carterwang.Calculator.GRCM;
import com.carterwang.Data.DataRow;
import com.carterwang.Data.Params;
import com.carterwang.Data.SampleData;
import com.carterwang.Population.Individual;
import com.carterwang.Population.Population;
import com.carterwang.Repo.PopulationRepo;
import com.carterwang.Repo.SampleDataRepo;

/**
 * 计算适应度的工具类，由它来组织调度二叉树结构处理与逆波兰表达式计算
 */
public class FitnessUtility {

    private FitnessUtility(){}

    /**
     *
     * @return 是否有个体达到适应度最大值
     */
    public static boolean reachMaximum() {
        boolean hasMaximum = false;
        for(Individual i : PopulationRepo.getPopulation().getAllIndividuals()) {
            if(Math.abs(i.getFitness() - Params.NumberOfCases * Params.SELECTION_RANGE) <= Params.PRECISION)
                hasMaximum = true;
        }
        return hasMaximum;
    }

    /**
     * 计算适应度
     */
    public static void calculateFitness() {
        compute();
    }

    /**
     * f = sum(M - |C(i,j) - T|)
     * M为选择范围， C(i,j)为个体i在样本数据集j中的返回值，T为适应度样本目标值，即表达式的值
     */
    private static void compute() {
        Population population = PopulationRepo.getPopulation();
        SampleData data = SampleDataRepo.getSampleData();
        double T;
        double fitness;
        int beginIndex;
        //重置最大适应度个体
        PopulationRepo.getBest().setFitness(0);
        for(Individual in : population.getAllIndividuals()) {
            fitness = 0;
            for(DataRow row : data.getDataRows()) {
                T = 0;
                for (int i = 0; i<Params.GENE_NUM; i++) {
                    beginIndex = i * Params.GENE_LENGTH;
                    //用GRCM算法计算,不同基因间用+连接
                    T += GRCM.compute(in.getChromosome().substring(beginIndex, beginIndex + Params.GENE_LENGTH), row);
                }
                fitness = fitness + (Params.SELECTION_RANGE - Math.abs(row.getValue() - T));
            }
            if(fitness > 0)
                in.setFitness(fitness);
            else
                in.setFitness(0);
            //更新最大适应度个体
            if(fitness > PopulationRepo.getBest().getFitness()) {
                PopulationRepo.setBest(in);
            }
        }
    }
}
