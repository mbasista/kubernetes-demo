package pl.demo.noteservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TreeConverter {

    public Tree jsonToTree(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, TreeNode.class);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            List<TreeNode> nodes = mapper.readValue(json, type);
            return new Tree(nodes);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String treeToJson(Tree tree) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return mapper.writeValueAsString(tree.getTreeNodes());
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}