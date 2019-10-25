package com.carterwang;

import com.carterwang.Data.Params;
import com.carterwang.Utility.FileUtility;
import com.carterwang.Utility.FitnessUtility;
import com.carterwang.Utility.MutationUtility;
import com.carterwang.Utility.RecombinationUtility;
import com.carterwang.Utility.SelectionUtility;
import com.carterwang.Utility.TranspositionUtility;
import com.carterwang.Population.Individual;
import com.carterwang.Population.PopulationGenerator;
import com.carterwang.Repo.PopulationRepo;


public class EvolutionController {

    private int generation = 0;
    private boolean turnOnMutation = true;
    private boolean turnOnRecombination = true;
    private boolean turnOnTransposition = true;
    private boolean onlyShowBest = true;
    private boolean shouldEnd = false;

    public boolean isShouldEnd() {
        return shouldEnd;
    }

    public int getGeneration() {
        return generation;
    }

    public EvolutionController() {
        //读入数据
        FileUtility.readData();
        //随机产生初始种群
        new PopulationGenerator().generatePopulation();
        //打印初始种群信息，准备开始进化
        System.out.println(PopulationRepo.getPopulation());
        System.out.println("First Generation created!");
        showReady();
    }

    private void showReady() {
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
    }

    /**
     * 启动种群进化
     */
    public void evolution() {
        //计算适应度
        FitnessUtility.calculateFitness();

        //打印种群信息
        if(onlyShowBest) {
            printBest();
        } else {
            printPopulation();
        }

        //判断是否产生最优个体
        if(FitnessUtility.reachMaximum()) {
            shouldEnd = true;
            return;
        }

        //判断是否到达进化代数上限
        if(generation + 1 == Params.GENERATIONS) {
            shouldEnd = true;
            return;
        }

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


    private void printPopulation() {
        System.out.println("Generation: " + generation);
        System.out.println(PopulationRepo.getPopulation());
    }

    private void printBest() {
        Individual best = SelectionUtility.selectBestIndividual();
        System.out.printf("Generation [%d]\n",generation);
        System.out.println(best);
    }

    public void showResult() {
        Individual best = SelectionUtility.selectBestIndividual();
        //best = BiTree.disposeBest(best);
        System.out.println();
        System.out.println("最优个体:");
        System.out.printf("%s",best.toString());
        System.out.println("代数: " + generation);
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<best.getGenesInfixString().size();i++) {
            builder.append('(');
            builder.append(best.getGenesInfixString().get(i));
            builder.append(')');
            if(i < best.getGenesInfixString().size() - 1)
                builder.append('+');
        }
        System.out.println("中缀表达式: " + builder.toString());
    }

}

