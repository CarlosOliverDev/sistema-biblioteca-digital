package entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

public class Usuario implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private final HashSet<Emprestimo> livrosPegosPorEmprestimo;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.livrosPegosPorEmprestimo = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public HashSet<Emprestimo> getLivrosPegosPorEmprestimo() {
        return livrosPegosPorEmprestimo;
    }

    public boolean existemLivrosPorEmprestimo() {
        return !livrosPegosPorEmprestimo.isEmpty();
    }

    public boolean contemLivro(Livro livro) {
        return livrosPegosPorEmprestimo.stream()
                .filter(e->e.getLivro().equals(livro))
                .anyMatch(e -> !e.foiDevolvido());
    }

    public void adicionarNovoEmprestimo(Emprestimo emprestimo){
        livrosPegosPorEmprestimo.add(emprestimo);
    }

    //TODO Remover empréstimo é um jeito preguiçoso de atualizar o empréstimo.
    public void devolverEmprestimo(Emprestimo emprestimo) {
        livrosPegosPorEmprestimo.remove(emprestimo);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nEmail: " + email;
    }
}
