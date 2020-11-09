// --== CS400 File Header Information ==--
// Name: Minghao Zhou
// Email: mzhou222@wisc.edu
// Team: JB
// Role: Testing Engineer
// TA: Harper
// Lecturer: Gary Dahl
// Notes to Grader: N/A
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * TestSuite for testing methods and main argument functionalities of WeatherAppS
 *
 * @author Liangqi Cai, Minghao Zhou
 */
public class TestSuite {

  /**
   * Testing Contains method of CityNameList
   * 
   */
  @Test
 public void testCityNameListContains() {
    CityNameList test = new CityNameList();
    try {
      test.load(); // load the CityNameList
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    // non-existing city
    if (test.contains("THISISNOTACITY")) {
      fail("contains() method fails: non-existing city is detected");
    }
    // existing city
    if (!test.contains("Mwanga") || !test.contains("Jeddah")) {
      fail("contains() method fails: existing cites are not detected");
    }
  }

  /**
   * Testing Search method of CityNameList. Search method should return the matched city names with
   * with corresponding country according to the input city name and country
   * 
   */
  @Test
  void testCityNameListSearch() {
    CityNameList test = new CityNameList();
    try {
      test.load(); // load the CityNameList
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String a = "";
    Iterator<String> testCity = test.search("Wuh", "cn"); // search city name containing "Wuh" with
                                                          // country CN
    while (testCity.hasNext()) {
      a += testCity.next();
    }
    if (!a.equals(
        "Wuhai     --CNWuhan     --CNWuhe Chengguanzhen     --CNWuhou     --CNWuhu     --CN")) {
      fail("fails to search cities");
    }
  }

  /**
   * Testing Insert and Lookup methods of WeatherTree. This test fails if testTree fails to insert
   * city or the inserted city is not found. This test also checks the functionality of update
   * method in Data
   */
  @Test
  void testWeatherTreeInsertAndLookup() {
    Data dataBase = new Data();
    Weather weatherInfo = null;
    try {
      try {
        weatherInfo = dataBase.update("Shanghai");
      } catch (ParseException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    WeatherTree testTree = new WeatherTree();
    testTree.insert(weatherInfo);
    if (testTree.lookup("Shanghai") == null) {
      fail("fails to look up the added city");
    }
  }

  /**
   * Testing getDetailInfo method of WeatherTree.
   */
  @Test
  void testWeatherTreeGetDetailInfo() {
    // makes up a city named "ABC" with weather informations
    Weather test1 = new Weather("ABC", 1, 2, "test", 3, 4, 5, 6, 7, 8, 9, 10);
    WeatherTree testTree = new WeatherTree();
    testTree.insert(test1);
    try {
      String a;
      try {
        a = testTree.getDetailInfo("ABC");
        // Data should be printed out with corresponding information types and correct numbers
        if (!a.contains("City: ABC") || !a.contains("Today's Weather: test")
            || !a.contains("Current Temperature: 3.0°C") || !a.contains("Highest Temperature: 6.0°C")
            || !a.contains("Lowest Temperature: 5.0°C") || !a.contains("Apparent Temperature: 4.0°C")
            || !a.contains("Humidity: 8.0%") || !a.contains("Visibility: 10.0 ft")) {
          fail("fails to get the detail information of the created city [ABC]");
        }
      } catch (IOException e) {
        e.printStackTrace();
        fail();
      }
      } catch (ParseException e) {
        e.printStackTrace();
      }
  }

  /**
   * Testing getImportantInfo method of WeatherTree.
   */
  @Test
  void testWeatherTreeGetImportantInfo() {
    // makes up a city named "CBA" with weather informations
    Weather test1 = new Weather("CBA", 1, 2, "test", 3, 4, 5, 6, 7, 8, 9, 10);
    WeatherTree testTree = new WeatherTree();
    testTree.insert(test1);
    try {
      String a;
      try {
        a = testTree.getImportantInfo("CBA");
        // Only important data should be printed out with corresponding information types and correct
        // numbers
        if (!a.contains("City: CBA") || !a.contains("Today's Weather: test")
            || !a.contains("Current Temperature: 3.0°C") || !a.contains("Highest Temperature: 6.0°C")
            || !a.contains("Lowest Temperature: 5.0°C") || a.contains("Apparent Temperature")
            || a.contains("Humidity") || a.contains("Visibility")) {
          fail("fails to get the important information of the created city [CBA]");
        }
      } catch (IOException e) {
        e.printStackTrace();
        fail();
      }
      } catch (ParseException e) {
        e.printStackTrace();
      }
  }


  /**
   * Testing main argument with correct formated user input. When the user enters correct formated
   * input, WeatherApp should recognize user's goal and set corresponding command boolean to true.
   */
  @Test
  public void mainTestCommand() throws Exception {
    String[] programArgs = new String[] {"--help", "-l", "-v", "--detail", "--clean-cache"};
    ArgumentParser test = new ArgumentParser(programArgs);
    test.parseArgument();
    if (!test.isArgCleanCache() || !test.isArgHelp() || !test.isArgDetail() || !test.isArgList()
        || !test.isArgVersion()) {
      fail("fails to read user's command");
    }
  }

  /**
   * Testing main argument with incorrect formated user input. When the user enters incorrect
   * formated input, IllegalArgumentException should be thrown.
   */
  @Test
  public void mainTestWrongCommand() throws Exception {
    String[] programArgs = new String[] {"-BlahBlahBlah"};
    ArgumentParser test = new ArgumentParser(programArgs);
    Assertions.assertThrows(IllegalArgumentException.class, () -> test.parseArgument());
  }
}
