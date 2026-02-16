package entities;

import exceptions.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Biblioteca {
    private final ArrayList<Livro> listaLivros;
    private final HashMap<String,Usuario> listaUsuarios;
    private final HashSet<Emprestimo> conjuntoEmprestimo;

    public Biblioteca(ArrayList<Livro> listaLivros, HashMap<String, Usuario> listaUsuarios, HashSet<Emprestimo> conjuntoEmprestimo) {
        this.listaLivros = listaLivros;
        this.listaUsuarios = listaUsuarios;
        this.conjuntoEmprestimo = conjuntoEmprestimo;
    }

    public ArrayList<Livro> getListaLivros() {
        return listaLivros;
    }
    public HashMap<String, Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    public HashSet<Emprestimo> getConjuntoEmprestimo() {
        return conjuntoEmprestimo;
    }

    public void cadastrarNovoLivro(Livro novoLivro) {
        if(listaLivros.contains(novoLivro)) {
            System.out.println("A biblioteca já tem esse livro.\nFoi adicionado outra cópia do livro na biblioteca!");
        } else {
            System.out.println("Esse livro foi adicionado na biblioteca!");
        }
        listaLivros.add(novoLivro);
    }

    public void listarTodosLivros() {
        listaLivros.forEach(this::imprimirDetalhesLivro);
    }

    public void imprimirDetalhesLivro(Livro livro) {
        System.out.println("=-=-=-=-=-=");
        System.out.println("Livro: \n" + livro);
        if(livro.isEmprestado()) {
            System.out.println("\nO livro está emprestado.");
        } else {
            System.out.println("\nLivro disponível.");
        }
        System.out.println("=-=-=-=-=-=");
    }

    public void buscarLivroPorTitulo(String tituloBuscado) throws LivroNaoEncontradoException {
        List<Livro> listaFiltrada = listaLivros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(tituloBuscado.toLowerCase()))
                .toList();

        if(listaFiltrada.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado.\nNão há nenhum livro com esse nome na biblioteca.");
        }
        if(listaFiltrada.size() > 1){
            System.out.println("Foi encontrado alguns livros com esse título:");
            listaFiltrada.forEach(this::imprimirDetalhesLivro);
        } else {
            System.out.println("Foi encontrado 1 livro com esse título:");
            imprimirDetalhesLivro(listaFiltrada.getFirst());
        }
    }

    public void ordenarLivrosPorAnoLancamento() {
        listaLivros.stream()
                .sorted(Comparator.comparing(Livro::getAnoLancamento).reversed())
                .forEach(this::imprimirDetalhesLivro);
    }

    public void ordenarLivrosPorTitulo() {
        listaLivros.stream()
                .sorted()
                .forEach(this::imprimirDetalhesLivro);
    }

    public boolean listaLivrosEstaVazia() {
        return listaLivros.isEmpty();
    }

    public void cadastrarNovoUsuario(String novoEmail, Usuario novoUsuario) {
        System.out.println("Novo usuário cadastrado!");
        listaUsuarios.put(novoEmail,novoUsuario);
    }

    public void verificarEmail(String email) throws EmailJaExistenteException {
        if(listaUsuarios.containsKey(email)) {
            throw new EmailJaExistenteException("Já existe um usuário utilizando esse email.");
        }
    }

    public void listarTodosUsuarios() {
        listaUsuarios.forEach((email, usuario) -> imprimirDetalhesUsuario(usuario));
    }

    public void imprimirDetalhesUsuario(Usuario usuario) {
        System.out.println("=-=-=-=-=-=");
        System.out.println("Usuário: \n" + usuario);
        if(usuario.existemLivrosPorEmprestimo()) {
            System.out.println("\nO usuário tem empréstimos feitos.");
        } else {
            System.out.println("\nO usuário ainda não pegou um livro emprestado.");
        }
        System.out.println("=-=-=-=-=-=");
    }

    public boolean listaUsuariosEstaVazia() {
        return listaUsuarios.isEmpty();
    }

    private void verificarLivroEstaEmprestado(Livro livro) throws LivroIndisponivelException {
        if(livro.isEmprestado()) {
            throw new LivroIndisponivelException("Esse livro está indisponível para empréstimo.");
        }
        System.out.println("Livro disponível para empréstimo!");
    }

    private void verificarUsuarioTemEsseLivro(Usuario usuario, Livro livro) throws EmprestimoDuplicadoException {
        if(usuario.contemLivro(livro)) {
            throw new EmprestimoDuplicadoException("Esse usuário já tem esse livro por empréstimo.");
        }
        System.out.println("O usuário pode pegar esse livro por empréstimo!");
    }

    public void realizarEmprestimo(Emprestimo emprestimo) throws LivroIndisponivelException, EmprestimoDuplicadoException {
        verificarLivroEstaEmprestado(emprestimo.getLivro());
        verificarUsuarioTemEsseLivro(emprestimo.getUsuario(),emprestimo.getLivro());

        emprestimo.getLivro().setEmprestado(true);
        emprestimo.getUsuario().adicionarNovoEmprestimo(emprestimo);

        conjuntoEmprestimo.add(emprestimo);
        System.out.printf("Foi realizado o empréstimo do livro %s para o usuário %s!\n",emprestimo.getLivro().getTitulo(),emprestimo.getUsuario().getNome());
    }

    public Emprestimo buscarEmprestimoAtivo(Usuario usuario, Livro livro) {
        return conjuntoEmprestimo.stream()
                .filter(e->e.getUsuario().equals(usuario) && e.getLivro().equals(livro) && e.getDiaDevolucao() == null)
                .findFirst()
                .orElse(null);
    }

    public void devolverLivro(Emprestimo emprestimo) throws EmprestimoNaoRegistradoException {
        Emprestimo emprestimoOriginal = buscarEmprestimoAtivo(emprestimo.getUsuario(), emprestimo.getLivro());
        if(emprestimoOriginal == null) {
            throw new EmprestimoNaoRegistradoException("Não foi encontrado empréstimo ativo deste livro para este usuário.");
        }
        emprestimoOriginal.getLivro().setEmprestado(false);
        emprestimoOriginal.getUsuario().devolverEmprestimo(emprestimoOriginal);
        emprestimoOriginal.setDiaDevolucao(LocalDate.now());
        System.out.println("O livro foi devolvido com sucesso!");
    }

    public void listarHistoricoEmprestimos() {
        conjuntoEmprestimo.forEach(this::imprimirDetalheEmprestimos);
    }

    public void imprimirDetalheEmprestimos(Emprestimo emprestimo) {
        System.out.println("=-=-=-=-=-=");
        System.out.println("Usuário: \n" + emprestimo);
        System.out.println("=-=-=-=-=-=");
    }
}
