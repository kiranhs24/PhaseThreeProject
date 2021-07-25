package step_defs;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestValidation {

	private final Logger logger = LogManager.getLogger(RequestValidation.class.getName());
	RequestSpecification request;
	Response response;

	@Given("^The base url of API is \"([^\"]*)\"$")
	public void the_base_url_of_API_is(String baseUri) throws Throwable {
		
		PropertyConfigurator.configure("log4j.properties");
		RestAssured.baseURI = baseUri;
		request = RestAssured.given();

	}

	@When("^The path \"([^\"]*)\" and request method \"([^\"]*)\" is used to hit the API$")
	public void the_path_and_request_method_is_used_to_hit_the_API(String path, String requestmethod) throws Throwable {

		switch (requestmethod) {
		case "GET":
			response = request.get(path);
			logger.info("Request Method == " + "[ " + requestmethod + " ]");
			logger.info("Requested for API == " + RestAssured.baseURI + path);
			break;

		case "POST":
			String requestBody = "{\"name\":\"Kiran\",\"job\":\"Director of QA\"}";
			logger.info("Request body used == " + requestBody);
			response = request.contentType(ContentType.JSON).body(requestBody).post(path);
			logger.info("Request Method == " + "[ " + requestmethod + " ]");
			logger.info("Requested for API == " + RestAssured.baseURI + path);
			break;

		default:
			logger.error("No request method was specified");
			break;
		}

	}

	@Then("^The response code should be (\\d+)$")
	public void the_response_code_should_be(int responseCode) throws Throwable {

		if (responseCode == 200) {

			logger.info("Response code returned == " + responseCode);
			response.then().assertThat().statusCode(200);

		} else if (responseCode == 201) {

			logger.info("Response code returned == " + responseCode);
			response.then().assertThat().statusCode(201);

		} else {

			logger.warn("Something went wrong !!");

		}

	}

	@Given("^The query parameters is as follows$")
	public void the_query_parameters_is_as_follows(DataTable table) throws Throwable {
		
		Map<String, String> map = table.asMap(String.class, String.class);

		for (String key : map.keySet()) {

			String value = map.get(key);
			request.queryParam(key, value);

		}

		logger.info("Query Paramters set == " + map);
		
	}

	@Then("^Response body should reflect the page number as (\\d+)$")
	public void response_body_should_reflect_the_page_number_as(int pageNumber) throws Throwable {

		logger.info("Page number found == " + pageNumber);
		response.then().assertThat().body("page", equalTo(pageNumber));

	}

	@Then("^Response body should have following fields$")
	public void response_body_should_have_following_fields(DataTable table) throws Throwable {
		
		Map<String, String> map = table.asMap(String.class, String.class);

		for (String key : map.keySet()) {

			String value = map.get(key);
			response.then().assertThat().body(containsString(value));

		}

	}

	@Then("^Response body should have details used to POST a request$")
	public void response_body_should_have_details_used_to_POST_a_request() throws Throwable {
		
		response.then().assertThat().body(containsString("name"), containsString("Kiran"), containsString("job"), containsString("Director of QA"));
		
	}

}
