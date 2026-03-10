package id.ac.ui.cs.advprog.eshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Order order;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);

        List<Product> products = new ArrayList<>();
        products.add(product);

        order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
    }

    @Test
    void testCreateOrderSuccess() throws Exception {
        doReturn(order).when(orderService).createOrder(any(Order.class));

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(order.getId()));

        verify(orderService).createOrder(any(Order.class));
    }

    @Test
    void testCreateOrderConflict() throws Exception {
        doReturn(null).when(orderService).createOrder(any(Order.class));

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isConflict())
                .andExpect(content().string(""));
    }

    @Test
    void testUpdateStatusSuccess() throws Exception {
        Order updated = new Order(
                order.getId(),
                order.getProducts(),
                order.getOrderTime(),
                order.getAuthor(),
                OrderStatus.SUCCESS.getValue()
        );
        doReturn(updated).when(orderService).updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        mockMvc.perform(put("/order/{orderId}/status", order.getId())
                        .param("status", OrderStatus.SUCCESS.getValue()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(OrderStatus.SUCCESS.getValue()));
    }

    @Test
    void testUpdateStatusNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(orderService)
                .updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        mockMvc.perform(put("/order/{orderId}/status", order.getId())
                        .param("status", OrderStatus.SUCCESS.getValue()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByIdFound() throws Exception {
        doReturn(order).when(orderService).findById(order.getId());

        mockMvc.perform(get("/order/{orderId}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(order.getAuthor()));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        doReturn(null).when(orderService).findById(order.getId());

        mockMvc.perform(get("/order/{orderId}", order.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllByAuthor() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        doReturn(orders).when(orderService).findAllByAuthor(order.getAuthor());

        mockMvc.perform(get("/order").param("author", order.getAuthor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order.getId()));
    }
}