Feature: As a user, I want to multiply two digits, so that I can find the product.

	Scenario: Positive Multiplication
		Given The calculator is on
		When I multiply 7 and 11
		Then The result is 77
	
	Scenario: Negative Multiplication
		Given The calculator is on
		When I multiply -12 and -3
		Then The result is 36
	
	Scenario: Mixed Multiplication
		Given The calculator is on
		When I multiply -9 and 8
		Then The result is -72