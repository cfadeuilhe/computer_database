package com.excilys.computerdatabase.userinterface;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
public class ComputerCliServiceTest {

    private Client client = ClientBuilder.newClient();
    private final String TARGET_URL = new String("http://localhost:8080/Cdb");

    private WebTarget webTarget = client.target(TARGET_URL);

    @Test
    public void testGetComputerId(int id) {

        WebTarget computerWebTarget = webTarget.path("computers/id/" + id);
        Computer c = computerWebTarget.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(Computer.class);
        
        assertNotNull(c);
    }

    @Test
    public void testListEntities(Map<String, String> orderMap) {
        WebTarget computerWebTarget = webTarget.path("computers");
        List<Computer> list = new ArrayList<Computer>();
        list = computerWebTarget.request(MediaType.APPLICATION_JSON_TYPE).get()
                .readEntity(new GenericType<List<Computer>>() {
                });

        assertNotNull(list);
    }

    @Test
    public void testDeleteComputer(Map<String, String> orderMap) {
        
    }

}
