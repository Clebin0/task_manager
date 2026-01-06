# 📝 Gerenciador de Tarefas (Console)

Este é um projeto de estudo focado em **Lógica de Programação** e **Estrutura de Dados**, implementado em duas linguagens diferentes para fins de comparação: **Java** e **Kotlin**.

O objetivo foi criar uma aplicação via terminal (CLI) para gerenciar uma lista de tarefas simples, praticando conceitos fundamentais de desenvolvimento de software.

## 🚀 Funcionalidades

* **Menu Interativo:** Navegação simples via console.
* **Adicionar Tarefa:** Entrada de dados pelo usuário.
* **Listar Tarefas:** Exibição dinâmica da lista com indexação.
* **Remover Tarefa:** Remoção segura de itens da lista.
* **Validação:** Tratamento básico de erros (ex: impedir que o programa feche se o usuário digitar letras onde deveria ser número).

## 🛠️ Tecnologias Utilizadas

* **Java** (Uso de `ArrayList`, `Scanner`, `Switch Case`)
* **Kotlin** (Uso de `MutableList`, `Interpolation`, `When`)
* **IDE:** (IntelliJ)

## 🧠 Aprendizados e Desafios

Durante o desenvolvimento deste projeto, pude fixar conceitos importantes:

1.  **O "Bug" do Scanner no Java:** Entendi que após ler um número (`nextInt`), é necessário consumir a quebra de linha com um `nextLine()` extra, caso contrário a próxima leitura de texto é ignorada.
2.  **Java vs Kotlin:**
    * Percebi como o Kotlin reduz a verbosidade (menos código para fazer a mesma coisa).
    * Comparação prática entre `switch` (Java) e `when` (Kotlin).
    * Manipulação de listas (`ArrayList` vs `mutableListOf`).
3.  **Tratamento de Exceções:** Implementação de lógica para garantir que entradas inválidas não parem a execução do programa.

## 📂 Como Executar

Basta clonar este repositório e abrir os arquivos em qualquer IDE compatível com Java/Kotlin.

**Versão Java:**
Execute a classe `src.GerenciadorTarefas.java`.

**Versão Kotlin:**
Execute o arquivo `src.GerenciadorTarefas.kt`.

---
*Desenvolvido por Cledson Santos durante os estudos.*