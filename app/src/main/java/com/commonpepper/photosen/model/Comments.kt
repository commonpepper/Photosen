package com.commonpepper.photosen.model

class Comments {
    var comments: CommentsBean? = null
    var stat: String? = null

    class CommentsBean {
        var photo_id: String? = null
        var comment: List<CommentBean>? = null

        class CommentBean {
            var id: String? = null
            var author: String? = null
            var author_is_deleted = 0
            var authorname: String? = null
            var iconserver: String? = null
            var iconfarm = 0
            var datecreate: String? = null
            var permalink: String? = null
            var path_alias: String? = null
            var realname: String? = null
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