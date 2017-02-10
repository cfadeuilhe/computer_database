package com.excilys.computerdatabase.userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.model.Computer;

@Service
public class ComputerCliService {

    private final static Logger logger = LoggerFactory.getLogger(ComputerCliService.class);

    private Client client = ClientBuilder.newClient();
    private final String TARGET_URL = "http://localhost:8080/Cdb";

    private WebTarget webTarget = client.target(TARGET_URL);

    public Computer getComputerById(int id) {
        WebTarget computerWebTarget = webTarget.path("computer/" + id);
        Computer c = computerWebTarget.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(Computer.class);

        return c;
    }

    public List<Computer> listEntities(Map<String, String> orderMap) {
        WebTarget computerWebTarget = webTarget.path("computer");
        List<Computer> list = new ArrayList<Computer>();
        list = computerWebTarget.request(MediaType.APPLICATION_JSON_TYPE).get()
                .readEntity(new GenericType<List<Computer>>() {
                });

        return list;
    }

    public void delete(long id) {
        WebTarget computerWebTarget = webTarget.path("computer/"+id);
        
        Response s = computerWebTarget.request().delete();

        logger.info("ComputerCli delete-------"+s.getStatus());
        logger.info("-------"+s.readEntity(Computer.class));
    }

    public Response createComputer(Computer computer) {
        WebTarget computerWebTarget = webTarget.path("computer");

        Response s = computerWebTarget.request().post(Entity.entity(computer, MediaType.APPLICATION_JSON));

        logger.info("ComputerCli create-------"+s.getStatus());
        logger.info("-------"+s.readEntity(Computer.class));
        
        return s;
    }

    public void update(long id, Computer computer) {
        WebTarget computerWebTarget = webTarget.path("computer/"+id);

        Response s = computerWebTarget.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(computer, MediaType.APPLICATION_JSON));

        Computer c = s.readEntity(Computer.class);

        logger.info("ComputerCli update-------"+s.getStatus());
        logger.info("-------"+c);
    }
}
