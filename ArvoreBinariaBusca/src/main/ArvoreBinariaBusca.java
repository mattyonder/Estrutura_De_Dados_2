package main;

import exceptions.*;
import interfaces.*;

/**
 * Classe para instanciar árvores binárias de busca.
 * @param <T>
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class ArvoreBinariaBusca<T> implements IArvoreBinaria {
	
	/**
	 * A raiz da árvore é o primeiro nó que ela tem.
	 */
	INo raiz;
	
	/**
	 * O atributo quantidadeFolha foi criado unicamente para facilitar o método ehCompleta()
	 * Ele armazena a quantidade de folhas da árvore.
	 */
	int quantidadeFolhas;
	
	public ArvoreBinariaBusca() {
		this.raiz = null;
		this.quantidadeFolhas = 0;
	}

	/**
	 * Método que insere um nó na árvore.
	 * Se a raiz é nula ( está vazia ), o no se torna a raiz.
	 * Caso não, ele chama o método privado inserirRecursivo que recebe a raiz e o no como parametro.
	 * Ao final do método independente de estar vazia ou não, ele incrementa em um o atributo quantidadeFolhas.
	 * @param no Nó a ser adicionado
	 */
	@Override
	public void inserir(INo no) throws Exception {
		if (estaVazia())
			raiz =  no;
		else 
			inserirRecursivo(raiz, no);
		quantidadeFolhas++;		
	}
	
	/**
	 * Método recursivo que insere um nó na arvore caso ela já tenha raiz.
	 * Esse método é chamado pelo método inserir() caso a raiz não seja nula
	 * Ele verifica se o valor contido no parametro noDaArvore é maior ou igual ao do nó a ser inserido.
	 * A explicação do compareTo() está na classe No.
	 * Se ele retornar 1, o valor é menor que o nó da árvore, senão, ele retorna 0.
	 * 
	 *  Caso retorne 1, ele verifica se o filho esquerdo do noDaArvore é igual a null, se sim ele adiciona o nó como filho esquerdo.
	 *  Se não ele chama o inserirRecursivo usando o filho esquerdo como parametro dessa vez.
	 *  
	 *  O mesmo se repete no caso de retornar 0, mas com o filho direito.
	 *  Independente do lado onde adicionar, o noDaArvore se torna o pai do nó, e em sequencia seu nivel é definido.
	 * @param noDaArvore Nó que vai receber o nó adicionado como filho
	 * @param no Nó a ser adicionado na árvore
	 */
	private void inserirRecursivo(INo noDaArvore, INo no) {
		if(noDaArvore.compareTo(no) == 1) {
			if(noDaArvore.getFilhoEsq() == null) {
				noDaArvore.setFilhoEsq(no);
				no.setPai(noDaArvore);
			}
			else
				inserirRecursivo(noDaArvore.getFilhoEsq(), no);
		} else {
			if(noDaArvore.getFilhoDir() == null) {
				noDaArvore.setFilhoDir(no);
				no.setPai(noDaArvore);
			}
			else
				inserirRecursivo(noDaArvore.getFilhoDir(), no);
		}
		no.setNivel();
	}

	/**
	 * Esse método remove um nó da árvore com base em 3 casos:
	 * 1° Se o nó a ser removido não tem filhos:
	 * Nesse caso o nó pai o espaço onde o nó a ser removido estava armazenado recebe o valor null.
	 * Para isso ele verifica se o nó a ser removido tem vaga por meio do método temVaga().
	 * Apó isso ele verifica qual dos espaços está o nó a ser removido por meio de um compareTo() do nó pai e assim o remove dependendo do resultado. 
	 * 
	 * 2° Se o nó a ser removido tem no mínimo um filho.
	 * Já nesse caso, ele verifica primeiramente se pelo menos um dos espaços de filhos é nulo por meio do operador XOR ^.
	 * Após isso ele verifica se o filho está a direita ou esquerda, define o pai do filho do nó a ser removido como o pai do nó a ser removido.
	 * E depois verifica em qual espaço o nó a ser removido estava usando um compareTo() no nó pai. Assim definidindo o filho esquerdo ou direito do nó pai como o filho do nó removido.
	 * 
	 * 3° Se o nó removido tem 2 filhos.
	 * Nesse caso em especifico, é buscado na sub-arvore esquerda o maior elemento dela.
	 * Após isso, esse maior elemento substitui o elemento a ser removido. E depois esse maior elemento é removido de sua posição original.
	 * Ao fim desse método, a quantidade de elementos e decrementada em um.
	 * 
	 */
	@Override
	public INo remover(INo no) throws NoInexistenteException {
		INo noRemovido = visitar(no);
		INo pai = noRemovido.getPai();
		INo filhoEsq = noRemovido.getFilhoEsq();
		INo filhoDir = noRemovido.getFilhoDir();
		
		if (filhoEsq == null ^ filhoDir == null){
			if (filhoEsq != null) {
				filhoEsq.setPai(pai);
				if(pai.compareTo(noRemovido) == 1) 
					pai.setFilhoEsq(filhoEsq);
				else 
					pai.setFilhoDir(filhoEsq);		
			} else {
				filhoDir.setPai(pai);
				if(pai.compareTo(noRemovido) == 1)
					pai.setFilhoEsq(filhoDir);
				else
					pai.setFilhoDir(filhoDir);
			}
		} else if(temVaga(noRemovido)) {
			if (pai.compareTo(noRemovido) == 1)
				pai.setFilhoEsq(null);
			else
				pai.setFilhoDir(null);
		} else {
			INo noAtual = noRemovido.getFilhoEsq();
			while (noAtual.getFilhoDir() != null) {
				noAtual = noAtual.getFilhoDir();
			}
			noRemovido.setValor(noAtual.getValor());
			remover(noAtual);
		}
		
		quantidadeFolhas--;
		return noRemovido;
	}

	/**
	 * Esse método busca um valor na arvore.
	 * Ele verifica inicialmente se a arvore está vazia, caso esteja, lança uma exceção
	 * Senão chama o buscar recursivo usando a raiz e o nó procurado como parâmetro.
	 */
	@Override
	public int buscar(int valor) throws NoInexistenteException {
		if (estaVazia()) throw new ArvoreVaziaException("Esse nó não pode ser encontrado pois a arvore não existe");
		else 		
			return buscaRecursiva(raiz, valor);
	}
	
	/**
	 * Método usado pelo método buscar() para auxiliar a busca.
	 * Ele inicialmente verifica se o valor passado como parâmetro é o valor procurado, caso seja, ele retorna esse valor.
	 * Senão, ele verifica se o nó onde atual tem vaga, pois caso não tenha, não há outros nós na arvore após ele e por consequencia o valor procurado não foi encontrado, assim lançando uma exceção.
	 * Caso ele tenha vagas, ele verifica se o valor do nó passado como parametro é maior ou igual ao valor procurado, assim delimitando a área de busca entre o lado esquerdo ou direito e facilitando ele de ser encontrado.
	 * Após comparar os valores por meio do compareTo, o buscarRecursivo é chamado tendo como parâmetro dessa vez o filho esquerdo ou direito, dependendo do resultado do compareTo() e o valor procurado.
	 * @param no Nó da árvore 
	 * @param valor valor que está sendo procurado
	 * @return o valor da árvore caso seja encontrado.
	 * @throws NoInexistenteException Caso esse valo não seja encontrado em nenhum nó.
	 */
	private int buscaRecursiva(INo no, int valor) throws NoInexistenteException {	
		if (no.getValor() == valor)
			return no.getValor();
		else if (temVaga(no)) throw new NoInexistenteException("Esse nó não existe na arvore");

		else {
			if(no.getFilhoEsq() != null && no.getValor() >= valor)
				return buscaRecursiva(no.getFilhoEsq(), valor);
			else
				return buscaRecursiva(no.getFilhoDir(), valor);
		}
	}
	
	/**
	 * Esse método visita um nó na arvore.
	 * Ele verifica inicialmente se a arvore está vazia, caso esteja, lança uma exceção
	 * Senão chama o visitar recursivo usando a raiz e o nó procurado como parâmetro.
	 * @param no Nó que está sendo procurado
	 * @return O nó encontrado na árvore
	 * @throws NoInexistenteException Caso não econtre esse nó.
	 */
	protected INo visitar(INo no) throws NoInexistenteException {
		if (estaVazia()) throw new ArvoreVaziaException("Esse nó não pode ser encontrado pois a arvore não existe");
		else 
			return visitarRecursivo(raiz, no);
	}
	
	/**
	 * Método usado pelo método visitar() para auxiliar a busca.
	 * Ele inicialmente verifica se o nó passado como parâmetro é o nó procurado, caso seja, ele retorna esse nó.
	 * Senão, ele verifica se esse nó tem vaga, pois caso não tenha, não há outros nós na arvore após ele e por concequencia o nó procurado não foi encontrado, assim lançando uma ecxeção.
	 * Caso ele tenha vagas, ele verifica se o nó passado como parametro é maior ou igual ao nó procurado, assim delimitando a área de busca entre o lado esquerdo ou direito e facilitando ele de ser encontrado.
	 * Após comparar os valores por meio do compareTo, o visitarRecursivo é chamado tendo como parâmetro dessa vez o filho esquerdo ou direito, dependendo do resultado do compareTo() e o nó procurado.
	 * @param no Nó da árvore 
	 * @param noProcurado Nó que está sendo procurado
	 * @return o Nó da árvore caso seja encontrado.
	 * @throws NoInexistenteException Caso esse nó não seja encontrado.
	 */
	private INo visitarRecursivo(INo no, INo noProcurado) throws NoInexistenteException {
		if (no == noProcurado)
			return no;
		else if (temVaga(no)) throw new NoInexistenteException("Esse nó não existe na arvore");
		else {
			if(no.getFilhoEsq() != null && no.compareTo(noProcurado) == 1)
					return visitarRecursivo(no.getFilhoEsq(), noProcurado);
				else
					return visitarRecursivo(no.getFilhoDir(), noProcurado);	
			}	
	}

	/**
	 * Método que verifica se a arvore está vazia verificando se sua raiz é nula.
	 */
	@Override
	public boolean estaVazia() {
		return raiz == null;
	}
	
	/**
	 * Esse método verifica se a árvore está completa por meio de um calculo que se baseia em:
	 * A árvore está completa caso a sua quantidade de folhas seja igual ao resultado da equação 2^(altura + 1) - 1.
	 * Caso o resultado seja diferente disso, a árvore não está cheia.
	 */
	@Override
	public boolean ehCompleta() throws ArvoreVaziaException {
		return quantidadeFolhas == (Math.pow(2, altura() +1) - 1);
		
	}
	
	/**
	 * Esse método é usado para facilitar o funcionamento dos outros métodos.
	 * Ele verifica se o nó passado como parametro tem os 2 espaços de filho definidos como nulo.
	 * @param no Nó que vai ser verificado.
	 * @return True ou False caso o nó tenha um espaço vago.
	 */
	private boolean temVaga(INo no) {
		return no.getFilhoEsq() == null && no.getFilhoDir() == null;
		
	}

	/**
	 * Inicialmente esse método verifica se a árvore está vazia, caso esteja ele lança uma exceção.
	 * Senão, ele chama o método alturaRecursiva para continuar a operação.
	 */
	@Override
	public int altura() throws ArvoreVaziaException {
		if (estaVazia()) throw new ArvoreVaziaException();
		return alturaRecursiva(raiz);
	}

	/**
	 * Esse método privado serve para fazer a seguinte operação:
	 * Ele verifica primeiro se tem vaga no nó passado como parametro, caso tenha, ele retorna o nivel dele
	 * Caso não, ele cria 2 variaveis para guardar a altura do filho esquerdo e direito do nó passado, e verifica se eles são diferentes de nulo.
	 * Caso sejam, ele faz as variaveis de altura receberem uma chamada de alturaRecursiva com os filhos esquerdo e direito do nó passado como parametro.
	 * E depois ele retorna qual dos 2 é o maior por meio do Math.max.
	 * @param no da árvore, inicialmente sendo esse nó a raiz da árvore
	 * @return O maior nível encontrado em algum elemento da árvore, já que tecnicamente o nível de uma folha se refere a que nível ele está na altura da árvore
	 */
	private int alturaRecursiva(INo no) {
		if(temVaga(no))
			return no.getNivel();
		else {
			int alturaEsquerda = 0;
			int alturaDireita = 0;
			if (no.getFilhoEsq() != null)
				alturaEsquerda = alturaRecursiva(no.getFilhoEsq());
			if (no.getFilhoDir() != null)
				alturaDireita = alturaRecursiva(no.getFilhoDir());
			return Math.max(alturaEsquerda, alturaDireita);
		}
	}
	
	/**
	 * Esse método usa de outro método, o inOrdem, entrando como parâmetro a raiz da árvore.
	 */
	@Override
	public void imprimirArvore() throws ArvoreVaziaException{
		preOrdem(raiz);
	}

	/**
	 * Método explicado na interface IArvoreBinária
	 */
	@Override
	public void preOrdem(INo no) throws ArvoreVaziaException {
		if (estaVazia()) throw new ArvoreVaziaException();
		else
		{
			System.out.println(no.getValor());
			if(no.getFilhoEsq() != null)
				preOrdem(no.getFilhoEsq());
			if(no.getFilhoDir() != null)
				preOrdem(no.getFilhoDir());
		}
	}

	/**
	 * Método explicado na interface IArvoreBinária
	 */
	@Override
	public void inOrdem(INo no) throws ArvoreVaziaException{
		if (estaVazia()) throw new ArvoreVaziaException();
		else
		{
			if (no.getFilhoEsq() != null)
				inOrdem(no.getFilhoEsq());
			System.out.println(no.getValor());
			if (no.getFilhoDir() != null)
				inOrdem(no.getFilhoDir());
		}		
	}

	/**
	 * Método explicado na interface IArvoreBinária
	 */
	@Override
	public void posOrdem(INo no) throws ArvoreVaziaException {
		if (estaVazia()) throw new ArvoreVaziaException();
		else
		{
			if(no.getFilhoEsq() != null)
				posOrdem(no.getFilhoEsq());
			if(no.getFilhoDir() != null)
				posOrdem(no.getFilhoDir());
			System.out.println(no.getValor());
			
		}		
	}
}
