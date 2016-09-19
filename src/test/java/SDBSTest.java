import com.ws.process.SDBSPicPageProcessor;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Spider;

/**
 * Created by laowang on 16-9-18.
 */
public class SDBSTest {
    public static void main(String args[]){
        PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));
        Spider.create(new SDBSPicPageProcessor())
                .addUrl("http://sdbs.db.aist.go.jp/sdbs/cgi-bin/img_disp.cgi?disptype=disp3&amp;imgdir=cds&amp;fname=CDS07852&amp;sdbsno=1")
                .thread(1)
                .run();
    }
}
