/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author guiza
 */
public class Mochila {
    private List<Item> itens;
    private double capacidade;


    public Mochila(List<Item> itens, double capacidade) {
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

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }


    public double MochilaPraValer(List<Item> itens, double capacidade, int quantidade) {
        if(quantidade == 0 || capacidade == 0){
            return 0;
        }else if(itens.get(quantidade-1).getPeso() <= capacidade){
            Item teste = itens.get(quantidade - 1);
            return Math.max( teste.getPreco() + MochilaPraValer(itens, capacidade - teste.getPeso() , quantidade -1),
                    MochilaPraValer(itens, capacidade, quantidade -1));
        }else{
            return MochilaPraValer(itens, capacidade, quantidade -1);
        }
    }

}
