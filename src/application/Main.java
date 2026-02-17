package application;

import entities.Biblioteca;
import entities.Livro;
import exceptions.StringVaziaException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        System.out.print("\nBem-vindo ao Sistema Gerenciador de Biblioteca!");
        menuPrincipal();
    }

    public static void menuPrincipal() {
        int opcaoUsuario = 0;
        do {
            System.out.println("\n-=- Menu Principal -=-");
            System.out.println("1- Gestão de Livros\n2- Gestão de Usuários\n3- Empréstimos\n4- Salvar e sair\n");
            opcaoUsuario = verificadorInt("Digite uma das opções: ");
            opcaoMenuPrincipal(opcaoUsuario);
        } while(opcaoUsuario != 4);
        //biblioteca.salvarTodosDados();
        scanner.close();
    }

    public static void opcaoMenuPrincipal(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                menuGestaoLivros();
                break;
            case 2:
                //TODO menuGestaoUsuarios();
                break;
            case 3:
                //TODO menuEmprestimos();
                break;
            case 4:
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    public static void menuGestaoLivros() {
        int opcaoUsuario = 0;
        do {
            System.out.println("\n-=- Gestão dos Livros -=-");
            System.out.println("1- Cadastrar um Novo Livro\n2- Listar os Livros\n3- Ordenar os Livros\n4- Buscar um Livro pelo Título\n5- Agrupar Livros por Autores\n6- Voltar ao Menu Principal\n");
            opcaoUsuario = verificadorInt("Digite uma das opções: ");
            opcaoMenuGestaoLivros(opcaoUsuario);
        } while(opcaoUsuario != 6);
    }

    public static void opcaoMenuGestaoLivros(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                cadastrarNovoLivro();
                break;
            case 2:
                //TODO menuListaLivros();
                break;
            case 3:
                //TODO ordenarLivros();
                break;
            case 4:
                //TODO buscarLivroComTitulo();
                break;
            case 5:
                //TODO agruparLivrosAutores();
                break;
            case 6:
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    public static void cadastrarNovoLivro() {
        System.out.println("\n-=- Cadastrar um Novo Livro -=-");
        String tituloLivro = verificadorStringVazio("Digite o título do livro: ");
        String autorLivro = verificadorStringVazio("Digite o autor do livro: ");
        int anoLancamentoLivro = verificadorInt("Digite o ano que o livro foi publicado: ");
        Livro novoLivro = new Livro(tituloLivro, autorLivro, anoLancamentoLivro);
        
        System.out.println("\n-=- Livro -=-");
        System.out.println(novoLivro);
        if(verificarConfirmacao("Deseja cadastrar esse livro na biblioteca? (s/n) ")) {
            biblioteca.cadastrarNovoLivro(novoLivro);
        } else {
            System.out.println("Cadastro cancelado.");
        }
    }

    public static int verificadorInt(String mensagem) {
        while(true) {
            try {
                System.out.print(mensagem);
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, digite um número inteiro.\n");
                scanner.nextLine();
            }
        }
    }

    public static String verificadorStringVazio(String mensagem) {
        while(true) {
            try {
                System.out.print(mensagem);
                String stringUsuario = scanner.nextLine();
                if(stringUsuario.isEmpty()) {
                    throw new StringVaziaException("Não é possível deixar esse campo vazio.\n");
                }
                return stringUsuario;
            } catch(StringVaziaException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean verificarConfirmacao(String mensagem) {
        String opcaoUsuario = verificadorStringVazio(mensagem);
        if(opcaoUsuario.toLowerCase().charAt(0) == 's') {
            return true;
        }
        return false;
    }
}
