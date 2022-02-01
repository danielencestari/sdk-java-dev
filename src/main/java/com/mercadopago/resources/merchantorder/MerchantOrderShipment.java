package com.mercadopago.resources.merchantorder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/** Shipment information. */
@Getter
public class MerchantOrderShipment {
  /** Shipping ID. */
  private long id;

  /** Shipping type. */
  private String shippingType;

  /** Shipping mode. */
  private String shippingMode;

  /** Shipping picking type. */
  private String pickingType;

  /** Shipping status. */
  private String status;

  /** Shipping sub status. */
  private String shippingSubstatus;

  /** Shipping items. */
  private List<Map<String, Object>> items;

  /** Date of creation. */
  private Date dateCreated;

  /** Last modified date. */
  private Date lastModified;

  /** First printed date. */
  private Date dateFirstPrinted;

  /** Shipping service ID. */
  private String serviceId;

  /** Sender ID. */
  private long senderId;

  /** Receiver ID. */
  private long receiverId;

  /** Shipping address. */
  private MerchantOrderReceiverAddress receiverAddress;

  /** Shipping options. */
  private MerchantOrderShippingOption shippingOption;
}