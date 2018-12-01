/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
/**
 *
 * @author guiza
 */
public class TrabalhoGenetico {
   private static int qtdItens;
    private static List<Item> itens = new ArrayList<>();
    private static int capacidade;
    private static int tamanhoPopulacao;
    private static int qtdGeracoes;
    private static double probCruzamento;
    private static double probMutacao;

     public static void lerArquivo() {
        System.out.print("Insira o nome do arquivo de entrada: ");
        Scanner entrada = new Scanner(System.in);
        String nomeArquivo = entrada.nextLine();
        int aux;
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
    //javac src/*.java
    //java -cp ./src TrabalhoMoa 100 25 300 1 100 200 0.6 0.015 at.txt
    //[qtdElem][iniValor][endValor][iniPeso][endPeso][capacidade][tamPopulacao][qtdGeracoes][proCruz][probMuta][arquivo de Saida]
    public static void generator(String[] args){
        int n, iniValor, capacidade,endValor, iniPeso, endPeso, tamanhoPopulacao, qtdGeracoes;
        double probCruzamento, probMutacao;

        n = parseInt(args[0]);
        iniValor = parseInt(args[1]);
        endValor = parseInt(args[2]);
        iniPeso = parseInt(args[3]);
        endPeso = parseInt(args[4]);
        capacidade = parseInt(args[5]);
        tamanhoPopulacao = parseInt(args[6]);
        qtdGeracoes = parseInt(args[7]);
        probCruzamento = parseDouble(args[8]);
        probMutacao = parseDouble(args[9]);
        try {
            PrintStream arquivo = new PrintStream(args[10]);
            System.setOut(arquivo);
            Random r = new Random();
            int intervaloValor = endValor - iniValor;
            int intervaloPeso = endPeso - iniPeso;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //generator(args);
        lerArquivo();

        System.out.println("------------------Calculo da Mochila Binaria utilizando programação dinamica---------------");
        Mochila teste = new Mochila(itens, capacidade);
        System.out.println("Valor Solução: " + teste.MochilaPraValer(itens,capacidade) + "\n");


        System.out.println("\n------------------Calculo da Mochila Binaria utilizando algoritmo genetico------------------");
        MochilaGenetica teste2 = new MochilaGenetica(qtdItens, itens, capacidade, tamanhoPopulacao, qtdGeracoes, probCruzamento, probMutacao);
        teste2.executaMochilaGenetica();
        
    }

}
