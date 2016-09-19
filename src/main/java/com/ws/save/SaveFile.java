package com.ws.save;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.ws.file.GetFile;
import com.ws.model.*;
import us.codecraft.webmagic.Page;

/**
 * Created by laowang on 16-9-19.
 */
public class SaveFile {
    private Page page;

    public SaveFile(Page page){
        this.page = page;
    }

    private GetFile getFile=null;

    private MongoClient mongoClient = new MongoClient();
    private DB db = mongoClient.getDB("mymongo");
    private DBCollection collection = null;

    //存储ms的所有信息
    public synchronized void saveMs( String fileName,int sdbsno){
        getFile = new GetFile(page);
        getFile.processPictureAndText(new MS(),sdbsno);//文字采集
        try {
            collection = db.getCollection("ms_table");
            getFile.processPicture(fileName);//图片采集
            BasicDBObject object = new BasicDBObject();
            object.put("picUrl",fileName);
            BasicDBObject query = new BasicDBObject();
            query.put("sdbsno",sdbsno);
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set",object);
            collection.update(query,updateObject);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mongoClient!=null)
                mongoClient.close();
        }
    }

    //存储cnmr所有信息
    public synchronized void saveCnmr( String fileName,int sdbsno){
        getFile = new GetFile(page);
        getFile.processText(new CNMR(),sdbsno);//文字采集
        try {
            collection = db.getCollection("cnmr_table");
            getFile.processPicture(fileName);
            getFile.processSecondPicture(fileName+"1");
            BasicDBObject object = new BasicDBObject();
            object.put("firstPicUrl",fileName);
            object.put("secondPicUrl",fileName+"1");
            BasicDBObject query = new BasicDBObject();
            query.put("sdbsno",sdbsno);
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set",object);
            collection.update(query,updateObject);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mongoClient!=null)
                mongoClient.close();
        }
    }

    public synchronized void saveHnmr(String fileName,int sdbsno){
        getFile = new GetFile(page);
        getFile.processText(new HNMR(),sdbsno);//文字采集
        try {
            collection = db.getCollection("hnmr_table");
            getFile.processPicture(fileName);//第一张图片采集
            getFile.processSecondPicture(fileName+"1");//第二张图片采集
            BasicDBObject object = new BasicDBObject();
            object.put("firstPicUrl",fileName);
            object.put("secondPicUrl",fileName+"1");
            BasicDBObject query = new BasicDBObject();
            query.put("sdbsno",sdbsno);
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set",object);
            collection.update(query,updateObject);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mongoClient!=null)
                mongoClient.close();
        }
    }

    public synchronized void saveIR(String fileName,int sdbsno){
        getFile = new GetFile(page);
        try {
            collection = db.getCollection("ir_table");
//            getFile.processPicture(fileName);//第一张图片采集
            getFile.getPicture(fileName);
            IR ir = new IR();
            ir.setSdbsno(sdbsno);
            ir.setPicUrl(fileName);
            DBObject object = (BasicDBObject)JSON.parse(ir.toJson());
            collection.insert(object);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mongoClient!=null)
                mongoClient.close();
        }
    }

    public synchronized  void saveRAMAN(String fileName,int sdbsno){
        getFile = new GetFile(page);
        try {
            collection = db.getCollection("raman_table");
            getFile.getPicture(fileName);//第一张图片采集
            RAMAN raman = new RAMAN();
            raman.setSdbsno(sdbsno);
            raman.setPicUrl(fileName);
            DBObject object = (BasicDBObject)JSON.parse(raman.toJson());
            collection.insert(object);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mongoClient!=null)
                mongoClient.close();
        }
    }

    public synchronized void saveESR(String fileName,int sdbsno){
        getFile = new GetFile(page);
        getFile.processPictureAndText(new ESR(),sdbsno);//文字采集
        try {
            collection = db.getCollection("esr_table");
            getFile.processPicture(fileName);//图片采集
            BasicDBObject object = new BasicDBObject();
            object.put("picUrl",fileName);
            BasicDBObject query = new BasicDBObject();
            query.put("sdbsno",sdbsno);
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set",object);
            collection.update(query,updateObject);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mongoClient!=null)
                mongoClient.close();
        }
    }
}
