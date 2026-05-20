package ao.universidade.poo.biblioteca;

import java.time.LocalDate;
import java.util.Scanner;

public class TrabalhoBiblioteca {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        // Criando um aluno padrão para facilitar os testes iniciais de empréstimo
        Aluno alunoAtual = new Aluno("Fernando Perreira", "2026044", "Engenharia Informática");
        biblioteca.registarAluno(alunoAtual);

        // Algumas instâncias iniciais no catálogo para a biblioteca não começar vazia
        biblioteca.registarLivro(new Livro("Introdução a Java", "Ana Silva", "9781234567890"));
        biblioteca.registarLivro(new Livro("Estruturas de Dados", "Carlos Souza", "9780987654321"));

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==========================================");
            System.out.println("   SISTEMA DE GESTÃO DE BIBLIOTECA - UNIKIVI     ");
            System.out.println("============================================");
            System.out.println("1 - Registar Novo Livro no Catálogo");
            System.out.println("2 - Mostrar Catálogo da Biblioteca");
            System.out.println("3 - Requisitar um Livro (Empréstimo)");
            System.out.println("4 - Devolver um Livro (Com cálculo de Multa)");
            System.out.println("5 - Ver Meus Livros Requisitados");
            System.out.println("0 - Sair do Programa");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduza um número válido.");
                continue;
            }

            System.out.println("-------------------------------------------");

            switch (opcao) {
                case 1:
                    System.out.println("--- REGISTAR NOVO LIVRO ---");
                    System.out.print("Título do Livro: ");
                    String titulo = teclado.nextLine();
                    System.out.print("Autor do Livro: ");
                    String autor = teclado.nextLine();
                    System.out.print("ISBN (13 caracteres): ");
                    String isbn = teclado.nextLine();

                    try {
                        Livro novoLivro = new Livro(titulo, autor, isbn);
                        biblioteca.registarLivro(novoLivro);
                        System.out.println("✓ Livro '" + titulo + "' registado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro ao registar: " + e.getMessage());
                    }
                    break;

                case 2:
                    biblioteca.mostrarCatalogo();
                    break;

                case 3:
                    System.out.println("--- REQUISITAR LIVRO ---");
                    System.out.print("Introduza o Título exato do livro que deseja: ");
                    String tituloBusca = teclado.nextLine();

                    Livro livroEncontrado = biblioteca.buscarPorTitulo(tituloBusca);

                    if (livroEncontrado == null) {
                        System.out.println("✕ Erro: Livro não encontrado no catálogo.");
                    } else if (!livroEncontrado.isDisponivel()) {
                        System.out.println("✕ Erro: Este livro já se encontra emprestado.");
                    } else {
                        // Empréstimo na data atual
                        LocalDate dataHoje = LocalDate.now();
                        boolean sucesso = alunoAtual.matricularLivro(livroEncontrado, dataHoje);
                        if (sucesso) {
                            System.out.println("✓ Empréstimo de '" + livroEncontrado.getTitulo() + "' efetuado com sucesso!");
                        }
                    }
                    break;

                case 4:
                    System.out.println("--- DEVOLVER LIVRO ---");
                    System.out.print("Introduza o Título do livro a devolver: ");
                    String tituloDevolver = teclado.nextLine();

                    Livro livroDevolucao = biblioteca.buscarPorTitulo(tituloDevolver);

                    if (livroDevolucao == null) {
                        System.out.println("✕ Erro: Este livro não pertence à biblioteca.");
                    } else {
                        //  perguntar ao utilizador quantos dias se passaram na realidade.
                        System.out.print("Quantos dias se passaram desde o empréstimo? (Prazo: 7 dias): ");
                        int diasDecorridos;
                        try {
                            diasDecorridos = Integer.parseInt(teclado.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("✕ Dias inválidos. Operação cancelada.");
                            break;
                        }

                        LocalDate dataEmprestimoOriginal = livroDevolucao.getDataEmprestimo();
                        if (dataEmprestimoOriginal == null) {
                            System.out.println("✕ Erro: Este livro não consta como emprestado.");
                            break;
                        }

                        LocalDate dataDevolucaoSimulada = dataEmprestimoOriginal.plusDays(diasDecorridos);

                        boolean devolvido = alunoAtual.devolverLivro(livroDevolucao, dataDevolucaoSimulada);
                        if (devolvido) {
                            System.out.println("✓ Processo de devolução concluído.");
                        } else {
                            System.out.println("✕ Erro: Este livro não estava na tua lista de empréstimos.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("--- MEUS EMPRÉSTIMOS ---");
                    System.out.println(alunoAtual.mostrarEmprestimos());
                    break;

                case 0:
                    System.out.println("A encerrar o sistema... Até à próxima!");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha um número entre 0 e 5.");
            }
        }
        teclado.close();
    }
}