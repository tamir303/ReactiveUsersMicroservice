package ReactiveUsersMicroservice.utils;

import ReactiveUsersMicroservice.utils.exceptions.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ReactiveUsersMicroservice.utils.Constants.DATE_FORMAT;

public class DepartmentUtils {
    public static void isValidDeptId(String deptId) {
        if(GeneralUtils.notNullOrEmpty(deptId))
            return;
        throw new InvalidInputException("Invalid deptId %s".formatted(deptId));
    }

    public static void isValidDepartmentName(String departmentName) {
        if(GeneralUtils.notNullOrEmpty(departmentName))
            return;
        throw new InvalidInputException("Invalid departmentName %s".formatted(departmentName));
    }

    public static String localDateToFormattedString() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        return localDate.format(dateTimeFormatter);
    }
}
