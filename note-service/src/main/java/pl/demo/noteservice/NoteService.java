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
    private final HashingService hashingService;
    private final BlockchainAlgorithm blockchainAlgorithm;
    private final TreeConverter treeConverter;

    @Autowired
    public NoteService(DbServiceClient dbServiceClient, HashingService hashingService, BlockchainAlgorithm blockchainAlgorithm, TreeConverter treeConverter) {
        this.dbServiceClient = dbServiceClient;
        this.hashingService = hashingService;
        this.blockchainAlgorithm = blockchainAlgorithm;
        this.treeConverter = treeConverter;
    }

    public String saveNote(String note) {
        String noteId = getNoteId(note);
        try {
            saveNote(noteId, note);
        } catch (ConcurrentModificationException e) {
            saveNote(note);
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

    private void saveNote(String id, String newContent) {
        SaveJsonRequest rq = new SaveJsonRequest();
        rq.setId(hashingService.sha256(id));
        try {
            FindJsonResponse existingNote = findNoteByPlainId(id);
            rq.setContent(updateContent(existingNote.getContent(), id, newContent));
            rq.setVersion(existingNote.getVersion());
        } catch(NoteNotFoundException e) {
            rq.setContent(createContent(id, newContent));
        }
        dbServiceClient.saveJson(rq);
    }

    private String updateContent(String currentTree, String id, String newValue) {
        Tree tree = treeConverter.jsonToTree(currentTree);
        tree = blockchainAlgorithm.addNoteToTree(tree, id, newValue);
        return treeConverter.treeToJson(tree);
    }

    private String createContent(String id, String content) {
        Tree tree = blockchainAlgorithm.createTree(id, content);
        return treeConverter.treeToJson(tree);
    }

    public FindJsonResponse findNoteByPlainId(String id) {
        FindJsonRequest rq = new FindJsonRequest();
        rq.setId(hashingService.sha256(id));
        return dbServiceClient.findJson(rq);
    }
}
