package com.commonpepper.photosen.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.R
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * GENERATED
 */

@Entity
class Photo : Parcelable {

    val iconUrl: String
        get() {
            if (Integer.parseInt(iconserver!!) > 0) {
                Log.d("Avatar:", "https://farm" + iconfarm + ".staticflickr.com/"
                        + iconserver + "/buddyicons/"
                        + owner + ".jpg")
                return ("https://farm" + iconfarm + ".staticflickr.com/"
                        + iconserver + "/buddyicons/"
                        + owner + ".jpg")
            } else {
                return "https://www.flickr.com/images/buddyicon.jpg"
            }
        }

    val isSexuallyExplicit: Boolean
        get() {
            val explicit = Photosen.instance!!.resources.getStringArray(R.array.sexually_explicit_content_words)
            for (str in explicit) {
                if (tags!!.toLowerCase().contains(str)) {
                    Log.d("EXPLICIT tags:", tags!!.toLowerCase() + " CONTAINS " + str)
                    return true
                }
            }
            for (str in explicit) {
                if (title!!.toLowerCase().contains(str)) {
                    Log.d("EXPLICIT title:", title!!.toLowerCase() + " CONTAINS " + str)
                    return true
                }
            }
            return false
        }

    /**
     * id : 47038014201
     * owner : 126497846@N03
     * secret : a2e6e6012f
     * server : 7845
     * farm : 8
     * title : HDS-15.jpg
     * ispublic : 1
     * isfriend : 0
     * isfamily : 0
     * dateupload : 1549728453
     * iconserver : 3901
     * iconfarm : 4
     * originalsecret : 333c48f1ae
     * originalformat : jpg
     * url_o : https://farm8.staticflickr.com/7845/47038014201_333c48f1ae_o.jpg
     * height_o : 3448
     * width_o : 5168
     * url_z : https://farm8.staticflickr.com/7845/47038014201_a2e6e6012f_z.jpg
     * height_z : 427
     * width_z : 640
     * + tags
     */


    //for database history
    @PrimaryKey(autoGenerate = true)
    var primarykey: Int = 0
    var time: Long = 0

    var id: String? = null
    var owner: String? = null
    var secret: String? = null
    var server: String? = null
    var farm: Int = 0
    var title: String? = null
    var ispublic: Int = 0
    var isfriend: Int = 0
    var isfamily: Int = 0
    var datetaken: String? = null
    var iconserver: String? = null
    var iconfarm: Int = 0
    var originalsecret: String? = null
    var originalformat: String? = null
    var url_o: String? = null
    var height_o: Int = 0
    var width_o: Int = 0
    private var url_z: String? = null
    var height_z: Int = 0
    var width_z: Int = 0
    var owner_name: String? = null
    var tags: String? = null

    override fun equals(obj: Any?): Boolean {
        if (obj === this) return true
        if (obj is Photo) {
            val article = obj as Photo?
            return article!!.id == this.id
        } else
            return false
    }

    fun getUrl_z(): String {
        return url_z ?: ("https://farm" + farm + ".staticflickr.com/" + server
                        + "/" + id + "_" + secret + ".jpg")
    }

    fun setUrl_z(url_z: String) {
        this.url_z = url_z
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.id)
        dest.writeString(this.owner)
        dest.writeString(this.secret)
        dest.writeString(this.server)
        dest.writeInt(this.farm)
        dest.writeString(this.title)
        dest.writeInt(this.ispublic)
        dest.writeInt(this.isfriend)
        dest.writeInt(this.isfamily)
        dest.writeString(this.datetaken)
        dest.writeString(this.iconserver)
        dest.writeInt(this.iconfarm)
        dest.writeString(this.originalsecret)
        dest.writeString(this.originalformat)
        dest.writeString(this.url_o)
        dest.writeInt(this.height_o)
        dest.writeInt(this.width_o)
        dest.writeString(this.url_z)
        dest.writeInt(this.height_z)
        dest.writeInt(this.width_z)
        dest.writeString(this.owner_name)
        dest.writeString(this.tags)
    }

    constructor()

    protected constructor(`in`: Parcel) {
        this.id = `in`.readString()
        this.owner = `in`.readString()
        this.secret = `in`.readString()
        this.server = `in`.readString()
        this.farm = `in`.readInt()
        this.title = `in`.readString()
        this.ispublic = `in`.readInt()
        this.isfriend = `in`.readInt()
        this.isfamily = `in`.readInt()
        this.datetaken = `in`.readString()
        this.iconserver = `in`.readString()
        this.iconfarm = `in`.readInt()
        this.originalsecret = `in`.readString()
        this.originalformat = `in`.readString()
        this.url_o = `in`.readString()
        this.height_o = `in`.readInt()
        this.width_o = `in`.readInt()
        this.url_z = `in`.readString()
        this.height_z = `in`.readInt()
        this.width_z = `in`.readInt()
        this.owner_name = `in`.readString()
        this.tags = `in`.readString()
    }

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Photo> = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }

        @JvmField
        var CREATOR: Parcelable.Creator<Photo> = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(source: Parcel): Photo {
                return Photo(source)
            }

            override fun newArray(size: Int): Array<Photo?> {
                return arrayOfNulls(size)
            }
        }
    }
}
