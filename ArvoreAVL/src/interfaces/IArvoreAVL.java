package interfaces;

import exceptions.ArvoreVaziaException;

/**
 * Iterface com propósito de ser usada em arvores avl
 */
@SuppressWarnings("rawtypes")
public interface IArvoreAVL extends IArvore {
	/**
	 * Imprime a arvore no trajeto em pré ordem, onde, primeiro vem a raiz, depois o lado esquerdo e depois o direito.
	 * @param no da arvore
	 * @throws ArvoreVaziaException No caso da arvore estar vazia, assim não tendo como imprimir.
	 */
	void preOrdem(INo no) throws ArvoreVaziaException;
	
	/**
	 * Imprime a arvore no trajeto em ordem, onde, primeiro vem o lado esquerdo, depois o a raiz e depois o lado direito.
	 * @param no da arvore
	 * @throws ArvoreVaziaException No caso da arvore estar vazia, assim não tendo como imprimir.
	 */
	void inOrdem(INo no) throws ArvoreVaziaException;
	
	/**
	 * Imprime a arvore no trajeto em pós ordem, onde, primeiro vem o lado esquerdo, depois o direito e depois a raiz.
	 * @param no
	 * @throws ArvoreVaziaException
	 */
	void posOrdem(INo no) throws ArvoreVaziaException;

}
