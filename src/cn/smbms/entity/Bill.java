package cn.smbms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author zc
 * @create 2020-7-15
 */
public class Bill {

    private Integer id;//订单编号
    private String productName;//商品名称
    private String category;//商品分类
    private int count;//商品数量
    private double totalPrice;//总价
    private int isPay;//1表示已付款  2表示未付款
    private Integer createdBy;//创建人

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;//创建时间
    private Integer modifyBy;//修改人

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date modifyDate;//修改时间
    private int providerId;//供应商编号


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", count=" + count +
                ", totalPrice=" + totalPrice +
                ", isPay=" + isPay +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", modifyBy=" + modifyBy +
                ", modifyDate=" + modifyDate +
                ", providerId=" + providerId +
                '}';
    }
}
