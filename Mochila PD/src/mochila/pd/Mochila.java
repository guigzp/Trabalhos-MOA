/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mochila.pd;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author guiza
 */
import java.util.List;

public class Mochila {

    //Calcula o melhor resultado possivel usando um algoritmo dinamico
    public double CalculoMochilaPD(List<Item> itens, int capacidade) {
        double memo[][] = new double[itens.size()+1][capacidade+1];
        for(int i=0; i<= itens.size(); i++){
            for(int j=0; j<= capacidade; j++){
                if(i == 0 || j == 0){
                    memo[i][j] = 0;
                }else if(itens.get(i -1).getPeso() <= j){
                    Item teste = itens.get(i -1);
                    memo[i][j] = Math.max(teste.getPreco() + memo[i-1][j-itens.get(i-1).getPeso()], memo[i-1][j]);
                }else{
                    memo[i][j] =  memo[i-1][j];
                }
            }
        }
        return memo[itens.size()][capacidade];
    }

    // Mochila Binária sem usar programação dinâmica com formulação recursiva
    public double MochilaRecursiva(List<Item> itens, double capacidade, int quantidade) {
        if(quantidade == 0 || capacidade == 0){
            return 0;
        }else if(itens.get(quantidade-1).getPeso() <= capacidade){
            Item teste = itens.get(quantidade - 1);
            return Math.max( teste.getPreco() + MochilaRecursiva(itens, capacidade - teste.getPeso() , quantidade -1),
                    MochilaRecursiva(itens, capacidade, quantidade -1));
        }else{
            return MochilaRecursiva(itens, capacidade, quantidade -1);
        }
    }

}