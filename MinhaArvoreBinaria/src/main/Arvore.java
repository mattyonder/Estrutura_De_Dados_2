package main;

public class Arvore {
	
	public Arvore () {
		raiz = null;
		folhaDisponivel = null;

	}
	
	No raiz;
	No folhaDisponivel;

	
	
	public void inserirFolha(No folha) {
		if (raizEstaVazia())
			raiz = folha;
		else {
			localizarFolha(raiz);
			folha.pai = folhaDisponivel;
			
			if (folhaDisponivel.esquerda == null)
				folhaDisponivel.esquerda = folha;
			else
				folhaDisponivel.direita = folha;
			}
		folhaDisponivel = null;
	}
	
	public void localizarFolha(No folha) {
		if (folhaDisponivel == null) {
			if (verificarVaga(folha)) {
				folhaDisponivel = folha;
			} else {
				if(verificarVaga(folha.esquerda))
					localizarFolha(folha.esquerda);
				else if (verificarVaga(folha.direita))
					localizarFolha(folha.direita);
				else {
					localizarFolha(folha.esquerda);
					localizarFolha(folha.direita);
				}
			}
				
		}	
	}
	
	public boolean verificarVaga (No folha) {
		if (folha.esquerda == null || folha.direita == null)
			return true;
		else
			return false;
		
	}
	
	public void percursoPreOrdem(No raiz) {
		if (raiz != null) {
			System.out.print("Folha: "+raiz.info + " ");
				if (raiz.pai != null)
					System.out.print("Pai: "+raiz.pai.info + " ");
				if (raiz.esquerda != null)
					System.out.print("Esquerda: "+raiz.esquerda.info + " ");
				if (raiz.direita != null)
					System.out.print("Direita: "+raiz.direita.info + " ");
				
				System.out.println();


				percursoPreOrdem(raiz.esquerda);
	
				percursoPreOrdem(raiz.direita);

		}	
	}
	
	public void percursoSimetrico(No raiz) {
		if (raiz != null) {
			percursoSimetrico(raiz.esquerda);
			
			System.out.print("Folha: "+raiz.info + " ");
			if (raiz.pai != null)
				System.out.print("Pai: "+raiz.pai.info + " ");
			if (raiz.esquerda != null)
				System.out.print("Esquerda: "+raiz.esquerda.info + " ");
			if (raiz.direita != null)
				System.out.print("Direita: "+raiz.direita.info + " ");
			System.out.println();
			
			percursoSimetrico(raiz.direita);
			
		}
		
	}
	
	public void percursoPosOrdem(No raiz) {
		if (raiz != null) {
			percursoPosOrdem(raiz.esquerda);
			percursoPosOrdem(raiz.direita);
			System.out.print("Folha: "+raiz.info + " ");
			if (raiz.pai != null)
				System.out.print("Pai: "+raiz.pai.info + " ");
			if (raiz.esquerda != null)
				System.out.print("Esquerda: "+raiz.esquerda.info + " ");
			if (raiz.direita != null)
				System.out.print("Direita: "+raiz.direita.info + " ");
			
			System.out.println();
		}
	}
	
	 public boolean raizEstaVazia(){
	        return raiz == null;
	 }
}