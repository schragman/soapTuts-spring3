package net.schrage.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.apache.cxf.feature.Features;

@WebService
//LoggingFeature is deprecated
@Features(features="org.apache.cxf.feature.LoggingFeature")
public class HelloWs {

  @WebMethod
  public String hello() {
    return "Hello";
  }

}
