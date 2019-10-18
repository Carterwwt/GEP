package com.carterwang.Utility;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;
import com.carterwang.Population.Population;
import com.carterwang.Repo.PopulationRepo;

import java.util.ArrayList;

/**
 * 遗传选择工具
 */
public class SelectionUtility {
    private SelectionUtility() {}

    /**
     * 挑选最佳个体
     * @return 返回最佳个体
     */
    public static Individual selectBestIndividual() {
        return PopulationRepo.getBest();
    }

    /**
     * 进行遗传选择
     * 采用轮盘赌算法
     */
    public static void performSelection() {
        Population population = PopulationRepo.getPopulation();
        double sumFitness = 0;
        //统计总适应度
        for(Individual i : population.getAllIndividuals()) {
            sumFitness += i.getFitness();
        }
        //计算累积概率
        double[] possibility = new double[population.getPopulationSize()];
        double former = 0;
        for(int i =0;i<population.getPopulationSize();i++) {
            Individual in = population.getAllIndividuals().get(i);
            possibility[i] = (former + in.getFitness()) / sumFitness;
            former += in.getFitness();
        }
        ArrayList<Individual> individuals = new ArrayList<>();
        //精英策略
        int eliteNum = 3;
        Individual best = SelectionUtility.selectBestIndividual();
        for(int i=0;i<eliteNum;i++) {
            individuals.add(best);
        }
        //轮盘赌
        for(int i=0;i<population.getPopulationSize() - eliteNum;i++) {
            double r = Math.random();
            int index = selectedIndex(possibility, r);
            Individual in = new Individual(population.getAllIndividuals().get(index));
            in.setIndex(i);
            individuals.add(in);
        }
        population.setAllIndividuals(individuals);
    }

    /**
     *
     * @param possibility 概率分布情况
     * @param r 随机生成的数
     * @return 返回选中个体的下标
     */
    private static int selectedIndex(double[] possibility, double r) {
        for(int i=0;i<possibility.length;i++) {
            if(i == 0) {
                if (r < possibility[i]) {
                    return 0;
                }
            } else {
                if(r <= possibility[i] && r > possibility[i-1])
                    return i;
            }
        }
        return -1;
    }

    public static boolean isTerminal(char c) {
        boolean isTerminal = true;
        for(int i=0;i< Params.F.length;i++) {
            if (c == Params.F[i]) {
                isTerminal = false;
                break;
            }
        }
        return isTerminal;
    }

    public static boolean isTerminal(String s) {
        boolean isTerminal = true;
        for(int i=0;i< Params.F.length;i++) {
            if (s.equals("" + Params.F[i])) {
                isTerminal = false;
                break;
            }
        }
        return isTerminal;
    }

}
