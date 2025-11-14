package ru.yandex.practicum.model;

public class OrderPojo {
    //поля для создания заказа
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer renTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Integer getTrack() {
        return track;
    }

    public OrderPojo setTrack(Integer track) {
        this.track = track;
        return this;
    }

    private Integer track;

    public String getFirstName() {
        return firstName;
    }

    public OrderPojo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public OrderPojo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderPojo setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public OrderPojo setMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OrderPojo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Integer getRenTime() {
        return renTime;
    }

    public OrderPojo setRenTime(Integer renTime) {
        this.renTime = renTime;
        return this;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public OrderPojo setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public OrderPojo setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String[] getColor() {
        return color;
    }

    public OrderPojo setColor(String[] color) {
        this.color = color;
        return this;
    }

}

