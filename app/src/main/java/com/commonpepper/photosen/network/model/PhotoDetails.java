package com.commonpepper.photosen.network.model;

import java.util.List;

public class PhotoDetails {

    /**
     * photo : {"id":"33213028138","secret":"b119361ce4","server":"7840","farm":8,"dateuploaded":"1550111763","isfavorite":0,"license":0,"safety_level":0,"rotation":0,"owner":{"nsid":"110745934@N07","username":"nikunj.m.patel","realname":"","location":"","iconserver":"695","iconfarm":1,"path_alias":""},"title":{"_content":"Lesser Scaup"},"description":{"_content":""},"visibility":{"ispublic":1,"isfriend":0,"isfamily":0},"dates":{"posted":"1550111763","taken":"2019-02-13 21:34:06","takengranularity":0,"takenunknown":1,"lastupdate":"1550285861"},"views":"118206","editability":{"cancomment":1,"canaddmeta":0},"publiceditability":{"cancomment":1,"canaddmeta":0},"usage":{"candownload":0,"canblog":1,"canprint":0,"canshare":1},"comments":{"_content":74},"notes":{"note":[]},"people":{"haspeople":0},"tags":{"tag":[{"id":"110724604-33213028138-31942","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"Waterfowl","_content":"waterfowl","machine_tag":0},{"id":"110724604-33213028138-241","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"wild","_content":"wild","machine_tag":0},{"id":"110724604-33213028138-5833","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"wildlife","_content":"wildlife","machine_tag":0},{"id":"110724604-33213028138-791","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"nature","_content":"nature","machine_tag":0},{"id":"110724604-33213028138-1935","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"Photography","_content":"photography","machine_tag":0},{"id":"110724604-33213028138-8010","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"ducks","_content":"ducks","machine_tag":0},{"id":"110724604-33213028138-87701","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"scaup","_content":"scaup","machine_tag":0},{"id":"110724604-33213028138-287417","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"delmarva","_content":"delmarva","machine_tag":0},{"id":"110724604-33213028138-29622","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"migration","_content":"migration","machine_tag":0},{"id":"110724604-33213028138-201","author":"110745934@N07","authorname":"nikunj.m.patel","raw":"winter","_content":"winter","machine_tag":0}]},"urls":{"url":[{"type":"photopage","_content":"https://www.flickr.com/photos/110745934@N07/33213028138/"}]},"media":"photo"}
     * stat : ok
     */

    private PhotoBean photo;
    private String stat;

