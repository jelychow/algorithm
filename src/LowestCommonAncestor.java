public class LowestCommonAncestor {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    TreeNode parent;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        parent = root;
        // 二叉搜索树的最近公共祖先
        /**
         * 二叉搜索树 右子树都大于 root
         * 左子树都小于 root
         */
        if (root.right!=null&&root.val<p.val&&root.val<q.val) {
            return  lowestCommonAncestor(root.right,p,q);
        } else if (root.left!=null&&root.val>p.val&&root.val>q.val) {
            return lowestCommonAncestor(root.left,p,q);
        } else {
           return root;
        }

    }
}
