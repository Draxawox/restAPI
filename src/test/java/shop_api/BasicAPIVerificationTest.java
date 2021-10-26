package shop_api;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BasicAPIVerificationTest {

    @Test
    public void shouldReturnCustomersList() {
        given()
                .when()
                .get("http://localhost:3000/customers")
                .then().statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void shouldReturnCustomerInfoForIDEquals2() {
        when().get("http://localhost:3000/customers/2")
                .then()
                .statusCode(200)
                .body("person.email", is(equalTo("john.doe@customDomain.com")))
                .body("address.phone", is(equalTo("33 55 789 123")));

    }
}
