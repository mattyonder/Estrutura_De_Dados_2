package main;

public class Teste {
	    public static void main(String[] args) {
	        Arvore arvore = new Arvore();	        

	        // Inserir nós com valores inteiros de 0 até 10 na árvore
	        for (int i = 0; i <= 10; i++) {
	            No folha = new No(i);
	            arvore.inserirFolha(folha);
	        }

	        // Imprimir a árvore em ordem
	        System.out.println("Árvore em pré ordem:");
	        arvore.percursoPreOrdem(arvore.raiz);
	        System.out.println();
	        
	        System.out.println("Árvore em percurso simétrico:");
	        arvore.percursoSimetrico(arvore.raiz);
	        System.out.println();

	        System.out.println("Árvore em pós ordem:");
	        arvore.percursoPosOrdem(arvore.raiz);

	    
	}
}
