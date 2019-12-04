package com.commonpepper.photosen.model

class PhotoSizes {

    var sizes: SizesBean? = null
    var stat: String? = null

    class SizesBean {

        var canblog = 0
        var canprint = 0
        var candownload = 0
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