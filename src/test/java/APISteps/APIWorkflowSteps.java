package APISteps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class APIWorkflowSteps {
    RequestSpecification request;
    Response response;
    public static String employee_id;
    @Given("a request is prepared for creating an employee")
    public void a_request_is_prepared_for_creating_an_employee() {
        request = given().header(APIConstants.Header_Content_Type, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization, GenerateTokenSteps.token).
                body(APIPayloadConstants.createEmployeePayload());
    }


    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        response = request.when().post(APIConstants.CREATE_EMPLOYEE_URI);

    }
    @Then("the status code for creating an employee is {int}")
    public void the_status_code_for_creating_an_employee_is(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }
    @Then("the employee created contains key {string} and value {string}")
    public void the_employee_created_contains_key_and_value(String message, String messageCreated) {
        response.then().assertThat().body(message, equalTo(messageCreated));
    }
    @Then("the employee id {string} is stored as a global variable to be used for other calls")
    public void the_employee_id_is_stored_as_a_global_variable_to_be_used_for_other_calls(String empID) {
        employee_id = response.jsonPath().getString(empID);
        System.out.println(employee_id);
    }

    // ------------------------------------------------------------------------------------
    @Given("a request is prepared to get the created employee using api call")
    public void a_request_is_prepared_to_get_the_created_employee_using_api_call() {
        request = given().header(APIConstants.Header_Content_Type, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization, GenerateTokenSteps.token).queryParam("employee_id", employee_id);
    }
    @When("a GET call is made to retrieve the created employee")
    public void a_get_call_is_made_to_retrieve_the_created_employee() {
        response = request.when().get(APIConstants.GET_ONE_EMPLOYEE_URI);
    }
    @Then("the status code for this employee is {int}")
    public void the_status_code_for_this_employee_is(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }
    @Then("the employee dats we get having ID {string} should match with the globally stored employee id")
    public void the_employee_dats_we_get_having_id_should_match_with_the_globally_stored_employee_id(String empIDTemp) {
        String tempID = response.jsonPath().getString(empIDTemp);
        Assert.assertEquals(tempID, employee_id);
    }
    @Then("the retrieved data at {string} object matches the data used to create an employee with employee id {string}")
    public void the_retrieved_data_at_object_matches_the_data_used_to_create_an_employee_with_employee_id(String employeeObject, String responseEmpID, DataTable dataTable) {
        List<Map<String, String>> expectedData = dataTable.asMaps(String.class, String.class);

        Map<String, String> actualData = response.body().jsonPath().get(employeeObject);

        for (Map<String, String> map: expectedData) {
            Set<String> keys = map.keySet();
            for(String key: keys){
                String expectedValue = map.get(key);
                String actualValue = actualData.get(key);
                Assert.assertEquals(expectedValue, actualValue);
            }
        }
        String empID = response.body().jsonPath().getString(responseEmpID);
        Assert.assertEquals(empID, employeeObject);
    }

}
