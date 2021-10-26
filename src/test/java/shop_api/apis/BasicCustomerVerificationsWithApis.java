package shop_api.apis;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import shop_api.Customer;
import shop_api.enviroments.Environment;
import shop_api.enviroments.EnvironmentManager;

import java.util.List;

public class BasicCustomerVerificationsWithApis {

    private CustomerAPI customerAPI;

    @BeforeClass
    public void setUp(){
        String env = System.getProperty("env");
        Environment currentEnv = EnvironmentManager.getEnvironment(env);
        customerAPI = CustomerAPI.get(currentEnv);
    }

    @Test
    public void shouldGetListOfExistingCustomers(){
        List<Customer> customers = customerAPI.getAllCustomers();
        Assert.assertTrue(customers.size() > 0
                ,"Customer list size is 0. Check logs for additional details");
    }

    @Test
    public void shouldChangeCustomerEmailWithNewOne(){
        String customerId = "3";
        String newEmail = "alex.kowalski@isChanged.yes";
        Customer alex = customerAPI.updateEmailAddressForCustomer(customerId, newEmail);
        Assert.assertEquals(alex.getPerson().getEmail(), newEmail
                ,"Email do not Match");
    }

    @Test
    public void shouldCheckIfItemWithIdWasAddedToCart(){
        String customerId = "4";
        String itemId = "3";
        String quantity = "3";
        Customer andrew = customerAPI.getCustomerById(customerId);

        customerAPI.addItemToCartForCustomer(customerId, itemId, quantity);

        Assert.assertTrue(andrew.getShoppingCart().getItems().length > 0
                ,"Items was not added to cart");
    }
}
