package com.unicesumar.paymentMethods;

import com.unicesumar.model.PaymentType;
import com.unicesumar.view.View;

// Implementacao de "PaymentMethod" para Boleto
public class BoletoPayment implements PaymentMethod {
    private final View view = new View();

    @Override
    public PaymentType getType() {
        return PaymentType.BOLETO;  // Retorna o tipo "BOLETO" do Enum
    }

    @Override
    public boolean pay(double amount) {
        String boletoCode = generateBoletoCode();

        // Exibe as informacoes no terminal
        view.showMessage("Código do boleto gerado: " + boletoCode);
        view.showMessage("Realizando o pagamento de R$ " + String.format("%.2f", amount) + "...");

        return true;
    }

    public String getPaymentConfirmation() {
        return "Pagamento efetuado COM SUCESSO via boleto.";
    }

    // Metodo para retornar uma string aleatoria que simula o codigo do boleto
    private String generateBoletoCode() {
        return "BOL-" + java.util.UUID.randomUUID();
    }
}
