package icarus.webmagic.processor;

import icarus.webmagic.model.HouseDetail;
import icarus.webmagic.model.SimpleHouse;
import icarus.webmagic.mysql.MysqlDatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class LianjiaHouseDetailProcessor implements PageProcessor {

    private Site             site            = Site.me().setDomain("sh.lianjia.com")
                                                 .setSleepTime(1);
    public List<SimpleHouse> handleHouseList = new ArrayList<SimpleHouse>();

    public LianjiaHouseDetailProcessor(List<SimpleHouse> handlerHouseList) {
        this.handleHouseList = handlerHouseList;
    }

    public void process(Page page) {
        if (!handleHouseList.isEmpty()) {
            Iterator<SimpleHouse> iter = handleHouseList.iterator();
            while (iter.hasNext()) {
                page.addTargetRequest(iter.next().getLink());
            }
            handleHouseList = new ArrayList<SimpleHouse>();
        }
        String title = page.getHtml().xpath("//h1[@class='title-box']/text()").get();
        String pricePerSquare = page.getHtml().xpath("//dd[@class='short']/text()").get();
        String totalPrice = page.getHtml().xpath("//strong[@class='ft-num']/text()").get() + "万";
        String totalArea = page.getHtml()
            .xpath("//div[@class='desc-text']/dl/dd/span[@class='em-text']/i/text()").get();
        if (totalArea != null) {
            totalArea = totalArea.substring(2);
        }
        String houseType = page.getHtml().xpath("div[@class='desc-text']/dl[5]/dd/text()").get();
        String district = page.getHtml().xpath("//a[@class='zone-name']/text()").get();
        String builtYear = page.getHtml()
            .xpath("div[@class='desc-text']/dl[@class='clear']/dd/text()").get();
        String toward = page.getHtml().xpath("div[@class='desc-text']/dl[6]/dd/text()").get();
        String floor = page.getHtml().xpath("div[@class='desc-text']/dl[7]/dd/text()").get();

        String seller = page.getHtml().xpath("p[@class='p-01']/a/text()").get();
        String telephone = page.getHtml()
            .xpath("div[@class='contact-panel']/span[@class='ft-num']/text()").get();
        if (telephone != null) {
            String mainPhone = telephone.substring(0, 10);
            String subPhone = telephone.substring(10);
            telephone = mainPhone + "转" + subPhone;
        }
        String link = page.getUrl().get();
        if (link.startsWith("http://sh.lianjia.com/ershoufang/")) {
            HouseDetail hd = new HouseDetail();
            hd.setLink(link);
            if (builtYear.contains("未知")) {
                hd.setBuiltYear(1900);
            } else {
                String[] bylist = builtYear.split("年");
                int by = new Integer(bylist[0]);
                hd.setBuiltYear(by);
            }

            String[] talist = totalArea.split("㎡");
            int ta = new Integer(talist[0]);
            hd.setTotalArea(ta);
            hd.setHouseEstate(district);
            hd.setHouseFloor(floor);
            hd.setHouseTowards(toward);
            hd.setHouseType(houseType);
            String[] ppslist = pricePerSquare.split(" ");
            int pps = new Integer(ppslist[0]);
            hd.setPricePerSquare(pps);
            hd.setSeller(seller);
            hd.setSellerPhone(telephone);
            hd.setTitle(title);
            String[] tplist = totalPrice.split("万");
            int tp = new Integer(tplist[0]);
            hd.setTotalPrice(tp);
            MysqlDatabaseHandler handler = new MysqlDatabaseHandler();
            try {
                handler.saveHouseDetailInfo(hd);
            } catch (SQLException e) {
                System.out.println("save housedetail error!");
            }
        }

    }

    //    private void saveAsFile(List<String> htmlList) {
    //        File f1 = new File("./1.txt");
    //        if (!f1.exists())
    //            try {
    //                f1.createNewFile();
    //            } catch (IOException e) {
    //                System.out.println("create file error!");
    //            }
    //        FileOutputStream out;
    //        try {
    //            out = new FileOutputStream(f1, true);
    //            out.write(htmlList.toString().getBytes());
    //            out.close();
    //        } catch (Exception e) {
    //            System.out.println("write file error!");
    //        }
    //    }

    public Site getSite() {
        return site;
    }

}
