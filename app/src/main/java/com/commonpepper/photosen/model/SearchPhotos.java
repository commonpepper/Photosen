package com.commonpepper.photosen.model;

import java.util.List;

/**
 * GENERATED
 */
public class SearchPhotos {
    /**
     * photos : {"page":1,"pages":100,"perpage":10,"total":"1000","photo":[{"id":"32094189427","owner":"153684453@N08","secret":"1e63ba3d53","server":"7885","farm":8,"title":"IMG_7313.jpg","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713319","url_o":"https://farm8.staticflickr.com/7885/32094189427_e439559f60_o.jpg","height_o":"2848","width_o":"4272","url_z":"https://farm8.staticflickr.com/7885/32094189427_1e63ba3d53_z.jpg","height_z":"427","width_z":"640"},{"id":"32094189647","owner":"57841116@N03","secret":"7100275b47","server":"7863","farm":8,"title":"Torquay front beach","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713320","url_o":"https://farm8.staticflickr.com/7863/32094189647_3f35063d89_o.jpg","height_o":"1536","width_o":"2048","url_z":"https://farm8.staticflickr.com/7863/32094189647_7100275b47_z.jpg","height_z":"480","width_z":"640"},{"id":"33160689718","owner":"143059895@N02","secret":"05b61c3693","server":"7866","farm":8,"title":"Natur in den Alpen","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713317","url_o":"https://farm8.staticflickr.com/7866/33160689718_e00c127c5b_o.jpg","height_o":"1200","width_o":"1600","url_z":"https://farm8.staticflickr.com/7866/33160689718_05b61c3693_z.jpg","height_z":"480","width_z":"640"},{"id":"33160690538","owner":"163636778@N08","secret":"b4893a9df9","server":"7811","farm":8,"title":"IMG_20180813_152315","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7811/33160690538_f9402cf151_o.jpg","height_o":"2073","width_o":"1538","url_z":"https://farm8.staticflickr.com/7811/33160690538_b4893a9df9_z.jpg","height_z":"640","width_z":"475"},{"id":"33160690648","owner":"136985332@N07","secret":"9bc7e82a5f","server":"7877","farm":8,"title":"IMG_20190208_113048-01","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713323","url_o":"https://farm8.staticflickr.com/7877/33160690648_488b9b50fd_o.jpg","height_o":"4902","width_o":"3221","url_z":"https://farm8.staticflickr.com/7877/33160690648_9bc7e82a5f_z.jpg","height_z":"640","width_z":"421"},{"id":"46122188425","owner":"156327326@N08","secret":"e1dee495f0","server":"7915","farm":8,"title":"","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713319","url_o":"https://farm8.staticflickr.com/7915/46122188425_2a5c4a8a6a_o.jpg","height_o":"3024","width_o":"4032","url_z":"https://farm8.staticflickr.com/7915/46122188425_e1dee495f0_z.jpg","height_z":"480","width_z":"640"},{"id":"46122188665","owner":"156923452@N02","secret":"c4f1b41298","server":"7896","farm":8,"title":"East","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7896/46122188665_d15ea72200_o.jpg","height_o":"1080","width_o":"1920","url_z":"https://farm8.staticflickr.com/7896/46122188665_c4f1b41298_z.jpg","height_z":"360","width_z":"640"},{"id":"46311832664","owner":"163344444@N08","secret":"89c23a4157","server":"7858","farm":8,"title":"Rallye des Hautes Fagnes 1987","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713323","url_o":"https://farm8.staticflickr.com/7858/46311832664_9bc7e82a5f_o.jpg","height_o":"2386","width_o":"3600","url_z":"https://farm8.staticflickr.com/7858/46311832664_89c23a4157_z.jpg","height_z":"424","width_z":"640"},{"id":"46983871722","owner":"167627257@N08","secret":"c166cfb097","server":"7845","farm":8,"title":"","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7845/46983871722_cfa37ea506_o.jpg","height_o":"1567","width_o":"1045","url_z":"https://farm8.staticflickr.com/7845/46983871722_c166cfb097_z.jpg","height_z":"640","width_z":"427"},{"id":"47035873641","owner":"156015535@N03","secret":"9c770d21d3","server":"7865","farm":8,"title":"IMG-20190202-WA0067","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7865/47035873641_3e94fc20df_o.jpg","height_o":"750","width_o":"998","url_z":"https://farm8.staticflickr.com/7865/47035873641_9c770d21d3_z.jpg","height_z":"481","width_z":"640"}]}
     * stat : ok
     */

    private PhotosBean photos;
    private String stat;

