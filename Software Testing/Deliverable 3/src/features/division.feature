Feature: As a user, I want to divide two digits, so that I can find the quotient.

	Scenario: Positive Division
		Given The calculator is on
		When I divide 2 into 8 
		Then The result is 4
	
	Scenario: Negative Division
		Given The calculator is on
		When I divide -12 into -19
		Then The result is 1.5833333333333333
	
	Scenario: Mixed Division
		Given The calculator is on
		When I divide -9 into 39
		Then The result is -4.333333333333333
		
	Scenario: Division by Zero
		Given The calculator is on
		When I divide 0 into 10
		Then The result is 0