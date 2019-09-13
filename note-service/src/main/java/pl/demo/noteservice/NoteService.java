package pl.demo.noteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.demo.noteservice.exception.ConcurrentModificationException;
import pl.demo.noteservice.exception.NoteNotFoundException;
import pl.demo.noteservice.generated.dbservice.wsdl.FindJsonRequest;
import pl.demo.noteservice.generated.dbservice.wsdl.FindJsonResponse;
import pl.demo.noteservice.generated.dbservice.wsdl.SaveJsonRequest;

@Service
public class NoteService {

    private final DbServiceClient dbServiceClient;

    @Autowired
    public NoteService(DbServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    public String saveNote(String content) {
        String noteId = getNoteId(content);
        try {
            saveNote(noteId, content);
        } catch (ConcurrentModificationException e) {
            saveNote(noteId, content);
        }
        return noteId;
    }

    private String getNoteId(String note) {
        String id = note.split(" ")[0];
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("content is empty");
        } else {
            return id;
        }
    }

    private void saveNote(String id, String content) {
        SaveJsonRequest rq = new SaveJsonRequest();
        rq.setId(id);
        try {
            FindJsonResponse note = findNote(id);
            rq.setContent(note.getContent() + content);
            rq.setVersion(note.getVersion());
        } catch(NoteNotFoundException e) {
            rq.setContent(content);
        }
        dbServiceClient.saveJson(rq);
    }

    public FindJsonResponse findNote(String id) {
        FindJsonRequest rq = new FindJsonRequest();
        rq.setId(id);
        return dbServiceClient.findJson(rq);
    }
}
