package bio.knowledge.validator.aggregator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bio.knowledge.client.api.ConceptsApi;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregatorTest {
	
	@Test
	public void test() {
		bio.knowledge.client.ApiClient beaconApiClient = new bio.knowledge.client.ApiClient();
		beaconApiClient.setBasePath("https://biolink-kb.ncats.io");
		
		bio.knowledge.aggregator.client.ApiClient aggApiClient = new bio.knowledge.aggregator.client.ApiClient();
		aggApiClient.setBasePath("localhost:8080");
		
		bio.knowledge.client.api.ConceptsApi beaconConceptsApi = new bio.knowledge.client.api.ConceptsApi(beaconApiClient);
		
		bio.knowledge.aggregator.client.api.ConceptsApi aggConceptsApi = new bio.knowledge.aggregator.client.api.ConceptsApi(aggApiClient);
		
	}

}
