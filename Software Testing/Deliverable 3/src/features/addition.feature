Feature:  As a user, I want to add two digits, so that I can get their sum.

	Scenario: Positive Addition
		Given The calculator is on
		When I add 7.2 and 11.301
		Then The result is 18.501
	
	Scenario: Negative Addition
		Given The calculator is on
		When I add -12.5 and -1
		Then The result is -13.5
	
	Scenario: Mixed Addition
		Given The calculator is on
		When I add -9 and 13.631
		Then The result is 4.631
