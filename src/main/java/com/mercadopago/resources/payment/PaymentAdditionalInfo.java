package com.mercadopago.resources.payment;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** PaymentAdditionalInfo class. */
@Getter
public class PaymentAdditionalInfo {
  /** IP from where the request comes from (only for bank transfers). */
  private String ipAddress;

  /** List of items to be paid. */
  private List<? extends PaymentItem> items;

  /** Payer's information. */
  private PaymentAdditionalInfoPayer payer;

  /** Shipping information. */
  private PaymentShipments shipments;
}
