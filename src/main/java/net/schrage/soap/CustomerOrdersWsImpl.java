package net.schrage.soap;

import jakarta.xml.ws.WebServiceFeature;
import net.schrage.soap.Product;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOrdersWsImpl implements net.schrage.soap.CustomerOrdersPortType {

  Map<BigInteger, List<net.schrage.soap.Order>> customerOrders = new HashMap<>();
  int currentId;

  public CustomerOrdersWsImpl() {
    init();
  }

  public void init() {
    List<net.schrage.soap.Order> orders = new ArrayList<>();
    net.schrage.soap.Order order = new net.schrage.soap.Order();
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
  public net.schrage.soap.GetOrdersResponse getOrders(net.schrage.soap.GetOrdersRequest request) {
    BigInteger customerId = request.getCustomerId();
    List<net.schrage.soap.Order> orders = customerOrders.get(customerId);
    net.schrage.soap.GetOrdersResponse response = new net.schrage.soap.GetOrdersResponse();
    response.getOrder().addAll(orders);

    return response;
  }


  @Override
  public net.schrage.soap.CreateOrdersResponse createOrders(net.schrage.soap.CreateOrdersRequest request) {
    BigInteger customerId = request.customerId;
    net.schrage.soap.Order order = request.getOrder();
    List<net.schrage.soap.Order> orders = customerOrders.get(customerId);
    orders.add(order);

    net.schrage.soap.CreateOrdersResponse response = new net.schrage.soap.CreateOrdersResponse();

    response.setResult(true);

    return response;

  }
}
