package com.test.persontest

class CBTInserter(root: TreeNode?) {
    val list = arrayListOf<TreeNode?>()

    init {
        list.add(root)
        var cur = 0
        while (cur < list.size) {
            val node = list[cur]
            if (node?.left != null) list.add(node.left)
            if (node?.right != null) list.add(node.right)
            cur++
        }
    }

    var id = 0

    fun insert(`val`: Int): Int {
        val treeNode = TreeNode(`val`)
        while (list[id]?.left != null && list[id]?.right != null) id++
        val fa = list[id]
        if (fa?.left == null) {
            fa?.left = treeNode
        } else if (fa.right == null) {
            fa.right = treeNode
        }
        list.add(treeNode)
        return fa?.`val` ?: 0
    }

    fun get_root(): TreeNode? {
        return list.firstOrNull()
    }

}
