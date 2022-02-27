package API;

import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.print.attribute.standard.RequestingUserName;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDU2NjMyMjMsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY0NTcwNjQyMywidXNlcklkIjoiMzQ3NyJ9.Yp21hA2AM-E2VgaRIoyeviSZlBg2zQzPB2E5TxlJ6Xg";
    static String employee_ID;
    @Test
    public void bGetCreatedEmployee(){

        //preparing the request to get an employee
        RequestSpecification preparedRequest = given().header("Content-Type", "application/json").
                header("Authorization", token).
                queryParam("employee_id",employee_ID);

        Response response = preparedRequest.when().get("/getOneEmployee.php");

        //prettyprint does the same as system.out.println();
        response.prettyPrint();
        String empID = response.jsonPath().getString("employee.employee_id");
        boolean comparingEmployeeIDs = empID.contentEquals(employee_ID);
        //adding asserstion to get true return from boolean
        Assert.assertTrue(comparingEmployeeIDs);
        response.then().assertThat().statusCode(200);

        String lastName = response.jsonPath().getString("employee.emp_lastname");
        Assert.assertTrue(lastName.contentEquals("Stan"));
    }
//To create an employee
    @Test
    public void aCreateEmployee(){
        RequestSpecification preparedRequest = given().header("Content-Type", "application/json").
                header("Authorization", token).body(" {\n"+
                        "  \"emp_firstname\": \"Alan\",\n" +
                        "  \"emp_lastname\": \"Stan\",\n" +
                        "  \"emp_middle_name\": \"MS\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1987-01-01\",\n" +
                        "  \"emp_status\": \"Employee\",\n" +
                        "  \"emp_job_title\": \"SDET Engineer\"\n"+
                        "}");
        Response response = preparedRequest.when().post("/createEmployee.php");
        response.prettyPrint();

        //We use jsonPath() to get specific information from the json object
        employee_ID = response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_ID);

        //Assertion
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("Employee.emp_middle_name", equalTo("MS"));
        response.then().assertThat().body("Message", equalTo("Employee Created"));
       // response.then().assertThat().body("Server", equalTo("Apache/2.4.39 (Win64) PHP/7.2.18"));
        response.then().assertThat().body("Employee.emp_job_title", equalTo("SDET Engineer"));
    }

    @Test
    public void cUpdateEmployee(){
        //update the existing employee
        RequestSpecification preparedRequest = given().header("Authorization", token).
                header("Content-Type","application/json").body("{\n" +
                        "  \"employee_id\": \""+employee_ID+"\",\n" +
                        "  \"emp_firstname\": \"Tal\",\n" +
                        "  \"emp_lastname\": \"Tanner\",\n" +
                        "  \"emp_middle_name\": \"MS\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2002-02-19\",\n" +
                        "  \"emp_status\": \"employee\",\n" +
                        "  \"emp_job_title\": \"SDET\"\n" +
                        "}");

        Response response = preparedRequest.when().put("updateEmployee.php");
        response.prettyPrint();
        response.then().assertThat().body("Message", equalTo("Employee record Updated"));
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void dgetUpdatedEmployee(){
        RequestSpecification preparedRequest = given().header("Content-Type", "application/json").
                header("Authorization", token).
                queryParam("employee_id",employee_ID);

        Response response = preparedRequest.when().get("/getOneEmployee.php");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        String middleName = response.jsonPath().getString("employee.emp_middle_name");

    }
}
