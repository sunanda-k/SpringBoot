package com.sunanda.bootstrap;

import com.sunanda.domain.*;
import com.sunanda.enums.OrderStatus;
import com.sunanda.services.UserService;
import com.sunanda.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadProducts();
        loadUsersAndCustomers();
        loadCarts();
        loadOrderHistory();

    }

    private void loadOrderHistory() {
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user ->{
            Order order = new Order();
            order.setCustomer(user.getCustomer());
            order.setOrderStatus(OrderStatus.SHIPPED);

            products.forEach(product -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(1);
                order.addToOrderDetails(orderDetail);
            });
        });
    }

    private void loadCarts() {
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user -> {
            user.setCart(new Cart());
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(products.get(0));
            cartDetail.setQuantity(2);
            user.getCart().addCartDetail(cartDetail);
            userService.saveOrUpdate(user);
        });
    }

    public void loadUsersAndCustomers() {
        User user1 = new User();
        user1.setUsername("sunanda");
        user1.setPassword("sam");

        Customer customer1 = new Customer();
        customer1.setFirstName("sunanda");
        customer1.setLastName("sam");
        customer1.setBillingAddress(new Address());
        customer1.getBillingAddress().setAddressLine1("1 Main St");
        customer1.getBillingAddress().setCity("Miami");
        customer1.getBillingAddress().setState("Florida");
        customer1.getBillingAddress().setZipCode("33101");
        customer1.setEmail("sunanda@gmail.com");
        customer1.setPhoneNumber("517.214.0000");
        user1.setCustomer(customer1);
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("ram");
        user2.setPassword("qwerty");

        Customer customer2 = new Customer();
        customer2.setFirstName("raj");
        customer2.setLastName("king");
        customer2.setBillingAddress(new Address());
        customer2.getBillingAddress().setAddressLine1("1 Key Biscane Ave");
        customer2.getBillingAddress().setCity("Chicago");
        customer2.getBillingAddress().setState("Illinois");
        customer2.getBillingAddress().setZipCode("60614");
        customer2.setEmail("raj@gmail.com");
        customer2.setPhoneNumber("552.444.1111");
        user2.setCustomer(customer2);
        userService.saveOrUpdate(user2);

        User user3 = new User();
        user3.setUsername("krishna");
        user3.setPassword("qwerty");
        Customer customer3 = new Customer();
        customer3.setFirstName("alex");
        customer3.setLastName("Axe");
        customer3.setBillingAddress(new Address());
        customer3.getBillingAddress().setAddressLine1("15 westin heights");
        customer3.getBillingAddress().setCity("Austin");
        customer3.getBillingAddress().setState("Texas");
        customer3.getBillingAddress().setZipCode("51026");
        customer3.setEmail("alex@gmail.com");
        customer3.setPhoneNumber("511.125.2222");

        user3.setCustomer(customer3);
        userService.saveOrUpdate(user3);
    }

    public void loadProducts(){

        Product product1 = new Product();
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://product1");
        productService.saveOrUpdate(product1);

        Product product2 = new Product();
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("14.99"));
        product2.setImageUrl("http:///product2");
        productService.saveOrUpdate(product2);

        Product product3 = new Product();
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("34.99"));
        product3.setImageUrl("http://product3");
        productService.saveOrUpdate(product3);

        Product product4 = new Product();
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("44.99"));
        product4.setImageUrl("http://product4");
        productService.saveOrUpdate(product4);

        Product product5 = new Product();
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("25.99"));
        product5.setImageUrl("http://product5");
        productService.saveOrUpdate(product5);

    }
}
