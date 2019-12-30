package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.HashSet

class SearchActivityViewModel : ViewModel() {
    val tags: HashSet<String> = HashSet()
    var query: String? = null
}