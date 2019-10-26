package com.carterwang.JavafxApplication;

import com.carterwang.Data.Params;
import com.carterwang.EvolutionController;
import javafx.concurrent.Task;

public class EvolutionTask extends Task<Void> {

    MainApplication application;
    EvolutionController controller;

    public EvolutionTask(MainApplication application) {
        this.application = application;
        controller = new EvolutionController();
    }

    @Override
    protected Void call() {
        while(true) {
            controller.evolution();
            application.setGeneration(controller.getGeneration());
            if(controller.getGeneration() % 5 == 0) {
                updateProgress(controller.getGeneration(), Params.GENERATIONS);
            }
            if(isCancelled() || controller.isShouldEnd()) {
                updateMessage("进化终止!");
                break;
            }
        }
        controller.showResult();
        return null;
    }

    @Override
    protected void running() {
        super.running();
    }

    @Override
    protected void succeeded() {
        updateMessage("Done!");
        application.setResult();
    }

    @Override
    protected void cancelled() {
        updateMessage("Cancelled!");
        application.setResult();
    }

    @Override
    protected void failed() {
        updateMessage("Failed!");
    }


}
