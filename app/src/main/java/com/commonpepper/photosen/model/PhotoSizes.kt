package com.commonpepper.photosen.model

class PhotoSizes {

    /**
     * sizes : {"canblog":1,"canprint":0,"candownload":0,"size":[{"label":"Square","width":75,"height":75,"source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_s.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/sq/","media":"photo"},{"label":"Large Square","width":"150","height":"150","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_q.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/q/","media":"photo"},{"label":"Thumbnail","width":100,"height":88,"source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_t.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/t/","media":"photo"},{"label":"Small","width":"240","height":"211","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_m.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/s/","media":"photo"},{"label":"Small 320","width":"320","height":"281","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_n.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/n/","media":"photo"},{"label":"Medium","width":"500","height":"439","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/m/","media":"photo"},{"label":"Medium 640","width":"640","height":"563","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_z.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/z/","media":"photo"},{"label":"Medium 800","width":"800","height":"703","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_c.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/c/","media":"photo"},{"label":"Large","width":"1024","height":"900","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_b.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/l/","media":"photo"},{"label":"Large 1600","width":"1600","height":"1407","source":"https://farm8.staticflickr.com/7813/40151768093_ae65e1468b_h.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/h/","media":"photo"},{"label":"Large 2048","width":"2048","height":"1801","source":"https://farm8.staticflickr.com/7813/40151768093_bdc4922d30_k.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/k/","media":"photo"}]}
     * stat : ok
     */

    var sizes: SizesBean? = null
    var stat: String? = null

    class SizesBean {
        /**
         * canblog : 1
         * canprint : 0
         * candownload : 0
         * size : [{"label":"Square","width":75,"height":75,"source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_s.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/sq/","media":"photo"},{"label":"Large Square","width":"150","height":"150","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_q.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/q/","media":"photo"},{"label":"Thumbnail","width":100,"height":88,"source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_t.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/t/","media":"photo"},{"label":"Small","width":"240","height":"211","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_m.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/s/","media":"photo"},{"label":"Small 320","width":"320","height":"281","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_n.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/n/","media":"photo"},{"label":"Medium","width":"500","height":"439","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/m/","media":"photo"},{"label":"Medium 640","width":"640","height":"563","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_z.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/z/","media":"photo"},{"label":"Medium 800","width":"800","height":"703","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_c.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/c/","media":"photo"},{"label":"Large","width":"1024","height":"900","source":"https://farm8.staticflickr.com/7813/40151768093_029412b17d_b.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/l/","media":"photo"},{"label":"Large 1600","width":"1600","height":"1407","source":"https://farm8.staticflickr.com/7813/40151768093_ae65e1468b_h.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/h/","media":"photo"},{"label":"Large 2048","width":"2048","height":"1801","source":"https://farm8.staticflickr.com/7813/40151768093_bdc4922d30_k.jpg","url":"https://www.flickr.com/photos/88974471@N05/40151768093/sizes/k/","media":"photo"}]
         */

        var canblog: Int = 0
        var canprint: Int = 0
        var candownload: Int = 0
        var size: List<SizeBean>? = null

        class SizeBean {
            /**
             * label : Square
             * width : 75
             * height : 75
             * source : https://farm8.staticflickr.com/7813/40151768093_029412b17d_s.jpg
             * url : https://www.flickr.com/photos/88974471@N05/40151768093/sizes/sq/
             * media : photo
             */

            var label: String? = null
            var width: Int = 0
            var height: Int = 0
            var source: String? = null
            var url: String? = null
            var media: String? = null
        }
    }
}
