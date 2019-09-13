package pl.demo.noteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockchainAlgorithm {

    private HashingService hashingService;

    @Autowired
    public BlockchainAlgorithm(HashingService hashingService) {
        this.hashingService = hashingService;
    }

    public Tree createTree(String noteId, String note) {
        TreeNode treeNode = new TreeNode();
        treeNode.setKey(hashingService.sha256(noteId));
        treeNode.setValue(hashingService.sha256(note));

        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(treeNode);
        return new Tree(nodes);
    }

    public Tree addNoteToTree(Tree tree, String noteId, String note) {
        List<TreeNode> nodes = tree.getTreeNodes();
        String noteHash = hashingService.sha256(note);
        String resultKey = nodes.stream()
                .map(TreeNode::getKey)
                .reduce(noteHash, (partialString, element) -> hashingService.sha256(partialString + element));
        TreeNode node = new TreeNode();
        node.setKey(resultKey);
        node.setValue(noteHash);
        tree.getTreeNodes().add(node);
        return tree;
    }
}
