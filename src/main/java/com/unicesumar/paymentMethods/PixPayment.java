package com.unicesumar.paymentMethods;

import com.unicesumar.model.PaymentType;
import com.unicesumar.view.View;

// Implementacao de "PaymentMethod" para Pix
public class PixPayment implements PaymentMethod {
    private final View view = new View();

    @Override
    public PaymentType getType() {
        return PaymentType.PIX;  // Retorna o tipo "PIX" do Enum
    }

    @Override
    public boolean pay(double amount) {
        String pixCode = generatePixCode();

        // Exibe as informacoes no terminal
        view.showMessage("Código Pix gerado: " + pixCode);
        view.showMessage("Realizando o pagamento de R$ " + String.format("%.2f", amount) + "...");

        return true;
    }

    @Override
    public String getPaymentConfirmation() {
        return "Pagamento efetuado COM SUCESSO via PIX.";
    }

    // Metodo para retornar uma string aleatoria que simula o codigo Pix
    private String generatePixCode() {
        return java.util.UUID.randomUUID().toString();
    }
}
