package com.example.vcartbusbooking.utils;

import java.util.ArrayList;



// Root Class (DashboardResponse)// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1


public class DashboardResponse{
    public int statuscode;
    public boolean success;
    public String message;
    public Data data;

    public static class Customers{
        public int total;
        public int active;
        public ArrayList<Top> top;
        public ArrayList<Latest> latest;
    }

    public static class Data{
        public Day day;
        public Week week;
        public Month month;
        public Year year;
        public Orders orders;
        public Customers customers;
        public Products products;
        public Visitors visitors;
    }

    public static class Day{
        public String day;
        public String reports;
    }

    public static class Latest{
        public String name;
        public int order_status;
        public String payment_status;
        public String payment_method;
        public String shipping_method;
        public String order_id;
        public int country;
        public String deliveryaddress;
        public int totalamt;
        public String record_date;
        public String storename;
        public int id;
        public String mobile;
        public Object email;
        public String last_login;
        public String created_at;
        public Object last_ordered;
    }

    public static class Month{
        public String month;
        public ArrayList<Report> reports;
    }

    public static class Orders{
        public int total_count;
        public String total_value;
        public int success_count;
        public String success_value;
        public int processing_count;
        public int processing_value;
        public int shipped_count;
        public String shipped_value;
        public int completed_count;
        public String completed_value;
        public ArrayList<Latest> latest;
    }

    public static class Product{
        public int id;
        public String esin;
        public String sku;
        public String name;
        public String imageurl;
        public int quantity;
        public String category_name;
    }

    public static class Products{
        public ArrayList<TopSelling> top_selling;
    }

    public static class Report{
        public String day;
        public String reports;
        public ArrayList<String> categories;
        public ArrayList<Series> series;
    }

    public static class Series{
        public String name;
        public ArrayList<Integer> data;
    }

    public static class Top{
        public String name;
        public String mobile;
        public String email;
        public int id;
        public String total_ordervalue;
        public int total_ordercount;
    }

    public static class TopSelling{
        public String product_id;
        public int order_count;
        public String total_amount;
        public Product product;
    }

    public static class Visitors{
        public int pageview;
        public int productview;
        public int addtocart;
        public int checkout;
        public int purchase;
    }

    public static class Week{
        public String week;
        public ArrayList<Report> reports;
    }

    public static class Year{
        public String year;
        public Report reports;
    }


}



