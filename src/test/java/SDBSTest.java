import com.mongodb.*;
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
//        int []nums = new int[]{365};
        for(int i=53001;i<=54500;i++){
//        for(int i : nums){
//            collection = db.getCollection("raman_table");//测试
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

//                if(!ms.equals("N"))
//                    stringList.add(ms);
//                if (!hnmr.equals("N"))
//                    stringList.add(hnmr);
//                if(!cnmr.equals("N"))
//                    stringList.add(cnmr);
                if(!ir.equals("N"))
                    if(ir.contains("imgdir=ir2")){
                        String []urls = ir.split("imgdir=ir2");
                        ir = "http://sdbs.db.aist.go.jp/sdbs/cgi-bin/ir_disp.cgi?imgdir=ir2"+urls[1];
                        stringList.add(ir);
                    }else{
                        System.out.println("ir");
                    }

//                if(!raman.equals("N"))
//                    stringList.add(raman);
//                if(!esr.equals("N"))
//                    stringList.add(esr);

                for (String aStringList : stringList) {
//                    System.out.println(aStringList);

                    PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));

                    Spider.create(new SDBSPicPageProcessor())
                            .addUrl(aStringList)
                            .thread(3)
                            .run();

                }


//                System.out.println("**************************");
               // System.out.println(dbObjectList.get(0).get("ms"));

            }
//            System.out.println(i+":"+object.count());
        }
    }
}
