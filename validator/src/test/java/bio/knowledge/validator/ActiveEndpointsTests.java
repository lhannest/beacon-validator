package bio.knowledge.validator;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bio.knowledge.client.ApiException;
import bio.knowledge.client.api.ConceptsApi;
import bio.knowledge.client.api.MetadataApi;
import bio.knowledge.client.api.StatementsApi;
import bio.knowledge.client.model.BeaconConcept;
import bio.knowledge.client.model.BeaconStatement;
import bio.knowledge.validator.logging.Logger;
import bio.knowledge.validator.logging.LoggerFactory;
import bio.knowledge.validator.rules.RuleContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveEndpointsTests {
	
	@Value(value="${basePath}")
	public String BASE_PATH;
	
	@Autowired RuleContainer ruleContainer;
	
	@Rule public TestWatcher testWatcher;
	@Rule public Stopwatch stopwatch;
	
	@PostConstruct
	public void init() {
		testWatcher = ruleContainer.getTestWatcher();
		stopwatch = ruleContainer.getStopwatch();
	}
	
	@Test
	public void testConceptsCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		try {
			conceptsApi.getConcepts(Utils.asList("e"), null, 1, 1);
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConceptDetailsCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		try {
			List<BeaconConcept> concepts = conceptsApi.getConcepts(Utils.asList("e"), null, 1, 1);
			
			if (concepts.isEmpty()) {
				fail("Could not run test since concepts endpoint returned empty");
			}
			
			String conceptId = concepts.get(0).getId();
			
			conceptsApi.getConceptDetails(conceptId);
			
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testExactMatchesToConceptIdCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		try {
			List<BeaconConcept> concepts = conceptsApi.getConcepts(Utils.asList("e"), null, 1, 1);
			
			if (concepts.isEmpty()) {
				fail("Could not run test since concepts endpoint returned empty");
			}
			
			String conceptId = concepts.get(0).getId();
			
			conceptsApi.getExactMatchesToConcept(conceptId);
			
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testExactMatchesToConceptListCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		try {
			List<BeaconConcept> concepts = conceptsApi.getConcepts(Utils.asList("e"), null, 1, 1);
			
			if (concepts.isEmpty()) {
				fail("Could not run test since concepts endpoint returned empty");
			}
			
			String conceptId = concepts.get(0).getId();
			
			conceptsApi.getExactMatchesToConceptList(Utils.asList(conceptId));
			
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testStatementsCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		StatementsApi statementsApi = new StatementsApi(apiClient);
		try {
			List<BeaconConcept> concepts = conceptsApi.getConcepts(Utils.asList("e"), null, 1, 1);
			
			if (concepts.isEmpty()) {
				fail("Could not run test since concepts endpoint returned empty");
			}
			
			String conceptId = concepts.get(0).getId();
			
			statementsApi.getStatements(Utils.asList(conceptId), null, null, null, null, 1, 1);
			
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEvidenceCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		StatementsApi statementsApi = new StatementsApi(apiClient);
		try {
			List<BeaconConcept> concepts = conceptsApi.getConcepts(Utils.asList("e"), null, 1, 50);
			
			if (concepts.isEmpty()) {
				fail("Could not run test since concepts endpoint returned empty");
			}
			
			for (BeaconConcept concept : concepts) {
				String conceptId = concept.getId();
				List<BeaconStatement> statements = statementsApi.getStatements(Utils.asList(conceptId), null, null, null, null, 1, 1);
				
				if (!statements.isEmpty()) {
					String statementId = statements.get(0).getId();
					
					statementsApi.getEvidence(statementId, null, 1, 1);
					return;
				}
			}
			
			fail("Could not find any evidence to test this endpoint");
			
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testKmapCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		MetadataApi metadataApi = new MetadataApi(apiClient);
		try {
			metadataApi.getKnowledgeMap();
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testTypesCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		MetadataApi metadataApi = new MetadataApi(apiClient);
		try {
			metadataApi.getConceptTypes();
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testPredicatesCall() {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		MetadataApi metadataApi = new MetadataApi(apiClient);
		try {
			metadataApi.getPredicates();
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}

}
