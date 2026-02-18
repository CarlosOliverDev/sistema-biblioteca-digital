package entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Livro implements Serializable, Comparable<Livro> {
    @Serial
    private static final long serialVersionUID = 1L;

    private String titulo;
    private String autor;
    private int anoPublicado;
    private boolean emprestado = false;

    public Livro(String titulo, String autor, int anoPublicado) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicado = anoPublicado;
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

    public int getAnoPublicado() {
        return anoPublicado;
    }
    public void setAnoPublicado(int anoPublicado) {
        this.anoPublicado = anoPublicado;
    }

    public boolean isEmprestado() {
        return emprestado;
    }
    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }

    public String stringAnoPublicado() {
        if(anoPublicado >= 1 ) {
            return Integer.toString(anoPublicado);
        }
        return Math.abs(anoPublicado) + " a.C.";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return anoPublicado == livro.anoPublicado && Objects.equals(titulo, livro.titulo) && Objects.equals(autor, livro.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, autor, anoPublicado);
    }

    @Override
    public String toString() {
        return "Título: " + titulo +
                "\nAutor: " + autor +
                "\nAno de Publicação: " + stringAnoPublicado();
    }

    @Override
    public int compareTo(Livro o) {
        return this.getTitulo().compareToIgnoreCase(o.getTitulo());
    }
}
