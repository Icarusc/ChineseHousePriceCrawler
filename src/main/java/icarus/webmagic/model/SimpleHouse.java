package icarus.webmagic.model;

public class SimpleHouse {
    public String  link;
    public boolean isCrawled;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isCrawled() {
        return isCrawled;
    }

    public void setCrawled(boolean isCrawled) {
        this.isCrawled = isCrawled;
    }
}
