package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Stateful(name = "LoggedInUserEJB")
public class ShoppingCartBean implements ShoppingCartBeanLocal {


    private List<CartItem> cart;
    private int cartLength;
    private List<OrderInfoHistory> orderInfoHistoryList;

    @EJB
    private ReadDataBeanLocal fetchDataBean;

    @EJB
    private CreateDataBeanLocal createDataBean;


    public ShoppingCartBean() {
        cart = new ArrayList<>();
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public List<CartItem> getCart() {
        return this.cart;
    }

    public int getCartLength() {
        return cartLength;
    }

    public void setCartLength(int cartLength) {
        this.cartLength = cartLength;
    }

    public List<OrderInfoHistory> getOrderInfoHistoryList() {
        return orderInfoHistoryList;
    }

    public void setOrderInfoHistoryList(List<OrderInfoHistory> orderInfoHistoryList) {
        this.orderInfoHistoryList = orderInfoHistoryList;
    }

    @Override
    public void addToCart(int id) {
        if (cartLength < 10_000) {

            CartItem cartItem = getCartItemFromId(id);
            if (cartItem != null) {
                cartItem.setQty(cartItem.getQty() + 1);
            } else {
                for (Record record : fetchDataBean.getAllRecords()) {
                    if (record.getId() == id) {
                        cart.add(new CartItem(record));
                    }
                }
            }
            updateCartLength();
        }
    }

    @Override
    public void clearCart() {
        this.cart.clear();
        this.cartLength = 0;
    }

    @Override
    public CartItem getCartItemFromId(int id) {
        for (CartItem cartItem : cart) {
            if (cartItem.getRecord().getId() == id) {
                return cartItem;
            }
        }
        return null;
    }

    @Override
    public void updateCartLength() {
        int count = 0;
        for (CartItem cartItem : cart) {
            count += cartItem.getQty();
        }
        this.cartLength = count;
    }


    public int getTotalCartSum(User user) {
        int result = 0;
        for (CartItem cartItem : cart) {
            result += cartItem.getTotalPrice();
        }
        if (user.getRole() == Role.PREMIUM) {
            double rounded = Math.round(result * 0.9);
            result = (int) rounded;
        }
        return result;

    }

    public List<OrderInfoHistory> getCartAsOrderInfoList() {

        this.orderInfoHistoryList = new ArrayList<>();

        for (CartItem cartItem : this.cart) {
            OrderInfoHistory orderInfoHistory = new OrderInfoHistory();
            orderInfoHistory.setRecord(cartItem.getRecord());
            orderInfoHistory.setQuantity(cartItem.getQty());
            orderInfoHistoryList.add(orderInfoHistory);
        }

        return this.orderInfoHistoryList;
    }

    public int getTotalSum(User user) {
        int sum = 0;
        for (OrderInfoHistory orderInfoHistory : this.orderInfoHistoryList) {
            sum += orderInfoHistory.getTotalPrice();
        }
        if (user.getRole() == Role.PREMIUM) {
            double rounded = Math.round(sum * 0.9);
            sum = (int) rounded;
        }
        return sum;
    }

    public String getTotPriceFromOrderInfoList(User user) {
        int sum = 0;
        for (OrderInfoHistory orderInfoHistory : this.orderInfoHistoryList) {
            sum += orderInfoHistory.getTotalPrice();
        }
        if (user.getRole() == Role.PREMIUM) {
            double rounded = Math.round(sum * 0.9);
            sum = (int) rounded;
        }
        createDataBean.premiumUpgrade(user);
        NumberFormat nf = NumberFormat.getInstance(new Locale("sv", "SE"));
        return nf.format(sum);
    }

    public void updateQuantity(Long recordId, int quantity) {
        for (CartItem cartItem : cart) {

            if (cartItem.getRecord().getId() == recordId) {
                cartItem.setQty(quantity);
            }
        }
    }
}
