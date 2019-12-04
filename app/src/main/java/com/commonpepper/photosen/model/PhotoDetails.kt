package com.commonpepper.photosen.model

class PhotoDetails {
    /**
     * photo : {"id":"33213028138","secret":"b119361ce4","server":"7840","farm":8,"dateuploaded":"1550111763","isfavorite":0,"license":0,"safety_level":0,"rotation":0,"owner":{"nsid":"110745934@N07","username":"nikunj.m.patel","realname":"","location":"","iconserver":"695","iconfarm":1,"path_alias":""},"title":{"_content":"Lesser Scaup"},"description":{"_content":""},"visibility":{"ispublic":1,"isfriend":0,"isfamily":0},"dates":{"posted":"1550111763","taken":"2019-02-13 21:34:06","takengranularity":0,"takenunknown":1,"lastupdate":"1550285861"},"views":"118206","editability":{"cancomment":1,"canaddmeta":0},"publiceditability":{"cancomment":1,"canaddmeta":0},"usage":{"candownload":0,"canblog":1,"canprint":0,"canshare":1},"comments":{"_content":74},"notes":{"note":[]},"people":{"haspeople":0},"tags":{"tag":[{"id":"110724604-33213028138-31942","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"Waterfowl","_content":"waterfowl","machine_tag":0},{"id":"110724604-33213028138-241","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"wild","_content":"wild","machine_tag":0},{"id":"110724604-33213028138-5833","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"wildlife","_content":"wildlife","machine_tag":0},{"id":"110724604-33213028138-791","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"nature","_content":"nature","machine_tag":0},{"id":"110724604-33213028138-1935","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"Photography","_content":"photography","machine_tag":0},{"id":"110724604-33213028138-8010","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"ducks","_content":"ducks","machine_tag":0},{"id":"110724604-33213028138-87701","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"scaup","_content":"scaup","machine_tag":0},{"id":"110724604-33213028138-287417","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"delmarva","_content":"delmarva","machine_tag":0},{"id":"110724604-33213028138-29622","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"migration","_content":"migration","machine_tag":0},{"id":"110724604-33213028138-201","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"winter","_content":"winter","machine_tag":0}]},"urls":{"url":[{"type":"photopage","_content":"https://www.flickr.com/photos/110745934@N07/33213028138/"}]},"media":"photo"}
     * stat : ok
     */

    var photo: PhotoBean? = null
    var stat: String? = null

    class PhotoBean {
        /**
         * id : 33213028138
         * secret : b119361ce4
         * server : 7840
         * farm : 8
         * dateuploaded : 1550111763
         * isfavorite : 0
         * license : 0
         * safety_level : 0
         * rotation : 0
         * owner : {"nsid":"110745934@N07","username":"nikunj.m.patel","realname":"","location":"","iconserver":"695","iconfarm":1,"path_alias":""}
         * title : {"_content":"Lesser Scaup"}
         * description : {"_content":""}
         * visibility : {"ispublic":1,"isfriend":0,"isfamily":0}
         * dates : {"posted":"1550111763","taken":"2019-02-13 21:34:06","takengranularity":0,"takenunknown":1,"lastupdate":"1550285861"}
         * views : 118206
         * editability : {"cancomment":1,"canaddmeta":0}
         * publiceditability : {"cancomment":1,"canaddmeta":0}
         * usage : {"candownload":0,"canblog":1,"canprint":0,"canshare":1}
         * comments : {"_content":74}
         * notes : {"note":[]}
         * people : {"haspeople":0}
         * tags : {"tag":[{"id":"110724604-33213028138-31942","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"Waterfowl","_content":"waterfowl","machine_tag":0},{"id":"110724604-33213028138-241","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"wild","_content":"wild","machine_tag":0},{"id":"110724604-33213028138-5833","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"wildlife","_content":"wildlife","machine_tag":0},{"id":"110724604-33213028138-791","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"nature","_content":"nature","machine_tag":0},{"id":"110724604-33213028138-1935","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"Photography","_content":"photography","machine_tag":0},{"id":"110724604-33213028138-8010","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"ducks","_content":"ducks","machine_tag":0},{"id":"110724604-33213028138-87701","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"scaup","_content":"scaup","machine_tag":0},{"id":"110724604-33213028138-287417","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"delmarva","_content":"delmarva","machine_tag":0},{"id":"110724604-33213028138-29622","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"migration","_content":"migration","machine_tag":0},{"id":"110724604-33213028138-201","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"winter","_content":"winter","machine_tag":0}]}
         * urls : {"url":[{"type":"photopage","_content":"https://www.flickr.com/photos/110745934@N07/33213028138/"}]}
         * media : photo
         */

