package entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Emprestimo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Usuario usuario;
    private final Livro livro;
    private final LocalDate diaEmprestimo;
    private LocalDate diaDevolucao;

    public Emprestimo(Usuario usuario, Livro livro, LocalDate diaEmprestimo) {
        this.usuario = usuario;
        this.livro = livro;
        this.diaEmprestimo = diaEmprestimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public Livro getLivro() {
        return livro;
    }
    public LocalDate getDiaEmprestimo() {
        return diaEmprestimo;
    }

    public LocalDate getDiaDevolucao() {
        return diaDevolucao;
    }
    public void setDiaDevolucao(LocalDate diaDevolucao) {
        this.diaDevolucao = diaDevolucao;
    }

    public boolean foiDevolvido() {
        if(getDiaDevolucao() == null) {
            return false;
        }
        return true;
    }

    public String stringDataDevolucao() {
        if(foiDevolvido()) {
            return "\nDia da Devolução: " + diaDevolucao;
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(livro, that.livro) && Objects.equals(diaEmprestimo, that.diaEmprestimo) && Objects.equals(diaDevolucao, that.diaDevolucao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, livro, diaEmprestimo, diaDevolucao);
    }

    @Override
    public String toString() {
        return  usuario +
                "\n" + livro +
                "\nDia do Empréstimo: " + diaEmprestimo +
                stringDataDevolucao();
    }
}
