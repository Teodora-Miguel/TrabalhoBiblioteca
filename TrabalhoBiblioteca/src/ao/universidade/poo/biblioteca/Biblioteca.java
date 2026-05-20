package ao.universidade.poo.biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> catalogo = new ArrayList<>();
    private List<Aluno> alunosRegistados = new ArrayList<>();

    public void registarLivro(Livro livro) {
        if (livro == null) throw new IllegalArgumentException("Livro inválido");
        catalogo.add(livro);
    }

    public void registarAluno(Aluno aluno) {
        if (aluno == null) throw new IllegalArgumentException("Aluno inválido");
        alunosRegistados.add(aluno);
    }

    public Livro buscarPorTitulo(String titulo) {
        for (Livro l : catalogo) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) return l;
        }
        return null;
    }

    public Livro buscarPorIsbn(String isbn) {
        for (Livro l : catalogo) {
            if (l.getIsbn().equals(isbn)) return l;
        }
        return null;
    }

    public void mostrarCatalogo() {
        System.out.println("=== Catálogo Atual da Biblioteca ===");
        if (catalogo.isEmpty()) System.out.println("Nenhum livro registado.");
        for (Livro l : catalogo) System.out.println(" " + l);
    }
}