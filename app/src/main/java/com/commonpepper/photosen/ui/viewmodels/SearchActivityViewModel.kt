package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.HashSet

class SearchActivityViewModel : ViewModel() {
    val tags: HashSet<String> = HashSet()
    var queue: String? = null

    fun tagsToString(): String? {
        val set = tags
        if (set.isEmpty()) return null
        val ans = StringBuilder()
        for (tag in set) {
            ans.append(tag)
            ans.append(',')
        }
        return ans.toString().substring(0, ans.length - 1)
    }
}