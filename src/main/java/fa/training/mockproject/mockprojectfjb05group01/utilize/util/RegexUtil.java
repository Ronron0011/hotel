package fa.training.mockproject.mockprojectfjb05group01.utilize.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil  {
    public static boolean validateEmail(String email) {
        String EMAIL_REGEX = "^([a-z0-9_.-]+(?:[._-][a-z0-9]+)*)@([a-z0-9]+(?:[.-][a-z0-9]+)*\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean validatePassword(String password, Map<String, String> maps) {

        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern upperCasePattern = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        boolean flag = true;

        if (password.isEmpty()) {
            maps.put("errorMessage", "Password not be empty");
            flag = false;
        } else if (password.length() < 6) {
            maps.put("errorMessage", "Password length must have least 6 character");
            flag = false;
        } else if (!specialCharPatten.matcher(password).find()) {
            maps.put("errorMessage", "Password must have latest one special character");
            flag = false;
        } else if (!upperCasePattern.matcher(password).find()) {
            maps.put("errorMessage", "Password must have latest one uppercase character");
            flag = false;
        }else if (!lowerCasePatten.matcher(password).find()) {
            maps.put("errorMessage", "Password must have latest one lowercase character");
            flag = false;
        } else if (!digitCasePatten.matcher(password).find()) {
            maps.put("errorMessage", "Password must have latest one digit character");
            flag = false;
        }
        return flag;

    }
}
