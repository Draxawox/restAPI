package shop_api;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class CustomerComplexTest {
    private String basePath = "http://localhost:3000/customers";
    private String addItemToCartPath = "http://localhost:3000/customers/{customerId}/cart" +
            "?quantity={quantity}&productId={productId}";
    private final String SEPARATOR = "/";

    @Test
    public void shouldGetCustomerAddressFromExtraObject() {
        Customer customer = when().get(basePath + SEPARATOR + "3").as(Customer.class);
        Assert.assertEquals(customer.getShoppingCart().getItems().length, 1);
    }

    @Test
    public void customerShoppingCartItemsListShouldHave0Length() {
        putProductToCartForCustomer("3", 4, 5);
        Customer customer = when().get(basePath + SEPARATOR + "3").as(Customer.class);
        System.out.println(customer.getShoppingCart().getItems());
        Assert.assertEquals(customer.getShoppingCart().getItems().length, 1);
    }

    private void putProductToCartForCustomer(String customerId, int productId, int productQuantity) {
        String query = basePath + SEPARATOR + customerId + SEPARATOR + "cart";
        given().queryParam("quantity", productQuantity)
                .queryParam("productId", productId)
                .when().put(query).then().log().all();
    }



    private void emptyCartForCustomer (String customerId) {
        when().delete(basePath + SEPARATOR + customerId + SEPARATOR + "cart/empty")
                .then().statusCode(200);
    }

    private void putProductToCartForCustomerPathParams(String customerId, int productId, int productquantity) {
        given().pathParam("customerId", customerId)
                .pathParam("productId", productId)
                .pathParam("productQuantity", productquantity)
                .when().put(addItemToCartPath).then().log().all();
    }


}
