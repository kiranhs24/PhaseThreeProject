Feature: POST Request Valdations

	Scenario: Validate response code without query parameters
		Given The base url of API is "https://reqres.in" 
		When The path "/api/users" and request method "POST" is used to hit the API
		Then The response code should be 201
		 
	Scenario: Validate response code with query parameters
		Given The base url of API is "https://reqres.in" 
		When The path "/api/users" and request method "POST" is used to hit the API
		Then The response code should be 201
		And Response body should have details used to POST a request
