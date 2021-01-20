package ejb;

import java.text.NumberFormat;
import java.util.Locale;

public class CartItem {

    private Record record;
    private int qty;

    public CartItem(Record record) {
        this.record = record;
        this.qty = 1;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotalPrice() {
        return record.getPrice() * qty;
    }

    public String totalPriceFormatted() {
        NumberFormat nf = NumberFormat.getInstance(new Locale("sv", "SE"));
        return nf.format(record.getPrice() * qty);
    }
}