    public PhotoBean getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoBean photo) {
        this.photo = photo;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public static class PhotoBean {
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

        private String id;
        private String secret;
        private String server;
        private int farm;
        private String dateuploaded;
        private int isfavorite;
        private int license;
        private int safety_level;
        private int rotation;
        private OwnerBean owner;
        private TitleBean title;
        private DescriptionBean description;
        private VisibilityBean visibility;
        private DatesBean dates;
        private int views;
        private EditabilityBean editability;
        private PubliceditabilityBean publiceditability;
        private UsageBean usage;
        private CommentsBean comments;
        private NotesBean notes;
        private PeopleBean people;
        private TagsBean tags;
        private UrlsBean urls;
        private String media;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getDateuploaded() {
            return dateuploaded;
        }

        public void setDateuploaded(String dateuploaded) {
            this.dateuploaded = dateuploaded;
        }

        public int getIsfavorite() {
            return isfavorite;
        }

        public void setIsfavorite(int isfavorite) {
            this.isfavorite = isfavorite;
        }

        public int getLicense() {
            return license;
        }

        public void setLicense(int license) {
            this.license = license;
        }

        public int getSafety_level() {
            return safety_level;
        }

        public void setSafety_level(int safety_level) {
            this.safety_level = safety_level;
        }

        public int getRotation() {
            return rotation;
        }

        public void setRotation(int rotation) {
            this.rotation = rotation;
        }

        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
        }

        public TitleBean getTitle() {
            return title;
        }

        public void setTitle(TitleBean title) {
            this.title = title;
        }

        public DescriptionBean getDescription() {
            return description;
        }

        public void setDescription(DescriptionBean description) {
            this.description = description;
        }

        public VisibilityBean getVisibility() {
            return visibility;
        }

        public void setVisibility(VisibilityBean visibility) {
            this.visibility = visibility;
        }

        public DatesBean getDates() {
            return dates;
        }

        public void setDates(DatesBean dates) {
            this.dates = dates;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public EditabilityBean getEditability() {
            return editability;
        }

        public void setEditability(EditabilityBean editability) {
            this.editability = editability;
        }

        public PubliceditabilityBean getPubliceditability() {
            return publiceditability;
        }

        public void setPubliceditability(PubliceditabilityBean publiceditability) {
            this.publiceditability = publiceditability;
        }

        public UsageBean getUsage() {
            return usage;
        }

        public void setUsage(UsageBean usage) {
            this.usage = usage;
        }

        public CommentsBean getComments() {
            return comments;
        }

        public void setComments(CommentsBean comments) {
            this.comments = comments;
        }

        public NotesBean getNotes() {
            return notes;
        }

        public void setNotes(NotesBean notes) {
            this.notes = notes;
        }

        public PeopleBean getPeople() {
            return people;
        }

        public void setPeople(PeopleBean people) {
            this.people = people;
        }

        public TagsBean getTags() {
            return tags;
        }

        public void setTags(TagsBean tags) {
            this.tags = tags;
        }

        public UrlsBean getUrls() {
            return urls;
        }

        public void setUrls(UrlsBean urls) {
            this.urls = urls;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public static class OwnerBean {
            /**
             * nsid : 110745934@N07
             * username : nikunj.m.patel
             * realname :
             * location :
             * iconserver : 695
             * iconfarm : 1
             * path_alias :
             */

            private String nsid;
            private String username;
            private String realname;
            private String location;
            private String iconserver;
            private int iconfarm;
            private String path_alias;

            public String getNsid() {
                return nsid;
            }

            public void setNsid(String nsid) {
                this.nsid = nsid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
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

            public String getPath_alias() {
                return path_alias;
            }

            public void setPath_alias(String path_alias) {
                this.path_alias = path_alias;
            }
        }

        public static class TitleBean {
            /**
             * _content : Lesser Scaup
             */

            private String _content;

            public String get_content() {
                return _content;
            }

            public void set_content(String _content) {
                this._content = _content;
            }
        }

        public static class DescriptionBean {
            /**
             * _content :
             */

            private String _content;

            public String get_content() {
                return _content;
            }

            public void set_content(String _content) {
                this._content = _content;
            }
        }

        public static class VisibilityBean {
            /**
             * ispublic : 1
             * isfriend : 0
             * isfamily : 0
             */

            private int ispublic;
            private int isfriend;
            private int isfamily;

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
        }

        public static class DatesBean {
            /**
             * posted : 1550111763
             * taken : 2019-02-13 21:34:06
             * takengranularity : 0
             * takenunknown : 1
             * lastupdate : 1550285861
             */

            private String posted;
            private String taken;
            private int takengranularity;
            private int takenunknown;
            private String lastupdate;

            public String getPosted() {
                return posted;
            }

            public void setPosted(String posted) {
                this.posted = posted;
            }

            public String getTaken() {
                return taken;
            }

            public void setTaken(String taken) {
                this.taken = taken;
            }

            public int getTakengranularity() {
                return takengranularity;
            }

            public void setTakengranularity(int takengranularity) {
                this.takengranularity = takengranularity;
            }

            public int getTakenunknown() {
                return takenunknown;
            }

            public void setTakenunknown(int takenunknown) {
                this.takenunknown = takenunknown;
            }

            public String getLastupdate() {
                return lastupdate;
            }

            public void setLastupdate(String lastupdate) {
                this.lastupdate = lastupdate;
            }
        }

        public static class EditabilityBean {
            /**
             * cancomment : 1
             * canaddmeta : 0
             */

            private int cancomment;
            private int canaddmeta;

            public int getCancomment() {
                return cancomment;
            }

            public void setCancomment(int cancomment) {
                this.cancomment = cancomment;
            }

            public int getCanaddmeta() {
                return canaddmeta;
            }

            public void setCanaddmeta(int canaddmeta) {
                this.canaddmeta = canaddmeta;
            }
        }

        public static class PubliceditabilityBean {
            /**
             * cancomment : 1
             * canaddmeta : 0
             */

            private int cancomment;
            private int canaddmeta;

            public int getCancomment() {
                return cancomment;
            }

            public void setCancomment(int cancomment) {
                this.cancomment = cancomment;
            }

            public int getCanaddmeta() {
                return canaddmeta;
            }

            public void setCanaddmeta(int canaddmeta) {
                this.canaddmeta = canaddmeta;
            }
        }

        public static class UsageBean {
            /**
             * candownload : 0
             * canblog : 1
             * canprint : 0
             * canshare : 1
             */

            private int candownload;
            private int canblog;
            private int canprint;
            private int canshare;

            public int getCandownload() {
                return candownload;
            }

            public void setCandownload(int candownload) {
                this.candownload = candownload;
            }

            public int getCanblog() {
                return canblog;
            }

            public void setCanblog(int canblog) {
                this.canblog = canblog;
            }

            public int getCanprint() {
                return canprint;
            }

            public void setCanprint(int canprint) {
                this.canprint = canprint;
            }

            public int getCanshare() {
                return canshare;
            }

            public void setCanshare(int canshare) {
                this.canshare = canshare;
            }
        }

        public static class CommentsBean {
            /**
             * _content : 74
             */

            private int _content;

            public int get_content() {
                return _content;
            }

            public void set_content(int _content) {
                this._content = _content;
            }
        }

        public static class NotesBean {
            private List<?> note;

            public List<?> getNote() {
                return note;
            }

            public void setNote(List<?> note) {
                this.note = note;
            }
        }

        public static class PeopleBean {
            /**
             * haspeople : 0
             */

            private int haspeople;

            public int getHaspeople() {
                return haspeople;
            }

            public void setHaspeople(int haspeople) {
                this.haspeople = haspeople;
            }
        }

        public static class TagsBean {
            private List<TagBean> tag;

            public List<TagBean> getTag() {
                return tag;
            }

            public void setTag(List<TagBean> tag) {
                this.tag = tag;
            }

            public static class TagBean {
                /**
                 * id : 110724604-33213028138-31942
                 * author : 110745934@N07
                 * authorname : nikunj.m.patel
                 * raw : Waterfowl
                 * _content : waterfowl
                 * machine_tag : 0
                 */

                private String id;
                private String author;
                private String authorname;
                private String raw;
                private String _content;
                private int machine_tag;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public String getAuthorname() {
                    return authorname;
                }

                public void setAuthorname(String authorname) {
                    this.authorname = authorname;
                }

                public String getRaw() {
                    return raw;
                }

                public void setRaw(String raw) {
                    this.raw = raw;
                }

                public String get_content() {
                    return _content;
                }

                public void set_content(String _content) {
                    this._content = _content;
                }

                public int getMachine_tag() {
                    return machine_tag;
                }

                public void setMachine_tag(int machine_tag) {
                    this.machine_tag = machine_tag;
                }
            }
        }

        public static class UrlsBean {
            private List<UrlBean> url;

            public List<UrlBean> getUrl() {
                return url;
            }

            public void setUrl(List<UrlBean> url) {
                this.url = url;
            }

            public static class UrlBean {
                /**
                 * type : photopage
                 * _content : https://www.flickr.com/photos/110745934@N07/33213028138/
                 */

                private String type;
                private String _content;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String get_content() {
                    return _content;
                }

                public void set_content(String _content) {
                    this._content = _content;
                }
            }
        }
    }
}
