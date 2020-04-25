package com.commonpepper.photosen.model

class PhotoSizes {

    var sizes: SizesBean? = null
    var stat: String? = null

    class SizesBean {

        var canblog = false
        var canprint = false
        var candownload = false
        var size: List<SizeBean>? = null

        class SizeBean {

            var label: String? = null
            var width = 0
            var height = 0
            var source: String? = null
            var url: String? = null
            var media: String? = null

        }
    }
}