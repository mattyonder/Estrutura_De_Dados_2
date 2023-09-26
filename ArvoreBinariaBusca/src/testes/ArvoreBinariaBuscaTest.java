package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.*;
import main.*;
import interfaces.INo;

@SuppressWarnings("rawtypes")
class ArvoreBinariaBuscaTest<T> {
	public ArvoreBinariaBusca arvore = new ArvoreBinariaBusca();

	
	/**
	 *  O teste funciona da seguinte maneira:
	 * 
	 * Eu adiciono 7 valores na árvore mas testo 3 em específico para verificar se foram inseridos corretamente:
	 * Um na raiz.
	 * Outro como filho direito da raiz.
	 * E outro como ultimo elemento à esquerda da sub-arvore esquerda dá raiz.
	 * 
	 * Usando por padrão os valores, 50, 63 e 22, assim além de buscar os valores desse nós na arvore, também verifico se:
	 * A raiz com valor 50 tem como filhos esquerdo e direito, 38 e 63 respectivamente.
	 * O nó com valor 63 tem como pai, filho esquerdo e direito, os nós com valores 50, 62, e 70 respectivamente.
	 * E o nó com valor 22 se o pai dele é o nó com valor 38.
	 * @throws Exception
	 */
	@Test
    public void testInserir() throws Exception {
		/*
		 * Criando nós espécificos.
		 */
		No noUm = new No(50);
		No noDois = new No(63);
		No noTres = new No(22);

		/*
		 * Inserindo os 7 valores.
		 */
		arvore.inserir(noUm);
		arvore.inserir(new No(38));
		arvore.inserir(new No(40));
		arvore.inserir(noDois);
		arvore.inserir(noTres);
		arvore.inserir(new No(62));
		arvore.inserir(new No(70));
		
		/**
		 * Definindo nós a serem procurados.
		 */
		INo noProcuradoUm = noUm;
		INo noProcuradoDois = noDois;
		INo noProcuradoTres = noTres;

		/**
		 * Obtendo filhos esquerdo e direito do noUm.
		 */
		INo filhoEsqNoUm = noUm.getFilhoEsq();
		INo filhoDirNoUm = noUm.getFilhoDir();
		
		/**
		 * Obtendo pai e filhos esquerdo e direito do noDois.
		 */
		INo paiNoDois = noDois.getPai();
		INo filhoEsqNoDois = noDois.getFilhoEsq();
		INo filhoDirNoDois = noDois.getFilhoDir();
		
		/**
		 * Obtendo pai do noTres
		 */
		
		INo paiNoTres = noTres.getPai();

		/*
		 * Testando inserção do noUm:
		 */
		assertTrue(noProcuradoUm.getValor() == arvore.buscar(50));
        assertEquals(noProcuradoUm.getFilhoEsq().getValor(), filhoEsqNoUm.getValor());
        assertEquals(noProcuradoUm.getFilhoDir().getValor(), filhoDirNoUm.getValor());
        
        /**
         * Testando inserção do noDois:
         */
		assertTrue(noProcuradoDois.getValor() == arvore.buscar(63));
        assertEquals(noProcuradoDois.getPai().getValor(), paiNoDois.getValor());
        assertEquals(noProcuradoDois.getFilhoEsq().getValor(), filhoEsqNoDois.getValor());
        assertEquals(noProcuradoDois.getFilhoDir().getValor(), filhoDirNoDois.getValor());
        
        /**
         * Testando inserção do noTres:
         */
		assertTrue(noProcuradoTres.getValor() == arvore.buscar(22));
        assertEquals(noProcuradoTres.getPai().getValor(), paiNoTres.getValor());

    }

	/**
	 * O teste funciona da seguinte maneira:
	 * 
	 * Eu adiciono 7 valores na árvore mas faço o teste com 2 em espécifico para testar a remoção:
	 * Um na raiz, que vai ser removido.
	 * Outro que vai substituir a raiz.
	 * 
	 * Usando pro padrão os valores, 50 e 40, eu testo da seguinte maneira:
	 * Se o valor do no removido é igual a 40
	 * E se o no filho do pai do nó removido é nulo
	 * @throws Exception
	 */
	@Test
    public void testRemoverUm() throws Exception {
		/**
		 * Criando nó espécifico e adicionando outros nós
		 */
		No noUm = new No(50);
		No noDois = new No(40);
		
		arvore.inserir(noUm);
		arvore.inserir(new No(38));
		arvore.inserir(noDois);
		arvore.inserir(new No(63));
		arvore.inserir(new No(22));
		arvore.inserir(new No(62));
		arvore.inserir(new No(70));
		
		/**
		 * Obtendo valores do filhos esquerdo e direito do noUm, e pai do noDois e valor desejado ao final da remoção.
		 */
		INo paiNoDois = noDois.getPai();
		int valorDesejado = noDois.getValor();
		int valorNoUmAntigo = noUm.getValor();
		
		/*
		 * Removendo e lançando exceção após busca do valor de noUm na arvore
		 */
        arvore.remover(noUm);
        assertThrows(NoInexistenteException.class, () -> arvore.buscar(valorNoUmAntigo));
       
        /**
         * Obtendo valores atualizados do nó um
         */
        int valorNoUmAtualizado = noUm.getValor();
        
        /**
         * Testando remoção do noUm
         */
        assertEquals(valorDesejado, valorNoUmAtualizado);       
        assertTrue(paiNoDois.getFilhoDir() == null);
        
    }
	
