package com.carterwang.Population;

import java.util.ArrayList;

/**
 * 种群个体的结构
 */
public class Individual {
    //染色体结构
    private String chromosome;

    //当前适应度
    private double fitness;

    //所有基因的中缀表达式
    private ArrayList<String> genesInfixString = new ArrayList<>();

    //当前个体的序号
    private int index;

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public ArrayList<String> getGenesInfixString() {
        return genesInfixString;
    }

    public void setGenesInfixString(ArrayList<String> genesInfixString) {
        this.genesInfixString = genesInfixString;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Individual(Individual individual){
        this.chromosome = individual.getChromosome();
        this.fitness = individual.getFitness();
        this.genesInfixString = individual.getGenesInfixString();
    }

    public Individual(String chromosome, int index) {
        this.chromosome = chromosome;
        this.index = index;
    }

@Override
public String toString() {
        String output = "";
        for(String str : genesInfixString) {
        output += String.format("Sub-ET%d %-16s",genesInfixString.indexOf(str), str);
        }
        return String.format("%s [%2d] [%9.4f] %s\n", chromosome,index,fitness,output);
    }
}
