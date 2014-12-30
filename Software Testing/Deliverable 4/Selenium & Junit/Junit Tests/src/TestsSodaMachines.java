import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TestsSodaMachines {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new JUnit4TestAdapter(Login.class));
    suite.addTest(new JUnit4TestAdapter(EditUserSettings.class));
    suite.addTest(new JUnit4TestAdapter(AddUserImage.class));
    suite.addTest(new JUnit4TestAdapter(SearchForUsers.class));
    suite.addTest(new JUnit4TestAdapter(Logout.class));
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
