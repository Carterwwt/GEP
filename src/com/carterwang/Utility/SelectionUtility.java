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
        double max = 0;
        int index = 0;
        ArrayList<Individual> allIndividuals = PopulationRepo.getPopulation().getAllIndividuals();
        for(Individual i : allIndividuals) {
            if(i.getFitness() > max) {
                index = allIndividuals.indexOf(i);
                max = i.getFitness();
            }
        }
        return allIndividuals.get(index);
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
        //轮盘赌
        ArrayList<Individual> individuals = new ArrayList<>();
        for(int i=0;i<population.getPopulationSize();i++) {
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
