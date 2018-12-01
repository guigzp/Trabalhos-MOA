package trabalhogenetico;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MochilaGenetica {

    //atributos da mochila binária
    private List<Item> itens;
    private int capacidade;

    //atributos do algoritmo genetico
    private int qtdGeracoes;
    private static double probCruzamento;
    private static double probMutacao;
    private ArrayList<ArrayList<Integer>> cromossomos;
    private static int tamanhoPopulacao;
    private ArrayList<Integer> melhorAdaptado = new ArrayList<>();
    private ArrayList<Integer> solucoes = new ArrayList<>();

    //Construtor da classe
    public MochilaGenetica(List<Item> itens, int capacidade, int tamanhoPopulacao, int qtdGeracoes, double probCruzamento, double probMutacao){
        this.itens = itens;
        this.capacidade = capacidade;
        MochilaGenetica.tamanhoPopulacao = tamanhoPopulacao;
        MochilaGenetica.probCruzamento = probCruzamento;
        MochilaGenetica.probMutacao = probMutacao;
        cromossomos = new ArrayList<>();
        this.qtdGeracoes = qtdGeracoes;
        gerarCromossomos();
    }

    //verifica se existe algum elemento com peso menor q a capacidade
    public boolean getMenorItem(ArrayList<Integer> item){
        boolean Achou = false;
        for(int i = 0; i < itens.size() && !Achou; i++){
            if(itens.get(i).getPeso() <= capacidade){
                for(int j =0; j < itens.size(); j++){
                    if(j != i){
                        item.add(0);
                    }else{
                        item.add(1);
                    }
                }
                Achou = true;
            }
        }
        return Achou;
    }

    public boolean confirma(){
        return (solucoes.lastIndexOf(getValor()) - solucoes.indexOf(getValor())) <= this.qtdGeracoes/4;
    }

    //Executa o algoritmo até atingir a quantidade de gerações passada
    public void executaMochilaGenetica(){
        for(int l=1; (l< qtdGeracoes+1) && confirma() ; l++){
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

    //Cria a primeira geração de cromossomos
    public void gerarCromossomos(){
        ArrayList<Integer> cromossomo;
        ArrayList<Integer> item = new ArrayList<>();
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
        getMelhorAdaptado();
        if(melhorAdaptado.isEmpty()){
            boolean achou = getMenorItem(item);
            if(!achou){
                System.out.println("Não exite solução para a mochila");
                System.exit('1');
            }
            cromossomos.set(0,item);
            setMelhorAdaptado(item);
        }
    }

    public void setSolucoes(int solucao) {
        this.solucoes.add(solucao);
    }

    //Retorna o cromosso que gera a melhor resposta para o algoritmo da mochila binária
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

    //Retorna o valor do melhorAdaptado
    public int getValor(){
        int valorTotal =0;
        for(int j=0; j<melhorAdaptado.size() ; j++) {
            valorTotal += melhorAdaptado.get(j) * itens.get(j).getPreco();
        }
        return valorTotal;
    }

    //Possibilita a mudança da lista melhorAdaptado
    public void setMelhorAdaptado(ArrayList<Integer> melhor){
        this.melhorAdaptado = melhor;
    }

    //Realiza o cruzamento de dois cromossomos
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

    //Realiza a mutação de um cromossomo
    public ArrayList<Integer> mutacao(ArrayList<Integer> entrada) {
        ArrayList<Integer> novo = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < itens.size(); i++) {
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

    //Gera a proxima geração de cromossomos
    public int geraGeracao(){
        setSolucoes(getValor());
        ArrayList<Integer> melhor1, melhor2;
        melhor1 = getMelhorAdaptado();
        cromossomos.remove(melhor1);
        melhor2 = getMelhorAdaptado();
        if(melhor2.isEmpty()){
            cromossomos.clear();
            cromossomos.add(melhor1);
            for (int i = 1; i < tamanhoPopulacao; i++) {
                cromossomos.add(mutacao(melhor1));
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
