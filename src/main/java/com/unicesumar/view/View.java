package com.unicesumar.view;

import com.unicesumar.model.Product;
import com.unicesumar.model.User;

import java.util.List;
import java.util.Scanner;

public class View {
    private final Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n---MENU---");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar Produto");
        System.out.println("2 - Listar Produtos");
        System.out.println("3 - Cadastrar Usuário");
        System.out.println("4 - Listar Usuários");
        System.out.println("5 - Realizar nova venda");
        System.out.print("Escolha uma opção: ");
    }

    public int readOption() {
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public Product readNewProduct() {
        System.out.println("\n== Cadastrar Produto ==");
        System.out.print("--> Digite o NOME do novo produto: ");
        String name = scanner.nextLine();
        System.out.print("--> Digite o PREÇO do novo produto: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        return new Product(name, price);
    }

    public void showProductSaved(Product product) {
        System.out.println("✔ Produto salvo: " + product);
    }

    public void showProducts(List<Product> products) {
        final int NAME_WIDTH = 20;

        String headerFormat = "| %-36s | %-20s | %14s |%n";
        String rowFormat    = "| %-36s | %-20s | %14.2f |%n";

        System.out.println("\n+------------------------------------------------------------------------------+");
        System.out.println("|                              LISTA DE PRODUTOS                               |");
        System.out.println("+--------------------------------------+----------------------+----------------+");
        System.out.printf(headerFormat, "ID", "NOME", "PREÇO (R$)");
        System.out.println("+--------------------------------------+----------------------+----------------+");

        if (products.isEmpty()) {
            System.out.println("|                      Nenhum produto cadastrado.                             |");
        } else {
            for (Product p : products) {
                String name = p.getName();
                // Garantir que o nome nao ultrapasse o limite visual
                if (name.length() > NAME_WIDTH) {
                    name = name.substring(0, NAME_WIDTH - 1) + "…";
                }
                System.out.printf(rowFormat, p.getUuid(), name, p.getPrice());
            }
        }

        System.out.println("+--------------------------------------+----------------------+----------------+");
    }

    public User readNewUser() {
        System.out.println("\n== Cadastrar Usuário ==");
        System.out.print("--> Digite o NOME: ");
        String name = scanner.nextLine();
        System.out.print("--> Digite o E-MAIL: ");
        String email = scanner.nextLine();
        System.out.print("--> Digite a SENHA: ");
        String password = scanner.nextLine();
        return new User(name, email, password);
    }

    public void showUserSaved(User user) {
        System.out.println("✔ Usuário salvo: " + user);
    }

    public void showUsers(List<User> users) {
        final int NAME_WIDTH = 20;
        final int EMAIL_WIDTH = 30;

        String headerFormat = "| %-36s | %-20s | %-42s |%n";
        String rowFormat    = "| %-36s | %-20s | %-42s |%n";

        System.out.println("\n+----------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                         LISTA DE USUÁRIOS                                                |");
        System.out.println("+--------------------------------------+----------------------+--------------------------------------------+");
        System.out.printf(headerFormat, "ID", "NOME", "EMAIL");
        System.out.println("+--------------------------------------+----------------------+--------------------------------------------+");

        if (users.isEmpty()) {
            System.out.println("|                                  Nenhum usuário cadastrado.                                              |");
        } else {
            for (User u : users) {
                String name  = u.getName();
                String email = u.getEmail();
                // Corta os campos caso ultrapassem o limite visual
                if (name.length() > NAME_WIDTH) {
                    name = name.substring(0, NAME_WIDTH - 1) + "…";
                }
                if (email.length() > EMAIL_WIDTH) {
                    email = email.substring(0, EMAIL_WIDTH - 1) + "…";
                }
                System.out.printf(rowFormat, u.getUuid(), name, email);
            }
        }

        System.out.println("+--------------------------------------+----------------------+--------------------------------------------+");
    }

    // Metodo para ler o e-mail do comprador para a venda
    public String readBuyerEmail() {
        System.out.print("--> Digite o E-MAIL do usuário: ");
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    // Metodo para ler os IDs dos produtos (como uma string separada por virgula)
    public String readProductIds() {
        System.out.print("--> Digite os IDs dos produtos (separados por vírgula): ");
        return scanner.nextLine();
    }

    // Metodo para exibir os produtos encontrados
    public void showProductsFound(List<Product> products) {
        System.out.println("Produtos encontrados:");
        products.forEach(p -> System.out.println("- " + p.getName() + " (R$ " + String.format("%.2f", p.getPrice()) + ")"));
    }

    // Metodo para solicitar e ler a opcao de forma de pagamento
    public int readPaymentOption() {
        System.out.println("Escolha a forma de pagamento:");
        System.out.println("1 - Cartão de Crédito");
        System.out.println("2 - Boleto");
        System.out.println("3 - PIX");
        System.out.print("Opção: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public String readCardNumber() {
        System.out.print("--> Digite o número do cartão de crédito: ");
        return scanner.nextLine();  // Retorna a entrada do usuario (numero do cartao) - como String
    }

    // Metodo para exibir o resumo da venda
    public void showSaleSummary(String buyerName, List<Product> products, double total, String paymentConfirmation) {
        System.out.println("\n\nResumo da venda:");
        System.out.println("> Cliente: " + buyerName);
        System.out.println("> Produtos:");
        products.forEach(p -> System.out.println("- " + p.getName()));
        System.out.println("> Valor total: R$ " + String.format("%.2f", total));
        System.out.println("> Pagamento: " + paymentConfirmation);
    }

    public void showInvalidOption() {
        System.out.println("ERRO! Opção INVÁLIDA. Tente novamente.");
    }

    public void showExit() {
        System.out.println("Saindo...");
    }
}