        var id: String? = null
        var secret: String? = null
        var server: String? = null
        var farm = 0
        var dateuploaded: String? = null
        var isfavorite = 0
        var license = 0
        var safety_level = 0
        var rotation = 0
        var owner: OwnerBean? = null
        var title: TitleBean? = null
        var description: DescriptionBean? = null
        var visibility: VisibilityBean? = null
        var dates: DatesBean? = null
        var views = 0
        var editability: EditabilityBean? = null
        var publiceditability: PubliceditabilityBean? = null
        var usage: UsageBean? = null
        var comments: CommentsBean? = null
        var notes: NotesBean? = null
        var people: PeopleBean? = null
        var tags: TagsBean? = null
        var urls: UrlsBean? = null
        var media: String? = null

        class OwnerBean {
            /**
             * nsid : 110745934@N07
             * username : nikunj.m.patel
             * realname :
             * location :
             * iconserver : 695
             * iconfarm : 1
             * path_alias :
             */

            var nsid: String? = null
            var username: String? = null
            var realname: String? = null
            var location: String? = null
            var iconserver: String? = null
            var iconfarm = 0
            var path_alias: String? = null

        }

        class TitleBean {
            /**
             * _content : Lesser Scaup
             */

            private var _content: String? = null

            fun get_content(): String? {
                return _content
            }

            fun set_content(_content: String?) {
                this._content = _content
            }
        }

        class DescriptionBean {
            /**
             * _content :
             */

            private var _content: String? = null

            fun get_content(): String? {
                return _content
            }

            fun set_content(_content: String?) {
                this._content = _content
            }
        }

        class VisibilityBean {
            /**
             * ispublic : 1
             * isfriend : 0
             * isfamily : 0
             */

            var ispublic = 0
            var isfriend = 0
            var isfamily = 0

        }

        class DatesBean {
            /**
             * posted : 1550111763
             * taken : 2019-02-13 21:34:06
             * takengranularity : 0
             * takenunknown : 1
             * lastupdate : 1550285861
             */

            var posted: String? = null
            var taken: String? = null
            var takengranularity = 0
            var takenunknown = 0
            var lastupdate: String? = null

        }

        class EditabilityBean {
            /**
             * cancomment : 1
             * canaddmeta : 0
             */

            var cancomment = 0
            var canaddmeta = 0

        }

        class PubliceditabilityBean {
            /**
             * cancomment : 1
             * canaddmeta : 0
             */

            var cancomment = 0
            var canaddmeta = 0

        }

        class UsageBean {
            /**
             * candownload : 0
             * canblog : 1
             * canprint : 0
             * canshare : 1
             */

            var candownload = 0
            var canblog = 0
            var canprint = 0
            var canshare = 0

        }

        class CommentsBean {
            /**
             * _content : 74
             */

            private var _content = 0

            fun get_content(): Int {
                return _content
            }

            fun set_content(_content: Int) {
                this._content = _content
            }
        }

        class NotesBean {
            var note: List<*>? = null

        }

        class PeopleBean {
            /**
             * haspeople : 0
             */

            var haspeople = 0

        }

        class TagsBean {
            var tag: List<TagBean>? = null

            class TagBean {
                /**
                 * id : 110724604-33213028138-31942
                 * author : 110745934@N07
                 * authorname : nikunj.m.patel
                 * raw : Waterfowl
                 * _content : waterfowl
                 * machine_tag : 0
                 */

                var id: String? = null
                var author: String? = null
                var authorname: String? = null
                var raw: String? = null
                private var _content: String? = null

                fun get_content(): String? {
                    return _content
                }

                fun set_content(_content: String?) {
                    this._content = _content
                }
            }
        }

        class UrlsBean {
            var url: List<UrlBean>? = null

            class UrlBean {
                /**
                 * type : photopage
                 * _content : https://www.flickr.com/photos/110745934@N07/33213028138/
                 */

                var type: String? = null
                private var _content: String? = null

                fun get_content(): String? {
                    return _content
                }

                fun set_content(_content: String?) {
                    this._content = _content
                }
            }
        }
    }
}