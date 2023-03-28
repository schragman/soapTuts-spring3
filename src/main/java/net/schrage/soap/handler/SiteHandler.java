package net.schrage.soap.handler;

import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.Set;

public class SiteHandler implements SOAPHandler<SOAPMessageContext> {
  @Override
  public Set<QName> getHeaders() {
    System.out.println("Get Headers");
    return null;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext soapMessageContext) {
    System.out.println("Handle Message");
    Boolean isResponse = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    if (!isResponse) {
      SOAPMessage soapMessage = soapMessageContext.getMessage();
      try {
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        SOAPHeader soapHeader = soapEnvelope.getHeader();
        Iterator<Node> childElements = soapHeader.getChildElements();
        while (childElements.hasNext()) {
          Node eachNode = childElements.next();
          String name = eachNode.getNodeName();
          if (name.equals("SiteName")) {
            System.out.println("Site name is ====> " + eachNode.getFirstChild().getNodeValue());
          }
        }
        /*NodeList childNodes = soapHeader.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
          Node eachNode = childNodes.item(i);
          String name = eachNode.getNodeName();
          if (name != null && name.equals("SiteName")) {
            System.out.println("Site name is ====> " + eachNode.getFirstChild().getNodeValue());
          }
        }*/
      } catch (SOAPException e) {
        throw new RuntimeException(e);
      }
    } else {
      System.out.println("Response on the way");
    }


    return true;
  }

  @Override
  public boolean handleFault(SOAPMessageContext soapMessageContext) {
    System.out.println("Handle Fault");
    return false;
  }

  @Override
  public void close(MessageContext messageContext) {
    System.out.println("Close");
  }
}
