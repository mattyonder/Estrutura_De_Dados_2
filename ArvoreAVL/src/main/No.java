package main;

import interfaces.INo;

/**
 * Classe usada para armazenar valores de tipo inteiro, para depois armazenar esses objetos No em uma arvore avl
 * @param <T>
 */
public class No<T> implements INo<T> {
	
	/**
	 * Os atributos do No, juntamente de seus Setters e Getters foram explicados na interface INo.
	 * Porém abaixo tem comentários explicando o setNivel() e o compareTo()
	 */
	private int valor;
	private No<T> pai;
	private No<T> filhoEsq;
	private No<T> filhoDir;
	private int nivel;

	/**
	 * Ao criar um objeto No, ele recebe o seu valor como parametro, onde vai ser inserido diretamente dentro dele.
	 * E ficara como nulo seu pai, filhoEsquerdo e filhoDireito respectivamente.
	 * Seu nivel por padrão vem como zero, e ao ser inserido na arvore, esse valor é atualizado.
	 * @param valor a ser inserido dentro do nó.
	 */
	public No(int valor) {
		this.valor = valor;
		this.pai = null;
		this.filhoEsq = null;
		this.filhoDir = null;
		this.nivel = 0;
	}

	@Override
	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public void setPai(INo<T> no) {
		this.pai =  (No<T>) no;
	}

	@Override
	public void setFilhoEsq(INo<T> no) {
		this.filhoEsq = (No<T>) no;
	}

	@Override
	public void setFilhoDir(INo<T> no) {
		this.filhoDir = (No<T>) no;
	}
	
	/**
	 * Tecnicamente o nivel de uma folha em uma arvore é o nivel de onde o sua folha pai está localizada mais 1.
	 * Então o método setNivel se utiliza do nível do pai do nó, e incremente em 1.
	 */
	public void setNivel() {
		if (this.pai != null)
			this.nivel = this.pai.getNivel() + 1;
		else
			this.nivel = 0;
	}

	@Override
	public int getValor() {
		return this.valor;
	}

	@Override
	public INo<T> getPai() {
		return this.pai;
	}

	@Override
	public INo<T> getFilhoEsq() {
		return this.filhoEsq;
	}

	@Override
	public INo<T> getFilhoDir() {
		return this.filhoDir;
	}
	
	@Override
	public int getNivel() {
		return this.nivel;
	}
	

	/**
	 * Esse método pega o valor do nó atual e compara com o de outro nó.
	 * Caso o resultado seja maior ou igual a zero, o outro nó é menor que o nó atual.
	 * Senão, o nó atual é menor que o outro nó.
	 * @return 0 ou 1 dependendo do resultado.
	 * @param outroNo Outro nó no qual vai ter seu valor comparado
	 */
	@Override
	public int compareTo(INo<T> outroNo) {
		if (this.valor >= outroNo.getValor())
			return 1;
		else
			return 0;
	}	
}
