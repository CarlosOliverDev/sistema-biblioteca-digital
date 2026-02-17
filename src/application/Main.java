package application;

import entities.Biblioteca;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        System.out.println("\nBem-vindo ao Sistema Gerenciador de Biblioteca!");
        menu();
    }

    public static void menu() {
        int opcaoUsuario = 0;
        do {
            System.out.println(" - Menu Principal - ");
            System.out.println("1- Gestão de Livros\n2- Gestão de Usuários\n3- Empréstimos\n4- Salvar e sair.\n");
            opcaoUsuario = verificadorInt("Digite uma das opções");
            opcaoMenu(opcaoUsuario);
        } while(opcaoUsuario != 4);
        biblioteca.salvarTodosDados();
        scanner.close();
    }

    public static void opcaoMenu(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                //TODO menuGestaoLivros();
                break;
            case 2:
                //TODO menuGestaoUsuarios();
                break;
            case 3:
                //TODO menuEmprestimos();
                break;
            case 4:
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    public static int verificadorInt(String mensagem) {
        while(true) {
            try {
                System.out.println(mensagem);
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, digite um número inteiro.");
            }
        }
    }
}
