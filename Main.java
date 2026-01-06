import java.util.ArrayList; // importa a ferramenta para criar listas
import java.util.Scanner;   // importa a ferramenta para ler os dados do teclado

public class Main {

    public static void main(String[] args) {
        // cria uma lista para armazenar as tarefas, no caso as Strings.
        ArrayList<String> listaDeTarefas = new ArrayList<>();
        
        // aqui ele vai criar o leitor para pegar o que o usuário digita
        Scanner scanner = new Scanner(System.in);
        
        int escolhaUsuario = 0; // variável para controlar o menu

        // Loop que vai manter o programa rodando enquanto a opção não for 4 (Sair)
        while (escolhaUsuario != 4) {
            System.out.println("\n--- MENU DE TAREFAS ---");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Listar Tarefas");
            System.out.println("3. Remover Tarefa");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            // Aqui ele vai verificar se o usuário digitou um número inteiro
            if (scanner.hasNextInt()) {
                escolhaUsuario = scanner.nextInt();
                scanner.nextLine(); // limpa o "Enter" que ficou na memória
            } else {
                System.out.println("Por favor, digite um número válido.");
                scanner.next(); // Descarta a entrada inválida
                continue; // Volta ao início do loop
            }

            switch (escolhaUsuario) {
                case 1:
                    System.out.print("Digite a descrição da tarefa: ");
                    String novaTarefa = scanner.nextLine();
                    listaDeTarefas.add(novaTarefa);
                    System.out.println("Tarefa adicionada com sucesso!");
                    break;

                case 2:
                    System.out.println("\n--- LISTA ---");
                    if (listaDeTarefas.isEmpty()) {
                        System.out.println("Nenhuma tarefa cadastrada.");
                    } else {
                        // Loop para mostrar cada tarefa com seu número (índice)
                        for (int i = 0; i < listaDeTarefas.size(); i++) {
                            System.out.println((i + 1) + ". " + listaDeTarefas.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n--- REMOVER ---");
                    // Primeiro listamos para o usuário saber qual número
                    if (listaDeTarefas.isEmpty()) {
                        System.out.println("A lista está vazia, nada para remover.");
                    } else {
                        for (int i = 0; i < listaDeTarefas.size(); i++) {
                            System.out.println((i + 1) + ". " + listaDeTarefas.get(i));
                        }
                        System.out.print("Digite o número da tarefa para remover: ");
                        if (scanner.hasNextInt()) {
                            int indice = scanner.nextInt();
                            
                            // Verifica se o número digitado vai ser válido (está dentro da lista)
                            if (indice > 0 && indice <= listaDeTarefas.size()) {
                                // removi (indice - 1) porque listas começam no zero
                                String tarefaRemovida = listaDeTarefas.remove(indice - 1);
                                System.out.println("Tarefa '" + tarefaRemovida + "' removida!");
                            } else {
                                System.out.println("Número inválido.");
                            }
                        } else {
                            System.out.println("Entrada inválida.");
                            scanner.next();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Saindo do sistema... Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        
        scanner.close(); // Fim, aqui fecha o leitor para liberar recursos
    }
}