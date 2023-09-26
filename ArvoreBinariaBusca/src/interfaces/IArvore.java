package interfaces;
import exceptions.*;

/**
 * Iterface com proposito de ser usada em outras interfaces de estruturas de árvores
 */
@SuppressWarnings("rawtypes")
public interface IArvore {
	
	/**
	 * Insere um objeto que implemente INo contendo um valor em seus atributos.
	 * @param no com valor dentro dele.
	 * @throws Exception.
	 */
	void inserir(INo no) throws Exception;
	
	/**
	 * Remove um no especifico da arvore e retorna o no removido.
	 * @param no que deve ser removido da arvore.
	 * @return no que foi removido.
	 * @throws NoInexistenteException no caso do no não existir na arvore, ou da propria arvore está vazia ( que se enquadra no primeiro caso ).
	 */
	INo remover(INo no) throws NoInexistenteException;  
	
	/**
	 * Busca um valor especifico na arvore e retorna ele (estou levando em conta o que foi discutido em classe de que o método visitar() não poderia mostrar todas as informações do no para o usuário.
	 * Tentei te enviar mensagem pelo google classroom mas até o momento não recebi resposta.
	 * Com base nisso, para não ir contra o que você disse em aula, interpretei que:
	 * O método buscar() busca um valor na arvore, então recebe esse valor e retorna ele se o encontrar.
	 * E o método visitar() visita um no na arvore e então o retorna caso o encontre na arvore.
	 * Não coloquei o visitar() na interface pois em sala foi discutido que ele seria protected, porém não é permitido colocar métodos protegidos em interface, então coloquei na classe da arvore.
	 * @param valor a ser procurado
	 * @return valor caso encontrado.
	 * @throws NoInexistenteException no caso do valor não existir na arvore, ou da propria arvore está vazia ( que se enquadra no primeiro caso ).
	 */
	int buscar(int valor) throws NoInexistenteException;
	  
	/**
	 * Verifica se a arvore está vazia ou não.
	 * @return True se está vazia ou False se tem no mínimo um elemento.
	 */
	boolean estaVazia();
	
	/**
	 * Verifica se a arvore está completa ou não.
	 * @return True se está completa ou False se não está.
	 * @throws ArvoreVaziaException No caso da arvore está vazia, assim, não tendo como estár completa.
	 */
	boolean ehCompleta()throws ArvoreVaziaException;
	
	/**
	 * Retorna a altura da arvore.
	 * @return Um inteiro referente a essa altura
	 * @throws ArvoreVaziaException No caso da arvore está vazia, assim, não tendo como ter uma altura.
	 */
	int altura() throws ArvoreVaziaException;
	
	/**
	 * Imprime a arvore em ordem simétrica
	 * @throws ArvoreVaziaException caso não tenha arvore, não há como imprimir
	 */
	void imprimirArvore() throws ArvoreVaziaException;
	}
