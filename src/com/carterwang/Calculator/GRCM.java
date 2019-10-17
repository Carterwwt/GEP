package com.carterwang.Calculator;

import com.carterwang.Data.DataRow;
import com.carterwang.Data.Params;
import com.carterwang.Utility.SelectionUtility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GRCM {

    /**
     * 计算一个基因的表达式的值
     * @param gene 基因结构
     * @return 表达式的值
     */
    public static double compute(String gene, DataRow row) {
        int length = validLength(gene);
        int p = length - 1;
        int f = length - 1;
        String[] str = gene.split("");
        char c;
        //将基因有效长度内的终结符替换成相应的数值
        for(int i=0;i<length;i++) {
            c = str[i].charAt(0);
            if(SelectionUtility.isTerminal(c)) {
                str[i] = str[i].replaceAll("" + c, String.valueOf(row.getTerminal()[c - 'a']));
            }
        }
        while(f != 0) {
            if(SelectionUtility.isTerminal(str[p])) {
                p--;
            } else {
                if(Params.map.get(str[p].charAt(0)) == 2) {
                    //取两个数出来，计算结果放入gene[p]
                    str[p] = getResult(str[f-1],str[f],str[p]);
                    p--;
                    f -= 2;
                }
            }
        }
        return Double.parseDouble(str[0]);
    }

    /**
     * 递归算出基因有效长度
     * @param gene 基因结构
     * @return 基因有效长度
     */
    private static int validLength(String gene) {
        int l = 0, r = 0;
        while(r < gene.length()) {
            if(l == r && SelectionUtility.isTerminal(gene.charAt(l)))
                break;
            if(!SelectionUtility.isTerminal(gene.charAt(l)))
                r += 2;
            l++;
        }
        return l + 1;
    }

    private static String getResult(String sour, String dest, String op) {
        BigDecimal l = new BigDecimal(sour);
        BigDecimal r = new BigDecimal(dest);
        try {
            switch (op) {
                case "+":
                    return l.add(r).toString();
                case "-":
                    return l.subtract(r).toString();
                case "*":
                    return l.multiply(r).toString();
                case "/":
                    return l.divide(r, 8, RoundingMode.HALF_UP).toString();
            }
        } catch (Exception e) {
            return "0.0";
        }
        return "0.0";
    }
}
