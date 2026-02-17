package entities;

import exceptions.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Biblioteca {
    private final ArrayList<Livro> listaLivros;
    private final HashMap<String,Usuario> listaUsuarios;
    private final HashSet<Emprestimo> conjuntoEmprestimo;

    public Biblioteca() {
        this.listaLivros = carregarDadosLivros();
        this.listaUsuarios = carregarDadosUsuarios();
        this.conjuntoEmprestimo = carregarDadosEmprestimos();
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
            System.out.println("Foi adicionado outra cópia do livro na biblioteca!");
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
            throw new LivroNaoEncontradoException("\nNenhum livro com esse nome foi encontrado na biblioteca.");
        }
        if(listaFiltrada.size() > 1){
            System.out.println("Alguns livros com esse título foram encontrados:");
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

    //Método de Modificação
    public void cadastrarNovoUsuario(String novoEmail, Usuario novoUsuario) throws EmailJaExistenteException {
        verificarEmail(novoEmail);
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
            System.out.println("\nO usuário tem empréstimos de livros feitos.");
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

    public void devolverLivro(Emprestimo emprestimo, LocalDate date) throws EmprestimoNaoRegistradoException {
        Emprestimo emprestimoOriginal = buscarEmprestimoAtivo(emprestimo.getUsuario(), emprestimo.getLivro());
        if(emprestimoOriginal == null) {
            throw new EmprestimoNaoRegistradoException("Não foi encontrado um empréstimo ativo deste livro para este usuário.");
        }
        emprestimoOriginal.getUsuario().devolverEmprestimo(emprestimoOriginal, date);
        emprestimoOriginal.getLivro().setEmprestado(false);
        emprestimoOriginal.setDiaDevolucao(date);
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

    public void listarLivrosAutor(String autor) throws AutorNaoEncontradoException {
        List<Livro> livrosAutor = listaLivros.stream()
                .filter(l->l.getAutor().equalsIgnoreCase(autor))
                .toList();
        if(livrosAutor.isEmpty()) {
            throw new AutorNaoEncontradoException("Não foi encontrado nenhum livro escrito pelo autor " + autor);
        } else {
            System.out.println("Livros de " + autor + " encontrados:");
            livrosAutor.forEach(this::imprimirDetalhesLivro);
        }
    }

    public void buscarEmail(String email) {
        if(listaUsuarios.containsKey(email)) {
            System.out.println("Usuário encontrado!");
            System.out.println(listaUsuarios.get(email));
        } else {
            System.out.println("Não foi encontrado nenhum usuário com esse email.");
            System.out.printf("Email %s disponível para uso.\n",email);
        }
    }

    public void agruparLivrosPorAutor() {
        Map<String, List<Livro>> grupoDeAutores= listaLivros.stream()
                .collect(Collectors.groupingBy(Livro::getAutor));

        System.out.println("Autores e seus livros:");

        grupoDeAutores.forEach((s, livros) -> {
            System.out.println("=-=-=-=-=-=");
            System.out.printf("Autor %s\n",s);
            System.out.printf("Livros encontrados: %d\n",livros.size());
            livros.forEach(l-> System.out.println(" | " + l.getTitulo() + " - " + l.getAnoLancamento() + " |"));
            System.out.println("=-=-=-=-=-=");
        });
    }

    public void listarLivrosDisponiveis() {
        List<Livro> livrosDisponiveis = listaLivros.stream()
                .filter(l->!l.isEmprestado())
                .toList();
        if(livrosDisponiveis.isEmpty()) {
            System.out.println("Não há livros disponíveis para empréstimo.");
        } else {
            System.out.println("Livros Disponíveis:");
            livrosDisponiveis.forEach(this::imprimirDetalhesLivro);
        }
    }

    public void listarLivrosEmprestados() {
        List<Livro> livrosEmprestados = listaLivros.stream()
                .filter(Livro::isEmprestado)
                .toList();
        if(livrosEmprestados.isEmpty()) {
            System.out.println("Todos os livros estão disponíveis para empréstimo.");
        } else {
            System.out.println("Livros Emprestados:");
            livrosEmprestados.forEach(this::imprimirDetalhesLivro);
        }
    }

    public ArrayList<Livro> carregarDadosLivros() {
        System.out.println("Carregando arquivo de livros...");
        try (FileInputStream fileInputStream = new FileInputStream("arquivos/livros.dat");
             ObjectInputStream ois = new ObjectInputStream(fileInputStream))
        {
            ArrayList<Livro> livrosCarregados = (ArrayList<Livro>) ois.readObject();
            System.out.println("Leitura do arquivo de livros concluído.");
            return livrosCarregados;
        } catch(ClassNotFoundException | IOException e) {
            System.out.println("Não foi encontrado nenhum arquivo dos livros.");
            System.out.println("Criando nova lista de livros.");
            return new ArrayList<Livro>();
        }
    }

    public HashMap<String,Usuario> carregarDadosUsuarios() {
        System.out.println("Carregando arquivo de usuários...");
        try (FileInputStream fileInputStream = new FileInputStream("arquivos/usuarios.dat");
             ObjectInputStream ois = new ObjectInputStream(fileInputStream))
        {
            HashMap<String,Usuario> usuariosCarregados = (HashMap<String, Usuario>) ois.readObject();
            System.out.println("Leitura do arquivo de usuários concluído.");
            return usuariosCarregados;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Não foi encontrado nenhum arquivo dos usuários.");
            System.out.println("Criando nova lista de usuários.");
            return new HashMap<String,Usuario>();
        }
    }

    public HashSet<Emprestimo> carregarDadosEmprestimos() {
        System.out.println("Carregando arquivo de empréstimos...");
        try (FileInputStream fileInputStream = new FileInputStream("arquivos/emprestimos.dat");
             ObjectInputStream ois = new ObjectInputStream(fileInputStream))
        {
            HashSet<Emprestimo> emprestimosCarregados = (HashSet<Emprestimo>) ois.readObject();
            System.out.println("Leitura do arquivo de empréstimos concluído.");
            return emprestimosCarregados;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Não foi encontrado nenhum arquivo dos empréstimos.");
            System.out.println("Criando nova lista de empréstimos.");
            return new HashSet<Emprestimo>();
        }
    }

    public void salvarDadosLivros() {
        System.out.println("Salvando arquivo de livros...");
        try (FileOutputStream fileOutputStream = new FileOutputStream("arquivos/livros.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream))
        {
            oos.writeObject(listaLivros);
            System.out.println("Livros salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro: Falha em salvar os livros no arquivo.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    public void salvarDadosUsuarios() {
        System.out.println("Salvando arquivo de usuários...");
        try (FileOutputStream fileOutputStream = new FileOutputStream("arquivos/usuarios.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream))
        {
            oos.writeObject(listaUsuarios);
            System.out.println("Usuários salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro: Falha em salvar os usuários no arquivo.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    public void salvarDadosEmprestimos() {
        System.out.println("Salvando arquivo de empréstimos...");
        try (FileOutputStream fileOutputStream = new FileOutputStream("arquivos/emprestimos.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream))
        {
            oos.writeObject(conjuntoEmprestimo);
            System.out.println("Empréstimos salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro: Falha em salvar os empréstimos no arquivo.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }
}
