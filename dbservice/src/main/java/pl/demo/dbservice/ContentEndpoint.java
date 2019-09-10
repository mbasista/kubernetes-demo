package pl.demo.dbservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import kdemo.FindJsonResponse;
import kdemo.FindJsonRequest;
import kdemo.SaveJsonResponse;
import kdemo.SaveJsonRequest;

@Endpoint
public class ContentEndpoint {

    private final static String NAMESPACE_URI = "kdemo";

    private ContentRepository contentRepository;

    @Autowired
    public ContentEndpoint(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findJsonRequest")
    public @ResponsePayload FindJsonResponse findJsonById(@RequestPayload FindJsonRequest rq) {
        FindJsonResponse rs = new FindJsonResponse();
        String content = contentRepository.findById(rq.getId())
                .map(JsonContent::getContent)
                .orElseThrow(RecordNotFoundException::new);
        rs.setContent(content);
        return rs;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveJsonRequest")
    public @ResponsePayload SaveJsonResponse saveJson(@RequestPayload SaveJsonRequest rq) {
        SaveJsonResponse rs = new SaveJsonResponse();
        Long id = contentRepository.save(new JsonContent(rq.getContent())).getId();
        rs.setId(id);
        return rs;
    }
}
