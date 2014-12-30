Feature:  As a user, I want to find the absolute value of a number.

	Scenario: Positive Absolute Value
		Given The calculator is on
		When I find the absolute value of 20
		Then The result is 20
	
	Scenario: Negative Absolute Value
		Given The calculator is on
		When I find the absolute value of -13
		Then The result is 13
	
	Scenario: Zero Absolute Value
		Given The calculator is on
		When I find the absolute value of 0
		Then The result is 0
		
	Scenario: Negative Zero Absolute Value
		Given The calculator is on
		When I find the absolute value of -0
		Then The result is 0
		
	Scenario: Tiny Absolute Value
		Given The calculator is on
		When I find the absolute value of -0.0000000000001
		Then The result is 0.0000000000001
