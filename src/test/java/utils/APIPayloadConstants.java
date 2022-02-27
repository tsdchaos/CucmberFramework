package utils;

public class APIPayloadConstants {
    public static String createEmployeePayload(){
        String createEmployeePayload = "{\n" +
                "  \"emp_firstname\": \"Alan\",\n" +
                "  \"emp_lastname\": \"Tal\",\n" +
                "  \"emp_middle_name\": \"Smith\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"1983-01-01\",\n" +
                "  \"emp_status\": \"Employee\",\n" +
                "  \"emp_job_title\": \"SDET Engineer\"\n" +
                "}";
        return createEmployeePayload;
    }
}
