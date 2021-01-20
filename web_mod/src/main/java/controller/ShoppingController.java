package controller;

import ejb.*;
import ejb.Record;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ShoppingController implements Serializable {

    @EJB
    private ReadDataBeanLocal fetchDataBean;
    @EJB
    private ShoppingBeanLocal shoppingBean;


    @PostConstruct
    public void initial() {
        shoppingBean.setRecords(fetchDataBean.getAllRecords());
    }

    public String getSearch() {
        return shoppingBean.getSearch();
    }

    public void setSearch(String search) {
        shoppingBean.setSearch(search);
    }


    public void search() {
        List<Record> filteredRecords = fetchDataBean.getAllRecords().parallelStream().filter(str ->
                str.getArtist().toLowerCase().contains(shoppingBean.getSearch().toLowerCase()) ||
                        str.getTitle().toLowerCase().contains(shoppingBean.getSearch().toLowerCase())).collect(Collectors.toList());
        shoppingBean.setRecords(filteredRecords);
        getRecords();
    }

    public List<Record> getRecords() {
        return shoppingBean.getRecords();
    }


    public String sendProduct(Record chosenProduct) {
        shoppingBean.setCurrentProduct(chosenProduct);
        return "productInfo";
    }
    public Record showProduct() {
        return shoppingBean.getCurrentProduct();
    }

}
