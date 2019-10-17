package com.carterwang.Utility;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;
import com.carterwang.Population.Population;
import com.carterwang.Repo.PopulationRepo;

/**
 * 转座工具
 */
public class TranspositionUtility {
    private TranspositionUtility() {}

    public static void performTransposition() {
        Population population = PopulationRepo.getPopulation();
        for(Individual ind : population.getAllIndividuals()) {
            if(Math.random() <= Params.IS_Transposition_Rate) {
                performIS(ind);
            }
            if(Math.random() <= Params.RIS_Transposition_Rate) {
                performRIS(ind);
            }
            if(Math.random() <= Params.Gene_Transposition_Rate) {
                performGene(ind);
            }
        }
    }

    private static void performIS(Individual ind) {
        int length = RandomUtility.randomLengthOfIS();
        int src,dest;
        src = RandomUtility.indexOfChromosome();
        while(src + length > Params.ChromosomeLength) {
            src = RandomUtility.indexOfChromosome();
        }
        dest = RandomUtility.indexOfChromosome();
        while(!RandomUtility.isHeadGene(dest) || RandomUtility.isFirstInGene(dest)) {
            dest = RandomUtility.indexOfChromosome();
        }
        int startIndex = dest / Params.GeneLength * Params.GeneLength;
        int endIndex = startIndex + Params.GeneLength;
        String IS = ind.getChromosome().substring(src,src + length);
        StringBuilder str = new StringBuilder(ind.getChromosome().substring(startIndex, endIndex));
        str.insert(dest - startIndex,IS);
        StringBuilder chromosome = new StringBuilder(ind.getChromosome());
        chromosome.replace(startIndex,startIndex + Params.HeadLength,str.substring(0,Params.HeadLength));
        ind.setChromosome(chromosome.toString());
    }

    private static void performRIS(Individual ind) {

    }

    private static void performGene(Individual ind) {

    }
}
