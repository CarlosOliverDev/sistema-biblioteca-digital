package entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Livro implements Serializable, Comparable<Livro> {
    @Serial
    private static final long serialVersionUID = 1L;

    private String titulo;
    private String autor;
    private int anoLancamento;
    private boolean emprestado = false;

    public Livro(int anoLancamento, String autor, String titulo) {
        this.anoLancamento = anoLancamento;
        this.autor = autor;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public boolean isEmprestado() {
        return emprestado;
    }
    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return anoLancamento == livro.anoLancamento && Objects.equals(titulo, livro.titulo) && Objects.equals(autor, livro.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, autor, anoLancamento);
    }

    @Override
    public String toString() {
        return "Título: " + titulo +
                "\nAutor: " + autor +
                "\nAno de Lançamento: " + anoLancamento;
    }

    @Override
    public int compareTo(Livro o) {
        return o.getTitulo().compareToIgnoreCase(this.getTitulo());
    }
}
