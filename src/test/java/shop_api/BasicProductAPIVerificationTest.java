package shop_api;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BasicProductAPIVerificationTest {
    private final String BASE_PATH = "http://localhost:3000/products";
    private final String SEPARATOR = "/";

    @Test
    public void shouldCreateNew() {
        given().contentType(ContentType.JSON).body("{\n" +
                        "    \"decription\": \"Roasted Coffee\",\n" +
                        "    \"id\": \"\",\n" +
                        "    \"manufacturer\": 8,\n" +
                        "    \"price\": 6.7\n" +
                        "}").log().body()
                .when().post(BASE_PATH)
                .then().log().all().statusLine(containsString("OK"));
    }
    @Test
    public void productShouldBeUpdated() {
        Float newPrice = 9.7f;
        HashMap<String, Object> productData = new HashMap<>();
        productData.put("description", "Banana");
        productData.put("id", "2");
        productData.put("maufacturer", 1);
        productData.put("price", newPrice);
        given().contentType(ContentType.JSON)
                .body(productData)
                .when().put(BASE_PATH + "/1")
                .then().log().all()
                .body("price", equalTo(Float.valueOf(newPrice)));
    }
    @Test
    public void productShouldBeDeleted() {
        List<String> arrOfIds = Arrays
                .stream(given()
                        .when()
                        .get(BASE_PATH)
                        .then()
                        .contentType(ContentType.JSON)
                        .extract()
                        .path("id", "description")
                        .toString()
                        .split(", "))
                .filter(x -> x.length() > 4)
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(arrOfIds.toArray()));
        /*delete(BASE_PATH + "/" + arrOfIds.get(0))
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);*/
    }
    @Test
    public void productShouldeDeleted() {
        List<String> arrOfIds = Arrays
                .stream(given()
                        .when()
                        .get(BASE_PATH)
                        .then()
                        .contentType(ContentType.JSON)
                        .extract()
                        .path("id", "description")
                        .toString()
                        .split(", "))
                .filter(x -> x.length() > 4)
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(arrOfIds.toArray()));
        /*delete(BASE_PATH + "/" + arrOfIds.get(0))
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);*/
    }

    @Test
    public void checkingArePeachAndStrawberryExisting() {
        when()
                .get(BASE_PATH)
                .then()
                .assertThat()
                .body("description", Matchers.hasItems("Peach", "Strawberry"));
    }

    @Test
    public void VerifyingStrawberryPrice() {
        when()
                .get(BASE_PATH+ "/5")
                .then()
                .assertThat()
                .body("price", equalTo(18.3f));
    }

    @Test
    public void shouldDeleteYerbaProduct() {
        String coffeeID = given().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"description\": \"Yerba Mate\",\n" +
                        "    \"id\": \"\",\n" +
                        "    \"manufacturer\": 4,\n" +
                        "    \"price\": 31.2\n" +
                        "}").log().body()
                .when()
                .post(BASE_PATH)
                .then().log().all()
                .extract().body().jsonPath().get("id");


        when().delete(BASE_PATH + SEPARATOR + coffeeID)
                .then()
                .statusCode(200)
                .body(equalTo(String.valueOf(true)));
    }
    @Test
    public void extractedProductShouldHaveExpectedDescription(){
        String productID = "3";
        String expectedDescription = "Grapes";

        Product grapes = given()
                .when().get(BASE_PATH + SEPARATOR + productID)
                .then().extract().body().as(Product.class);

        Assert.assertEquals(grapes.getDescription(), expectedDescription);
    }

    @Test
    public void extractedProductShouldHaveExpectedPriceAndDescription(){
        String productID = "7";
        String expectedDescription = "Orange";
        float expectedPrice = 10.5f;

        Product orange = given()
                .when().get(BASE_PATH + SEPARATOR + productID)
                .then().extract().body().as(Product.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(orange.getDescription(), expectedDescription);
        softAssert.assertEquals(orange.getPrice(), expectedPrice);
        softAssert.assertAll();

    }

    @Test
    public void retHashMap() {
        String productId = "6";
        given()
                .when().get(BASE_PATH + SEPARATOR + productId)
                .then().extract().body().jsonPath().getMap("", String.class, String.class);
    }
}
