package ReactiveUsersMicroservice.utils;

public class Constants {
    public static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\."
            + "[a-zA-Z0-9-]+)*$";
    public static final int MINIMUM_PASSWORD_LENGTH = 3;
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DOMAIN_CRITERIA = "byDomain";
    public static final String LAST_NAME_CRITERIA = "byLastname";
    public static final String MINIMUM_AGE_CRITERIA = "byMinimumAge";
}
