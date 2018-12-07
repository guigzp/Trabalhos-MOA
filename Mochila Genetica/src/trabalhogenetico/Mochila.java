package trabalhogenetico;

import java.util.List;

public class Mochila {

    //Calcula o melhor resultado possivel usando um algoritmo dinamico
    public double CalculoMochilaPD(List<Item> itens, int capacidade) {
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
