package controller.web;

import com.example.springbootwiththymleaf.model.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetClinicRestControllerTests {
    private RestTemplate restTemplate;


    @Before
    public void setUp(){
        restTemplate=new RestTemplate();
    }
    @Test
    public void testGetOwnerById(){
        ResponseEntity<Owner> response=restTemplate.getForEntity("http://localhost:8081/owner/ownerId/2",Owner.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(),Matchers.equalTo("AteistJoe"));
    }
    @Test
    public void testGetOwnersByLastName(){
        ResponseEntity<List> response=restTemplate.getForEntity("http://localhost:8081/owner/ownerLast?ln=Atmaca", List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<Map<String, String>> list=response.getBody();

        List <String> listed=list.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(listed,Matchers.containsInAnyOrder());


    }
    @Test
    public void testCreateOwner(){
        Owner owner=new Owner();
        owner.setFirstName("Mustafa");
        owner.setLastName("Karapinar");
        URI location=restTemplate.postForLocation("http://localhost:8081/owner/ownerId/12",owner);
        Owner owner1=restTemplate.getForObject(location,Owner.class);
        MatcherAssert.assertThat(owner1.getFirstName(),Matchers.equalTo(owner.getFirstName()));
        MatcherAssert.assertThat(owner1.getLastName(),Matchers.equalTo(owner.getLastName()));

    }
    @Test
    public void testGetOwners(){
      ResponseEntity<List> response=restTemplate.getForEntity("http://localhost:8081/owner/getOwners", List.class);

        List<Map<String, String>> list=response.getBody();

        List <String> listed=list.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(listed,Matchers.containsInAnyOrder("Mahmut","Muharrem"));

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
    }

    @Test
    public void testUpdateOwner(){
        Owner owner=restTemplate.getForObject("http://localhost:8081/owner/ownerId/2",Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(),Matchers.equalTo("Muharrem"));
        owner.setFirstName("AteistJoe");
        restTemplate.put("http://localhost:8081/owner/ownerId/2",owner);
        MatcherAssert.assertThat(owner.getFirstName(),Matchers.equalTo("AteistJoe"));
    }
    @Test
    public void testDeleteOwner(){
        restTemplate.delete("http://localhost:8081/owner/ownerId/?");
        try {
            restTemplate.getForEntity("http://localhost:8081/owner/ownerId/2",Owner.class);
            Assert.fail("Error:Owner is not Found");

        }catch (RestClientException ex){

        }
    }
}
