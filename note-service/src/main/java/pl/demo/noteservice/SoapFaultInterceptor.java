package pl.demo.noteservice;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import pl.demo.noteservice.exception.ConcurrentModificationException;
import pl.demo.noteservice.exception.NoteNotFoundException;
import pl.demo.noteservice.generated.dbservice.wsdl.FailureStatus;

public class SoapFaultInterceptor implements ClientInterceptor {
    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        SoapMessage message = (SoapMessage) messageContext.getResponse();
        String fault = message.getSoapBody().getFault().getFaultStringOrReason();
        if (fault != null) {
            if (fault.equals(FailureStatus.ENTRY_NOT_FOUND.value())) {
                throw new NoteNotFoundException();
            } else if (fault.equals(FailureStatus.CONCURRENT_MODIFICATION.value())) {
                throw new ConcurrentModificationException();
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }
}
