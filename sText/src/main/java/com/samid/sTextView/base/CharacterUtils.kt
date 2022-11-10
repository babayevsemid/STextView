package com.samid.stextview.base

object CharacterUtils {
    fun diff(oldText: CharSequence, newText: CharSequence): List<CharacterDiffResult> {
        val differentList: MutableList<CharacterDiffResult> = ArrayList()

        val skip: MutableSet<Int> = HashSet()

        for (i in oldText.indices) {
            val c = oldText[i]

            for (j in newText.indices) {
                if (!skip.contains(j) && c == newText[j]) {
                    skip.add(j)
                    val different = CharacterDiffResult()
                    different.c = c
                    different.fromIndex = i
                    different.moveIndex = j
                    differentList.add(different)
                    break
                }
            }
        }
        return differentList
    }

    fun needMove(index: Int, differentList: List<CharacterDiffResult>): Int {
        for (different in differentList) {
            if (different.fromIndex == index) {
                return different.moveIndex
            }
        }
        return -1
    }

    fun stayHere(index: Int, differentList: List<CharacterDiffResult>): Boolean {
        for (different in differentList) {
            if (different.moveIndex == index) {
                return true
            }
        }
        return false
    }

    fun getOffset(
        from: Int, move: Int, progress: Float, startX: Float, oldStartX: Float,
        gaps: List<Float>, oldGaps: List<Float>
    ): Float {
        var dist = startX

        for (i in 0 until move) {
            dist += gaps[i]
        }

        var cur = oldStartX

        for (i in 0 until from) {
            cur += oldGaps[i]
        }

        return cur + (dist - cur) * progress
    }
}