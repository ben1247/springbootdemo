package com.shuyun.sbd.utils.designPatternsDemo.builder;

/**
 * Component:
 * Description:
 * Date: 16/9/4
 *
 * @author yue.zhang
 */
public class Book {

    private final Long id;

    private final String name;

    private final Double price;

    public Book(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public static class Builder{

        private final Long id;

        private String name;

        private Double price;

        public Builder(Long id){
            this.id = id;
        }

        public Builder name(String val){
            this.name = val;
            return this;
        }

        public Builder price(Double val){
            this.price = val;
            return this;
        }

        public Book build(){
            return new Book(this);
        }
    }

}
