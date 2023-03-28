package net.schrage.soap.config;

import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.soap.SOAPBinding;
import net.schrage.soap.CustomerOrdersWsImpl;
import net.schrage.soap.handler.SiteHandler;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.dom.WSConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebServiceConfig {

  @Autowired
  private Bus bus;

  @Bean
  public Endpoint endpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, new CustomerOrdersWsImpl());
    endpoint.publish("/customerordersservice");

    Map<String, Object> inProps = new HashMap<>();

    inProps.put(ConfigurationConstants.ACTION, ConfigurationConstants.USERNAME_TOKEN);
    inProps.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
    inProps.put(ConfigurationConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());



    WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
    endpoint.getInInterceptors().add(wssIn);

    List<Handler> handlerList = new ArrayList<>();
    handlerList.add(new SiteHandler());

    SOAPBinding soapBinding = (SOAPBinding) endpoint.getBinding();
    soapBinding.setHandlerChain(handlerList);


    return endpoint;
  }

}
