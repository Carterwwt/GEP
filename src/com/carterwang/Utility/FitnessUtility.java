package com.carterwang.Utility;

import com.carterwang.Calculator.GRCM;
import com.carterwang.Data.DataRow;
import com.carterwang.Data.Params;
import com.carterwang.Data.SampleData;
import com.carterwang.Population.Individual;
import com.carterwang.Population.Population;
import com.carterwang.Repo.PopulationRepo;
import com.carterwang.Repo.SampleDataRepo;
import com.carterwang.Calculator.Polish;

import java.util.ArrayList;

/**
 * 计算适应度的工具类，由它来组织调度二叉树结构处理与逆波兰表达式计算
 */
public class FitnessUtility {

    private FitnessUtility(){}

    /**
     *
     * @return 是否有个体达到适应度最大值
     */
    public static boolean reachMaximum() {
        boolean hasMaximum = false;
        for(Individual i : PopulationRepo.getPopulation().getAllIndividuals()) {
            if(Math.abs(i.getFitness() - Params.NumberOfCases * Params.SelectionRange) <= Params.Precision)
                hasMaximum = true;
        }
        return hasMaximum;
    }

    /**
     * 对单个个体进行适应度计算，步骤如下
     * 1.对层次遍历的表达式进行建树，转化为中缀表达式
     * 2.将中缀表达式传入Calculator中进行计算
     */
    public static void calculateFitness() {
        //generateInfixString();
        compute();
    }

    /**
     * 将每个染色体的结构转化为中缀表达式，通过建立二叉树来完成
     */
    private static void generateInfixString() {
        Population population = PopulationRepo.getPopulation();
        ArrayList<BiTree> trees;
        Individual in;
        //对于每个个体
        for (int i=0;i<population.getAllIndividuals().size();i++) {
            ArrayList<String> genesInfixString = new ArrayList<>();
            in = population.getAllIndividuals().get(i);
            trees = BiTree.generateBiTree(in.getChromosome());
            genesInfixString.clear();
            //将每个基因生成的二叉树
            for(BiTree t : trees) {
                //做中序遍历，将中缀表达式存进一个数组
                genesInfixString.add(BiTree.traversal(t,1));
            }
            in.setGenesInfixString(genesInfixString);
        }
        PopulationRepo.setPopulation(population);
    }

    /**
     * f = sum(M - |C(i,j) - T|)
     * M为选择范围， C(i,j)为个体i在样本数据集j中的返回值，T为适应度样本目标值，即表达式的值
     */
    private static void compute() {
        Population population = PopulationRepo.getPopulation();
        SampleData data = SampleDataRepo.getSampleData();
        double T;
        double fitness;
        int beginIndex;
        for(Individual in : population.getAllIndividuals()) {
            fitness = 0;
            for(DataRow row : data.getDataRows()) {
                T = 0;
                for (int i=0;i<Params.NumberOfGenes;i++) {
                    beginIndex = i * Params.GeneLength;
                    T += GRCM.compute(in.getChromosome().substring(beginIndex, beginIndex + Params.GeneLength), row);
                }
                fitness = fitness + (Params.SelectionRange - Math.abs(row.getValue() - T));
            }
            if(fitness > 0)
                in.setFitness(fitness);
            else
                in.setFitness(0);
        }
    }
}



/**
 * 二叉树结构，包含二叉树三种遍历方法与建树方法
 */
class BiTree {
    private char val;
    private BiTree left;
    private BiTree right;

    private static String traversal = "";

    public BiTree(){
        this.left = null;
        this.right = null;
    }

    public BiTree(char val, BiTree left, BiTree right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * 传入一个个体的染色体结构（层次遍历的结果）将其中的每一个基因建成一个二叉树，装在BiTree类型的List里返回
     * @param str 个体染色体结构
     * @return List，装有一个个体中每个基因的树结构
     */
    public static ArrayList<BiTree> generateBiTree(String str) {
        if(str.length() == 0)
            return null;
        int startIndex = 0;
        ArrayList<BiTree> trees = new ArrayList<>();
        for(int i =0;i< Params.NumberOfGenes;i++) {
            BiTree root = new BiTree();
            root.left = generateBiTree(str.substring(startIndex,startIndex + Params.GeneLength), 1);
            startIndex += Params.GeneLength;
            trees.add(root.left);
        }
        return trees;
    }

    /**
     * 递归建树
     * @param str 染色体结构
     * @param index 当前节点的序号
     * @return 二叉树节点
     */
    private static BiTree generateBiTree(String str, int index) {
        char cur = str.charAt(index - 1);
        if(SelectionUtility.isTerminal(cur)) {
            return new BiTree(cur,null,null);
        } else {
            return new BiTree(cur,generateBiTree(str, index * 2),generateBiTree(str,index * 2 + 1));
        }
    }

    /**
     * 二叉树遍历
     * @param root 树根节点
     * @param order 0：前序  1：中序  2：后序
     * @return 遍历结果，用String返回
     */
    public static String traversal(BiTree root, int order) {
        traversal = "";
        switch (order) {
            case 0:
                preOrderTraversal(root);
                break;
            case 1:
                inOrderTraversal(root);
                break;
            case 2:
                postOrderTraversal(root);
                break;
            default:
                break;
        }
        return traversal;
    }

    /**
     * 前序遍历二叉树
     * @param node 二叉树节点
     */
    private static void preOrderTraversal(BiTree node) {
        if(node != null) {
            traversal += node.val;
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
        }
    }

    /**
     * 中序遍历二叉树
     * @param node 二叉树节点
     */
    private static void inOrderTraversal(BiTree node) {
        if(node != null) {
            inOrderTraversal(node.left);
            traversal += node.val;
            inOrderTraversal(node.right);
        }
    }

    /**
     * 后序遍历二叉树
     * @param node 二叉树节点
     */
    private static void postOrderTraversal(BiTree node) {
        if(node != null) {
            inOrderTraversal(node.left);
            inOrderTraversal(node.right);
            traversal += node.val;
        }
    }
}