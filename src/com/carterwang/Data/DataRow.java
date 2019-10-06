package com.carterwang.Data;

public class DataRow {
    double[] terminal;
    double value;

    public DataRow(double[] terminal, double value) {
        this.terminal = terminal;
        this.value = value;
    }

    public double[] getTerminal() {
        return terminal;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        String output = "";
        for(int i=0;i<terminal.length;i++) {
            output += String.format("%c: %-10f ",i + 'a',terminal[i]);
        }
        output += "f(..): " + String.valueOf(value) + "\n";
        return output;
    }
}
