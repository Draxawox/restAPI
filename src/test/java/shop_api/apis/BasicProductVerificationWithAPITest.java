package shop_api.apis;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import shop_api.Product;
import shop_api.enviroments.Environment;
import shop_api.enviroments.EnvironmentManager;

import java.util.List;

public class BasicProductVerificationWithAPITest {

    private ProductAPI productAPI;

    @BeforeClass
    public void setUp() {
        String env = System.getProperty("env");
        Environment currentEnv = EnvironmentManager.getEnvironment(env);
        productAPI = ProductAPI.get(currentEnv);
    }

    @Test
    public void shouldAddNewProduct() {
        String description = "Cola";
        String id = "";
        int manufacturer = 4;
        float price = 100.69f;
        int expectedSize = 16;

        productAPI.addNewProduct(description, id, manufacturer, price);

        List<Product> productList = productAPI.getAllProduct();

        Assert.assertEquals(productList.size(), expectedSize);
    }

    @Test
    public void shouldContainsPeachAndStrawberry() {
        String hasPeach = "Peach";
        String hasStrawberry = "Strawberry";

        String actualPeachProduct = productAPI.getProductDescription(hasPeach);
        String actualStrawberryProduct = productAPI.getProductDescription(hasStrawberry);


        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualPeachProduct, hasPeach);
        softAssert.assertEquals(actualStrawberryProduct, hasStrawberry);

    }
}
