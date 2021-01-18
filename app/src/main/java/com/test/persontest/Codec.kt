package com.test.persontest

class Codec() {
    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        val list = arrayListOf<String>()
        dfs(list, root)
        return "[${list.joinToString(separator = ",")}]"
    }

    fun dfs(list: ArrayList<String>, root: TreeNode?) {
        if (root == null) {
            list.add("null")
            return
        }
        list.add(root.`val`.toString())
        dfs(list, root.left)
        dfs(list, root.right)
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        var treeNode: TreeNode? = null
        data.removePrefix("[").removeSuffix("]").split(",").takeIf { it.isNotEmpty() }
            ?.toMutableList()?.let {
                treeNode = reDfs(it)
            }
        return treeNode
    }


    var ans: TreeNode? = null
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        dfs(root, p, q)
        return ans
    }

    fun dfs(root: TreeNode?, p: TreeNode?, q: TreeNode?): Boolean {
        if (root == null) return false
        val left = dfs(root.left, p, q)
        val right = dfs(root.right, p, q)
        if (left && right) {
            ans = root
        }
        return (left || right) || (root == p || root == q)
    }


    //前序遍历 preorder = [3,9,20,15,7]
    //中序遍历 inorder = [9,3,15,20,7]
    var cur = 0
    var pre = 0
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        return reDfs(preorder, inorder, Int.MIN_VALUE)
    }


    fun reDfs(preorder: IntArray, inorder: IntArray, stop: Int): TreeNode? {
        if (pre >= preorder.size) {
            return null
        }
        if (inorder[cur] == stop) {
            cur++
            return null
        }
        val treeNode = TreeNode(preorder[pre++])
        treeNode.left = reDfs(preorder, inorder, treeNode.`val`)
        treeNode.right = reDfs(preorder, inorder, stop)
        return treeNode
    }

    fun reDfs(list: MutableList<String>): TreeNode? {
        if (list[0] == "null") {
            list.removeAt(0)
            return null
        }
        val treeNode = TreeNode(list[0].toInt())
        list.removeAt(0)
        treeNode.left = reDfs(list)
        treeNode.right = reDfs(list)
        return treeNode
    }
}