    public PhotosBean getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosBean photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public static class PhotosBean {
        /**
         * page : 1
         * pages : 100
         * perpage : 10
         * total : 1000
         * photo : [{"id":"32094189427","owner":"153684453@N08","secret":"1e63ba3d53","server":"7885","farm":8,"title":"IMG_7313.jpg","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713319","url_o":"https://farm8.staticflickr.com/7885/32094189427_e439559f60_o.jpg","height_o":"2848","width_o":"4272","url_z":"https://farm8.staticflickr.com/7885/32094189427_1e63ba3d53_z.jpg","height_z":"427","width_z":"640"},{"id":"32094189647","owner":"57841116@N03","secret":"7100275b47","server":"7863","farm":8,"title":"Torquay front beach","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713320","url_o":"https://farm8.staticflickr.com/7863/32094189647_3f35063d89_o.jpg","height_o":"1536","width_o":"2048","url_z":"https://farm8.staticflickr.com/7863/32094189647_7100275b47_z.jpg","height_z":"480","width_z":"640"},{"id":"33160689718","owner":"143059895@N02","secret":"05b61c3693","server":"7866","farm":8,"title":"Natur in den Alpen","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713317","url_o":"https://farm8.staticflickr.com/7866/33160689718_e00c127c5b_o.jpg","height_o":"1200","width_o":"1600","url_z":"https://farm8.staticflickr.com/7866/33160689718_05b61c3693_z.jpg","height_z":"480","width_z":"640"},{"id":"33160690538","owner":"163636778@N08","secret":"b4893a9df9","server":"7811","farm":8,"title":"IMG_20180813_152315","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7811/33160690538_f9402cf151_o.jpg","height_o":"2073","width_o":"1538","url_z":"https://farm8.staticflickr.com/7811/33160690538_b4893a9df9_z.jpg","height_z":"640","width_z":"475"},{"id":"33160690648","owner":"136985332@N07","secret":"9bc7e82a5f","server":"7877","farm":8,"title":"IMG_20190208_113048-01","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713323","url_o":"https://farm8.staticflickr.com/7877/33160690648_488b9b50fd_o.jpg","height_o":"4902","width_o":"3221","url_z":"https://farm8.staticflickr.com/7877/33160690648_9bc7e82a5f_z.jpg","height_z":"640","width_z":"421"},{"id":"46122188425","owner":"156327326@N08","secret":"e1dee495f0","server":"7915","farm":8,"title":"","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713319","url_o":"https://farm8.staticflickr.com/7915/46122188425_2a5c4a8a6a_o.jpg","height_o":"3024","width_o":"4032","url_z":"https://farm8.staticflickr.com/7915/46122188425_e1dee495f0_z.jpg","height_z":"480","width_z":"640"},{"id":"46122188665","owner":"156923452@N02","secret":"c4f1b41298","server":"7896","farm":8,"title":"East","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7896/46122188665_d15ea72200_o.jpg","height_o":"1080","width_o":"1920","url_z":"https://farm8.staticflickr.com/7896/46122188665_c4f1b41298_z.jpg","height_z":"360","width_z":"640"},{"id":"46311832664","owner":"163344444@N08","secret":"89c23a4157","server":"7858","farm":8,"title":"Rallye des Hautes Fagnes 1987","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713323","url_o":"https://farm8.staticflickr.com/7858/46311832664_9bc7e82a5f_o.jpg","height_o":"2386","width_o":"3600","url_z":"https://farm8.staticflickr.com/7858/46311832664_89c23a4157_z.jpg","height_z":"424","width_z":"640"},{"id":"46983871722","owner":"167627257@N08","secret":"c166cfb097","server":"7845","farm":8,"title":"","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7845/46983871722_cfa37ea506_o.jpg","height_o":"1567","width_o":"1045","url_z":"https://farm8.staticflickr.com/7845/46983871722_c166cfb097_z.jpg","height_z":"640","width_z":"427"},{"id":"47035873641","owner":"156015535@N03","secret":"9c770d21d3","server":"7865","farm":8,"title":"IMG-20190202-WA0067","ispublic":1,"isfriend":0,"isfamily":0,"dateupload":"1549713322","url_o":"https://farm8.staticflickr.com/7865/47035873641_3e94fc20df_o.jpg","height_o":"750","width_o":"998","url_z":"https://farm8.staticflickr.com/7865/47035873641_9c770d21d3_z.jpg","height_z":"481","width_z":"640"}]
         */

        private int page;
        private int pages;
        private int perpage;
        private String total;
        private List<Photo> photo;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPerpage() {
            return perpage;
        }

        public void setPerpage(int perpage) {
            this.perpage = perpage;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<Photo> getPhoto() {
            return photo;
        }

        public void setPhoto(List<Photo> photo) {
            this.photo = photo;
        }
        
    }

    
}
