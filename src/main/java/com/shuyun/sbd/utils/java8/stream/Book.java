package com.shuyun.sbd.utils.java8.stream;

/**
 * Component:
 * Description:
 * Date: 16/7/10
 *
 * @author yue.zhang
 */
public class Book {

    private Long id;

    private String name;

    private int price;

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book(Long id, String name,int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
