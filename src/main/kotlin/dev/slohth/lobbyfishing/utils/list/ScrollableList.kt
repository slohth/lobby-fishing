package dev.slohth.lobbyfishing.utils.list

import java.util.*

class ScrollableList<T> {

    val linkedList: LinkedList<T> = LinkedList()

    fun getContents(scroll: Int, step: Int): LinkedList<T> {
        val l: LinkedList<T> = LinkedList()
        val start = scroll * step

        for (i in start..(start + step)) {
            if (i < linkedList.size) l.add(linkedList[i])
        }
        return l
    }

    fun hasNextScroll(scroll: Int, step: Int): Boolean = getContents(scroll, step).size == step

    fun hasPreviousScroll(scroll: Int, step: Int): Boolean {
        val contents = getContents(scroll, step)
        val first = getContents(0, step)

        if (contents.size != first.size) return true
        for (i in 0 until contents.size) {
            if (contents[i] != first[i]) return true
        }
        return false
    }

}