package ReactiveUsersMicroservice.utils;

import ReactiveUsersMicroservice.utils.exceptions.InvalidInputException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.time.Period;

import static ReactiveUsersMicroservice.utils.Constants.*;

public class UserUtils {

    /* Check if the email is not null or empty and matches the email regex pattern */
    public static void isValidEmail(String email) {
        if(GeneralUtils.notNullOrEmpty(email) && Pattern.matches(EMAIL_REGEX_PATTERN, email))
            return;

        throw new InvalidInputException("Invalid email address %s".formatted(email));
    }

    /* Check if the password is not null or empty and matches the minimum length */
    public static void isValidPassword(String password) {
        if(GeneralUtils.notNullOrEmpty(password) && password.length() >= MINIMUM_PASSWORD_LENGTH)
            return;

        throw new InvalidInputException("Invalid password %s".formatted(password));
    }

    /* Check if the date is not null or empty and matches the date format dd-mm-yyyy */
    public static void isValidDate(String date) {
        if(GeneralUtils.notNullOrEmpty(date) && GeneralUtils.isValidDateFormat(date))
            return;

        throw new InvalidInputException("Invalid date %s".formatted(date));
    }

    public static void isValidName(String name) {
        if(GeneralUtils.notNullOrEmpty(name))
            return;

        throw new InvalidInputException("Invalid name %s".formatted(name));
    }

    public static boolean isOlderThan(String birthdate, String minimumAgeInYears) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate birthdateDate = LocalDate.parse(birthdate, dateFormat);
        int minimumAge = Integer.parseInt(minimumAgeInYears);
        int ageInYears = Period.between(birthdateDate, LocalDate.now()).getYears();
        return ageInYears >= minimumAge;
    }
}
