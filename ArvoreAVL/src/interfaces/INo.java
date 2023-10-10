package interfaces;

/**
 * Interface criada para ser usada para criar classes de nós para serem inseridas nas estruturas de arvore.
 * 
 * @param <INo<T>> Modifiquei o parametro de T para INo<T> pois não estava conseguindo usar o comparable do jeito que estava na interface original.
 */
public interface INo<T> extends Comparable<INo<T>> {
	
	/**
	 * Setters e Getters dos respectivos atributos:
	 * int valor - valor inteiro armazenado dentro do nó
	 * INo<T> pai - nó pai anterior ao nó 
	 * INo<T> filhoEsq - nó filho do lado esquerdo do nó
	 * INo<T> filhoDir - nó filho do lado direito do nó
	 * int nivel - nivel da arvore no qual o nó está contido
	 * 
	 */
	void setValor(int valor);
	void setPai(INo<T> no);
	void setFilhoEsq(INo<T> no);
	void setFilhoDir(INo<T> no);
	void setNivel();
	  
	int getValor();
	INo<T> getPai();
	INo<T> getFilhoEsq();
	INo<T> getFilhoDir();  
	int getNivel();

}
