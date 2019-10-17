package com.carterwang.Utility;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;
import com.carterwang.Population.Population;
import com.carterwang.Repo.PopulationRepo;


/**
 * 重组工具
 */
public class RecombinationUtility {
    private RecombinationUtility() {}

    /**
     * 进行三种重组
     * 1.单点重组
     * 2.两点重组
     * 3.基因重组
     */
    public static void performRecombination() {
        Population population = PopulationRepo.getPopulation();
        for(Individual ind : population.getAllIndividuals()) {
            if(Math.random() <= Params.Onepoint_Recombination_Rate) {
                performOnePoint(ind);
            }
            if(Math.random() <= Params.Towpoint_Recombination_Rate) {
                performTowPoint(ind);
            }
            if(Math.random() <= Params.Gene_Recombination_Rate) {
                performGene(ind);
            }
        }

    }

    private static void performOnePoint(Individual ind) {
        Individual ind1 = ind;
        Individual ind2 = RandomUtility.randomIndividual();
        StringBuilder buffer1 = new StringBuilder(ind1.getChromosome());
        StringBuilder buffer2 = new StringBuilder(ind2.getChromosome());
        int point = RandomUtility.indexOfChromosome();
        char c1 = buffer1.charAt(point);
        char c2 = buffer2.charAt(point);
        buffer1.setCharAt(point,c2);
        buffer2.setCharAt(point,c1);
        ind1.setChromosome(buffer1.toString());
        ind2.setChromosome(buffer2.toString());
    }

    private static void performTowPoint(Individual ind) {
        Individual ind1 = ind;
        Individual ind2 = RandomUtility.randomIndividual();
        int point1 = RandomUtility.indexOfChromosome();
        int point2 = RandomUtility.indexOfChromosome();
        StringBuilder buffer1 = new StringBuilder(ind1.getChromosome());
        StringBuilder buffer2 = new StringBuilder(ind2.getChromosome());
        char c1_p1 = buffer1.charAt(point1);
        char c1_p2 = buffer1.charAt(point2);
        char c2_p1 = buffer2.charAt(point1);
        char c2_p2 = buffer2.charAt(point2);
        buffer1.setCharAt(point1,c2_p1);
        buffer1.setCharAt(point2,c2_p2);
        buffer2.setCharAt(point1,c1_p1);
        buffer2.setCharAt(point2,c1_p2);
        ind1.setChromosome(buffer1.toString());
        ind2.setChromosome(buffer2.toString());
    }

    private static void performGene(Individual ind) {
        Individual ind1 = ind;
        Individual ind2 = RandomUtility.randomIndividual();
        int gene = RandomUtility.indexOfGenes();
        int geneStart = gene * Params.GeneLength;
        int geneEnd = geneStart + Params.GeneLength;
        StringBuilder buffer1 = new StringBuilder(ind1.getChromosome());
        StringBuilder buffer2 = new StringBuilder(ind2.getChromosome());
        String gene1 = buffer1.substring(geneStart,geneEnd);
        String gene2 = buffer2.substring(geneStart,geneEnd);
        buffer1.replace(geneStart,geneEnd,gene2);
        buffer2.replace(geneStart,geneEnd,gene1);
        ind1.setChromosome(buffer1.toString());
        ind2.setChromosome(buffer2.toString());
    }
}
