package bio.knowledge.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bio.knowledge.client.ApiException;
import bio.knowledge.client.api.ConceptsApi;
import bio.knowledge.client.model.BeaconConcept;
import bio.knowledge.validator.rules.RuleContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTests {
	
	@Value("${basePath}")
	private String BASE_PATH;
	
	@Autowired RuleContainer ruleContainer;
	
	@Rule public TestWatcher testWatcher;
	@Rule public Stopwatch stopwatch;
	
	@PostConstruct
	public void init() {
		testWatcher = ruleContainer.getTestWatcher();
		stopwatch = ruleContainer.getStopwatch();
	}
	
	@Test
	public void testExactMatchesCaching() throws ApiException {
		ApiClient client = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(client);
		
		List<BeaconConcept> concepts = conceptsApi.getConcepts(Utils.asList("diabetes"), null, 1, 100);
		
		for (BeaconConcept concept : concepts) {
			Set<String> clique = new HashSet<String>(Utils.asList(concept.getId()));
			
			int size = 0;
			while (clique.size() != size) {
				size = clique.size();
				
				List<String> matches = conceptsApi.getExactMatchesToConcept(concept.getId());
				
				clique.addAll(matches);
			}
			
			System.out.println(clique.size());
			
			//TODO: test that the time it takes to return for each iteration is O(n) of the n 
			//		many new matches found.
		}
	}

}
