package com.shuyun.sbd.utils.java8.stream;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    private enum Status{
        OPEN,CLOSED
    }

    private static final class Task{
        private final Status status;
        private final Integer points;

        public Task(final Status status, final Integer points) {
            this.status = status;
            this.points = points;
        }

        public Status getStatus() {
            return status;
        }

        public Integer getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "status=" + status +
                    ", points=" + points +
                    '}';
        }
    }

    private static List<Task> initTask(){
        return Arrays.asList(
                new Task( Status.OPEN, 5 ),
                new Task( Status.OPEN, 13 ),
                new Task( Status.CLOSED, 8 )
        );
    }

    private static List<Task> initTask2(){
        int size = 100000;
        List<Task> tasks = new ArrayList<>(size);
        for(int i = 1 ; i <= size; i++){
            tasks.add(new Task( Status.OPEN, i ));
        }
        return tasks;
    }

    private static void sumTask(){
        List<Task> tasks = initTask2();
        long st = System.currentTimeMillis();
        int sum = tasks.stream().parallel().mapToInt(Task::getPoints).reduce(0,Integer::sum);
//        int sum = tasks.stream().mapToInt(Task::getPoints).sum();
        System.out.println("host: " + (System.currentTimeMillis() - st) + " ms");
        System.out.println(sum);
    }

    private static List<Book> initBook(){
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "book1",100));
        books.add(new Book(2L, "book2",200));
        books.add(new Book(3L, "book3",300));
        return books;
    }

    public static void queryBook(){

        List<Book> books = initBook();

        List<Book> newBooks = new ArrayList<>();

        newBooks.addAll(books.stream().filter(book -> book.getId().equals(2L)).collect(Collectors.toList()));

        newBooks.stream().forEach(System.out::println);
    }

    public static void sumBook(){
        List<Book> books = initBook();
//        int sum = books.stream().mapToInt(Book::getPrice).sum();
        int sum = books.stream().mapToInt(book -> book.getPrice()).sum();
        System.out.println(sum);
    }



    public static void main(String [] args){
        sumTask();

        Class clazz  = Task.class;
        clazz.getSuperclass();
        clazz.getInterfaces();
        clazz.getMethods();
    }


}
