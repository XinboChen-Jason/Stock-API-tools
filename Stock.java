package u.com.example.chenx.tradeasapro;

/**
 * Created by CHENX on 2018-07-22.
 */

public class Stock {

    private String info, type, id, name, curPrice, lastClose, curOpen, high, low, volume, volumeInCurrency, upRate, upAmount;

    public Stock(String info, String type, String id, String name, String curPrice, String lastClose, String curOpen, String high, String low, String volume, String volumeInCurrency, String upRate, String upAmount) {
        this.info = info;
        this.type = type;
        this.id = id;
        this.name = name;
        this.curPrice = curPrice;
        this.lastClose = lastClose;
        this.curOpen = curOpen;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.volumeInCurrency = volumeInCurrency;
        this.upRate = upRate;
        this.upAmount = upAmount;
    }

    public Stock(String info, String type, String id, String name, String curPrice, String lastClose, String curOpen, String high, String low, String volume, String volumeInCurrency) {
        this.info = info;
        this.type = type;
        this.id = id;
        this.name = name;
        this.curPrice = curPrice;
        this.lastClose = lastClose;
        this.curOpen = curOpen;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.volumeInCurrency = volumeInCurrency;
        generateUpRateAndAmount();
    }

    public Stock(String info) {
        this.info = info;
        if (info.contains("hq_str_sh")){
            type = "sh";
        }else if(info.contains("hq_str_sz")){
            type = "sz";
        }else if(info.contains("hq_str_hk")){
            type = "hk";
        }else if(info.contains("hq_str_gb")){
            type = "gb";
        }
        id = info.substring(info.indexOf("_", info.indexOf("_") + 1) + 1, info.indexOf("="));
        String msg = info.substring(info.indexOf("=") + 2, info.indexOf(";") - 1);
        String[] msgR = msg.split(",");
        if (type.equals("sh") || type.equals("sz")) {
            name = msgR[0];
            curPrice = msgR[3];
            lastClose = msgR[2];
            curOpen = msgR[1];
            high = msgR[4];
            low = msgR[5];
            volume = msgR[8];
            volumeInCurrency = msgR[9];
            generateUpRateAndAmount();
        }else if(type.equals("hk")){
            name = msgR[1];
            curPrice = msgR[6];
            lastClose = msgR[3];
            curOpen = msgR[2];
            high = msgR[4];
            low = msgR[5];
            volume = msgR[12];
            volumeInCurrency = msgR[11];
            upRate = msgR[8];
            upAmount = msgR[7];
        }else if(type.equals("gb")){
            name = msgR[0];
            curPrice = msgR[1];
            lastClose = msgR[26];
            curOpen = msgR[5];
            high = msgR[6];
            low = msgR[7];
            volume = msgR[10];
            Double temp = (Integer.parseInt(volume) * Double.parseDouble(curPrice));
            volumeInCurrency = temp.toString();
            upRate = msgR[2];
            upAmount = msgR[4];
        }
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurPrice() {
        return curPrice;
    }

    public String getLastClose() {
        return lastClose;
    }

    public String getCurOpen() {
        return curOpen;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getVolume() {
        return volume;
    }

    public String getVolumeInCurrency() {
        return volumeInCurrency;
    }

    public String getUpRate() {
        return upRate;
    }

    public String getUpAmount() {
        return upAmount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurPrice(String curPrice) {
        this.curPrice = curPrice;
    }

    public void setLastClose(String lastClose) {
        this.lastClose = lastClose;
    }

    public void setCurOpen(String curOpen) {
        this.curOpen = curOpen;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setVolumeInCurrency(String volumeInCurrency) {
        this.volumeInCurrency = volumeInCurrency;
    }

    public void setUpRate(String upRate) {
        this.upRate = upRate;
    }

    public void setUpAmount(String upAmount) {
        this.upAmount = upAmount;
    }

    public void generateUpRateAndAmount() {
        Double tempUpRate = ((Double.parseDouble(curPrice) / Double.parseDouble(lastClose)) - 1)*100;
        upRate = tempUpRate.toString();
        Double tempUpAmount = Double.parseDouble(curPrice) - Double.parseDouble(lastClose);
        upAmount = tempUpAmount.toString();
    }
}
