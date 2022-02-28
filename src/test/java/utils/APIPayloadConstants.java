package utils;

import org.json.JSONObject;

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

    public static String createEmployeeBody(){
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", "Grace");
        obj.put("emp_lastname", "Horwatz");
        obj.put("emp_middle_name", "MS");
        obj.put("emp_gender", "F");
        obj.put("emp_birthday", "1982-01-20");
        obj.put("emp_status", "Employee");
        obj.put("emp_job_title", "QA");

        return obj.toString();
    }

    public static String payloadMoreDynamic(String emp_firstname, String emp_lastname,
                                            String emp_middle_name, String emp_gender,
                                            String emp_birthday,
                                            String emp_status, String emp_job_title){

        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", emp_firstname);
        obj.put("emp_lastname", emp_lastname);
        obj.put("emp_middle_name", emp_middle_name);
        obj.put("emp_gender", emp_gender);
        obj.put("emp_birthday", emp_birthday);
        obj.put("emp_status", emp_status);
        obj.put("emp_job_title", emp_job_title);

        return obj.toString();
    }

    public static String updateEmployeeBody(){
        JSONObject obj = new JSONObject();
        obj.put("employee_id", "27288A");
        obj.put("emp_firstname", "Grace");
        obj.put("emp_lastname", "Horwatz");
        obj.put("emp_middle_name", "MS");
        obj.put("emp_gender", "F");
        obj.put("emp_birthday", "1982-01-20");
        obj.put("emp_status", "Employee");
        obj.put("emp_job_title", "QA");
        return obj.toString();
    }
}
