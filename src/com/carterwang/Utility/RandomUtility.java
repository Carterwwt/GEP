package com.carterwang.Utility;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;
import com.carterwang.Repo.PopulationRepo;

public class RandomUtility {

    /**
     * 给定整数range 随机生成 x 满足 0<=x<range
     * @param range 随机选取的范围
     * @return 给定范围内的随机整数
     */
    public static int random(int range) {
        return (int)(Math.random() * range);
    }

    /**
     * 随机选取一个终点集符号的下标
     * @return 选中的终点集符号下标
     */
    public static int indexOfTerminal() {
        return (int)(Math.random() * Params.T.length);
    }

    /**
     *
     * @return 随机选取的一个函数集符号的下标
     */
    public static int indexOfFunction() {
        return (int)(Math.random() * Params.F.length);
    }

    /**
     *
     * @return 随机抽取染色体中的一个参数，返回下标
     */
    public static int indexOfChromosome() {
        return (int)(Math.random() * Params.ChromosomeLength);
    }

    public static int indexOfGenes() {
        return (int)(Math.random() * Params.NumberOfGenes);
    }

    /**
     *
     * @return 随机抽取种群中的一个个体
     */
    public static Individual randomIndividual() {
        return PopulationRepo.getPopulation().getAllIndividuals().get(RandomUtility.random(Params.PopulationSize));
    }

    /**
     *
     * @return 随机生成的IS元素长度
     */
    public static int randomLengthOfIS() {
        return (int)(Math.random() * (Params.IS_Elements_Length) + 1);
    }

    /**
     *
     * @return 随机生成的RIS元素长度
     */
    public static int randomLengthOfRIS() {
        return (int)(Math.random() * (Params.RIS_Elements_Length) + 1);
    }

    /**
     *
     * @param index 元素下标
     * @return 返回是否是处在头部基因
     */
    public static boolean isHeadGene(int index) {
        return (index % Params.GeneLength) < Params.HeadLength;
    }

    /**
     *
     * @param index 元素下标
     * @return 返回是否处在基因的第一位，主要用于避免根转座
     */
    public static boolean isFirstInGene(int index) { return (index % Params.GeneLength) == 0;}

}
