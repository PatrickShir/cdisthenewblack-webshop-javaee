package ejb;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.util.List;

@Stateful(name = "ShoppingEJB")
public class ShoppingBean implements ShoppingBeanLocal{

    private String search;
    private List<Record> records;
    private Record currentProduct;

    public ShoppingBean() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search.trim();
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Record getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Record currentProduct) {
        this.currentProduct = currentProduct;
    }
}
