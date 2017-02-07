package com.excilys.computerdatabase.userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

@Service
public class CompanyCliService {

    private final static Logger logger = LoggerFactory.getLogger(CompanyCliService.class);

    private Client client = ClientBuilder.newClient();
    private final String TARGET_URL = new String("http://localhost:8080/Cdb");
    
    private WebTarget webTarget = client.target(TARGET_URL);
    
    public Company getComputerId(int id){


        WebTarget companyWebTarget = webTarget.path("company/"+id);
        Company c = companyWebTarget.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(Company.class);

        return c;
    }
    
    public List<Company> listEntities(Map<String, String> orderMap) {
        WebTarget companyWebTarget = webTarget.path("company");
        List<Company> list = new ArrayList<Company>();
        list = companyWebTarget.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(new GenericType<List<Company>>(){});

        return list;
    }
    
    public void delete(long id) {
        WebTarget companyWebTarget = webTarget.path("company/"+id);
        Response s = companyWebTarget.request(MediaType.APPLICATION_JSON_TYPE).delete();

        logger.info("ComputerCli delete-------"+s.getStatus());
        logger.info("-------"+s.readEntity(Computer.class));
    }
}
