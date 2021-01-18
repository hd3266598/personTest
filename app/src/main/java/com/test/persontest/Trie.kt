package com.test.persontest

class Trie() {

    /** Initialize your data structure here. */
    private val root = TrieNode()

    /** Inserts a word into the trie. */
    fun insert(word: String) {
        var node = root
        word.forEachIndexed { _, c ->
            if (!node.containsKey(c)) {
                node.put(c, TrieNode())
            }
            node.get(c)?.let {
                node = it
            }
        }
        node.setEnd()
    }

    /** Returns if the word is in the trie. */
    fun search(word: String): Boolean {
        val searchPrefix = searchPrefix(word)
        return searchPrefix != null && searchPrefix.isEnd()
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    fun startsWith(prefix: String): Boolean {
        return searchPrefix(prefix) != null
    }

    private fun searchPrefix(word: String): TrieNode? {
        var node = root
        word.forEach { it ->
            if (node.containsKey(it)) {
                node.get(it)?.let {
                    node = it
                }
            } else {
                return null
            }
        }
        return node
    }

    inner class TrieNode {
        private var links = Array<TrieNode?>(26) { null }

        private var isEnd: Boolean = false

        fun containsKey(ch: Char): Boolean {
            return links[ch - 'a'] != null
        }

        fun get(ch: Char): TrieNode? {
            return links[ch - 'a']
        }

        fun put(ch: Char, trieNode: TrieNode) {
            links[ch - 'a'] = trieNode
        }

        fun setEnd() {
            isEnd = true
        }

        fun isEnd(): Boolean {
            return isEnd
        }
    }

}