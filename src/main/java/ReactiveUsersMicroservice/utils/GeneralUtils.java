package ReactiveUsersMicroservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ReactiveUsersMicroservice.utils.Constants.DATE_FORMAT;

public class GeneralUtils {
    public static boolean notNullOrEmpty(String str) {
        return str != null && !str.isBlank();
    }

    public static boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
