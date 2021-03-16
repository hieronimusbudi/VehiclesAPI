package com.udacity.pricing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing.domain.price.Price;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingControllerTest {
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        mapper = new ObjectMapper();
    }

    @Test
    public void getPrice() throws Exception {
        Long vehicleId = 1L;
        String url = "/services/price/?vehicleId=" + vehicleId;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Price resultPrice = mapper.readValue(contentAsString, Price.class);

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        Assert.assertEquals(vehicleId, resultPrice.getVehicleId());
        Assert.assertEquals("USD", resultPrice.getCurrency());
        Assert.assertNotNull(resultPrice.getPrice());

        System.out.println(result.getResponse().getContentAsString());
    }
}
