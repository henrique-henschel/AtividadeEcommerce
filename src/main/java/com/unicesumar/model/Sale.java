package com.unicesumar.model;

import com.unicesumar.paymentMethods.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Classe que representa a entidade "sales"
public class Sale extends Entity {
    private final UUID userId;  // ID do usuario que comprou
    private final List<Product> products;  // Produtos vendidos
    private final PaymentMethod paymentMethod;  // Metodo de pagamento
    private final LocalDateTime saleDate;  // Data da venda

    public Sale(UUID userId, List<Product> products, PaymentMethod paymentMethod, LocalDateTime saleDate) {
        super();
        this.userId = userId;
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.saleDate = saleDate;
    }

    public Sale(UUID id, UUID userId, List<Product> products, PaymentMethod paymentMethod, LocalDateTime saleDate) {
        super(id);
        this.userId = userId;
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.saleDate = saleDate;
    }

    // Getters
    public UUID getUserId() {
        return userId;
    }

    public List<Product> getProductList() {
        return products;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }
}
