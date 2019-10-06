package com.carterwang.Population;

import java.util.ArrayList;

public class Population {
    private ArrayList<Individual> allIndividuals;

    public ArrayList<Individual> getAllIndividuals() {
        return allIndividuals;
    }

    public void setAllIndividuals(ArrayList<Individual> allIndividuals) {
        this.allIndividuals = allIndividuals;
    }

    public int getPopulationSize() {
        return allIndividuals.size();
    }

    public Population(ArrayList<Individual> allIndividuals) {
        this.allIndividuals = allIndividuals;
    }

    @Override
    public String toString() {
        String output = "";
        for(Individual in : allIndividuals) {
            output += in.toString();
        }
        return output;
    }
}
