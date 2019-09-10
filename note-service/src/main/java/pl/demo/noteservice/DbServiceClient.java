package pl.demo.noteservice;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import pl.demo.noteservice.generated.dbservice.wsdl.FindJsonRequest;
import pl.demo.noteservice.generated.dbservice.wsdl.FindJsonResponse;
import pl.demo.noteservice.generated.dbservice.wsdl.SaveJsonRequest;
import pl.demo.noteservice.generated.dbservice.wsdl.SaveJsonResponse;

public class DbServiceClient extends WebServiceGatewaySupport {

    public FindJsonResponse findJson(FindJsonRequest rq) {
        return (FindJsonResponse) getWebServiceTemplate().marshalSendAndReceive(rq);
    }

    public SaveJsonResponse saveJson(SaveJsonRequest rq) {
        return (SaveJsonResponse) getWebServiceTemplate().marshalSendAndReceive(rq);
    }
}
