package com.ws.process;


import com.ws.util.ImageDownloadUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.util.List;

import static us.codecraft.webmagic.selector.Selectors.xpath;

/**
 * Created by laowang on 16-9-18.
 */
public class SDBSPicPageProcessor implements PageProcessor {
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/52.0.2743.116 Chrome/52.0.2743.116 Safari/537.36")
            .setSleepTime(1000)
            .setRetryTimes(3)
            .addCookie("SDBS_ENTRANCE_CK","ZHxo9OjZIFY")
            .addCookie("SDBS_IMG","d1xPwybPXNc")
            .addCookie("SDBS_CK","gdLDgdBhLsc")
            .addCookie("__utmt_cloud","1")
            .addCookie("__utmt_aist","1")
            .addCookie("SDBS_LANG","eng")
            .addCookie("__utma","200088940.653950373.1474173549.1474173549.1474173549.1")
            .addCookie("__utmb","200088940.26.9.1474173628923")
            .addCookie("__utmc","200088940")
            .addCookie("__utmz","200088940.1474173549.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");

    private static Logger logger = Logger.getLogger(SDBSPicPageProcessor.class);

    @Override
    public void process(Page page) {
        System.out.println("=============爬虫开始工作=============");
        //定义抽取信息，并保存信息
        processPicture(page);
        System.out.println("=============爬虫工作结束=============");
        //因为没有考虑下一页，这里不想着添加下一页
    }

    @Override
    public Site getSite() {
        return site;
    }
    private void processPicture(Page page){
        //获取所有满足匹配的url,已经证明获取的不是空\
//        processPictureAndText(page);
        processText(page);
        processSecondPicture(page);
//        System.out.println(page.getHtml().toString());
        List<String> url = page.getHtml().xpath("//*html/body").all();
        int i=13;
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
                ImageDownloadUtils.downLoadImage(picU, filePath, String.valueOf(i), picType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }


    //获取ms和esr的文字部分数据
    private void processPictureAndText(Page page){
        try{
            List<String> listUpText = page.getHtml().xpath("//*html/body/pre[1]/text()").all();

            List<String> listDownText = page.getHtml().xpath("//*html/body/pre[2]/text()").all();

            System.out.println("text1："+listUpText.get(0));
            System.out.println("text2："+listDownText.get(0));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("*********不匹配********");
        }

    }

    //获取CNMR和HNMR的第二部分图片
    private void processSecondPicture(Page page){
        List<String> listPicture = page.getHtml().xpath("//*html/body/table[2]/tbody/tr[1]/td").all();
        String picU = xpath("//*img").selectElement(listPicture.get(0)).attr("src");
        picU = "http://sdbs.db.aist.go.jp/sdbs/cgi-bin/" + picU.substring(2, picU.length());
        System.out.println("第二部分图片："+picU);
        String downloadDir = "/home/laowang/pic";
        //文件保存路径
        String filePath = downloadDir + File.separator + "laowang";
        //文件保存类型
        String picType = "cgi";
        System.out.println(filePath);
        int i=14;
        try {
            //调用文件下载方法
            ImageDownloadUtils.downLoadImage(picU, filePath, String.valueOf(i), picType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取cnmr和hnmr内容
    private void processText(Page page){
        List<String> listTop_No = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[1]/td[1]/spacer/spacer/text()").all();
        List<String> listTop_Mhz = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[1]/td[2]/text()").all();
        List<String> listTop_gMl = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[2]/td[2]/text()").all();
        List<String> listTop_num = page.getHtml().xpath("//*html/body/table[1]/tbody/tr[2]/td[2]/sub/text()").all();//需要判断是否为空
        List<String> listUnder_title = page.getHtml().xpath("//*html/body/table[2]/tbody/tr[2]/td/pre/b/text()").all();
        List<String> listUnder_text = page.getHtml().xpath("//*html/body/table[2]/tbody/tr[2]/td/pre/text()").all();//这条会匹配两条数据，第一条为空
        System.out.println(listTop_No.get(0));
        System.out.println(listTop_Mhz.get(0));
        if(listTop_num.size()==0)
            System.out.println(listTop_gMl.get(0));
        else
            System.out.println(listTop_gMl.get(0).trim()+listTop_num.get(0).trim());
        System.out.println(listUnder_title.get(0));
        System.out.println(listUnder_text.get(0));
    }
}