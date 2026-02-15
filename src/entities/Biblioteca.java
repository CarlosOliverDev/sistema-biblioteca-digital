package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Biblioteca {
    private final List<Livro> listaLivros;
    private final List<Usuario> listaUsuarios;
    private final Map<Livro,Usuario> livrosEmprestados;

    public Biblioteca(List<Livro> listaLivros, List<Usuario> listaUsuarios, Map<Livro, Usuario> livrosEmprestados) {
        this.listaLivros = listaLivros;
        this.listaUsuarios = listaUsuarios;
        this.livrosEmprestados = livrosEmprestados;
    }

    public List<Livro> getListaLivros() {
        return listaLivros;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public Map<Livro, Usuario> getLivrosEmprestados() {
        return livrosEmprestados;
    }


}
