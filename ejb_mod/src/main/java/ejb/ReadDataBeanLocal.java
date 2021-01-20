package ejb;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ReadDataBeanLocal {
    List<User> fetchAllCustomers();

    List<OrderHistory> fetchOrderByCustomerId(Long id);

    List<OrderInfoHistory> fetchItemsByOrderId(Long id);

    List<Record> getAllRecords();

    User fetchUserById(Long id);

}