package com.commonpepper.photosen.network.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class Comments {

    /**
     * comments : {"photo_id":"46982335834","comment":[{"id":"55843683-46982335834-72157678075007337","author":"55849023@N05","author_is_deleted":0,"authorname":"jojesari","iconserver":"7301","iconfarm":8,"datecreate":"1556975891","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157678075007337","path_alias":"jojesari","realname":"Jose Jesus Sabaris Rico","_content":"."},{"id":"55843683-46982335834-72157708401403574","author":"135583184@N08","author_is_deleted":0,"authorname":"Celtarro","iconserver":"5792","iconfarm":6,"datecreate":"1556976357","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708401403574","path_alias":"","realname":"","_content":"Bo ceo,e ese arco de pedras coa espuma,un saudo"},{"id":"55843683-46982335834-72157678075134557","author":"50279554@N00","author_is_deleted":0,"authorname":"* Rhonda *","iconserver":"7414","iconfarm":8,"datecreate":"1556976494","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157678075134557","path_alias":"_rhonda_","realname":"","_content":"Gorgeous"},{"id":"55843683-46982335834-72157704930443162","author":"64942999@N02","author_is_deleted":0,"authorname":"JB.Photography2011","iconserver":"3925","iconfarm":4,"datecreate":"1556977438","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157704930443162","path_alias":"","realname":"John Borg","_content":"Superb capture and lovely details"},{"id":"55843683-46982335834-72157680233151258","author":"46759384@N03","author_is_deleted":0,"authorname":"rroel58","iconserver":"7026","iconfarm":8,"datecreate":"1556977817","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680233151258","path_alias":"","realname":"","_content":"Una pura maravilla.\nEstás que te sales José Jesús. \nDe fábula."},{"id":"55843683-46982335834-72157708402230624","author":"42597241@N04","author_is_deleted":0,"authorname":"ferfer2009","iconserver":"4052","iconfarm":5,"datecreate":"1556979265","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708402230624","path_alias":"","realname":"","_content":"Madre mía... Virgen del amor hermoso... Vaya foto bonita nos regalas hoy... Una delicatessen...."},{"id":"55843683-46982335834-72157708402242304","author":"42597241@N04","author_is_deleted":0,"authorname":"ferfer2009","iconserver":"4052","iconfarm":5,"datecreate":"1556979315","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708402242304","path_alias":"","realname":"","_content":"[http://www.flickr.com/photos/46759384@N03/]  Si Paco dice que es de fábula es que es de fábula... y no se hable más...."},{"id":"55843683-46982335834-72157704931487342","author":"135601601@N07","author_is_deleted":0,"authorname":"la_filo","iconserver":"516","iconfarm":1,"datecreate":"1556981947","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157704931487342","path_alias":"","realname":"Casilda Cañizo","_content":"Bellísima e Impresionante fotografía, José Jesús Sabaris. 👌👌👍"},{"id":"55843683-46982335834-72157691263840503","author":"97429791@N06","author_is_deleted":0,"authorname":"zon1966","iconserver":"5492","iconfarm":6,"datecreate":"1556985988","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691263840503","path_alias":"","realname":"Oleg ***","_content":"👍"},{"id":"55843683-46982335834-72157691273059953","author":"47046393@N00","author_is_deleted":0,"authorname":"dale 1","iconserver":"3691","iconfarm":4,"datecreate":"1557033235","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691273059953","path_alias":"","realname":"dale  bentham","_content":"Beautiful image congratulations on explore"},{"id":"55843683-46982335834-72157691273085713","author":"76805638@N07","author_is_deleted":0,"authorname":"Sergey S Ponomarev","iconserver":"7390","iconfarm":8,"datecreate":"1557033429","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691273085713","path_alias":"ponomarevsergey","realname":"Sergey Ponomarev","_content":"Fabulous...."},{"id":"55843683-46982335834-72157708414806374","author":"79214581@N07","author_is_deleted":0,"authorname":"Samie Wood","iconserver":"7864","iconfarm":8,"datecreate":"1557035986","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708414806374","path_alias":"tinkerbellsam","realname":"","_content":"Wow absolutely stunning wish I was here 😍"},{"id":"55843683-46982335834-72157691273670233","author":"147899697@N08","author_is_deleted":0,"authorname":"Jörg Kaftan","iconserver":"4561","iconfarm":5,"datecreate":"1557036952","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691273670233","path_alias":"","realname":"Jörg Kaftan","_content":"Impresionante captura, muy lograda"},{"id":"55843683-46982335834-72157708415299514","author":"128922997@N04","author_is_deleted":0,"authorname":"soehngenudo","iconserver":"4882","iconfarm":5,"datecreate":"1557038414","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708415299514","path_alias":"","realname":"udo soehngen","_content":"awesome perspective and composition! very well done."},{"id":"55843683-46982335834-72157691274125663","author":"12268891@N02","author_is_deleted":0,"authorname":"Dr. Ilia","iconserver":"7431","iconfarm":8,"datecreate":"1557039737","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691274125663","path_alias":"iliaal","realname":"Ilia Alshanetsky","_content":"Awesome..."},{"id":"55843683-46982335834-72157708415754794","author":"125020576@N06","author_is_deleted":0,"authorname":" fabienne fauré","iconserver":"1598","iconfarm":2,"datecreate":"1557040654","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708415754794","path_alias":"","realname":"Fabienne Fauré","_content":"Magnifique capture 👏👏👏"},{"id":"55843683-46982335834-72157691274603973","author":"44191635@N04","author_is_deleted":0,"authorname":"victor98_2001","iconserver":0,"iconfarm":0,"datecreate":"1557042533","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691274603973","path_alias":"victor98","realname":"","_content":"Excellent shot"},{"id":"55843683-46982335834-72157680246019778","author":"66481287@N04","author_is_deleted":0,"authorname":"fjsmalaga","iconserver":"3676","iconfarm":4,"datecreate":"1557042767","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246019778","path_alias":"fjs-malaga2","realname":"","_content":"buena foto compañero"},{"id":"55843683-46982335834-72157708416255284","author":"34798185@N05","author_is_deleted":0,"authorname":"raudio","iconserver":"5495","iconfarm":6,"datecreate":"1557043157","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708416255284","path_alias":"pw365","realname":"Tullio Rodda","_content":"Bellissima |!"},{"id":"55843683-46982335834-72157680246134628","author":"161626909@N02","author_is_deleted":0,"authorname":"Pasesi Interiors","iconserver":"4806","iconfarm":5,"datecreate":"1557043436","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246134628","path_alias":"","realname":"Pasesi Interiors","_content":"This is beautiful. Good job!"},{"id":"55843683-46982335834-72157706927383651","author":"60366501@N04","author_is_deleted":0,"authorname":"Endless Sky","iconserver":0,"iconfarm":0,"datecreate":"1557044227","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157706927383651","path_alias":"cwaim2","realname":"Sky Blue","_content":"nice cloud colors !!"},{"id":"55843683-46982335834-72157680246458078","author":"7899265@N07","author_is_deleted":0,"authorname":"atempviatja","iconserver":"486","iconfarm":1,"datecreate":"1557045248","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246458078","path_alias":"jorgeramos","realname":"","_content":"Magnifico!"},{"id":"55843683-46982335834-72157680246621738","author":"130010571@N06","author_is_deleted":0,"authorname":"Wald und Wiese","iconserver":"7896","iconfarm":8,"datecreate":"1557046154","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246621738","path_alias":"wald_und_wiese","realname":"Hermann Heuermann","_content":"Fantastic"},{"id":"55843683-46982335834-72157680246780848","author":"164661227@N08","author_is_deleted":0,"authorname":"btc67","iconserver":"7851","iconfarm":8,"datecreate":"1557047015","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246780848","path_alias":"btc67","realname":"67 btc","_content":"Superb ! Congratulation ! On explore <a href=\"https://www.flickr.com/photos/btc67/albums/with/72157699318306310\">Btc67<img src=\"https://live.staticflickr.com/7847/46454400644_1f464958b2_q_d.jpg\" width=\"70\" height=\"70\" title=\"Clic here\" />Clic Here<\/a>"},{"id":"55843683-46982335834-72157708273604795","author":"90793714@N04","author_is_deleted":0,"authorname":"waewduan4","iconserver":"8207","iconfarm":9,"datecreate":"1557047324","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708273604795","path_alias":"","realname":"waewduan C","_content":"Congrats !!"},{"id":"55843683-46982335834-72157708273733475","author":"31675053@N02","author_is_deleted":0,"authorname":"Stoff74","iconserver":"5466","iconfarm":6,"datecreate":"1557048015","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708273733475","path_alias":"stoff74","realname":"","_content":":)"},{"id":"55843683-46982335834-72157706928462291","author":"11035345@N07","author_is_deleted":0,"authorname":"CarloAlessioCozzolino","iconserver":"1082","iconfarm":2,"datecreate":"1557049662","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157706928462291","path_alias":"carloalessio77","realname":"Carlo Alessio Cozzolino","_content":"Wonderful capture, congrats on Explore!"},{"id":"55843683-46982335834-72157680247345588","author":"169038024@N07","author_is_deleted":0,"authorname":"Blindesire","iconserver":"7820","iconfarm":8,"datecreate":"1557050034","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680247345588","path_alias":"","realname":"Cristian Glodean","_content":"Wonderful"},{"id":"55843683-46982335834-72157706928692781","author":"95686244@N05","author_is_deleted":0,"authorname":"Doctor Kibble","iconserver":"2946","iconfarm":3,"datecreate":"1557050861","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157706928692781","path_alias":"","realname":"Kibsee","_content":"Absolutely beautiful."},{"id":"55843683-46982335834-72157708274296665","author":"16446716@N05","author_is_deleted":0,"authorname":"Lumenoid","iconserver":"8368","iconfarm":9,"datecreate":"1557050862","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708274296665","path_alias":"lumenoid","realname":"","_content":"<b> A fantastic shot and great photostream Jose!\n\nClick the smile on the left to visit mine \u2013 Bill <b><\/b><\/b>"},{"id":"55843683-46982335834-72157708274414355","author":"11308415@N06","author_is_deleted":0,"authorname":"Picture post.","iconserver":"7122","iconfarm":8,"datecreate":"1557051458","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708274414355","path_alias":"jepics","realname":"Picture Post.","_content":"Nice photography!"},{"id":"55843683-46982335834-72157708274691795","author":"45762667@N08","author_is_deleted":0,"authorname":"Luicabe","iconserver":"7868","iconfarm":8,"datecreate":"1557052732","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708274691795","path_alias":"lcabello","realname":"","_content":"Una fotografía excelente. ¡Enhorabuena! 👍👏"},{"id":"55843683-46982335834-72157708418367404","author":"48753362@N02","author_is_deleted":0,"authorname":"Marti_mart","iconserver":"7079","iconfarm":8,"datecreate":"1557053009","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708418367404","path_alias":"marti_mart","realname":"","_content":"Preciosa fotografía"},{"id":"55843683-46982335834-72157708418369014","author":"16826106@N03","author_is_deleted":0,"authorname":"Steve Millward","iconserver":"1621","iconfarm":2,"datecreate":"1557053016","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708418369014","path_alias":"stevemillward","realname":"Steve Millward","_content":"Magnificent capture"},{"id":"55843683-46982335834-72157678090331277","author":"138707650@N07","author_is_deleted":0,"authorname":"dgarridosan","iconserver":"2885","iconfarm":3,"datecreate":"1557053347","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157678090331277","path_alias":"","realname":"Daniel Garrido","_content":"Sensacional trabajo en larga exposición, Jose!\nFantástica composición y fabulosos colores en ese cielo.\nFelicidades por el Explore!"},{"id":"55843683-46982335834-72157691276646983","author":"48814067@N06","author_is_deleted":0,"authorname":"Kapaliadiyar","iconserver":"2838","iconfarm":3,"datecreate":"1557053499","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691276646983","path_alias":"kapaliadiyar","realname":"Kapaliadiyar  Thirumayilai","_content":"Excellent capture"}]}
     * stat : ok
     */

    private CommentsBean comments;
    private String stat;

    public CommentsBean getComments() {
        return comments;
    }

    public void setComments(CommentsBean comments) {
        this.comments = comments;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public static class CommentsBean {
        /**
         * photo_id : 46982335834
         * comment : [{"id":"55843683-46982335834-72157678075007337","author":"55849023@N05","author_is_deleted":0,"authorname":"jojesari","iconserver":"7301","iconfarm":8,"datecreate":"1556975891","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157678075007337","path_alias":"jojesari","realname":"Jose Jesus Sabaris Rico","_content":"."},{"id":"55843683-46982335834-72157708401403574","author":"135583184@N08","author_is_deleted":0,"authorname":"Celtarro","iconserver":"5792","iconfarm":6,"datecreate":"1556976357","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708401403574","path_alias":"","realname":"","_content":"Bo ceo,e ese arco de pedras coa espuma,un saudo"},{"id":"55843683-46982335834-72157678075134557","author":"50279554@N00","author_is_deleted":0,"authorname":"* Rhonda *","iconserver":"7414","iconfarm":8,"datecreate":"1556976494","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157678075134557","path_alias":"_rhonda_","realname":"","_content":"Gorgeous"},{"id":"55843683-46982335834-72157704930443162","author":"64942999@N02","author_is_deleted":0,"authorname":"JB.Photography2011","iconserver":"3925","iconfarm":4,"datecreate":"1556977438","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157704930443162","path_alias":"","realname":"John Borg","_content":"Superb capture and lovely details"},{"id":"55843683-46982335834-72157680233151258","author":"46759384@N03","author_is_deleted":0,"authorname":"rroel58","iconserver":"7026","iconfarm":8,"datecreate":"1556977817","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680233151258","path_alias":"","realname":"","_content":"Una pura maravilla.\nEstás que te sales José Jesús. \nDe fábula."},{"id":"55843683-46982335834-72157708402230624","author":"42597241@N04","author_is_deleted":0,"authorname":"ferfer2009","iconserver":"4052","iconfarm":5,"datecreate":"1556979265","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708402230624","path_alias":"","realname":"","_content":"Madre mía... Virgen del amor hermoso... Vaya foto bonita nos regalas hoy... Una delicatessen...."},{"id":"55843683-46982335834-72157708402242304","author":"42597241@N04","author_is_deleted":0,"authorname":"ferfer2009","iconserver":"4052","iconfarm":5,"datecreate":"1556979315","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708402242304","path_alias":"","realname":"","_content":"[http://www.flickr.com/photos/46759384@N03/]  Si Paco dice que es de fábula es que es de fábula... y no se hable más...."},{"id":"55843683-46982335834-72157704931487342","author":"135601601@N07","author_is_deleted":0,"authorname":"la_filo","iconserver":"516","iconfarm":1,"datecreate":"1556981947","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157704931487342","path_alias":"","realname":"Casilda Cañizo","_content":"Bellísima e Impresionante fotografía, José Jesús Sabaris. 👌👌👍"},{"id":"55843683-46982335834-72157691263840503","author":"97429791@N06","author_is_deleted":0,"authorname":"zon1966","iconserver":"5492","iconfarm":6,"datecreate":"1556985988","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691263840503","path_alias":"","realname":"Oleg ***","_content":"👍"},{"id":"55843683-46982335834-72157691273059953","author":"47046393@N00","author_is_deleted":0,"authorname":"dale 1","iconserver":"3691","iconfarm":4,"datecreate":"1557033235","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691273059953","path_alias":"","realname":"dale  bentham","_content":"Beautiful image congratulations on explore"},{"id":"55843683-46982335834-72157691273085713","author":"76805638@N07","author_is_deleted":0,"authorname":"Sergey S Ponomarev","iconserver":"7390","iconfarm":8,"datecreate":"1557033429","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691273085713","path_alias":"ponomarevsergey","realname":"Sergey Ponomarev","_content":"Fabulous...."},{"id":"55843683-46982335834-72157708414806374","author":"79214581@N07","author_is_deleted":0,"authorname":"Samie Wood","iconserver":"7864","iconfarm":8,"datecreate":"1557035986","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708414806374","path_alias":"tinkerbellsam","realname":"","_content":"Wow absolutely stunning wish I was here 😍"},{"id":"55843683-46982335834-72157691273670233","author":"147899697@N08","author_is_deleted":0,"authorname":"Jörg Kaftan","iconserver":"4561","iconfarm":5,"datecreate":"1557036952","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691273670233","path_alias":"","realname":"Jörg Kaftan","_content":"Impresionante captura, muy lograda"},{"id":"55843683-46982335834-72157708415299514","author":"128922997@N04","author_is_deleted":0,"authorname":"soehngenudo","iconserver":"4882","iconfarm":5,"datecreate":"1557038414","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708415299514","path_alias":"","realname":"udo soehngen","_content":"awesome perspective and composition! very well done."},{"id":"55843683-46982335834-72157691274125663","author":"12268891@N02","author_is_deleted":0,"authorname":"Dr. Ilia","iconserver":"7431","iconfarm":8,"datecreate":"1557039737","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691274125663","path_alias":"iliaal","realname":"Ilia Alshanetsky","_content":"Awesome..."},{"id":"55843683-46982335834-72157708415754794","author":"125020576@N06","author_is_deleted":0,"authorname":" fabienne fauré","iconserver":"1598","iconfarm":2,"datecreate":"1557040654","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708415754794","path_alias":"","realname":"Fabienne Fauré","_content":"Magnifique capture 👏👏👏"},{"id":"55843683-46982335834-72157691274603973","author":"44191635@N04","author_is_deleted":0,"authorname":"victor98_2001","iconserver":0,"iconfarm":0,"datecreate":"1557042533","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691274603973","path_alias":"victor98","realname":"","_content":"Excellent shot"},{"id":"55843683-46982335834-72157680246019778","author":"66481287@N04","author_is_deleted":0,"authorname":"fjsmalaga","iconserver":"3676","iconfarm":4,"datecreate":"1557042767","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246019778","path_alias":"fjs-malaga2","realname":"","_content":"buena foto compañero"},{"id":"55843683-46982335834-72157708416255284","author":"34798185@N05","author_is_deleted":0,"authorname":"raudio","iconserver":"5495","iconfarm":6,"datecreate":"1557043157","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708416255284","path_alias":"pw365","realname":"Tullio Rodda","_content":"Bellissima |!"},{"id":"55843683-46982335834-72157680246134628","author":"161626909@N02","author_is_deleted":0,"authorname":"Pasesi Interiors","iconserver":"4806","iconfarm":5,"datecreate":"1557043436","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246134628","path_alias":"","realname":"Pasesi Interiors","_content":"This is beautiful. Good job!"},{"id":"55843683-46982335834-72157706927383651","author":"60366501@N04","author_is_deleted":0,"authorname":"Endless Sky","iconserver":0,"iconfarm":0,"datecreate":"1557044227","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157706927383651","path_alias":"cwaim2","realname":"Sky Blue","_content":"nice cloud colors !!"},{"id":"55843683-46982335834-72157680246458078","author":"7899265@N07","author_is_deleted":0,"authorname":"atempviatja","iconserver":"486","iconfarm":1,"datecreate":"1557045248","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246458078","path_alias":"jorgeramos","realname":"","_content":"Magnifico!"},{"id":"55843683-46982335834-72157680246621738","author":"130010571@N06","author_is_deleted":0,"authorname":"Wald und Wiese","iconserver":"7896","iconfarm":8,"datecreate":"1557046154","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246621738","path_alias":"wald_und_wiese","realname":"Hermann Heuermann","_content":"Fantastic"},{"id":"55843683-46982335834-72157680246780848","author":"164661227@N08","author_is_deleted":0,"authorname":"btc67","iconserver":"7851","iconfarm":8,"datecreate":"1557047015","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680246780848","path_alias":"btc67","realname":"67 btc","_content":"Superb ! Congratulation ! On explore <a href=\"https://www.flickr.com/photos/btc67/albums/with/72157699318306310\">Btc67<img src=\"https://live.staticflickr.com/7847/46454400644_1f464958b2_q_d.jpg\" width=\"70\" height=\"70\" title=\"Clic here\" />Clic Here<\/a>"},{"id":"55843683-46982335834-72157708273604795","author":"90793714@N04","author_is_deleted":0,"authorname":"waewduan4","iconserver":"8207","iconfarm":9,"datecreate":"1557047324","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708273604795","path_alias":"","realname":"waewduan C","_content":"Congrats !!"},{"id":"55843683-46982335834-72157708273733475","author":"31675053@N02","author_is_deleted":0,"authorname":"Stoff74","iconserver":"5466","iconfarm":6,"datecreate":"1557048015","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708273733475","path_alias":"stoff74","realname":"","_content":":)"},{"id":"55843683-46982335834-72157706928462291","author":"11035345@N07","author_is_deleted":0,"authorname":"CarloAlessioCozzolino","iconserver":"1082","iconfarm":2,"datecreate":"1557049662","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157706928462291","path_alias":"carloalessio77","realname":"Carlo Alessio Cozzolino","_content":"Wonderful capture, congrats on Explore!"},{"id":"55843683-46982335834-72157680247345588","author":"169038024@N07","author_is_deleted":0,"authorname":"Blindesire","iconserver":"7820","iconfarm":8,"datecreate":"1557050034","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157680247345588","path_alias":"","realname":"Cristian Glodean","_content":"Wonderful"},{"id":"55843683-46982335834-72157706928692781","author":"95686244@N05","author_is_deleted":0,"authorname":"Doctor Kibble","iconserver":"2946","iconfarm":3,"datecreate":"1557050861","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157706928692781","path_alias":"","realname":"Kibsee","_content":"Absolutely beautiful."},{"id":"55843683-46982335834-72157708274296665","author":"16446716@N05","author_is_deleted":0,"authorname":"Lumenoid","iconserver":"8368","iconfarm":9,"datecreate":"1557050862","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708274296665","path_alias":"lumenoid","realname":"","_content":"<b> A fantastic shot and great photostream Jose!\n\nClick the smile on the left to visit mine \u2013 Bill <b><\/b><\/b>"},{"id":"55843683-46982335834-72157708274414355","author":"11308415@N06","author_is_deleted":0,"authorname":"Picture post.","iconserver":"7122","iconfarm":8,"datecreate":"1557051458","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708274414355","path_alias":"jepics","realname":"Picture Post.","_content":"Nice photography!"},{"id":"55843683-46982335834-72157708274691795","author":"45762667@N08","author_is_deleted":0,"authorname":"Luicabe","iconserver":"7868","iconfarm":8,"datecreate":"1557052732","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708274691795","path_alias":"lcabello","realname":"","_content":"Una fotografía excelente. ¡Enhorabuena! 👍👏"},{"id":"55843683-46982335834-72157708418367404","author":"48753362@N02","author_is_deleted":0,"authorname":"Marti_mart","iconserver":"7079","iconfarm":8,"datecreate":"1557053009","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708418367404","path_alias":"marti_mart","realname":"","_content":"Preciosa fotografía"},{"id":"55843683-46982335834-72157708418369014","author":"16826106@N03","author_is_deleted":0,"authorname":"Steve Millward","iconserver":"1621","iconfarm":2,"datecreate":"1557053016","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157708418369014","path_alias":"stevemillward","realname":"Steve Millward","_content":"Magnificent capture"},{"id":"55843683-46982335834-72157678090331277","author":"138707650@N07","author_is_deleted":0,"authorname":"dgarridosan","iconserver":"2885","iconfarm":3,"datecreate":"1557053347","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157678090331277","path_alias":"","realname":"Daniel Garrido","_content":"Sensacional trabajo en larga exposición, Jose!\nFantástica composición y fabulosos colores en ese cielo.\nFelicidades por el Explore!"},{"id":"55843683-46982335834-72157691276646983","author":"48814067@N06","author_is_deleted":0,"authorname":"Kapaliadiyar","iconserver":"2838","iconfarm":3,"datecreate":"1557053499","permalink":"https://www.flickr.com/photos/jojesari/46982335834/#comment72157691276646983","path_alias":"kapaliadiyar","realname":"Kapaliadiyar  Thirumayilai","_content":"Excellent capture"}]
         */

        private String photo_id;
        private List<CommentBean> comment;

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class CommentBean {
            /**
             * id : 55843683-46982335834-72157678075007337
             * author : 55849023@N05
             * author_is_deleted : 0
             * authorname : jojesari
             * iconserver : 7301
             * iconfarm : 8
             * datecreate : 1556975891
             * permalink : https://www.flickr.com/photos/jojesari/46982335834/#comment72157678075007337
             * path_alias : jojesari
             * realname : Jose Jesus Sabaris Rico
             * _content : .
             */

            private String id;
            private String author;
            private int author_is_deleted;
            private String authorname;
            private String iconserver;
            private int iconfarm;
            private String datecreate;
            private String permalink;
            private String path_alias;
            private String realname;
            private String _content;

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

            public int getAuthor_is_deleted() {
                return author_is_deleted;
            }

            public void setAuthor_is_deleted(int author_is_deleted) {
                this.author_is_deleted = author_is_deleted;
            }

            public String getAuthorname() {
                return authorname;
            }

            public void setAuthorname(String authorname) {
                this.authorname = authorname;
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

            public String getDatecreate() {
                return datecreate;
            }

            public void setDatecreate(String datecreate) {
                this.datecreate = datecreate;
            }

            public String getPermalink() {
                return permalink;
            }

            public void setPermalink(String permalink) {
                this.permalink = permalink;
            }

            public String getPath_alias() {
                return path_alias;
            }

            public void setPath_alias(String path_alias) {
                this.path_alias = path_alias;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
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
