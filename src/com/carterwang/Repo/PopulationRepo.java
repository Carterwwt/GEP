package com.carterwang.Repo;

import com.carterwang.Population.Population;

public class PopulationRepo {
    private static Population population;

    public static Population getPopulation() {
        return population;
    }

    public static void setPopulation(Population population) {
        PopulationRepo.population = population;
    }
}
