package Dashboard.Model;

public class Currency implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String currencyCode;
    private String currencyName;
    private float banknoteBuying;
    private float ForexBuying;
    private float banknoteSelling;
    private float ForexSelling;
    private float crossrateUSD;
    private float crossrateOther;
      public float getBanknoteBuying() {
        return banknoteBuying;
    }

    public void setBanknoteBuying(float banknoteBuying) {
        this.banknoteBuying = banknoteBuying;
    }

    public float getBanknoteSelling() {
        return banknoteSelling;
    }

    public void setBanknoteSelling(float banknoteSelling) {
        this.banknoteSelling = banknoteSelling;
    }

    public float getBanknoteBuyingForex() {
        return ForexBuying;
    }

    public void setBanknoteBuyingForex(float ForexBuying) {
        this.ForexBuying = ForexBuying;
    }

    public float getBanknoteSellingForex() {
        return ForexSelling;
    }

    public void setBanknoteSellingForex(float ForexSelling) {
        this.ForexSelling = ForexSelling;
    }

    public float getCrossrateUSD() {
        return crossrateUSD;
    }

    public void setCrossrateUSD(float crossrateUSD) {
        this.crossrateUSD = crossrateUSD;
    }

    public float getCrossrateOther() {
        return crossrateOther;
    }

    public void setCrossrateOther(float crossrateOther) {
        this.crossrateOther = crossrateOther;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }


}
