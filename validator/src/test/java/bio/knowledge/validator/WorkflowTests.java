package bio.knowledge.validator;

import static bio.knowledge.validator.Assert.assertFalse;
import static bio.knowledge.validator.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
import bio.knowledge.client.api.StatementsApi;
import bio.knowledge.client.extra.Concept;
import bio.knowledge.client.model.BeaconConcept;
import bio.knowledge.client.model.BeaconConceptWithDetails;
import bio.knowledge.client.model.BeaconStatement;
import bio.knowledge.validator.logging.Logger;
import bio.knowledge.validator.logging.LoggerFactory;
import bio.knowledge.validator.rules.RuleContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkflowTests {
	
	@Value(value="${basePath}")
	public String BASE_PATH;
	
	@Autowired RuleContainer ruleContainer;
	@Autowired LoggerFactory loggerFactory;
	
	@Rule public TestWatcher watcher;
	@Rule public Stopwatch stopwatch;
	
	@PostConstruct
	public void init() {
		stopwatch = ruleContainer.getStopwatch();
		watcher = ruleContainer.getTestWatcher();
	}
	
	private final Integer EXACT_MATCH_PAGE_SIZE = 10;
	private final Integer PAGE_NUMBER = 1;
	private final Integer CONCEPTS_PAGE_SIZE = 100;
	private final Integer STATEMENTS_PAGE_SIZE = 100;
	private final List<String> CONCEPTS_KEYWORDS = Utils.asList("e");
	private final List<String> CONCEPTS_TYPES = null;
	
	@Test
	public void testConcepts() throws ApiException {		
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		
		List<BeaconConcept> concepts = conceptsApi.getConcepts(CONCEPTS_KEYWORDS, CONCEPTS_TYPES, PAGE_NUMBER, CONCEPTS_PAGE_SIZE);
		
		assertFalse(
				apiClient,
				"No concepts found for filter keywords/types: " + CONCEPTS_KEYWORDS + "/" + CONCEPTS_TYPES,
				concepts == null || concepts.isEmpty()
		);
		
		assertFalse(
				apiClient,
				"Requested page size of " + CONCEPTS_PAGE_SIZE + " got " + concepts.size() + " instead",
				concepts.size() > CONCEPTS_PAGE_SIZE
		);
		
		// Test that the filters apply to each concept
		for (BeaconConcept concept : concepts) {
			String name = concept.getName();
			String definition = concept.getDefinition();
			
			List<String> synonyoms = concept.getSynonyms();
			
			String type = concept.getType();
			
			assertTrue(
					apiClient,
					"Keyword filter failed for: " + concept.getId(),
					CONCEPTS_KEYWORDS.stream().anyMatch(k -> contains(name, k)) ||
					CONCEPTS_KEYWORDS.stream().anyMatch(k -> contains(definition, k)) ||
					synonyoms.stream().anyMatch(s -> CONCEPTS_KEYWORDS.stream().anyMatch(k -> contains(s, k)))
			);
			
			assertTrue(
					apiClient,
					"Type filter failed for: " + concept.getId(),
					CONCEPTS_TYPES == null || CONCEPTS_TYPES.stream().anyMatch(type::equals)
			);
		}
	}
	
	@Test
	public void testConceptDetails() throws ApiException {
		Logger logger = loggerFactory.get(BASE_PATH);
		
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		
		List<BeaconConcept> concepts = conceptsApi.getConcepts(CONCEPTS_KEYWORDS, CONCEPTS_TYPES, PAGE_NUMBER, CONCEPTS_PAGE_SIZE);
		
		for (BeaconConcept concept : concepts) {
			List<BeaconConceptWithDetails> details = conceptsApi.getConceptDetails(concept.getId());
			
			for (BeaconConceptWithDetails detail : details) {
				String msg = "Detail %1$s is not the same as concept %1$s";
				
				if (detail.getId() == null) logger.warn("Concept " + detail.getId() + " has null ID");
				if (detail.getType() == null) logger.warn("Concept " + detail.getId() + " has null type");
				if (detail.getName() == null) logger.warn("Concept " + detail.getId() + " has null name");
				if (detail.getDefinition() == null) logger.warn("Concept " + detail.getId() + " has null definition");
				if (detail.getSynonyms() == null) logger.warn("Concept " + detail.getId() + " has null synonyms");
				
				assertTrue(
						apiClient,
						String.format(msg, "ID"),
						Objects.equals(detail.getId(), concept.getId())
				);
				
				assertTrue(
						apiClient,
						String.format(msg, "definition"),
						Objects.equals(detail.getDefinition(), concept.getDefinition())
				);
				
				assertTrue(
						apiClient,
						String.format(msg, "name"),
						Objects.equals(detail.getName(), concept.getName())
				);
				
				assertTrue(
						apiClient,
						String.format(msg, "type"),
						Objects.equals(detail.getType(), concept.getType())
				);
				
				assertTrue(
						apiClient,
						String.format(msg, "synonyms"),
						Objects.equals(detail.getSynonyms(), concept.getSynonyms())
				);
			}
		}
	}
	
	@Test
	public void testStatements() throws ApiException {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		StatementsApi statementsApi = new StatementsApi(apiClient);
		
		//TODO: remove magic number
		List<BeaconConcept> concepts = conceptsApi.getConcepts(CONCEPTS_KEYWORDS, CONCEPTS_TYPES, PAGE_NUMBER, 4);
		
		List<String> c = concepts.stream().map(x -> x.getId()).collect(Collectors.toList());
		
		List<BeaconStatement> statements = statementsApi.getStatements(c, null, null, null, null, 1, STATEMENTS_PAGE_SIZE);
		
		for (BeaconStatement statement : statements) {
			boolean anyMatch = false;
			
			for (BeaconConcept concept : concepts) {
				Concept subject = statement.getSubject();
				Concept object = statement.getObject();
				
				String msg = "Concept %s from statement %s does not have the same name and type as result from concept search";
				
				if (subject.getId().equals(concept.getId())) {
					anyMatch = true;
					assertTrue(
							apiClient,
							String.format(msg, subject.getId(), statement.getId()),
							Objects.equals(subject.getType(), concept.getType()) &&
							Objects.equals(subject.getName(), concept.getName())
					);
				} else if (object.getId().equals(concept.getId())) {
					anyMatch = true;
					assertTrue(
							apiClient,
							String.format(msg, object.getId(), statement.getId()),
							Objects.equals(object.getType(), concept.getType()) &&
							Objects.equals(object.getName(), concept.getName())
					);
				}
			}
			
			assertTrue(apiClient, "None of the given concepts found in statement " + statement.getId(), anyMatch);
		}
	}
	
	@Test
	public void testExactMatches() throws ApiException {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		
		List<BeaconConcept> concepts = conceptsApi.getConcepts(CONCEPTS_KEYWORDS, CONCEPTS_TYPES, PAGE_NUMBER, EXACT_MATCH_PAGE_SIZE);
		
		for (BeaconConcept concept : concepts) {
			List<String> matches1 = conceptsApi.getExactMatchesToConcept(concept.getId());
			Set<String> matches1Set = new HashSet<String>(matches1);
			
			assertTrue(apiClient, "The response is not a set", matches1.size() == matches1Set.size());
			
			List<String> matches2 = conceptsApi.getExactMatchesToConceptList(Utils.asList(concept.getId()));
			Set<String> matches2Set = new HashSet<String>(matches2);
			
			assertTrue(apiClient, "The response is not a set", matches2.size() == matches2Set.size());
			
			matches1Set.add(concept.getId());
			matches2Set.add(concept.getId());
			
			assertTrue(apiClient, "The two exact match endpoints did not return the same data", matches1Set.equals(matches2Set));
		}
	}
	
	private boolean contains(String superstring, String substring) {
		return superstring.toLowerCase().contains(substring.toLowerCase());
	}

}
