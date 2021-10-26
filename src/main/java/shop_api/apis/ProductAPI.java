package shop_api.apis;

import io.restassured.http.ContentType;
import shop_api.Product;
import shop_api.enviroments.Environment;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ProductAPI {
    private final String HOST;
    private final Environment environment;

    private final String PRODUCTS = "/products";
    private final String SEPARATOR = "/";

    public ProductAPI(Environment env) {
        this.environment = env;
        HOST = env.getHost();
    }

    public static ProductAPI get(Environment env) {
        return new ProductAPI(env);
    }

    public List<Product> getAllProduct(){
        String query = HOST + PRODUCTS;
        return when().get(query)
                .then().log().body()
                .extract().body().jsonPath().getList("", Product.class);
    }

    public void addNewProduct(String description, String id, int manufacturer, float price) {
        Product product = new Product(description, id, manufacturer, price);
        String query = HOST + PRODUCTS;
        given().contentType(ContentType.JSON)
                .body(product)
                .when().post(query)
                .then().log().body();
    }

    public Product getProductById(String productId) {
        return when().get(HOST + PRODUCTS + SEPARATOR + productId)
                .then().log().all()
                .extract().body().as(Product.class);
    }

    public void updateProductByID(String description, String id, int manufacturer, Float newPrice) {
        Product product = new Product(description, id, manufacturer, newPrice);

        given().contentType(ContentType.JSON)
                .body(product)
                .when().put(HOST + PRODUCTS + SEPARATOR + id)
                .then().log().body();
    }

    public String getProductDescription(String description) {
        return getAllProduct().stream()
                .map(Product::getDescription)
                .filter(desc -> desc.equals(description))
                .findFirst()
                .get();
    }

    public String deleteProductById(String id) {
        String query = HOST + PRODUCTS + SEPARATOR + id;
        return when().delete(query)
                .then().log().body()
                .extract()
                .body().asString();
    }


}
