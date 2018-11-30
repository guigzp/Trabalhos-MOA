import java.util.ArrayList;
import java.util.List;
import java.util.Random;

	public class MochilaGenetica {

	    private int qtdItens;
	    private List<Item> itens;
	    private double capacidade;

		private static int qtdGeracoes;
		private static double probCruzamento;
		private static double probMutacao;
		private ArrayList<ArrayList<Integer>> cromossomos;
	    private static int tamanhoPopulacao;
	    private double melhorAdaptado = 0;

	    public MochilaGenetica(int qtdItens, List<Item> itens, double capacidade, int tamanhoPopulacao, int qtdGeracoes, double probCruzamento, double probMutacao){
	        this.qtdItens = qtdItens;
	        this.itens = itens;
	        this.capacidade = capacidade;
	        MochilaGenetica.tamanhoPopulacao = tamanhoPopulacao;
			MochilaGenetica.qtdGeracoes = qtdGeracoes;
			MochilaGenetica.probCruzamento = probCruzamento;
			MochilaGenetica.probMutacao = probMutacao;
			cromossomos = new ArrayList<>();

	        gerarCromossomos();

	        for(ArrayList<Integer> cromossomo: cromossomos){
	            for(Integer c: cromossomo){
                    System.out.print(c);
                }
                System.out.println();
            }

            for(int l=0; l< qtdGeracoes; l++){
                geraGeracao();
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
        }

        public int getMelhorAdaptado(int ignore) {
	        double valorTotal, pesoTotal;
	        int posicaoMelhor = -1;
	        for(int i=0; i<tamanhoPopulacao; i++){
	            if(i == ignore){
	                continue;
                }
	            valorTotal = 0;
	            pesoTotal = 0;
	            for(int j=0; j<itens.size() ; j++){
	                if(cromossomos.get(i).get(j) == 1){
	                    pesoTotal = itens.get(j).getPeso();
                        valorTotal = itens.get(j).getPreco();
                    }
                }
                if(pesoTotal <= capacidade && melhorAdaptado <= valorTotal){
                    posicaoMelhor = i;
                    melhorAdaptado = valorTotal;
                }
            }
            return posicaoMelhor;
        }

        public void cruzamento(int pos1, int pos2){
            Random random = new Random();
            if(probCruzamento > random.nextDouble()){
                int pontoDeCruzamento = random.nextInt(itens.size());
                ArrayList<Integer> filho = new ArrayList<>();
                for(int i=0; i< itens.size(); i++){
                    if(i < pontoDeCruzamento){
                        filho.add(cromossomos.get(pos1).get(i));
                    } else {
                        filho.add(cromossomos.get(pos2).get(i));
                    }
                }
            }
        }

        public void geraGeracao(){
	        int pos1, pos2;
	        pos1 = getMelhorAdaptado(-1);
            System.out.println(pos1);
            pos2 = getMelhorAdaptado(pos1);
            System.out.println(pos2);
            if(pos1 == -1){
                System.out.println("Nenhuma solução encontrada");
            } else if(pos2 == -1){
                System.out.println("Valor da melhor e única solução encontrada: " + melhorAdaptado);
                System.out.println("Itens: ");
                for(Integer c: cromossomos.get(pos1)){
                    System.out.print(c + " ");
                }
            } else{
                cruzamento(pos1, pos2);
            }
        }
	}
