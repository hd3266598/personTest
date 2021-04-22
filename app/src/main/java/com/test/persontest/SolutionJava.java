package com.test.persontest;

import java.util.ArrayList;
import java.util.List;

class SolutionJava {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        inorder(root, res);
        return res;
    }

    public void inorder(TreeNode root, List<Integer> res) {

        if (root == null) {
            return;
        }
        inorder(root.getLeft(), res);
        res.add(root.getVal());
        inorder(root.getRight(), res);
    }

    int callatz(int n, int step) {
        if (n == 1) {
            return step;
        } else if ((n & 1) == 0) {
            return callatz(n / 2, ++step);
        } else {
            return callatz((n * 3 + 1) / 2, ++step);
        }
    }
}
