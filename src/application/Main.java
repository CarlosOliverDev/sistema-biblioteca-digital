package application;

import entities.Biblioteca;
import entities.Livro;
import entities.Usuario;
import exceptions.*;

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
            System.out.println("1- Gestão de Livros.\n2- Gestão de Usuários.\n3- Empréstimos.\n4- Salvar e sair.\n");
            opcaoUsuario = verificadorInt("Digite uma das opções: ");
            opcaoMenuPrincipal(opcaoUsuario);
        } while(opcaoUsuario != 4);
        biblioteca.salvarTodosDados();
        scanner.close();
    }

    public static void opcaoMenuPrincipal(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                menuGestaoLivros();
                break;
            case 2:
                menuGestaoUsuarios();
                break;
            case 3:
                menuEmprestimos();
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
            System.out.println("1- Cadastrar um Novo Livro.\n2- Listar os Livros.\n3- Ordenar os Livros.\n4- Buscar um Livro pelo Título.\n5- Agrupar Livros por Autores.\n6- Voltar ao Menu Principal.\n");
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
                if(biblioteca.listaLivrosEstaVazia()) {
                    System.out.println("A biblioteca não tem livros cadastrados. Adicione um livro para utilizar essa opção.");
                    break;
                }
                menuListaLivros();
                break;
            case 3:
                if(biblioteca.listaLivrosEstaVazia()) {
                    System.out.println("A biblioteca não tem livros cadastrados. Adicione um livro para utilizar essa opção.");
                    break;
                }
                ordenarLivros();
                break;
            case 4:
                if(biblioteca.listaLivrosEstaVazia()) {
                    System.out.println("A biblioteca não tem livros cadastrados. Adicione um livro para utilizar essa opção.");
                    break;
                }
                buscarLivroPorTitulo();
                break;
            case 5:
                if(biblioteca.listaLivrosEstaVazia()) {
                    System.out.println("A biblioteca não tem livros cadastrados. Adicione um livro para utilizar essa opção.");
                    break;
                }
                biblioteca.agruparLivrosPorAutor();
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
        if(verificarConfirmacao("\nDeseja cadastrar esse livro na biblioteca? (s/n) ")) {
            biblioteca.cadastrarNovoLivro(novoLivro);
        } else {
            System.out.println("Cadastro cancelado.");
        }
    }

    public static void menuListaLivros() {
        int opcaoUsuario = 0;
        do {
            System.out.println("\n-=- Listar os Livros -=-");
            System.out.println("1- Listar todos os livros.\n2- Listar os livros de determinado autor.\n3- Listar os livros disponíveis para empréstimos.\n4- Listar os livros emprestados.\n5- Voltar ao menu de Gestão de livros.\n");
            opcaoUsuario = verificadorInt("Digite uma das opções: ");
            opcaoMenuListaLivros(opcaoUsuario);
        } while(opcaoUsuario != 5);
    }

    public static void opcaoMenuListaLivros(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                biblioteca.listarTodosLivros();
                break;
            case 2:
                listarLivrosAutor();
                break;
            case 3:
                biblioteca.listarLivrosDisponiveis();
                break;
            case 4:
                biblioteca.listarLivrosEmprestados();
                break;
            case 5:
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    public static void listarLivrosAutor() {
        System.out.println("\n-=- Listar os Livros do Autor -=-");
        String autorLivro = verificadorStringVazio("Digite o autor dos livros: ");
        try {
            biblioteca.listarLivrosAutor(autorLivro);
        } catch(AutorNaoEncontradoException e ) {
            System.out.println(e.getMessage());
        }
    }

    public static void ordenarLivros() {
        int opcaoUsuario = 0;
        do {
            System.out.println("\n-=- Ordenar os Livros -=-");
            System.out.println("1- Ordenar livros por ano de publicação.\n2- Ordenar livros por título.\n3- Voltar ao menu de Gestão de livros.\n");
            opcaoUsuario = verificadorInt("Digite uma das opções: ");
            opcaoOrdenarLivros(opcaoUsuario);
        } while(opcaoUsuario != 3);
    }

    public static void opcaoOrdenarLivros(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                biblioteca.ordenarLivrosPorAnoLancamento();
                break;
            case 2:
                biblioteca.ordenarLivrosPorTitulo();
                break;
            case 3:
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    public static void buscarLivroPorTitulo() {
        System.out.println("\n-=- Buscar Livro por Título -=-");
        String tituloLivro = verificadorStringVazio("Digite o título do livro: ");
        try {
            biblioteca.buscarLivroPorTitulo(tituloLivro);
        } catch(LivroNaoEncontradoException e ) {
            System.out.println(e.getMessage());
        }
    }

    public static void menuGestaoUsuarios() {
        int opcaoUsuario = 0;
        do {
            System.out.println("\n-=- Gestão dos Usuários -=-");
            System.out.println("1- Cadastrar um Novo Usuário.\n2- Listar os Usuários.\n3- Buscar um Usuário pelo Email.\n4- Voltar ao Menu Principal.\n");
            opcaoUsuario = verificadorInt("Digite uma das opções: ");
            opcaoMenuGestaoUsuarios(opcaoUsuario);
        } while(opcaoUsuario != 4);
    }

    public static void opcaoMenuGestaoUsuarios(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                cadastrarNovoUsuario();
                break;
            case 2:
                if(biblioteca.listaUsuariosEstaVazia()) {
                    System.out.println("A biblioteca não tem usuários registrados. Adicione um usuário para utilizar essa opção.");
                    break;
                }
                biblioteca.listarTodosUsuarios();
                break;
            case 3:
                if(biblioteca.listaUsuariosEstaVazia()) {
                    System.out.println("A biblioteca não tem usuários registrados. Adicione um usuário para utilizar essa opção.");
                    break;
                }
                buscarUsuarioPorEmail();
                break;
            case 4:
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    public static void cadastrarNovoUsuario() throws EmailJaExistenteException {
        System.out.println("\n-=- Cadastrar um Novo Usuário -=-");
        String nomeUsuario = verificadorStringVazio("Digite o nome do usuário: ");
        String emailUsuario = verificadorEmail("Digite o email do usuário: ");

        try {
            biblioteca.verificarEmail(emailUsuario);
        } catch(EmailJaExistenteException e) {
            System.out.println(e.getMessage());
            return;
        }

        Usuario novoUsuario = new Usuario(nomeUsuario, emailUsuario);
        System.out.println("\n-=- Usuário -=-");
        System.out.println(novoUsuario);
        if(verificarConfirmacao("\nDeseja cadastrar esse usuário na biblioteca? (s/n) ")) {
            biblioteca.cadastrarNovoUsuario(novoUsuario);
        } else {
            System.out.println("Cadastro cancelado.");
        }
    }

    public static void buscarUsuarioPorEmail() {
        String emailBusca = verificadorEmail("Digite o email do usuário que deseja buscar: ");
        biblioteca.buscarEmail(emailBusca);
    }

    public static void menuEmprestimos() {
        int opcaoUsuario = 0;
        do {
            System.out.println("\n-=- Empréstimos -=-");
            System.out.println("1- Realizar Empréstimo.\n2- Devolver livro.\n3- Mostrar Histórico de Empréstimos.\n4- Voltar ao Menu Principal.\n");
            opcaoUsuario = verificadorInt("Digite uma das opções: ");
            opcaoMenuEmprestimos(opcaoUsuario);
        } while(opcaoUsuario != 4);
    }

    public static void opcaoMenuEmprestimos(int opcaoUsuario) {
        switch(opcaoUsuario) {
            case 1:
                if(biblioteca.listaLivrosEstaVazia() || biblioteca.listaUsuariosEstaVazia()) {
                    System.out.println("A biblioteca não tem livros cadastrados ou não tem usuários registrados. Não é possível realizar um empréstimo.");
                    break;
                }
                realizarEmprestimo();
                break;
            case 2:
                if(biblioteca.listaLivrosEstaVazia() || biblioteca.listaUsuariosEstaVazia()) {
                    System.out.println("A biblioteca não tem livros cadastrados ou não tem usuários registrados. Não é possível realizar uma devolução.");
                    break;
                }
                System.out.println("TODO");
                //TODO realizarDevolucao();
                break;
            case 3:
                if(biblioteca.listaLivrosEstaVazia() || biblioteca.listaUsuariosEstaVazia()) {
                    System.out.println("A biblioteca não tem livros cadastrados ou não tem usuários registrados. Também não há empréstimos já realizados.");
                } else if(biblioteca.conjuntoEmprestimosEstaVazia()) {
                    System.out.println("Não há nenhum empréstimo registrado na livraria.");
                } else {
                    biblioteca.listarHistoricoEmprestimos();
                }
                break;
            case 4:
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    public static void realizarEmprestimo() {
        System.out.println("\n-=- Realizar Empréstimo -=-");
        System.out.println("TODO");
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
                    throw new StringVaziaException("Não é possível deixar esse campo vazio.");
                }
                return stringUsuario;
            } catch(StringVaziaException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }

    public static boolean verificarConfirmacao(String mensagem) {
        String opcaoUsuario = verificadorStringVazio(mensagem);
        return opcaoUsuario.toLowerCase().charAt(0) == 's';
    }

    public static String verificadorEmail(String mensagem) {
        while(true) {
            try {
                String emailUsuario = verificadorStringVazio(mensagem);
                if(!emailUsuario.contains("@")) {
                    throw new EmailInvalidoException("Email inválido. O endereço deve conter '@'.");
                }
                return emailUsuario;
            } catch (EmailInvalidoException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }
}
