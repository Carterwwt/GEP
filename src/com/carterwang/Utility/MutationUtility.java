package com.carterwang.Utility;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;
import com.carterwang.Population.Population;
import com.carterwang.Repo.PopulationRepo;

/**
 * 变异工具
 */
public class MutationUtility {
    private MutationUtility() {}

    /**
     * 遗传算子[变异]
     * 单点变异
     */
    public static void performMutation() {
        Population population = PopulationRepo.getPopulation();
        double r = 0;
        int selected = 0;
        for(Individual i : population.getAllIndividuals()) {
            r = Math.random();
            if(r <= Params.MUTATION_RATE) {
                //System.out.println(i.getIndex());
                StringBuilder str = new StringBuilder(i.getChromosome());
                char c;
                selected = RandomUtility.indexOfChromosome();
                if(RandomUtility.isHeadGene(selected) && Math.random() < 0.5) {
                    //如果选中的是基因头部并且选中的是函数集
                    c = Params.F[RandomUtility.indexOfFunction()];
                } else {
                    //如果选中的是基因头部并且选中的是终点集
                    //或者选中的是基因尾部
                    c = Params.T[RandomUtility.indexOfTerminal()];
                }
                str.setCharAt(selected, c);
                //System.out.println("before " + i.getChromosome());
                i.setChromosome(str.toString());
                //System.out.println("after  " + i.getChromosome());
            }
        }
    }

}
