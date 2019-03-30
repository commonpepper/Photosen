package com.commonpepper.photosen.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * GENERATED
 */
public class Photo implements Parcelable {

    public static DiffUtil.ItemCallback<Photo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Photo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Photo) {
            Photo article = (Photo) obj;
            return article.id.equals(this.id);
        } else return false;
    }

    public String getIconUrl() {
        if (Integer.parseInt(iconserver) > 0) {
            return "http://farm" + iconfarm + ".staticflickr.com/"
                    + iconserver + "/buddyicons/"
                    + owner + ".jpg";
        } else {
            return "https://www.flickr.com/images/buddyicon.jpg";
        }
    }

    public boolean isSexuallyExplicit() {
        String[] explicit = Photosen.getInstance().getResources().getStringArray(R.array.sexually_explicit_content_words);
        for (String str : explicit) {
            if (tags.toLowerCase().contains(str)) {
                Log.d("EXPLICIT tags:", tags.toLowerCase() + " CONTAINS " + str);
                return true;
            }
        }
        for (String str : explicit) {
            if (title.toLowerCase().contains(str)) {
                Log.d("EXPLICIT title:", title.toLowerCase() + " CONTAINS " + str);
                return true;
            }
        }
        return false;
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

    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;
    private String datetaken;
    private String iconserver;
    private int iconfarm;
    private String originalsecret;
    private String originalformat;
    private String url_o;
    private int height_o;
    private int width_o;
    private String url_z;
    private int height_z;
    private int width_z;
    private String owner_name;
    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public String getDatetaken() {
        return datetaken;
    }

    public void setDatetaken(String datetaken) {
        this.datetaken = datetaken;
    }

    public String getIconserver() {
        return iconserver;
    }

    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }

    public int getIconfarm() {
        return iconfarm;
    }

    public void setIconfarm(int iconfarm) {
        this.iconfarm = iconfarm;
    }

    public String getOriginalsecret() {
        return originalsecret;
    }

    public void setOriginalsecret(String originalsecret) {
        this.originalsecret = originalsecret;
    }

    public String getOriginalformat() {
        return originalformat;
    }

    public void setOriginalformat(String originalformat) {
        this.originalformat = originalformat;
    }

    public String getUrl_o() {
        return url_o;
    }

    public void setUrl_o(String url_o) {
        this.url_o = url_o;
    }

    public int getHeight_o() {
        return height_o;
    }

    public void setHeight_o(int height_o) {
        this.height_o = height_o;
    }

    public int getWidth_o() {
        return width_o;
    }

    public void setWidth_o(int width_o) {
        this.width_o = width_o;
    }

    public String getUrl_z() {
        if (url_z == null) {
            return "https://farm" + getFarm() + ".staticflickr.com/" + getServer()
                    + "/" + getId() + "_" + getSecret() + ".jpg";
        }
        return url_z;
    }

    public void setUrl_z(String url_z) {
        this.url_z = url_z;
    }

    public int getHeight_z() {
        return height_z;
    }

    public void setHeight_z(int height_z) {
        this.height_z = height_z;
    }

    public int getWidth_z() {
        return width_z;
    }

    public void setWidth_z(int width_z) {
        this.width_z = width_z;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.owner);
        dest.writeString(this.secret);
        dest.writeString(this.server);
        dest.writeInt(this.farm);
        dest.writeString(this.title);
        dest.writeInt(this.ispublic);
        dest.writeInt(this.isfriend);
        dest.writeInt(this.isfamily);
        dest.writeString(this.datetaken);
        dest.writeString(this.iconserver);
        dest.writeInt(this.iconfarm);
        dest.writeString(this.originalsecret);
        dest.writeString(this.originalformat);
        dest.writeString(this.url_o);
        dest.writeInt(this.height_o);
        dest.writeInt(this.width_o);
        dest.writeString(this.url_z);
        dest.writeInt(this.height_z);
        dest.writeInt(this.width_z);
        dest.writeString(this.owner_name);
        dest.writeString(this.tags);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = in.readInt();
        this.title = in.readString();
        this.ispublic = in.readInt();
        this.isfriend = in.readInt();
        this.isfamily = in.readInt();
        this.datetaken = in.readString();
        this.iconserver = in.readString();
        this.iconfarm = in.readInt();
        this.originalsecret = in.readString();
        this.originalformat = in.readString();
        this.url_o = in.readString();
        this.height_o = in.readInt();
        this.width_o = in.readInt();
        this.url_z = in.readString();
        this.height_z = in.readInt();
        this.width_z = in.readInt();
        this.owner_name = in.readString();
        this.tags = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
