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
public class ConsultationsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ConsultationItem> ITEMS = new ArrayList<ConsultationItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ConsultationItem> ITEM_MAP = new HashMap<String, ConsultationItem>();

    private static final int COUNT = 10;

//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//    }

    public static void addItem(ConsultationItem item) {
        ITEMS.add(item);
        //ITEM_MAP.put(item.id, item);
    }

    private static ConsultationItem createDummyItem(int position) {
        return new ConsultationItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ConsultationItem {
        //public final String profImage;
        public final String profName;
        public final String location;
        public final String time;

        public ConsultationItem( String profName, String location, String time) {
            //this.profImage = profImage;
            this.profName = profName;
            this.location = location;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Консултации на " + profName + " во " + time + "ч во " + location;
        }
    }
}
