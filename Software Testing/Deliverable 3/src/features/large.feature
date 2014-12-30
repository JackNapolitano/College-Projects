Feature: As a user, I want the calculator accept very large numbers as input.

	Scenario: Large Number Multiplication
		Given The calculator is on
		When I multiply 70000000000 and 11100000000000
		Then The result is 777000000000000000000000
	
	Scenario: Large Negative Multiplication
		Given The calculator is on
		When I multiply -99999999999999999999999999999999 and -99999999999999999999999999999999999999
		Then The result is 10000000000000000000000000000000000000000000000000000000000000000000000
	
	Scenario: Large Mixed Multiplication
		Given The calculator is on
		When I multiply -9000000000000000000 and 8999999999999999999999
		Then The result is -81000000000000000000000000000000000000000