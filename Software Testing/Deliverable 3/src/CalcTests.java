import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;


public class CalcTests
{
	private Calculator calc;
	private double value;
	
	@Given("^The calculator is on$")
	public void setUp()
	{
		this.calc = new Calculator();
		this.value = 0;
	}
	
	@Then("^The result is (-?\\d*\\.{0,1}\\d+)$")
	public void result(double expected)
	{
		// 0.01 precision
		assertEquals(expected, this.value, 0.01);
	}
	
	@When("^I add (-?\\d*\\.{0,1}\\d+) and (-?\\d*\\.{0,1}\\d+)$")
	public void add(double arg1, double arg2)
	{
		this.value = this.calc.add(arg1, arg2);
	}
	
	@When("^I subtract (-?\\d*\\.{0,1}\\d+) and (-?\\d*\\.{0,1}\\d+)$")
	public void subtract(double arg1, double arg2)
	{
		this.value = this.calc.subtract(arg1, arg2);
	}
	
	@When("^I multiply (-?\\d*\\.{0,1}\\d+) and (-?\\d*\\.{0,1}\\d+)$")
	public void multiply(double arg1, double arg2)
	{
		this.value = this.calc.multiply(arg1, arg2);
	}
	
	@When("^I divide (-?\\d*\\.{0,1}\\d+) into (-?\\d*\\.{0,1}\\d+)$")
	public void divide(double arg1, double arg2)
	{
		this.value = this.calc.divide(arg1, arg2);
	}
	@When("^I find the factorial of (-?\\d*\\.{0,1}\\d+)$")
	public void factorial(double arg1)
	{
		this.value = this.calc.factorial(arg1);
	}
	@When("^I find the absolute value of (-?\\d*\\.{0,1}\\d+)$")
	public void absValue(double arg1)
	{
		this.value = this.calc.absVal(arg1);
	}
}