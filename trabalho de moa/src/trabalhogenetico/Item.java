package trabalhogenetico;

public class Item {
    private int peso;
    private int preco;

    //Construtor da classe
    public Item(int peso, int preco) {
        this.peso = peso;
        this.preco = preco;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getPreco() {
        return preco;
    }
}
