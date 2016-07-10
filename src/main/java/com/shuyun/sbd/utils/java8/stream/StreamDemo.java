package com.shuyun.sbd.utils.java8.stream;

import com.shuyun.sbd.utils.java8.stream.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component: 迭代器
 * Description:
 * Date: 16/7/9
 *
 * @author yue.zhang
 */
public class StreamDemo {

    public static void main(String [] args){

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "book1"));
        books.add(new Book(2L, "book2"));
        books.add(new Book(3L, "book3"));

        List<Book> newBooks = new ArrayList<>();

        newBooks.addAll(books.stream().filter(book -> book.getId().equals(2L)).collect(Collectors.toList()));

        newBooks.stream().forEach(System.out::println);

    }

}
