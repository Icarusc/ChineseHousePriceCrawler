package icarus.webmagic.model;


public class HouseDetail {

    String link;

    String title;

    int    PricePerSquare;

    int    totalPrice;

    int    totalArea;

    String houseType;

    String houseEstate;

    int    builtYear;

    String houseTowards;

    String houseFloor;

    String seller;

    String sellerPhone;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseEstate() {
        return houseEstate;
    }

    public void setHouseEstate(String houseEstate) {
        this.houseEstate = houseEstate;
    }

    public int getPricePerSquare() {
        return PricePerSquare;
    }

    public void setPricePerSquare(int pricePerSquare) {
        PricePerSquare = pricePerSquare;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(int totalArea) {
        this.totalArea = totalArea;
    }

    public int getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(int builtYear) {
        this.builtYear = builtYear;
    }

    public String getHouseTowards() {
        return houseTowards;
    }

    public void setHouseTowards(String houseTowards) {
        this.houseTowards = houseTowards;
    }

    public String getHouseFloor() {
        return houseFloor;
    }

    public void setHouseFloor(String houseFloor) {
        this.houseFloor = houseFloor;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

}
