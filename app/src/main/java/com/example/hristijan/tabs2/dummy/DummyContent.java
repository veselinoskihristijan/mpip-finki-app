package com.example.hristijan.tabs2.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 5;

    private static ArrayList<String> courses = new ArrayList<>();

    static {
        courses.add("Напредно програмирање");
        courses.add("Теорија на информации со дигитални комуникации");
        courses.add("Основи на компјутерската графика");
        courses.add("Компајлери");
        courses.add("Методологија на истражувањето во ИКТ");
        courses.add("Управување со ИКТ проекти");
    }

    private static ArrayList<String> location = new ArrayList<>();

    static {
        location.add("Барака 3.2 (ЕТ)");
        location.add("123 (ТМФ)");
        location.add("Polar Cape");
        location.add("223 (М)");
        location.add("Л3");
        location.add("Барака 2.1");
    }

    static {
        // Add some sample items.
        for (int i = 0; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position), getLocationName(position), getCourseName(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static String getCourseName(int position){
        return courses.get(position);
    }

    public static String getLocationName(int position){
        return location.get(position);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;
        public final String locationName;
        public final String courseNameD;

        public DummyItem(String id, String content, String details, String locationName, String courseNameD) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.locationName = locationName;
            this.courseNameD = courseNameD;
        }

        @Override
        public String toString() {
            return "Аудиториски вежби по предметот " + courseNameD;
        }
    }
}
