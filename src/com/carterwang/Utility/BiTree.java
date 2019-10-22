package com.carterwang.Utility;

import com.carterwang.Data.Params;
import com.carterwang.Population.Individual;

import java.util.ArrayList;

/**
 * 二叉树结构，包含二叉树三种遍历方法与建树方法
 */
public class BiTree {
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

    public static Individual disposeBest(Individual best) {
        ArrayList<BiTree> trees = generateBiTree(best.getChromosome());
        ArrayList<String> infix = new ArrayList<>();
        for(int i = 0;i < Params.NumberOfGenes;i++) {
            assert trees != null;
            infix.add(BiTree.traversal(trees.get(i), 1));
        }
        best.setGenesInfixString(infix);
        return best;
    }
    /**
     * 传入一个个体的染色体结构（层次遍历的结果）将其中的每一个基因建成一个二叉树，装在BiTree类型的List里返回
     * @param str 个体染色体结构
     * @return List，装有一个个体中每个基因的树结构
     */
    private static ArrayList<BiTree> generateBiTree(String str) {
        if(str.length() == 0)
            return null;
        int startIndex = 0;
        ArrayList<BiTree> trees = new ArrayList<>();
        for(int i = 0; i< Params.NumberOfGenes; i++) {
            String sub = str.substring(startIndex,startIndex + Params.GeneLength);
            int t;
            if(SelectionUtility.isTerminal(sub.charAt(0)))
                t = 0;
            else
                t = 1;
            BiTree root = generateBiTree(sub, 0, t, true);
            startIndex += Params.GeneLength;
            trees.add(root);
        }
        return trees;
    }

    /**
     *
     * @param str 染色体结构层次遍历的结果
     * @param cur 当前字符
     * @param last 上一个函数运算符的参数个数
     * @param left 当前是否在左分支中
     * @return 基因的二叉树结构
     */
    private static BiTree generateBiTree(String str, int cur, int last, boolean left) {
        char c = str.charAt(cur);
        if(SelectionUtility.isTerminal(c)) {
            return new BiTree(c,null,null);
        } else {
            BiTree l, r;
            int num = Params.map.get(c);
            if(left) {
                l = generateBiTree(str, cur + last, num, true);
                r = num == 1 ? null : generateBiTree(str, cur + last + num, num, false);
            } else {
                int t = Params.map.get(str.charAt(cur - 1));
                l = generateBiTree(str, cur + t + 1, num, true);
                r = num == 1 ? null : generateBiTree(str, cur + t + num, num, false);
            }
            return new BiTree(c, l, r);
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
