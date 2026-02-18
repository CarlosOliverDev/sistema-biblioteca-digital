# 📚 Sistema de Gerenciamento de Biblioteca Digital

Um sistema robusto desenvolvido em Java para gerenciar o acervo, usuários e o fluxo de empréstimos de uma biblioteca digital, com persistência de dados e aplicação de conceitos modernos da linguagem.

---

## 🎯 Objetivo do Projeto

Desenvolver uma aplicação capaz de controlar o fluxo de uma biblioteca, permitindo cadastro de livros e usuários, realização de empréstimos e devoluções, além de garantir que as informações sejam salvas e recuperadas automaticamente (persistência em arquivos).

O projeto foca na aplicação prática de:
* **POO (Programação Orientada a Objetos):** Encapsulamento, herança e polimorfismo.
* **Collections & Generics:** Uso eficiente de listas, conjuntos e mapas.
* **Programação Funcional:** Uso intensivo de Java Streams API e Lambdas.
* **Tratamento de Exceções:** Criação de exceções personalizadas para regras de negócio.
* **Persistência de Dados:** Serialização de objetos para arquivos binários (`.dat`).

---

## 🚀 Funcionalidades Implementadas

### 📖 Gestão de Livros
* **Cadastro:** Adição de novos livros com Título, Autor e Ano de Publicação.
* **Listagem Completa:** Visualização de todo o acervo com status (Disponível/Emprestado).
* **Busca:** Localização de livros por título (busca insensível a maiúsculas/minúsculas).
* **Ordenação:** Organização do acervo por Título ou Ano de Publicação.
* **Agrupamento:** Relatório de livros agrupados por Autor.
* **Filtros:**
    * Listar apenas livros disponíveis.
    * Listar apenas livros emprestados.
    * Listar livros de um autor específico.

### 👤 Gestão de Usuários
* **Cadastro:** Registro de usuários com Nome e Email (validação de unicidade e formato).
* **Listagem:** Visualização de todos os usuários cadastrados.
* **Busca:** Localização de usuário por Email.

### 🔄 Sistema de Empréstimos (Core)
* **Realizar Empréstimo:** Vincula um livro disponível a um usuário cadastrado.
    * *Validações:* Impede empréstimo de livro já emprestado ou duplicidade para o mesmo usuário.
* **Devolução Inteligente:**
    * Processo simplificado se o usuário tiver apenas um livro.
    * Busca e seleção por título caso o usuário tenha múltiplos empréstimos.
* **Histórico:** Registro completo de todos os empréstimos realizados (quem pegou, qual livro, data de retirada e data de devolução).

### 💾 Persistência de Dados
* **Salvamento Automático:** Todos os dados (livros, usuários, empréstimos) são salvos na pasta `arquivos/` ao encerrar o sistema.
* **Carregamento Automático:** Ao iniciar, o sistema restaura o estado anterior lendo os arquivos `.dat`.

---

## 🛠️ Tecnologias e Conceitos Aplicados

| Conceito | Aplicação no Código |
| :--- | :--- |
| **Java 21** | Uso de métodos modernos de coleções (`getFirst`, `List.of`, etc). |
| **Streams API** | Filtros (`filter`), mapeamento (`map`), ordenação (`sorted`) e coletores (`Collectors.groupingBy`). |
| **Generics** | `ArrayList<Livro>`, `HashMap<String, Usuario>`, `HashSet<Emprestimo>`. |
| **Exceptions** | Tratamento robusto com `try-catch` e exceções próprias (`LivroIndisponivelException`, `EmailInvalidoException`, etc). |
| **I/O & Serialização** | Classes `ObjectOutputStream` e `ObjectInputStream` para gravação de objetos. |
| **Interface `Comparable`** | Implementada na classe `Livro` para ordenação natural por título. |

---

## 📂 Estrutura do Projeto

O código está organizado nos seguintes pacotes:

* **`application`**: Contém a classe `Main`, responsável pela interação com o usuário (menus, inputs e outputs).
* **`entities`**: Contém as classes de modelo (`Biblioteca`, `Livro`, `Usuario`, `Emprestimo`) contendo a lógica de negócio e atributos.
* **`exceptions`**: Contém as classes de exceção personalizadas para controle de erros específicos do domínio.

---

## ▶️ Como Executar

## ▶️ Como Executar

1. Certifique-se de ter o **JDK 21** ou superior instalado em sua máquina.
2. Clone este repositório em seu terminal utilizando o Git:
   ```bash
   git clone https://github.com/CarlosOliverDev/sistema-biblioteca-digital.git
   ```
3. Abra a pasta do projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse, VS Code, etc.).
4. Localize o arquivo Main.java dentro do pacote application.
5. Execute a classe Main diretamente pelos controles da sua IDE.
---