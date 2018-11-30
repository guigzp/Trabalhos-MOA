/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author guiza
 */
public class Item {
    private double peso;
    private double preco;

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPreco() {
        return preco;
    }

    public Item(double peso, double preco) {
        this.peso = peso;
        this.preco = preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
