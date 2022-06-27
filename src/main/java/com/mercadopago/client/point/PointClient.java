package com.mercadopago.client.point;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.point.PointPaymentIntent;
import com.mercadopago.resources.point.PointPaymentIntentList;
import com.mercadopago.serialization.Serializer;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/** Client that use the Point APIs. */
public class PointClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PointClient.class.getName());

  private static final String PAYMENT_INTENT_URL =
      "/point/integration-api/devices/%s/payment-intents";

  private static final String PAYMENT_INTENT_LIST_URL =
      "/point/integration-api/payment-intents/events";

  /** Default constructor. Uses the default http client used by the SDK. */
  public PointClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
   */
  public PointClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Method responsible for creating a payment intent.
   *
   * @param deviceId device id
   * @param request attributes used to create a payment intent
   * @return payment intent information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/pt/reference/integrations_api_paymentintent_mlb/_point_integration-api_devices_deviceid_payment-intents/post">api
   *     docs</a>
   */
  public PointPaymentIntent createPaymentIntent(String deviceId, PointPaymentIntentRequest request)
      throws MPException, MPApiException {
    return this.createPaymentIntent(deviceId, request, null);
  }

  /**
   * Method responsible for creating payment intent with request options.
   *
   * @param deviceId device id
   * @param request attributes used to create a payment intent
   * @param requestOptions metadata to customize the request
   * @return payment intent information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/pt/reference/integrations_api_paymentintent_mlb/_point_integration-api_devices_deviceid_payment-intents/post">api
   *     docs</a>
   */
  public PointPaymentIntent createPaymentIntent(
      String deviceId, PointPaymentIntentRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create point payment intent request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(PAYMENT_INTENT_URL, deviceId))
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointPaymentIntent result =
        deserializeFromJson(PointPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for getting a list of payment intents with their final states between a date
   * range.
   *
   * @param request attributes used to set date range.
   * @return list of payment intents.
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/pt/reference/integrations_api/_point_integration-api_payment-intents_events/get">api
   *     docs</a>
   */
  public PointPaymentIntentList getPaymentIntentList(PointPaymentIntentListRequest request)
      throws MPException, MPApiException {
    return this.getPaymentIntentList(request, null);
  }

  /**
   * Method responsible for getting a list of payment intents with their final states between a date
   * range with request options.
   *
   * @param request attributes used to set date range.
   * @param requestOptions metadata to customize the request
   * @return list of payment intents.
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/pt/reference/integrations_api/_point_integration-api_payment-intents_events/get">api
   *     docs</a>
   */
  public PointPaymentIntentList getPaymentIntentList(
      PointPaymentIntentListRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(PAYMENT_INTENT_LIST_URL)
            .method(HttpMethod.GET)
            .queryParams(request.getParams())
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointPaymentIntentList result =
        deserializeFromJson(PointPaymentIntentList.class, response.getContent());
    result.setResponse(response);

    return result;
  }
}
