package net.schrage.soap;

import jakarta.xml.ws.WebServiceFeature;
import net.schrage.soap.Product;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOrdersWsImpl implements CustomerOrdersPortType {

  Map<BigInteger, List<Order>> customerOrders = new HashMap<>();
  int currentId;

  public CustomerOrdersWsImpl() {
    init();
  }

  public void init() {
    List<Order> orders = new ArrayList<>();
    Order order = new Order();
    order.setId(BigInteger.valueOf(1));

    Product product = new Product();
    product.setId("1");
    product.setDescription("iPhone");
    product.setQuantity(BigInteger.valueOf(3));
    order.getProduct().add(product);
    orders.add(order);
    customerOrders.put(BigInteger.valueOf(++currentId), orders);
  }

  @Override
  public GetOrdersResponse getOrders(GetOrdersRequest request) {
    BigInteger customerId = request.getCustomerId();
    if (customerId == null || customerId.compareTo(BigInteger.ONE) == -1) {
      throw new RuntimeException("Invalid customer id");
    }
    List<Order> orders = customerOrders.get(customerId);
    GetOrdersResponse response = new GetOrdersResponse();
    response.getOrder().addAll(orders);

    return response;
  }


  @Override
  public CreateOrdersResponse createOrders(CreateOrdersRequest request) {
    BigInteger customerId = request.customerId;
    Order order = request.getOrder();
    List<Order> orders = customerOrders.get(customerId);
    orders.add(order);

    CreateOrdersResponse response = new CreateOrdersResponse();

    response.setResult(true);

    return response;

  }
}
