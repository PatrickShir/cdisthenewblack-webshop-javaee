package ejb;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ShoppingBeanLocal {

    String getSearch();
    void setSearch(String search);
    List<Record> getRecords();
    void setRecords(List<Record> records);
    void setCurrentProduct(Record currentProduct);
    Record getCurrentProduct();
}
