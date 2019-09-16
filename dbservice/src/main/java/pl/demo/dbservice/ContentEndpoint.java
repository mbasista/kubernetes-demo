package pl.demo.dbservice;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.demo.dbservice.exceptions.ConcurrentModificationException;
import pl.demo.dbservice.exceptions.RecordNotFoundException;
import pl.demo.dbservice.gen.*;

@Endpoint
public class ContentEndpoint {

    private final static String NAMESPACE_URI = "demo.pl/dbservice/gen";

    private ContentRepository contentRepository;

    @Autowired
    public ContentEndpoint(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findJsonRequest")
    public @ResponsePayload
    FindJsonResponse findJsonById(@RequestPayload FindJsonRequest rq) throws RecordNotFoundException {
        FindJsonResponse rs = new FindJsonResponse();
        JsonContent jsonContent = contentRepository
                .findById(rq.getId())
                .orElseThrow(RecordNotFoundException::new);
        rs.setContent(jsonContent.getContent());
        rs.setVersion(jsonContent.getVersion());
        return rs;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveJsonRequest")
    public @ResponsePayload
    SaveJsonResponse saveJson(@RequestPayload SaveJsonRequest rq) throws ConcurrentModificationException {
        SaveJsonResponse rs = new SaveJsonResponse();
        try {
            JsonContent jsonContent = new JsonContent(rq.getId(), rq.getContent(), rq.getVersion());
            jsonContent = contentRepository.save(jsonContent);
            rs.setId(jsonContent.getId());
        } catch(ObjectOptimisticLockingFailureException | DataIntegrityViolationException e) {
            throw new ConcurrentModificationException();
        }
        return rs;
    }
}
