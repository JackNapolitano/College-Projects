public class Calculator
{
	public Calculator()
	{
		
	}
	
	public double add(double arg1, double arg2)
	{
		return (arg1 + arg2);
	}

	public double subtract(double arg1, double arg2)
	{
		return (arg1 - arg2);
	}

	public double multiply(double arg1, double arg2)
	{
		return (arg1 * arg2);
	}

	public double divide(double arg1, double arg2)
	{
		if(arg1 == 0)
			return arg1;
		return (arg2 / arg1);
	
	}
	public double absVal(double arg1)
	{
		return Math.abs(arg1);
	}
	public double factorial(double arg1)
	{
	    double result = 1;
	    for (double i = 1; i <= arg1; i++) 
	    {
	    	result = result * i;
	    }
	    return result;
	}
}
