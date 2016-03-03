package icarus.webmagic.processor;

import icarus.webmagic.mysql.MysqlDatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class LianjiaGetHousesProcessor implements PageProcessor {

    List<String> houseLinks = new ArrayList<String>();
    private Site site       = Site.me().setDomain("sh.lianjia.com").setSleepTime(1);

    public void process(Page page) {
        Selectable houses = page.getHtml().xpath("//div[@class='con-box']");
        //匹配每一条房源信息
        List<String> duplicatedDetailLinks = houses.regex(
            "http://sh\\.lianjia\\.com/ershoufang/[a-zA-Z]+\\d+.html").all();
        //去掉重复的链接
        for (String link : duplicatedDetailLinks) {
            if (!houseLinks.contains(link))
                houseLinks.add(link);
        }
        MysqlDatabaseHandler handler = new MysqlDatabaseHandler();
        for (String link : houseLinks) {
            try {
                handler.saveHouseInfo(link);
            } catch (SQLException e) {
                System.out.println("close sql connection fail!");
            }
        }
        //        page.putField("title", page.getHtml().xpath("//div[@class='headConLeft']/h1").toString());
        //        page.putField("salary", page.getHtml().xpath("//div[@class='salaNum']/strong").toString());
        //        page.putField("duty", page.getHtml().$("div.posMsg.p").toString());
        //page.putField("tags", page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
        //page.putField("content", page.getHtml().$("div.content").toString());
    }

    public Site getSite() {
        return site;

    }
}