	/**
	 * O teste funciona da seguinte maneira:
	 * 
	 * Eu adiciono 8 valores na árvore mas faço o teste com 2 em espécifico para testar a remoção:
	 * Um  que vai ser removido.
	 * E seu filho
	 * 
	 * Usando pro padrão os valores, 70 e 71, eu testo da seguinte maneira:
	 * Se o pai do 70 aponta para o 71 que é seu filho.
	 * @throws Exception
	 */
	@Test
    public void testRemoverDois() throws Exception {
		/**
		 * Criando nó espécifico e adicionando outros nós
		 */
		No noUm = new No(70);
		No noDois = new No(71);
		arvore.inserir(new No(50));
		arvore.inserir(new No(38));
		arvore.inserir(new No(40));
		arvore.inserir(new No(63));
		arvore.inserir(new No(22));
		arvore.inserir(new No(62));
		arvore.inserir(noUm);
		arvore.inserir(noDois);
		
		/**
		 * Obtendo valor, filho e pai do noUm 
		 */
		int valorNoUmAntigo = noUm.getValor();
		INo noUmFilho = noUm.getFilhoDir();
		INo noUmPai = noUm.getPai();
		
		/**
		 * Removendo noUm e lançando exceção após buscar noUm
		 */
		arvore.remover(noUm);
        assertThrows(NoInexistenteException.class, () -> arvore.buscar(valorNoUmAntigo));

		
		/**
		 * Testando remoção
		 */
        assertTrue(noDois.getPai() == noUmPai);
        assertTrue(noUmPai.getFilhoDir() == noUmFilho);
        
    }
	

	/**
	 * O teste funciona da seguinte maneira:
	 * 
	 * Eu adiciono 8 valores na árvore mas faço o teste com 2 em espécifico para testar a remoção:
	 * Um  que vai ser removido.
	 * E seu pai
	 * 
	 * Usando pro padrão os valores, 22 e 38, eu testo da seguinte maneira:
	 * Se o pai do 22 aponta para nulo.
	 * @throws Exception
	 */
	@Test
	void testeRemoverTres() throws Exception{	
		/**
		 * Criando nó espécifico e adicionando outros nós
		 */
		No noUm = new No(22);
		No noDois = new No(38);
		
		arvore.inserir(new No(50));
		arvore.inserir(noDois);
		arvore.inserir(new No(40));
		arvore.inserir(new No(63));
		arvore.inserir(noUm);
		arvore.inserir(new No(62));
		arvore.inserir(new No(70));
		
		/**
		 * Obtendo ponteiro do pai e valor do noUm
		 */
		
		INo paiNoUm = noDois;
		int noUmValor = noUm.getValor();
		
		/**
		 * Removendo noUm e lançando exceção após buscar valor de noUm na árvore
		 */
		arvore.remover(noUm);
        assertThrows(NoInexistenteException.class, () -> arvore.buscar(noUmValor));
        
        /**
         * Testando remoção
         */
        assertTrue(paiNoUm.getFilhoEsq() == null);

		
	}
	
	/**
	 * O teste consiste em adicionar valores na arvore e depois buscar eles.
	 * @throws Exception
	 */
	@Test
	void testBuscar() throws Exception{
		/**
		 * Adicionando valores na árvore
		 */
		int [] valores = {50, 38, 22, 40, 63, 62, 70};
		for(int valor : valores) {
			arvore.inserir(new No(valor));
		}
		
		/**
		 * Buscando valores na arvore 
		 */
		for(int valor : valores) {
			int valorEncontrado = arvore.buscar(valor);
			assertEquals(valorEncontrado, valor);
		}
	}

	/**
	 * O teste funciona da seguinte maneira:
	 * Ele adiciona elementos de modo que a arvore fique completa e depois ele verifica se a arvore realmente está completa.
	 * @throws Exception
	 */
	 @Test
	public void testEhCompleta() throws Exception {
		 /**
			 * Adicionando valores na árvore
			 */
			int [] valores = {50, 38, 22, 40, 63, 62, 70};
			for(int valor : valores) {
				arvore.inserir(new No(valor));
			}
			
			/**
			 * Testando o método
			 */
			
			boolean resultadoEsperado = true;
			
			assertTrue(arvore.ehCompleta() == resultadoEsperado);
	    }

	 /**
		 * O teste funciona da seguinte maneira:
		 * Ele adiciona elementos de modo que a arvore fique com uma altura especifica e depois ele verifica se a arvore realmente tem essa altura.
		 * @throws Exception
		 */
	 @Test
	 public void testAltura() throws Exception {
		 /**
			 * Adicionando valores na árvore
			 */
			int [] valores = {50, 38, 22, 40, 63, 62, 70, 71};
			for(int valor : valores) {
				arvore.inserir(new No(valor));
			}
			
			/**
			 * Testando método
			 */
			int valorEsperado = 3;
			
			assertEquals(valorEsperado, arvore.altura());
	  }
	
	 /**
	  * Ele tenta buscar um valor que não existe e lança uma exceção.
	  */
	 @Test
	    public void testBuscarNoInexistente() {
	        assertThrows(NoInexistenteException.class, () -> {
	            arvore.buscar(10);
	        });
	    }
	 
	 /**
	  * Ele tenta remover um valor que não existe e lança uma exceção.
	  */
    @Test
    public void testRemoverNoInexistente() {
        assertThrows(NoInexistenteException.class, () -> {
            arvore.remover(new No(10));
        });
    }



}
