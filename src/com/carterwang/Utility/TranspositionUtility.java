package com.carterwang.Utility;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;

/**
 * 转座工具
 */
public class TranspositionUtility {
    private TranspositionUtility() {}

    public static void performTransposition() {
        if(Math.random() <= Params.IS_Transposition_Rate) {
            performIS();
        }
        if(Math.random() <= Params.RIS_Transposition_Rate) {
            performRIS();
        }
        if(Math.random() <= Params.Gene_Transposition_Rate) {
            performGene();
        }
    }

    private static void performIS() {
        Individual ind = RandomUtility.randomIndividual();
        int index;
        while(!RandomUtility.isHeadGene(index = RandomUtility.indexOfChromosome()));
        int length = RandomUtility.randomLengthOfIS();
        if(!RandomUtility.isHeadGene(index + length)) {
            //对转座的元素进行裁剪

        }
    }

    private static void performRIS() {

    }

    private static void performGene() {

    }
}
