import com.mongodb.*;
import com.sun.xml.internal.bind.v2.TODO;
import com.ws.process.SDBSPicPageProcessor;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Spider;

/**
 * Created by laowang on 16-9-18.
 */
public class SDBSTest {
    private static MongoClient mongoClient = new MongoClient();
    private static DB db = mongoClient.getDB("mymongo");
    private static DBCollection collection = null;
    public static void main(String args[]){
        for(int i=1;i<=100;i++){

            collection  = db.getCollection("sdbs_collection");
            DBCursor object = collection.find(new BasicDBObject("sdbsno",i));
            if(object.size()>0){
                //TODO 数据去重，依次查找每行六条数据，如果数据为"N"，说明没有数据，直接忽略，否则爬取
                //TODO 还需要考虑保存数据位置问题
            }



            PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));

//            Spider.create(new SDBSPicPageProcessor())
//                    .addUrl("http://sdbs.db.aist.go.jp/sdbs/cgi-bin/img_disp.cgi?disptype=disp1&amp;imgdir=esr&amp;fname=ESR0981&amp;sdbsno=96")
//                    .thread(3)
//                    .start();
        }
    }
}
