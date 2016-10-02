package com.ws.file;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.ws.model.*;
import com.ws.process.SDBSPicPageProcessor;
import com.ws.util.ImageDownloadUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static us.codecraft.webmagic.selector.Selectors.or;
import static us.codecraft.webmagic.selector.Selectors.xpath;

/**
 * Created by laowang on 16-9-19.
 */
public class GetFile {
    private Page page;

    public GetFile(Page page){
        this.page = page;
    }

    private MongoClient mongoClient = new MongoClient();
    private DB db = mongoClient.getDB("mymongo");
    private DBCollection collection = null;
    private Map<String,String> urlMap = new HashMap<String, String>();

    private static Logger logger = Logger.getLogger(SDBSPicPageProcessor.class);

    public void  processPicture(String fileName){
        //获取所有满足匹配的url,已经证明获取的不是空\
//        processPictureAndText(page);
//        System.out.println(page.getHtml().toString());
        List<String> url = page.getHtml().xpath("//*html/body").all();
//        System.out.println(url.size());
        for(String picUrl:url) {
            //从每个url中获取图片路径
            String picU = xpath("//*img").selectElement(picUrl).attr("src");
            picU = "http://sdbs.db.aist.go.jp/sdbs/cgi-bin/" + picU.substring(2, picU.length());
            logger.info("图片url:" + picU);
            System.out.println("图片："+picU);
            //下载路径
            String downloadDir = "/home/laowang/pic";
            //文件保存路径
            String filePath = downloadDir + File.separator + "laowang";
            //文件保存类型
            String picType = "cgi";
            System.out.println(filePath);
            try {
                //调用文件下载方法
                ImageDownloadUtils.downLoadImage(picU, filePath, fileName, picType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //获取ms和esr的文字部分数据
    public void processPictureAndText(Object model,int sdbsNo){
        try{
            List<String> listUpText = page.getHtml().xpath("//*html/body/pre[1]/text()").all();

            List<String> listDownText = page.getHtml().xpath("//*html/body/pre[2]/text()").all();

            if(model instanceof MS){
                try {
                    collection = db.getCollection("ms_table");
                    MS ms = new MS();
                    ms.setSdbsno(sdbsNo);
                    ms.setTopText(listUpText.get(0));
                    ms.setUnderText(listDownText.get(0));
                    DBObject object = (BasicDBObject)JSON.parse(ms.toJson());
                    collection.insert(object);
                }catch (Exception e){
                    logger.warn("wrong url:ms_"+sdbsNo);
                    e.printStackTrace();
                }finally {
                    if(mongoClient!=null){
                        mongoClient.close();
                        mongoClient = null;
                    }

                }
            }else{
                try {
                    collection = db.getCollection("esr_table");
                    ESR esr = new ESR();
                    esr.setSdbsno(sdbsNo);
                    esr.setTopText(listUpText.get(0));
                    esr.setUnderText(listDownText.get(0));
                    DBObject object = (BasicDBObject)JSON.parse(esr.toJson());
                    collection.insert(object);
                }catch (Exception e){
                    logger.warn("wrong url:esr_"+sdbsNo);
                    e.printStackTrace();
                }finally {
                    if(mongoClient!=null){
                        mongoClient.close();
                        mongoClient = null;
                    }
                }
            }

            System.out.println("text1："+listUpText.get(0));
            System.out.println("text2："+listDownText.get(0));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("*********不匹配********");
        }

    }

    //获取CNMR和HNMR的第二部分图片
    public void processSecondPicture(String fileName){
        List<String> listPicture = page.getHtml().xpath("//*html/body/table[2]/tbody/tr[1]/td").all();
        List<String> listPicture1 = page.getHtml().xpath("//*html/body/table[2]/tbody/tr/td[2]").all();
        String picU=null;

        if(listPicture1.size()>0){
            picU = xpath("//*img").selectElement(listPicture1.get(0)).attr("src");
        }else if(listPicture.size()>0){
            picU = xpath("//*img").selectElement(listPicture.get(0)).attr("src");
        }
        picU = "http://sdbs.db.aist.go.jp/sdbs/cgi-bin/" + picU.substring(2, picU.length());
        System.out.println("第二部分图片："+picU);
        String downloadDir = "/home/laowang/pic";
        //文件保存路径
        String filePath = downloadDir + File.separator + "laowang";
        //文件保存类型
        String picType = "cgi";
        System.out.println(filePath);
        try {
            //调用文件下载方法
            ImageDownloadUtils.downLoadImage(picU, filePath, fileName, picType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取cnmr和hnmr内容
    public void processText(Object model,int sdbsNo){

        List<String> listTop_No = null;
        List<String> listTop_Mhz = null;
        List<String> listTop_gMl = null;
        List<String> listTop_num = null;
        List<String> listUnder_title = null;
        List<String> listUnder_text = null;

        List<String> listTop_No2 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[1]/td[1]/spacer/spacer/text()").all();
        List<String> listTop_Mhz2 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[1]/td[2]/text()").all();
        List<String> listTop_gMl2 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[2]/td[2]/text()").all();
        List<String> listTop_num2 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[2]/td[2]/sub/text()").all();//需要判断是否为空
        List<String> listUnder_title2 = page.getHtml().xpath("//*html/body/table[2]/tbody/tr[2]/td/pre/b/text()").all();
        List<String> listUnder_text2 = page.getHtml().xpath("//*html/body/table[2]/tbody/tr[2]/td/pre/text()").all();//这条会匹配两条数据，第一条为空

        List<String> listTop_No1 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[1]/td[1]/spacer/spacer/text()").all();//为空
        List<String> listTop_Mhz1 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[1]/td[2]/b/text()").all();//新的
        List<String> listTop_gMl1 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[2]/td[2]/b/text()").all();
        List<String> listTop_num1 = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[2]/td[2]/b/sub/text()").all();//需要判断是否为空
        List<String> listUnder_title1 = page.getHtml().xpath("//*html/body/table[2]/tbody/tr/td[1]/pre/b/text()").all();
        List<String> listUnder_text1 = page.getHtml().xpath("//*html/body/table[2]/tbody/tr/td[1]/pre/text()").all();//这条会匹配两条数据，第一条为空

        if (listTop_No2.size()==0 && listTop_num2.size() == 0 && listUnder_title2.size() == 0 && listUnder_text2.size() == 0) {
            if(listTop_No1!=null || listTop_Mhz1!=null || listTop_gMl1!=null || listTop_num1!=null|| listUnder_title1!=null||listUnder_text1!=null){
                listTop_No = listTop_No1;
                listTop_Mhz = listTop_Mhz1;
                listTop_gMl = listTop_gMl1;
                listTop_num = listTop_num1;
                listUnder_title = listUnder_title1;
                listUnder_text = listUnder_text1;
            }
        } else {
            listTop_No = listTop_No2;
            listTop_Mhz = listTop_Mhz2;
            listTop_gMl = listTop_gMl2;
            listTop_num = listTop_num2;
            listUnder_title = listUnder_title2;
            listUnder_text = listUnder_text2;
        }

        if(model instanceof CNMR){
            try{
                collection = db.getCollection("cnmr_table");
                CNMR cnmr = new CNMR();
                cnmr.setSdbsno(sdbsNo);
                if(listTop_No.size()>0)
                    cnmr.setTop_NO(listTop_No.get(0));
                else
                    cnmr.setTop_NO("");
                cnmr.setTop_Mhz(listTop_Mhz.get(0));
                if(listTop_num.size() == 0){
                    cnmr.setTop_gMl(listTop_gMl.get(0));
                }else{
                    cnmr.setTop_gMl(listTop_gMl.get(0).trim()+listTop_num.get(0).trim());
                }
                cnmr.setUnder_Title(listUnder_title.get(0));

                cnmr.setUnder_Text(listUnder_text.get(0));

                DBObject object = (BasicDBObject) JSON.parse(cnmr.toJson());
                collection.insert(object);
            }catch (Exception e){
                logger.warn("wrong url:cnmr_"+sdbsNo);
                e.printStackTrace();
            }finally {
                if(mongoClient!=null){
                    mongoClient.close();
                    mongoClient = null;
                }
            }
        }else{
            try{
                collection = db.getCollection("hnmr_table");
                HNMR hnmr = new HNMR();
                hnmr.setSdbsno(sdbsNo);
                if(listTop_No.size()>0)
                    hnmr.setTop_NO(listTop_No.get(0));
                else
                    hnmr.setTop_NO("");
                hnmr.setTop_Mhz(listTop_Mhz.get(0));
                if(listTop_num.size() == 0){
                    hnmr.setTop_gMl(listTop_gMl.get(0));
                }else{
                    hnmr.setTop_gMl(listTop_gMl.get(0).trim()+listTop_num.get(0).trim());
                }
                hnmr.setUnder_Title(listUnder_title.get(0));
                hnmr.setUnder_Text(listUnder_text.get(0));

                DBObject object = (BasicDBObject) JSON.parse(hnmr.toJson());
                collection.insert(object);
            }catch (Exception e){
                logger.warn("wrong url:hnmr_"+sdbsNo);
                e.printStackTrace();
            }finally {
                if(mongoClient!=null){
                    mongoClient.close();
                    mongoClient = null;
                }
            }
        }
//        System.out.println(listTop_No.get(0));
        System.out.println(listTop_Mhz.get(0));
        if(listTop_num.size()==0)
            System.out.println(listTop_gMl.get(0));
        else
            System.out.println(listTop_gMl.get(0).trim()+listTop_num.get(0).trim());
        System.out.println(listUnder_title.get(0));
        System.out.println(listUnder_text.get(0));
    }

    public void getPicture(String fileName){
        //获取所有满足匹配的url,已经证明获取的不是空\
//        processPictureAndText(page);
//        System.out.println(page.getHtml().toString());
        List<String> url = page.getUrl().all();
//        System.out.println(url.size());
        for(String picUrl:url) {
            //从每个url中获取图片路径
//            String picU = xpath("//*img").selectElement(picUrl).attr("src");
//            picU = "http://sdbs.db.aist.go.jp/sdbs/cgi-bin/" + picU.substring(2, picU.length());
            logger.info("图片url:" + picUrl);
            System.out.println("图片：" + picUrl);
            //下载路径
            String downloadDir = "/home/laowang/pic";
            //文件保存路径
            String filePath = downloadDir + File.separator + "laowang";
            //文件保存类型
            String picType = "cgi";
            System.out.println(filePath);
            try {
                //调用文件下载方法
                ImageDownloadUtils.downLoadImage(picUrl, filePath, fileName, picType);
            } catch (Exception e) {
                logger.warn("wrong url:picture_"+fileName);
                e.printStackTrace();
            }
        }
    }

    public void get2Picture(String fileName){
        List<String> pic1 = page.getHtml().xpath("//*html/body").all();
        String img1=null;
        String img2=null;
        if(pic1.size() >0){
            img1 = xpath("//*img[1]").selectElement(pic1.get(0)).attr("src");
            img1 = "http://sdbs.db.aist.go.jp/sdbs/cgi-bin/"+xpath("//*img[1]").selectElement(pic1.get(0)).attr("src").substring(2, img1.length());
            img2 = xpath("//*img[2]").selectElement(pic1.get(0)).attr("src");
            img2 = "http://sdbs.db.aist.go.jp"+img2;
        }
        if(img1!=null){
            downloadPic(img1,fileName);
            System.out.println("第一张图片下载:"+img1);
        }
        if(img2!=null){
            downloadPic(img2,fileName+"1");
            System.out.println("第二张图片下载:"+img2);
        }
    }

    private void downloadPic(String url,String fileName){
        String downloadDir = "/home/laowang/pic";
        //文件保存路径
        String filePath = downloadDir + File.separator + "laowang";
        //文件保存类型
        String picType = "cgi";
        System.out.println(filePath);
        try {
            //调用文件下载方法
            ImageDownloadUtils.downLoadImage(url, filePath, fileName, picType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadText(int sdbsno){

        List<String> headList = page.getHtml().xpath("//*html/body/table[1]").all();
        String tileTitle = "Wave number (cm-1) and Transmittance (T%)";
        List<String> tileList = page.getHtml().xpath("//*html/body/table[2]").all();
        try {
            collection = db.getCollection("ir_table");
            IR2 ir2 = new IR2();
            ir2.setSdbsno(sdbsno);
            if(headList.size() >0){
                ir2.setHead(headList.get(0));
            }
            if(tileList.size() >0){
                ir2.setTile(tileList.get(0));
                ir2.setTileTitle(tileTitle);
            }
            DBObject object = (BasicDBObject)JSON.parse(ir2.toJson());
            collection.insert(object);
        }catch (Exception e){
            logger.warn("wrong url:ir2_"+sdbsno);
            e.printStackTrace();
        }finally {
            if(mongoClient!=null){
                mongoClient.close();
                mongoClient = null;
            }

        }
    }
}
