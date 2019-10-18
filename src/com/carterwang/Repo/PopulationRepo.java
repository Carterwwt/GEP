package com.carterwang.Repo;

import com.carterwang.Population.Individual;
import com.carterwang.Population.Population;

public class PopulationRepo {
    private static Population population;

    private static Individual best;

    public static Individual getBest() {
        return best;
    }

    public static void setBest(Individual best) {
        PopulationRepo.best = best;
    }

    public static Population getPopulation() {
        return population;
    }

    public static void setPopulation(Population population) {
        PopulationRepo.population = population;
    }
}
