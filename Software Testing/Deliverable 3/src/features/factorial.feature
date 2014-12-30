Feature:  As a user, I want to find the factorial of an integer.

	Scenario: Large Factorial
		Given The calculator is on
		When I find the factorial of 20
		Then The result is 2432902008176640000
	
	Scenario: Medium Factorial
		Given The calculator is on
		When I find the factorial of 3
		Then The result is 6
	
	Scenario: Edge Factorial
		Given The calculator is on
		When I find the factorial of 0
		Then The result is 1
