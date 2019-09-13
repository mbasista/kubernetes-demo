package pl.demo.noteservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Tree {
    private List<TreeNode> treeNodes;

    public Tree() {
        treeNodes = new ArrayList<>();
    }
}
