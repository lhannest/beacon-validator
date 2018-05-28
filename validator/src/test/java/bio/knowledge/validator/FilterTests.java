package bio.knowledge.validator;


import static bio.knowledge.validator.Assert.assertTrue;
import static bio.knowledge.validator.Assert.fail;

import java.util.ArrayList;
import java.util.List;
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
import bio.knowledge.client.model.BeaconConcept;
import bio.knowledge.client.model.BeaconStatement;
import bio.knowledge.client.model.BeaconStatementObject;
import bio.knowledge.client.model.BeaconStatementSubject;
import bio.knowledge.validator.containers.FilterSetContainer;
import bio.knowledge.validator.containers.FilterSetContainer.FilterSet;
import bio.knowledge.validator.containers.MetadataContainer;
import bio.knowledge.validator.rules.RuleContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterTests {
	@Value(value="${basePath}")
	public String BASE_PATH;
	
	@Autowired FilterSetContainer filterSetContainer;
	@Autowired MetadataContainer metadataContainer;
	@Autowired RuleContainer ruleContainer;
	
	@Rule public TestWatcher testWatcher;
	@Rule public Stopwatch stopwatch;
	
	@PostConstruct
	public void init() {
		testWatcher = ruleContainer.getTestWatcher();
		stopwatch = ruleContainer.getStopwatch();
	}
	
	private static final Integer FILTER_SIZE = 3;
	
	@Test
	public void testConceptFilters() throws ApiException {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		
		FilterSet filterSet = filterSetContainer.getFilterSet();
		
		List<List<String>> typesList = filterSet.getTypesList();
		List<List<String>> keywordsList = filterSet.getKeywordsList();
		
		for (List<String> keywords : keywordsList) {
			for (List<String> types : typesList) {
				List<BeaconConcept> concepts = conceptsApi.getConcepts(keywords, types, 100);
				
				for (BeaconConcept concept : concepts) {
					assertTrue(apiClient, "Types filter failed for " + concept.getId(), types.contains(concept.getCategory()));
					
					Boolean name = keywords.stream().anyMatch(keyword -> contains(keyword, concept.getName()));
					Boolean definition = keywords.stream().anyMatch(keyword -> contains(keyword, concept.getDefinition()));
					Boolean synonyms = keywords.stream().anyMatch(keyword -> concept.getSynonyms().stream().anyMatch(synonym -> contains(keyword, synonym)));
					
					assertTrue(
							apiClient,
							"Keywords filter failed for " + concept.getId(),
							keywords.stream().anyMatch(keyword -> contains(keyword, concept.getName())) ||
							keywords.stream().anyMatch(keyword -> contains(keyword, concept.getDefinition())) ||
							keywords.stream().anyMatch(keyword -> concept.getSynonyms().stream().anyMatch(synonym -> contains(keyword, synonym)))
					);
				}
			}
		}
	}
	
	
	@Test
	public void testStatementsFilters() throws ApiException {
		FilterSet filterSet = filterSetContainer.getFilterSet();
		
		List<String> predicates = metadataContainer.getPredicates().stream().map(
				predicate -> predicate.getId()
		).collect(Collectors.toList());
		
		List<List<String>> typesList = filterSet.getTypesList();
		List<List<String>> keywordsList = filterSet.getKeywordsList();
		
		ApiClient apiClient = new ApiClient(BASE_PATH);
		StatementsApi statementsApi = new StatementsApi(apiClient);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		
		for (List<String> keywords : keywordsList) {
			for (List<String> types : typesList) {
				List<BeaconConcept> concepts = conceptsApi.getConcepts(keywords, null, FILTER_SIZE);
				
				List<String> s = concepts.stream().map(concept -> concept.getId()).collect(Collectors.toList());
				
				for (String predicate : predicates) {
					List<BeaconStatement> statements = statementsApi.getStatements(s, Utils.asList(predicate), null, keywords, types, 100);
					
					List<String> targets = new ArrayList<String>();
					
					for (BeaconStatement statement : statements) {
						assertTrue(
								apiClient,
								"Predicate filter failed for statement " + statement.getId(),
								statement.getPredicate().getEdgeLabel().equals(predicate)
						);
						
						BeaconStatementSubject subject = statement.getSubject();
						BeaconStatementObject object = statement.getObject();
						
						if (s.contains(subject.getId())) {
							targets.add(object.getId());
						} else if (s.contains(object.getId())) {
							targets.add(subject.getId());
						} else {
							fail(apiClient, "Source filter failed for statement " + statement.getId());
						}
					}
					
					targets  = targets.subList(0, Math.min(FILTER_SIZE, targets.size()));
					
					List<BeaconStatement> targetedStatements = statementsApi.getStatements(s, null, targets, null, null, 100);
					
					for (BeaconStatement statement : targetedStatements) {
						String subjectId = statement.getSubject().getId();
						String objectId = statement.getObject().getId();
						
						String targetId = null;
						
						if (s.contains(subjectId)) {
							targetId = objectId;
						} else if (s.contains(objectId)) {
							targetId = subjectId;
						} else {
							fail(apiClient, "Source filter failed for statement " + statement.getId());
						}
						
						//TODO: Check against exact matches list
						
						assertTrue(
								apiClient,
								"Target filter failed for statement " + statement.getId(),
								targets.isEmpty() || targets.contains(targetId)
						);
					}
				}
			}
		}
	}
	
	/**
	 * Returns true if any term in substring is contained in superstring, false otherwise.
	 */
	private boolean contains(String superstring, String substring) {
		if (substring == null) {
			return false;
		}
		
		if (substring.contains(" ")) {
			String[] substrings = substring.split(" ");
			
			for (String s : substrings) {
				if (contains(superstring, s)) return true;
			}
			
			return false;
		}
		
		superstring = superstring.toLowerCase();
		substring = substring.toLowerCase();
		
		return (superstring.contains(substring) || substring.contains(superstring));
	}
}
