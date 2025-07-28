package com.foodDelivery.util;

import java.util.Random;

public class UserIDGenerator {

    public static int generateUserId() {
        long timestamp = System.currentTimeMillis(); // current time in ms
        int randomNum = new Random().nextInt(900) + 100; // random 3-digit number

        String idStr = (timestamp + "" + randomNum).substring(4, 13); // take 9 digits
        return Integer.parseInt(idStr);
    }
}
