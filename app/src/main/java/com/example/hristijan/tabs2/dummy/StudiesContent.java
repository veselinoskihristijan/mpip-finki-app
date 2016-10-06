package com.example.hristijan.tabs2.dummy;

import com.example.hristijan.tabs2.items.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hristijan on 8/16/2016.
 */
public class StudiesContent {

    public static final List<Study> ITEMS = new ArrayList<Study>();

    static {
//
        ITEMS.add(new Study(1, "Компјутерски науки и инженерство", "", 1));
        ITEMS.add(new Study(2, "Мрежни технологии", "", 1));
        ITEMS.add(new Study(3, "Примена на е-технологии", "", 1));
        ITEMS.add(new Study(4, "Информатика и компјутерско инженерство", "", 1));
        ITEMS.add(new Study(5, "Кодирање и криптографија", "", 2));
        ITEMS.add(new Study(6, "Управување во информатички технологии", "", 2));
        ITEMS.add(new Study(7, "Компјутерски науки и инженерство", "", 3));
        ITEMS.add(new Study(8, "Информатика", "", 3));
    }




}
