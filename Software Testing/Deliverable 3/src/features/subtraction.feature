Feature: As a user, I want to subtract two numbers, so that I can get their difference.

	Scenario: Positive Subtraction
		Given The calculator is on
		When I subtract 4 and 2
		Then The result is 2
	
	Scenario: Negative Subtraction
		Given The calculator is on
		When I subtract -12 and -7
		Then The result is -5
	
	Scenario: Mixed Subtraction
		Given The calculator is on
		When I subtract 5 and -6
		Then The result is 11