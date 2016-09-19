import com.mongodb.*;
import com.sun.xml.internal.bind.v2.TODO;
import com.ws.process.SDBSPicPageProcessor;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by laowang on 16-9-18.
 */
public class SDBSTest {
    private static MongoClient mongoClient = new MongoClient();
    private static DB db = mongoClient.getDB("mymongo");
    private static DBCollection collection = null;
    private static List<String> stringList = null;
    public static void main(String args[]){
        for(int i=1;i<=10;i++){

            collection  = db.getCollection("sdbs_collection");
            DBCursor object = collection.find(new BasicDBObject("sdbsno",i));
            if(object.size()>0){
                List<DBObject> dbObjectList = object.toArray();

                Map map = dbObjectList.get(0).toMap();
                stringList = new ArrayList<String>();

                String ms = map.get("ms").toString();
                String cnmr = map.get("cnmr").toString();
                String hnmr = map.get("hnmr").toString();
                String ir = map.get("ir").toString();
                String raman = map.get("raman").toString();
                String esr = map.get("esr").toString();

                if(!ms.equals("N"))
                    stringList.add(ms);
                if (!hnmr.equals("N"))
                    stringList.add(hnmr);
                if(!cnmr.equals("N"))
                    stringList.add(cnmr);
                if(!ir.equals("N"))
                    stringList.add(ir);
                if(!raman.equals("N"))
                    stringList.add(raman);
                if(!esr.equals("N"))
                    stringList.add(esr);

                for (String aStringList : stringList) {
//                    System.out.println(aStringList);

                    PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));

                    Spider.create(new SDBSPicPageProcessor())
                            .addUrl(aStringList)
                            .thread(3)
                            .start();

                }

//                System.out.println("**************************");
               // System.out.println(dbObjectList.get(0).get("ms"));

                //TODO 还需要考虑保存数据位置问题
            }

        }
    }
}
