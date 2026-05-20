package ao.universidade.poo.biblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private String numeroMatricula;
    private String curso;
    private List<Livro> emprestimos = new ArrayList<>();

    private static final int MAX_LIVROS = 5; // Limite de 5 livros
    private static final int PRAZO_DIAS = 7;  // Prazo de devolução: 7 dias
    private static final double MULTA_POR_DIA = 50.0; // 50 Kz por dia de atraso

    public Aluno(String nome, String numeroMatricula, String curso) {
        setNome(nome);
        setNumeroMatricula(numeroMatricula);
        setCurso(curso);
    }

    // Getters e Setters com validação
    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome inválido");
        this.nome = nome;
    }

    public String getNumeroMatricula() { return numeroMatricula; }
    public void setNumeroMatricula(String numeroMatricula) {
        if (numeroMatricula == null || numeroMatricula.isBlank()) throw new IllegalArgumentException("Matrícula inválida");
        this.numeroMatricula = numeroMatricula;
    }

    public String getCurso() { return curso; }
    public void setCurso(String curso) {
        if (curso == null) curso = "";
        this.curso = curso;
    }

    public List<Livro> getEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    // Não pode requisitar mais de 5 livros
    public boolean matricularLivro(Livro livro, LocalDate dataEmprestimo) {
        if (livro == null) throw new IllegalArgumentException("Livro nulo");

        if (emprestimos.size() >= MAX_LIVROS) {
            System.out.println("Erro: " + nome + " já atingiu o limite máximo de " + MAX_LIVROS + " livros emprestados.");
            return false;
        }

        if (!livro.emprestar(dataEmprestimo)) return false;

        emprestimos.add(livro);
        return true;
    }

    //  Calcula a multa no ato da devolução caso haja atraso
    public boolean devolverLivro(Livro livro, LocalDate dataDevolucao) {
        if (livro == null) throw new IllegalArgumentException("Livro nulo");

        boolean removido = emprestimos.remove(livro);
        if (removido) {
            // Calcular dias decorridos entre o empréstimo e a devolução
            long diasComLivro = ChronoUnit.DAYS.between(livro.getDataEmprestimo(), dataDevolucao);

            if (diasComLivro > PRAZO_DIAS) {
                long diasAtraso = diasComLivro - PRAZO_DIAS;
                double valorMulta = diasAtraso * MULTA_POR_DIA;
                System.out.printf("-> ATENÇÃO: Livro devolvido com %d dias de atraso. Multa a pagar: %.2f Kz\n", diasAtraso, valorMulta);
            } else {
                System.out.println("-> Livro devolvido dentro do prazo. Sem multas.");
            }

            livro.devolver();
        }
        return removido;
    }

    public String mostrarEmprestimos() {
        if (emprestimos.isEmpty()) return nome + " não tem empréstimos.";
        StringBuilder sb = new StringBuilder(nome + " tem os seguintes livros:\n");
        for (Livro l : emprestimos) sb.append(" - ").append(l.info()).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return nome + " (" + numeroMatricula + ") - " + curso;
    }
}


