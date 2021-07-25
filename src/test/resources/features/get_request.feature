Feature: GET Request Valdations

	Scenario: Validate response code without query parameters
		Given The base url of API is "https://reqres.in" 
		When The path "/api/users" and request method "GET" is used to hit the API
		Then The response code should be 200
	
	Scenario: Validate response code with query parameters
		Given The base url of API is "https://reqres.in" 
		And The query parameters is as follows
		 | page | 2 |
		When The path "/api/users" and request method "GET" is used to hit the API
		Then The response code should be 200
		And Response body should reflect the page number as 2
		
	Scenario: Validate response body
		Given The base url of API is "https://reqres.in" 
		And The query parameters is as follows
		 | page | 2 |
		When The path "/api/users" and request method "GET" is used to hit the API
		Then The response code should be 200
		And Response body should have following fields
			| Field1 | page 				|	
			| Field2 | per_page 		|
			| Field3 | total 				|
			| Field4 | total_pages	|
			| Field5 | id 					|
			| Field6 | email 				|
			| Field7 | first_name 	|
			| Field8 | last_name 		|
