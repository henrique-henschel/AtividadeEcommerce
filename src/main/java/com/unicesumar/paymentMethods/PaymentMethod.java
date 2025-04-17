package com.unicesumar.paymentMethods;

import com.unicesumar.model.PaymentType;

// Contrato a ser implementado em todos os metodos de pagamento
public interface PaymentMethod {
    PaymentType getType();  // Retornar o tipo de pagamento
    boolean pay(double amount);  // Metodo para realizar o pagamento
    String getPaymentConfirmation();  // Retornar confirmacao (pagamento bem sucedido)
}
