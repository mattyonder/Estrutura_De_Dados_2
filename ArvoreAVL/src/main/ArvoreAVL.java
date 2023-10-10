package main;

import exceptions.*;
import interfaces.*;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class ArvoreAVL<T> implements IArvoreAVL {
	
	INo raiz;
	int quantidadeFolhas;
	
	public ArvoreAVL() {
		this.raiz = null;
		this.quantidadeFolhas = 0;
	}

	@Override
	public void inserir(INo no) throws Exception {
		if (estaVazia())
			raiz =  no;
		else 
			inserirRecursivo(raiz, no);
		
		if (!ehBalanceada()) {
			INo pivo = encontrarPivo(no);
			realizarRotacao(pivo);
			ajustarNiveis();
		}
		quantidadeFolhas++;		
	}
	
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
	
	@Override
	public INo remover(INo no) throws NoInexistenteException {
		INo noRemovido = visitar(no);
		INo noEncontrado = noRemovido;
		INo pai = noEncontrado.getPai();
		INo filhoEsq = noEncontrado.getFilhoEsq();
		INo filhoDir = noEncontrado.getFilhoDir();
		INo noPartida = null;
		
		if (filhoEsq == null ^ filhoDir == null){
			if(noEncontrado == raiz) {
				if (filhoEsq != null) {
					filhoEsq.setPai(null);
					noEncontrado = filhoEsq;
				}
				else {
					filhoDir.setPai(null);
					noEncontrado = filhoDir;
				}
				noPartida = noEncontrado;
			} else {
				if (filhoEsq != null) {
					filhoEsq.setPai(pai);
					if(pai.compareTo(noEncontrado) == 1) 
						pai.setFilhoEsq(filhoEsq);
					else 
						pai.setFilhoDir(filhoEsq);		
					noPartida = filhoEsq;
				} else {
					filhoDir.setPai(pai);
					if(pai.compareTo(noEncontrado) == 1)
						pai.setFilhoEsq(filhoDir);
					else
						pai.setFilhoDir(filhoDir);
					noPartida = filhoDir;
				}
			}
			
		} else if(temVaga(noEncontrado)) {
			if (noEncontrado == raiz) {
				raiz = null;
			}
			else {
				if (pai.compareTo(noEncontrado) == 1)
					pai.setFilhoEsq(null);
				else
					pai.setFilhoDir(null);
				noPartida = pai;
			}
			
		} else {
			INo noAtual = noEncontrado.getFilhoEsq();
			while (noAtual.getFilhoDir() != null) {
				noAtual = noAtual.getFilhoDir();
			}
			noEncontrado.setValor(noAtual.getValor());
			remover(noAtual);
		}
		
		if (!ehBalanceada()) {
			INo pivo = encontrarPivo(noPartida);
			realizarRotacao(pivo);
			ajustarNiveis();
		}
		
		quantidadeFolhas--;
		return noRemovido;
	}

	@Override
	public int buscar(int valor) throws NoInexistenteException {
		if (estaVazia()) throw new ArvoreVaziaException("Esse nó não pode ser encontrado pois a arvore não existe");
		else 		
			return buscaRecursiva(raiz, valor);
	}
	
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
	
	protected INo visitar(INo no) throws NoInexistenteException {
		if (estaVazia()) throw new ArvoreVaziaException("Esse nó não pode ser encontrado pois a arvore não existe");
		else 
			return visitarRecursivo(raiz, no);
	}

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

	
	@Override
	public boolean estaVazia() {
		return raiz == null;
	}
	
	@Override
	public boolean ehCompleta() throws ArvoreVaziaException {
		return quantidadeFolhas == (Math.pow(2, altura() +1) - 1);
		
	}
	

	private boolean temVaga(INo no) {
		return no.getFilhoEsq() == null && no.getFilhoDir() == null;
		
	}

	@Override
	public int altura() throws ArvoreVaziaException {
		if (estaVazia()) throw new ArvoreVaziaException();
		return alturaRecursiva(raiz);
	}

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

	private INo encontrarPivo(INo noPartida) {
		INo pivo = null;
			while(pivo == null) {
				if(verificarBalanco(noPartida) == false) 
					pivo = noPartida;
				else
					noPartida = noPartida.getPai();
				}	
		return pivo;
	}
	
	protected boolean ehBalanceada() {
		return verificarBalanco(raiz);
	}
	
	private boolean verificarBalanco(INo no) {
		int balanco = calcularBalanco(no);
			if (balanco >= -1 && balanco <= 1)
			return true;
		else
			return false;
	}
	
	protected int calcularBalanco(INo no) {
		int alturaEsquerda = 0;
		int alturaDireita = 0;
		int alturaNo = no.getNivel();
		
		if (no.getFilhoEsq() != null)
			alturaEsquerda = alturaRecursiva(no.getFilhoEsq()) - alturaNo; 
		if (no.getFilhoDir() != null)
			alturaDireita = alturaRecursiva(no.getFilhoDir()) - alturaNo;

		return alturaEsquerda - alturaDireita;
}
	
	protected void realizarRotacao(INo pivo) {
		int balanco = calcularBalanco(pivo);
		if(balanco > 0) {
			if(pivo.getFilhoEsq() != null && calcularBalanco(pivo.getFilhoEsq()) > 0) 
				rotacaoSimplesDir(pivo);
			else 
				rotacaoDuplaEsqDir(pivo);
		}
		else {
			if(pivo.getFilhoDir() != null && calcularBalanco(pivo.getFilhoDir()) < 0)
				rotacaoSimplesEsq(pivo);
			else
				rotacaoDuplaDirEsq(pivo);
		}
	}
	
	private void rotacaoSimplesDir(INo pivo) {
		INo e = pivo.getFilhoEsq();
	    INo paiPivo = pivo.getPai();
	    pivo.setFilhoEsq(e.getFilhoDir());
	    
	    if (e.getFilhoDir() != null) 
	        e.getFilhoDir().setPai(pivo);  

	    e.setFilhoDir(pivo);
	    pivo.setPai(e);
	    e.setPai(paiPivo);

	    if (paiPivo != null) {
	        if (paiPivo.getFilhoEsq() == pivo) 
	            paiPivo.setFilhoEsq(e);
	        else 
	            paiPivo.setFilhoDir(e);	        
	    } else 
	        raiz = e;   
	}
		
	
	private void rotacaoSimplesEsq(INo pivo) {
		 INo d = pivo.getFilhoDir();
		 INo paiPivo = pivo.getPai();
		 pivo.setFilhoDir(d.getFilhoEsq());
		 
		 if (d.getFilhoEsq() != null)    
			 d.getFilhoEsq().setPai(pivo);
		    		  
		 d.setFilhoEsq(pivo);		    
		 pivo.setPai(d);   
		 d.setPai(paiPivo);
		    
		 if (paiPivo != null) {			 		        
			 if (paiPivo.getFilhoEsq() == pivo) 		            
				 paiPivo.setFilhoEsq(d);		 
			 else 	            
				 paiPivo.setFilhoDir(d);		        		   
		 } else 		        		    	
			 raiz = d;		    		
	}
	
	private void rotacaoDuplaEsqDir(INo pivo) {
		rotacaoSimplesEsq(pivo.getFilhoEsq());
		rotacaoSimplesDir(pivo);
		
	}
	
	private void rotacaoDuplaDirEsq(INo pivo) {
		rotacaoSimplesDir(pivo.getFilhoDir());
		rotacaoSimplesEsq(pivo);
	}
	
	protected void ajustarNiveis() {
		ajustarNiveisRecursivo(raiz);
	}
	
	private void ajustarNiveisRecursivo(INo no){
		no.setNivel();
		if(no.getFilhoEsq() != null)
			ajustarNiveisRecursivo(no.getFilhoEsq());
		if(no.getFilhoDir() != null)
			ajustarNiveisRecursivo(no.getFilhoDir());
	}
	
	@Override
	public void imprimirArvore() throws ArvoreVaziaException{
		preOrdem(raiz);
	}

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
	
	public void impressaoDescritiva() throws ArvoreVaziaException {
		impressaoDescritivaRecursivo(raiz);
	}
	
	public void impressaoDescritivaRecursivo(INo no) throws ArvoreVaziaException {
		if (estaVazia()) throw new ArvoreVaziaException();
		else
		{
			System.out.print("Valor: "+no.getValor());
			if (no.getPai() != null)
			System.out.print(" Pai: "+no.getPai().getValor());
			if (no.getFilhoEsq() != null)
			System.out.print(" Filho Esq: "+no.getFilhoEsq().getValor());
			if (no.getFilhoDir() != null)
			System.out.print(" Filho Dir: "+no.getFilhoDir().getValor());
			System.out.print(" Nivel: "+no.getNivel());

			
			System.out.println("");

			if(no.getFilhoEsq() != null)
				impressaoDescritivaRecursivo(no.getFilhoEsq());
			if(no.getFilhoDir() != null)
				impressaoDescritivaRecursivo(no.getFilhoDir());
		}
	}
}
