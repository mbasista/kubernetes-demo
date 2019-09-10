package pl.demo.noteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.demo.noteservice.generated.dbservice.wsdl.FindJsonRequest;
import pl.demo.noteservice.generated.dbservice.wsdl.FindJsonResponse;
import pl.demo.noteservice.generated.dbservice.wsdl.SaveJsonRequest;
import pl.demo.noteservice.generated.dbservice.wsdl.SaveJsonResponse;

@Service
public class NoteService {

    private final DbServiceClient dbServiceClient;

    @Autowired
    public NoteService(DbServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    public Long saveJson(String content) {
        SaveJsonRequest rq = new SaveJsonRequest();
        rq.setContent(content);
        SaveJsonResponse rs = dbServiceClient.saveJson(rq);
        return rs.getId();
    }

    public String findJson(Long id) {
        FindJsonRequest rq = new FindJsonRequest();
        rq.setId(id);
        FindJsonResponse rs = dbServiceClient.findJson(rq);
        return rs.getContent();
    }
}
