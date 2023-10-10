package testes;

import java.util.Scanner;

import main.ArvoreAVL;
import main.No;

public class TesteIterativo {

	public static void main(String[] args) throws Exception {
		ArvoreAVL arvore = new ArvoreAVL();

        Scanner scanner = new Scanner(System.in);

		
		int escolha;
		
		do {
			System.out.println("------------------------------------------------");
			System.out.println("Menu:");
			System.out.println("1.Inserir nó");
			System.out.println("2.Remover nó");
			System.out.println("3.Ver arvore nó");
			System.out.println("0.Sair da arvore");
			
			escolha = scanner.nextInt();
			
			switch (escolha) {
			
			case 1:
				System.out.println("Digite o valor que deseja adicionar: ");
				int valor = scanner.nextInt();
				arvore.inserir(new No(valor));
				
				System.out.println("------------------------------------------------");
				arvore.impressaoDescritiva();

				break;
				
			case 2:
				System.out.println("Digite o valor que deseja remover: ");
				int removido = scanner.nextInt();
				arvore.remover(new No(removido));
				
				System.out.println("------------------------------------------------");
				arvore.impressaoDescritiva();

				break;
				
			case 3:
				System.out.println("------------------------------------------------");
				arvore.impressaoDescritiva();
				
				break;
			case 0:
				System.out.println("Sayonara");
				break;
			default:
				System.out.println("Escolhe direito issae");
				break;
			}
		}while(escolha != 0);
		
		

	}

}
