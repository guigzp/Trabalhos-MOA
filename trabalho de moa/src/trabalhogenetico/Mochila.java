/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalhogenetico;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author guiza
 */
public class Mochila {
    private List<Item> itens;
    private int capacidade;


    public Mochila(List<Item> itens, int capacidade) {
        this.itens = itens;
        this.capacidade = capacidade;
        }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(LinkedList<Item> itens) {
        this.itens = itens;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }


    public double MochilaPraValer(List<Item> itens, int capacidade) {
        double v[][] = new double[itens.size()+1][capacidade+1];
        for(int i=0; i<= itens.size(); i++){
            for(int j=0; j<= capacidade; j++){
                if(i == 0 || j == 0){
                    v[i][j] = 0;
                }else if(itens.get(i -1).getPeso() <= j){
                    Item teste = itens.get(i -1);
                    v[i][j] = Math.max(teste.getPreco() + v[i-1][j-itens.get(i-1).getPeso()], v[i-1][j]);
                }else{
                    v[i][j] =  v[i-1][j];
                }
            }
            }
        return v[itens.size()][capacidade];
    }

}
