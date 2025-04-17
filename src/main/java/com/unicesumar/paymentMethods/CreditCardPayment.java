package com.unicesumar.paymentMethods;

import com.unicesumar.model.PaymentType;
import com.unicesumar.view.View;

// Implementacao de "PaymentMethod" para Cartao de Credito
public class CreditCardPayment implements PaymentMethod {
    private final View view = new View();

    @Override
    public PaymentType getType() {
        return PaymentType.CARTAO;  // Retorna o tipo "CARTAO" do Enum
    }

    @Override
    public boolean pay(double amount) {
        String cardNumber = view.readCardNumber();

        // Exibe as informacoes no terminal
        view.showMessage("Número do cartão utilizado: " + cardNumber);
        view.showMessage("Realizando o pagamento de R$ " + String.format("%.2f", amount) + "...");

        return true;
    }

    @Override
    public String getPaymentConfirmation() {
        return "Pagamento efetuado COM SUCESSO via cartão de crédito.";
    }
}
