/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhogenetico;
/**
 *
 * @author guiza
 */
public class Item {
    private int peso;
    private int preco;

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getPreco() {
        return preco;
    }

    public Item(int peso, int preco) {
        this.peso = peso;
        this.preco = preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }
}
