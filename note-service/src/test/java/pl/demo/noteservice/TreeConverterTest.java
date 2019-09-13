package pl.demo.noteservice;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TreeConverterTest {

    @Test
    public void resolveTree() {
        TreeConverter treeConverter = new TreeConverter();
        Tree tree = treeConverter.jsonToTree("[\n" +
                "\t{\n" +
                "\t\t\"key\": \"1\",\n" +
                "\t\t\"value\": \"2\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"key\": \"3\",\n" +
                "\t\t\"value\": \"4\"\n" +
                "\t}\n" +
                "]");
        assertEquals(tree.getTreeNodes().size(), 2);
    }

    @Test
    public void resolveTreeWithSingleElement() {
        TreeConverter treeConverter = new TreeConverter();
        Tree tree = treeConverter.jsonToTree("[\n" +
                "\t{\n" +
                "\t\t\"key\": \"3\",\n" +
                "\t\t\"value\": \"4\"\n" +
                "\t}\n" +
                "]");
        assertEquals(tree.getTreeNodes().size(), 1);
    }

    @Test
    public void resolveEmptyTree() {
        TreeConverter treeConverter = new TreeConverter();
        Tree tree = treeConverter.jsonToTree("[\n" +
                "]");
        assertTrue(tree.getTreeNodes().isEmpty());
    }
}
