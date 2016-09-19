package com.ws.file;

import com.ws.process.SDBSPicPageProcessor;
import com.ws.util.ImageDownloadUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;

import java.io.File;
import java.util.List;

import static us.codecraft.webmagic.selector.Selectors.xpath;

/**
 * Created by laowang on 16-9-19.
 */
public class GetFile {
    private Page page;

    public GetFile(Page page){
        this.page = page;
    }

    private static Logger logger = Logger.getLogger(SDBSPicPageProcessor.class);

    public void  processPicture(Page page,String fileName){
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
    public void processPictureAndText(Page page,Object model){
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
    public void processSecondPicture(Page page,String fileName){
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
        try {
            //调用文件下载方法
            ImageDownloadUtils.downLoadImage(picU, filePath, fileName, picType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取cnmr和hnmr内容
    public void processText(Page page,Object model){
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
