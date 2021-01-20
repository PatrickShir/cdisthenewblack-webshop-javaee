package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless(name = "FetchDataEJB")
public class ReadDataBean implements ReadDataBeanLocal {

    @PersistenceContext(name = "webshopUnit")
    EntityManager em;

    public ReadDataBean() {
    }

    @Override
    public List<User> fetchAllCustomers() {
        TypedQuery<User> userQuery = em.createQuery("SELECT o FROM User o WHERE o.role = 0 OR o.role = 1", User.class);
        return userQuery.getResultList();
    }

    @Override
    public List<OrderHistory> fetchOrderByCustomerId(Long id) {
        TypedQuery<OrderHistory> orderQuery = em.createQuery("SELECT o FROM OrderHistory o WHERE o.user.id = :id"
                , OrderHistory.class).setParameter("id", id);
        return orderQuery.getResultList();
    }

    @Override
    public List<OrderInfoHistory> fetchItemsByOrderId(Long id) {
        TypedQuery<OrderInfoHistory> orderInfoQuery = em.createQuery("SELECT o FROM OrderInfoHistory o WHERE o.order.id = :id",
                OrderInfoHistory.class).setParameter("id", id);
        return orderInfoQuery.getResultList();
    }

    @Override
    public List<Record> getAllRecords() {
       return em.createQuery("SELECT o FROM Record o", Record.class).getResultList();
    }

    @Override
    public User fetchUserById(Long id) {
        return em.createQuery("SELECT u FROM User u WHERE u.id = :id",User.class).setParameter("id",id).getSingleResult();
    }


}
