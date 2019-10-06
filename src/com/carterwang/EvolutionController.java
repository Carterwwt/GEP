package com.carterwang;

import com.carterwang.Data.Params;
import com.carterwang.Utility.*;
import com.carterwang.Utility.FileUtility;
import com.carterwang.Population.Individual;
import com.carterwang.Population.PopulationGenerator;
import com.carterwang.Repo.PopulationRepo;

public class EvolutionController {

    private int generation = 0;

    public EvolutionController() {
        Params.T = Params.getT();
        PopulationRepo.setPopulation(new PopulationGenerator().generatePopulation());
        FileUtility.readData();
    }

    /**
     * 启动种群进化
     */
    public void start() {
        boolean turnOnMutation = true;
        boolean turnOnRecombination = true;
        boolean turnOnTransposition = true;
        boolean onlyShowBest = false;
        while(true) {
            //计算适应度
            FitnessUtility.calculateFitness();

            //打印种群信息
            if(onlyShowBest) {
                printBest();
            } else {
                printPopulation();
            }

            //判断是否产生最优个体
            if(FitnessUtility.reachMaximum())
                break;

            //遗传选择
            SelectionUtility.performSelection();

            //遗传变异
            if(turnOnMutation) {
                MutationUtility.performMutation();
            }

            //遗传重组
            if(turnOnRecombination) {
                RecombinationUtility.performRecombination();
            }

            //遗传转座
            if(turnOnTransposition) {
                TranspositionUtility.performTransposition();
            }

            //判断是否到达进化代数上限
            if(generation + 1 == Params.NumberOfGenerations)
                break;
            generation++;
        }
        //打印最佳个体
        showResult();
    }


    private void printPopulation() {
        System.out.println("Generation: " + generation);
        System.out.println(PopulationRepo.getPopulation());
    }

    private void printBest() {
        Individual best = SelectionUtility.selectBestIndividual();
        System.out.printf("Generation [%d]\n%s [%f]\n",generation,best.getChromosome(),best.getFitness());
    }

    private void showResult() {
        Individual best = SelectionUtility.selectBestIndividual();
        System.out.println();
        System.out.println("最优个体:");
        System.out.printf("%s",best.toString());
        System.out.println("代数: " + generation);
        System.out.println("染色体结构: " + best.getChromosome());
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<best.getGenesInfixString().size();i++) {
            buffer.append('(');
            buffer.append(best.getGenesInfixString().get(i));
            buffer.append(')');
            if(i < best.getGenesInfixString().size() - 1)
                buffer.append('+');
        }
        System.out.println("中缀表达式: " + buffer.toString());
    }

}

