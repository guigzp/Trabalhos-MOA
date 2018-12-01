package trabalhogenetico;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class TrabalhoGenetico {

    //atributos da mochila binária
    private static List<Item> itens = new ArrayList<>();
    private static int capacidade;

    //atributos do algoritmo genetico
    private static int tamanhoPopulacao;
    private static int qtdGeracoes;
    private static double probCruzamento;
    private static double probMutacao;

    //atributo auxiliar
    private static int modoDeExecuçao = 2;

    //Faz a leitura do arquivo e transforma os seus dados nos atributos dessa classe
    public static void lerArquivo() {
        System.out.print("Insira o nome do arquivo de entrada: ");
        Scanner entrada = new Scanner(System.in);
        String nomeArquivo = entrada.nextLine();
        int aux, qtdItens;
        try {
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            qtdItens = parseInt(linha);
            for(int i = 0; i < qtdItens; i++){
                linha = lerArq.readLine();
                aux = parseInt(linha);
                itens.add(new Item(0, aux));
            }
            for(int i = 0; i < qtdItens; i++){
                linha = lerArq.readLine();
                aux = parseInt(linha);
                itens.get(i).setPeso(aux);
            }
            linha = lerArq.readLine();
            capacidade = parseInt(linha);
            linha = lerArq.readLine();
            tamanhoPopulacao = parseInt(linha);
            linha = lerArq.readLine();
            qtdGeracoes = parseInt(linha);
            linha = lerArq.readLine();
            probCruzamento = parseDouble(linha);
            linha = lerArq.readLine();
            probMutacao = parseDouble(linha);
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    //Gera o arquivo txt com os valores de entrada.
    public static void generator(){
        int n=0, iniValor, capacidade=0, endValor, iniPeso, endPeso, tamanhoPopulacao=0, qtdGeracoes=0, intervaloValor = 0, intervaloPeso = 0;
        double probCruzamento=0, probMutacao=0;
        Scanner entrada = new Scanner(System.in);

        while (n<=0){
            System.out.print("Digite a quandidade de itens: ");
            n = entrada.nextInt();
        }
        System.out.print("Digite o limite inferior do valor dos itens: ");
        iniValor = entrada.nextInt();
        while (intervaloValor <= 0){
            System.out.print("Digite o limite superior do valor dos itens: ");
            endValor = entrada.nextInt();
            intervaloValor = endValor - iniValor;
        }
        System.out.print("Digite o limite inferior do peso dos itens: ");
        iniPeso = entrada.nextInt();
        while (intervaloPeso <= 0){
            System.out.print("Digite o limite superior do peso dos itens: ");
            endPeso = entrada.nextInt();
            intervaloPeso = endPeso - iniPeso;
        }
        while (capacidade<=0){
            System.out.print("Digite a capacidade da mochila: ");
            capacidade = entrada.nextInt();
        }
        while (tamanhoPopulacao <=0){
            System.out.print("Digite o tamanho da população: ");
            tamanhoPopulacao = entrada.nextInt();
        }
        while (qtdGeracoes <=0){
            System.out.print("Digite a quantidade maxima de gerações: ");
            qtdGeracoes = entrada.nextInt();
        }
        while (probCruzamento <=0){
            System.out.print("Digite a probabilidade de cruzamento: ");
            probCruzamento = entrada.nextDouble();
        }
        while (probMutacao <=0){
            System.out.print("Digite a probabilidade de mutação: ");
            probMutacao = entrada.nextDouble();
        }
        try {
            System.out.print("Digite o nome do arquivo a ser gerado: ");
            entrada.nextLine();
            String arquivoNome = entrada.nextLine();
            PrintStream stdout = System.out;
            PrintStream arquivo = new PrintStream(arquivoNome);
            System.setOut(arquivo);
            Random r = new Random();
            System.out.println(n);
            for(int i=0; i < n; i++){
                System.out.println(r.nextInt(intervaloValor)+iniValor);
            }
            for(int i=0; i < n; i++){
                System.out.println(r.nextInt(intervaloPeso)+iniPeso);
            }
            System.out.println(capacidade);
            System.out.println(tamanhoPopulacao);
            System.out.println(qtdGeracoes);
            System.out.println(probCruzamento);
            System.out.print(probMutacao);
            System.setOut(stdout);
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possivel criar o arquivo");
        }
    }

    //Classe principal
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite 0 para gerar um arquivo de itens ou 1 para continuar: ");
        while(modoDeExecuçao < 0 || modoDeExecuçao > 1){
            modoDeExecuçao = entrada.nextInt();
            if(modoDeExecuçao == 0){
                generator();
            } else if(modoDeExecuçao != 1){
                System.out.println("Opção invalida\n");
                System.out.print("Escolha uma opção valida: ");
            }
        }
        lerArquivo();
        System.out.println("------------------Calculo da Mochila Binaria utilizando programação dinamica---------------");
        Mochila teste = new Mochila();
        System.out.println("Valor Solução: " + teste.CalculoMochilaPD(itens,capacidade) + "\n");
        System.out.println("\n------------------Calculo da Mochila Binaria utilizando algoritmo genetico------------------");
        MochilaGenetica teste2 = new MochilaGenetica(itens, capacidade, tamanhoPopulacao, qtdGeracoes, probCruzamento, probMutacao);
        teste2.executaMochilaGenetica();
    }
}
