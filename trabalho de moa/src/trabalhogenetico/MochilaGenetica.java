package trabalhogenetico;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MochilaGenetica {

    private int qtdItens;
    private List<Item> itens;
    private int capacidade;
    private int qtdGeracoes;
    private int tentativas = 0;
    private static double probCruzamento;
    private static double probMutacao;
    private ArrayList<ArrayList<Integer>> cromossomos;
    private static int tamanhoPopulacao;
    private ArrayList<Integer> melhorAdaptado = new ArrayList<>();

    public MochilaGenetica(int qtdItens, List<Item> itens, int capacidade, int tamanhoPopulacao, int qtdGeracoes, double probCruzamento, double probMutacao){
        this.qtdItens = qtdItens;
        this.itens = itens;
        this.capacidade = capacidade;
        MochilaGenetica.tamanhoPopulacao = tamanhoPopulacao;
        MochilaGenetica.probCruzamento = probCruzamento;
        MochilaGenetica.probMutacao = probMutacao;
        cromossomos = new ArrayList<>();
        this.qtdGeracoes = qtdGeracoes;
        gerarCromossomos();
    }

    public boolean geraSolucoesIniciais(){
        boolean naoAchou = false;
        ArrayList<Integer> aux = new ArrayList<>();
        for(int i = 0; i < qtdItens && !naoAchou; i++){
            if(itens.get(i).getPeso() <= capacidade){
                for(int j =0; j < qtdItens; j++){
                    if(j != i){
                        aux.add(0);
                    }else{
                        aux.add(1);
                    }
                }
                cromossomos.add(aux);
                cromossomos.add(aux);
                naoAchou = true;
            }
        }
        return naoAchou;
    }

    public void executaMochilaGenetica(){
        for(int l=1; l< qtdGeracoes+1; l++){
            System.out.println("Geração: " + l);
            System.out.println("Valor da Melhor Solução: " + getValor());
            System.out.print("Itens da Soluçao: ");
            for(int i=1; i< melhorAdaptado.size()+1 ;i++){
                if(melhorAdaptado.get(i-1) == 1){
                    System.out.print(i + " ");
                }
            }
            System.out.println("\n");
            int reinicia = geraGeracao();
            if(reinicia == 1){
                l=0;
            }
        }
    }

    public void gerarCromossomos(){
        ArrayList<Integer> cromossomo;
        Random random = new Random();
        for(int i=0; i<tamanhoPopulacao ;i++){
            cromossomo = new ArrayList<>();
            for(int j=0; j<itens.size(); j++){
                if(random.nextDouble() > 0.5){
                    cromossomo.add(1);
                } else {
                    cromossomo.add(0);
                }
            }
            cromossomos.add(cromossomo);
        }
        geraSolucoesIniciais();
        getMelhorAdaptado();
    }

    public ArrayList<Integer> getMelhorAdaptado() {
        int valorTotal, pesoTotal, melhorLocalValor=0;
        ArrayList<Integer> melhorLocal = new ArrayList<>();
        for(int i=0; i<cromossomos.size(); i++){
            valorTotal = 0;
            pesoTotal = 0;
            for(int j=0; j<itens.size() ; j++){
                if(cromossomos.get(i).get(j) == 1){
                    pesoTotal += itens.get(j).getPeso();
                    valorTotal += itens.get(j).getPreco();
                }
            }
            if(pesoTotal <= capacidade && melhorLocalValor <= valorTotal){
                melhorLocal = cromossomos.get(i);
                melhorLocalValor = valorTotal;
                if(melhorLocalValor > getValor()){
                    setMelhorAdaptado(melhorLocal);
                }
            }
        }
        return melhorLocal;
    }

    public int getValor(){
        int valorTotal =0;
        for(int j=0; j<melhorAdaptado.size() ; j++) {
            valorTotal += melhorAdaptado.get(j) * itens.get(j).getPreco();
        }
        return valorTotal;
    }

    public void setMelhorAdaptado(ArrayList<Integer> melhor){
        this.melhorAdaptado = melhor;
    }

    public ArrayList<Integer> cruzamento(ArrayList<Integer> melhor1, ArrayList<Integer> melhor2){
        Random random = new Random();
        if(probCruzamento > random.nextDouble()){
            int pontoDeCruzamento = random.nextInt(itens.size());
            ArrayList<Integer> filho = new ArrayList<>();
            for(int i=0; i< itens.size(); i++){
                if(i < pontoDeCruzamento){
                    filho.add(melhor1.get(i));
                } else {
                    filho.add(melhor2.get(i));
                }
            }
            return filho;
        }
        return melhorAdaptado;
    }

    public ArrayList<Integer> mutacao(ArrayList<Integer> entrada) {
        ArrayList<Integer> novo = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < qtdItens; i++) {
            if (random.nextDouble() < probMutacao) {
                if (entrada.get(i) == 1) {
                    novo.add(0);
                } else {
                    novo.add(1);
                }
            } else {
                novo.add(entrada.get(i));
            }
        }
        return novo;
    }

    public int geraGeracao(){
        ArrayList<Integer> melhor1, melhor2;
        melhor1 = getMelhorAdaptado();
        cromossomos.remove(melhor1);
        melhor2 = getMelhorAdaptado();
        if(melhor1.isEmpty()){
            tentativas++;
            if(tentativas == 100){
                System.out.println("Depois de 100 tentativas o algoritmo não conseguiu nenhuma resposta valida por isso foi abortado");
                System.exit(1);
            }
            cromossomos.clear();
            gerarCromossomos();
            return 1;
        }else if(melhor2.isEmpty()){
            cromossomos.clear();
            cromossomos.add(melhor1);
            for (int i = 1; i < tamanhoPopulacao; i++) {
                cromossomos.add(mutacao(cruzamento(melhor1, melhor1)));
            }
        }else{
            cromossomos.clear();
            cromossomos.add(melhor1);
            for (int i = 1; i < tamanhoPopulacao; i++) {
                cromossomos.add(mutacao(cruzamento(melhor1, melhor2)));
            }
        }
        return 0;
    }
}
