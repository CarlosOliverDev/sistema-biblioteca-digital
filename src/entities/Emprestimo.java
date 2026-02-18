package entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Emprestimo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Usuario usuario;
    private final Livro livro;
    private final LocalDateTime diaEmprestimo;
    private LocalDateTime diaDevolucao;

    public Emprestimo(Usuario usuario, Livro livro, LocalDateTime diaEmprestimo) {
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
    public LocalDateTime getDiaEmprestimo() {
        return diaEmprestimo;
    }

    public LocalDateTime getDiaDevolucao() {
        return diaDevolucao;
    }
    public void setDiaDevolucao(LocalDateTime diaDevolucao) {
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
        return Objects.equals(usuario, that.usuario) &&
                Objects.equals(livro, that.livro) &&
                Objects.equals(diaEmprestimo, that.diaEmprestimo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, livro, diaEmprestimo);
    }

    @Override
    public String toString() {
        return  usuario +
                "\n" + livro +
                "\nDia do Empréstimo: " + diaEmprestimo +
                stringDataDevolucao();
    }
}
