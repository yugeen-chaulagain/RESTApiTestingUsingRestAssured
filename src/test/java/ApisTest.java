import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApisTest {

    @BeforeAll
    public static void setup() {

        RestAssured.baseURI = "http://localhost:8080";

    }

    @Test
    public void createCompany() {

        // Validate createCompany API
        String requestBody = "{\n" +
                "    \"name\": \"S&P 500\",\n" +
                "    \"description\": \"ETF\",\n" +
                "    \"stockSymbol\": \"SPY\"\n" +
                "}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/createCompany")
                .then()
                .extract().response();

        //Validate the status code
        Assertions.assertEquals(200, response.statusCode());

        //validate the body
        Map<String, String> data = response.jsonPath().getMap("data");
        Assertions.assertEquals("S&P 500", data.get("name"));
        Assertions.assertEquals("ETF", data.get("description"));
        Assertions.assertEquals("SPY", data.get("stockSymbol"));


    }

    // Validate getAllCompanies API
    @Test
    public void getAllCompanies() {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/getAllCompanies")
                .then()
                .extract().response();

        //Validate the status code
        Assertions.assertEquals(200, response.statusCode());

        //Validate a few values inside the body
        Map<String, String> data0 = response.jsonPath().getMap("data[0]");
        Assertions.assertEquals("Amazon.com", data0.get("name"));
        Assertions.assertEquals("Retailer", data0.get("description"));
        Assertions.assertEquals("AMZN", data0.get("stockSymbol"));

        Map<String, String> data1 = response.jsonPath().getMap("data[1]");
        Assertions.assertEquals("Google", data1.get("name"));
        Assertions.assertEquals("Search Engine", data1.get("description"));
        Assertions.assertEquals("GOOGL", data1.get("stockSymbol"));

        Map<String, String> data2 = response.jsonPath().getMap("data[2]");
        Assertions.assertEquals("Affrim Holdings", data2.get("name"));
        Assertions.assertEquals("Retailer", data2.get("description"));
        Assertions.assertEquals("AFRM", data2.get("stockSymbol"));

    }

    // Validate getAllStocks API
    @Test
    public void getAllStocks() {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/getAllStocks")
                .then()
                .extract().response();

        //Validate the status code
        Assertions.assertEquals(200, response.statusCode());

        //Validate values inside the body
        Map<String, Integer> data0 = response.jsonPath().getMap("data[0]");
        Assertions.assertEquals("AMZN", data0.get("symbol"));
        Assertions.assertEquals(3020, data0.get("stockPrice"));

        Map<String, Integer> data1 = response.jsonPath().getMap("data[1]");
        Assertions.assertEquals("GOOGL", data1.get("symbol"));
        Assertions.assertEquals(2500, data1.get("stockPrice"));

        Map<String, Integer> data2 = response.jsonPath().getMap("data[2]");
        Assertions.assertEquals("AFRM", data2.get("symbol"));
        Assertions.assertEquals(45, data2.get("stockPrice"));

        Map<String, Integer> data3 = response.jsonPath().getMap("data[3]");
        Assertions.assertEquals("UPSTRT", data3.get("symbol"));
        Assertions.assertEquals(110, data3.get("stockPrice"));

        Map<String, Integer> data4 = response.jsonPath().getMap("data[4]");
        Assertions.assertEquals("SPY", data4.get("symbol"));
        Assertions.assertEquals(453, data4.get("stockPrice"));


    }

    // Validate getStockPrice API
    // Validating stock price for Google
    @Test
    public void getStockPriceBySymbol() {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/getStockPrice/GOOGL")
                .then()
                .extract().response();

        //Validate the status code
        Assertions.assertEquals(200, response.statusCode());

        //Validate values inside the body
        Map<String, Integer> data = response.jsonPath().getMap("data");
        Assertions.assertEquals("GOOGL", data.get("symbol"));
        Assertions.assertEquals(2500, data.get("stockPrice"));
    }


}
