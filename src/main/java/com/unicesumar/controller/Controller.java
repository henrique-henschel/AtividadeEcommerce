package com.unicesumar.controller;

import com.unicesumar.paymentMethods.PaymentMethodFactory;
import com.unicesumar.paymentMethods.PaymentType;
import com.unicesumar.model.Product;
import com.unicesumar.model.Sale;
import com.unicesumar.model.User;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.repository.UserRepository;
import com.unicesumar.paymentMethods.PaymentMethod;
import com.unicesumar.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Controller {
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final SaleRepository saleRepo;
    private final View view;

    public Controller(ProductRepository productRepo, UserRepository userRepo, SaleRepository saleRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.saleRepo = saleRepo;
        this.view = new View();
    }

    public void run() {
        int option;
        do {
            view.showMenu();
            option = view.readOption();
            switch (option) {
                case 0: view.showExit(); break;
                case 1: handleCreateProduct(); break;
                case 2: handleListProducts(); break;
                case 3: handleCreateUser(); break;
                case 4: handleListUsers(); break;
                case 5: handleSell(); break;
                default: view.showInvalidOption();
            }
        } while (option != 0);
    }

    private void handleCreateProduct() {
        Product newProd = view.readNewProduct();
        productRepo.save(newProd);
        view.showProductSaved(newProd);
    }

    private void handleListProducts() {
        List<Product> products = productRepo.findAll();
        view.showProducts(products);
    }

    private void handleCreateUser() {
        User newUser = view.readNewUser();
        userRepo.save(newUser);
        view.showUserSaved(newUser);
    }

    private void handleListUsers() {
        List<User> users = userRepo.findAll();
        view.showUsers(users);
    }

    private void handleSell() {
        String buyerEmail = view.readBuyerEmail();
        Optional<User> optionalUser = userRepo.findByEmail(buyerEmail);
        if (!optionalUser.isPresent()) {
            view.showMessage("Usuário não encontrado!");
            return;
        }
        User buyer = optionalUser.get();
        view.showMessage("Usuário encontrado: " + buyer.getName());

        this.handleListProducts();  //Mostra todos os produtos com id, nome e preco

        String productsInput = view.readProductIds();
        String[] idStrings = productsInput.split(",");
        List<Product> selectedProducts = new ArrayList<>();
        for (String idStr : idStrings) {
            try {
                // Remover espacos e tentar converter para UUID
                UUID prodId = UUID.fromString(idStr.trim());
                Optional<Product> optionalProduct = productRepo.findById(prodId);
                if (optionalProduct.isPresent()) {
                    selectedProducts.add(optionalProduct.get());
                } else {
                    view.showMessage("Produto com ID " + idStr.trim() + " não encontrado.");
                }
            } catch (IllegalArgumentException e) {
                view.showMessage("ID de produto inválido: " + idStr.trim());
            }
        }
        if (selectedProducts.isEmpty()) {
            view.showMessage("Nenhum produto válido foi selecionado. Cancelando a venda...");
            return;
        }
        view.showProductsFound(selectedProducts);

        int paymentOption = view.readPaymentOption();
        PaymentType paymentType;
        switch (paymentOption) {
            case 1: paymentType = PaymentType.CARTAO; break;
            case 2: paymentType = PaymentType.BOLETO; break;
            case 3: paymentType = PaymentType.PIX; break;
            default:
                view.showMessage("Opção de pagamento inválida. Cancelando a venda...");
                return;
        }

        // Cria a estrategia de pagamento via Factory
        PaymentMethod paymentMethod = PaymentMethodFactory.create(paymentType);

        // Simulacao do processamento do pagamento
        view.showMessage("Aguarde, efetuando pagamento...");
        double total = selectedProducts.stream().mapToDouble(Product::getPrice).sum();
        boolean paymentResult = paymentMethod.pay(total);
        if (!paymentResult) {
            view.showMessage("Falha no processamento do pagamento. Cancelando a venda...");
            return;
        }
        view.showMessage(paymentMethod.getPaymentConfirmation());

        Sale sale = new Sale(
                buyer.getUuid(),
                selectedProducts,
                paymentMethod,
                java.time.LocalDateTime.now()
        );
        saleRepo.save(sale);
        view.showSaleSummary(buyer.getName(), selectedProducts, total, paymentMethod.getPaymentConfirmation());
        view.showMessage("\nVenda registrada com sucesso!");
    }
}
