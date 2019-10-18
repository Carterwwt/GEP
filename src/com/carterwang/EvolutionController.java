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
        //读入数据
        FileUtility.readData();
        //随机产生初始种群
        new PopulationGenerator().generatePopulation();
        //打印初始种群信息，准备开始进化
        System.out.println(PopulationRepo.getPopulation());
        System.out.println("First Generation created!");
    }

    /**
     * 启动种群进化
     */
    public void start() {
        boolean turnOnMutation = true;
        boolean turnOnRecombination = true;
        boolean turnOnTransposition = true;
        boolean onlyShowBest = true;

        //准备开始进化
        for(int i=0;i<10;i++)
            System.out.println();
        System.out.println("Evolution begins in...");
        for(int i=3;i>0;i--) {
            System.out.printf("%d...\n",i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //开始进化
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

            //判断是否到达进化代数上限
            if(generation + 1 == Params.NumberOfGenerations)
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

