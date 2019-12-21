package com.commonpepper.photosen.model

class PhotoDetails {

    var photo: PhotoBean? = null
    var stat: String? = null

    class PhotoBean {
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

            var nsid: String? = null
            var username: String? = null
            var realname: String? = null
            var location: String? = null
            var iconserver: String? = null
            var iconfarm = 0
            var path_alias: String? = null

        }

        class TitleBean {
            private var _content: String? = null

            fun get_content(): String? {
                return _content
            }

            fun set_content(_content: String?) {
                this._content = _content
            }
        }

        class DescriptionBean {

            private var _content: String? = null

            fun get_content(): String? {
                return _content
            }

            fun set_content(_content: String?) {
                this._content = _content
            }
        }

        class VisibilityBean {

            var ispublic = 0
            var isfriend = 0
            var isfamily = 0

        }

        class DatesBean {

            var posted: String? = null
            var taken: String? = null
            var takengranularity = 0
            var takenunknown = 0
            var lastupdate: String? = null

        }

        class EditabilityBean {

            var cancomment = 0
            var canaddmeta = 0

        }

        class PubliceditabilityBean {

            var cancomment = 0
            var canaddmeta = 0

        }

        class UsageBean {

            var candownload = 0
            var canblog = 0
            var canprint = 0
            var canshare = 0

        }

        class CommentsBean {

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

            var haspeople = 0

        }

        class TagsBean {
            var tag: List<TagBean>? = null

            class TagBean {

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