package com.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> x = new ArrayList<>();
        x.add(1);
        List<Integer> y = new ArrayList<>();
        y.add(5);
        y.add(x.remove(0));
        System.out.println(y.toString());
        System.out.println(x.toString());
    }
}
