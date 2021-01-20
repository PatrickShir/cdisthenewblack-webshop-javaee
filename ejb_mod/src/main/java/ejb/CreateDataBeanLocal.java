package ejb;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CreateDataBeanLocal {
    void fillDb();
    void addNewOrder(User user, List<OrderInfoHistory> itemList, int orderTotal);
    void premiumUpgrade(User user);
}
