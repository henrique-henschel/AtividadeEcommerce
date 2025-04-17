package com.unicesumar.paymentMethods;

import com.unicesumar.model.PaymentType;

public class PaymentMethodFactory {
    public static PaymentMethod create(PaymentType type) {
        switch (type) {
            case PIX:
                return new PixPayment();
            case CARTAO:
                return new CreditCardPayment();
            case BOLETO:
                return new BoletoPayment();
            default:
                throw new IllegalArgumentException("ERRO! Forma de pagamento INVÁLIDA.");
        }
    }
}
