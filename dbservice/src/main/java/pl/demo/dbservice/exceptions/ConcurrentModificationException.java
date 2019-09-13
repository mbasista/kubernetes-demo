package pl.demo.dbservice.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import pl.demo.dbservice.gen.ResponseStatus;

@SoapFault(faultCode = FaultCode.SERVER)
public class ConcurrentModificationException extends Exception {

    public ConcurrentModificationException() {
        super(ResponseStatus.CONCURRENT_MODIFICATION.value());
    }
}
