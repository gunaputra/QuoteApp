package com.example.quotesapp;

public class QuotesModelClass {

    Integer id;
    String name;
    String quotes;

    public QuotesModelClass(String name, String quotes) {
        this.name = name;
        this.quotes = quotes;
    }

    public QuotesModelClass(Integer id, String name, String quotes) {
        this.id = id;
        this.name = name;
        this.quotes = quotes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
