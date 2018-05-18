//package bio.knowledge.validator.aggregator;
//
//
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import bio.knowledge.aggregator.client.ApiResponse;
//import bio.knowledge.aggregator.client.model.ServerConcept;
//import bio.knowledge.aggregator.client.model.ServerConceptWithDetails;
//import bio.knowledge.aggregator.client.model.ServerConceptsQuery;
//import bio.knowledge.aggregator.client.model.ServerConceptsQueryBeaconStatus;
//import bio.knowledge.aggregator.client.model.ServerConceptsQueryResult;
//import bio.knowledge.aggregator.client.model.ServerConceptsQueryStatus;
//import bio.knowledge.client.ApiException;
//import bio.knowledge.client.api.ConceptsApi;
//import bio.knowledge.client.model.BeaconConcept;
//import bio.knowledge.client.model.BeaconConceptWithDetails;
//
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
//
///*
// * Tests simple queries to see if results make sense and if results from individual beacons 
// * match results for KBA running on localhost
// * 
// * May 4, 2018
// * - testing 2 beacons: Biolink, Reference (SemMedDB)
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AggregatorTest {
//	
//	@Value(value = "${biolinkBeaconPath}")
//	private static String PATH_BIOLINK;
//	
//	@Value(value = "${referenceBeaconPath}")
//	private static String PATH_REF;
//	
//	@Value(value = "${kbaPath}")
//	private static String PATH_KBA;
//	
//	bio.knowledge.client.ApiClient biolinkApiClient;
//	bio.knowledge.client.api.ConceptsApi biolinkConceptsApi;
//	
//	bio.knowledge.client.ApiClient refApiClient;
//	bio.knowledge.client.api.ConceptsApi refConceptsApi;
//	
//	bio.knowledge.aggregator.client.ApiClient kbaApiClient;
//	bio.knowledge.aggregator.client.api.ConceptsApi kbaConceptsApi;
//	
//	
//	
//	@Before
//	public void setUpConceptsApi() {
//		// set ApiClients for each beacon and the KBA
//		biolinkApiClient = new bio.knowledge.client.ApiClient();
//		biolinkApiClient.setBasePath(PATH_BIOLINK);
//		
//		refApiClient = new bio.knowledge.client.ApiClient();
//		refApiClient.setBasePath(PATH_REF);
//		
//		kbaApiClient = new bio.knowledge.aggregator.client.ApiClient();
//		kbaApiClient.setBasePath(PATH_KBA);
//	
//		
//		
//		// Create ConceptsApi
//		biolinkConceptsApi = new bio.knowledge.client.api.ConceptsApi(biolinkApiClient);
//		refConceptsApi = new bio.knowledge.client.api.ConceptsApi(refApiClient);
//		kbaConceptsApi = new bio.knowledge.aggregator.client.api.ConceptsApi(kbaApiClient);
//	}
//	
//	
//	
//	@SuppressWarnings("unchecked")
//	public <T> List<T> toList(T... vargs) {
//		List<T> newList = new ArrayList<T>();
//		for (T item : vargs) {
//			newList.add(item);
//		}
//		
//		return newList;
//	}
//	
//	@Test
//	public void biolinkConnecting() throws ApiException {
//		List<BeaconConcept> concepts = biolinkConceptsApi.getConcepts(toList("foo", "diabetes"), null, 1);
//		assertTrue(concepts.size() != 0);
//	}
//	
//	@Test
//	public void refBeaconConnecting() throws ApiException {
//		List<BeaconConcept> concepts = refConceptsApi.getConcepts(toList("foo"), null, 1);
//		assertTrue(concepts.size() != 0);
//	}
//	
//	@Test
//	public void kbaConnecting() throws bio.knowledge.aggregator.client.ApiException, InterruptedException {
//		ApiResponse<ServerConceptsQuery> resp = kbaConceptsApi.postConceptsQueryWithHttpInfo("ajfklje", null, null);
//		assertThat(resp.getStatusCode(), equalTo(200));
//	}
//	
//	
////	@Test
////	// Tests whether cliqueId will update upon receiving more information from MONDO:0005406 call
////	// - first assert will fail if database already contains correct clique info about MESH:D016640
////	public void cliqueEndpointMerge() throws bio.knowledge.aggregator.client.ApiException {
////		assertThat(kbaConceptsApi.getClique("MESH:D016640").getCliqueId(), equalTo("MESH:D016640"));
////		assertThat(kbaConceptsApi.getClique("MONDO:0005406").getCliqueId(), equalTo("NCIT:C34942"));
////		assertThat(kbaConceptsApi.getClique("MESH:D016640").getCliqueId(), equalTo("NCIT:C34942"));
////	}
//
//
//	@Test
//	// tests concept query for "GDM" correctly populates data about clique
//	public void kbaDiabetes() throws bio.knowledge.aggregator.client.ApiException, InterruptedException {
//		
//		ApiResponse<ServerConceptsQuery> resp = kbaConceptsApi.postConceptsQueryWithHttpInfo("GDM", null, null);
//		assertThat(resp.getStatusCode(), equalTo(200));
//		
//		String id = resp.getData().getQueryId();
//		waitAndCheckForStatusCompletion(id, 2);	
//		
//		//check some result was found
//		List<ServerConcept> concepts = kbaConceptsApi.getConcepts(id, null, 1, 1).getResults();
//		assertThat(concepts.size(), is(not(0)));
//		
//		//check we obtained correct clique info
//		assertThat(kbaConceptsApi.getClique("MESH:D016640").getCliqueId(), equalTo("NCIT:C34942"));
//		assertThat(kbaConceptsApi.getClique("MONDO:0005406").getCliqueId(), equalTo("NCIT:C34942"));
//		
//		assertThat(kbaConceptsApi.getConceptDetails("NCIT:C34942", null).getName(), equalTo("gestational diabetes"));
//		assertThat(kbaConceptsApi.getConceptDetails("NCIT:C34942", null).getType(), equalTo("disease"));
//	}
//	
//	
//	@Test
//	// tests concept query for "FANC" gives results and matches info from beacons on certain ids
//	// WARN: will take a while to complete
//	public void testFANC() throws bio.knowledge.aggregator.client.ApiException, InterruptedException, ApiException {
//		
//		ApiResponse<ServerConceptsQuery> resp = kbaConceptsApi.postConceptsQueryWithHttpInfo("FANC", null, null);
//		assertThat(resp.getStatusCode(), equalTo(200));
//		
//		String id = resp.getData().getQueryId();
//		waitAndCheckForStatusCompletion(id, 4);	
//		
//		//check some result was found
//		List<ServerConcept> concepts = kbaConceptsApi.getConcepts(id, null, 1, 200).getResults();
//		assertThat(concepts.size(), is(not(equalTo(0))));
//		
//		// spot check for concepts
//		Set<String> ids = new HashSet<>(); 
//		for (ServerConcept concept : concepts) {
//			ids.add(concept.getClique());
//		}
//		assertThat("FANC search contains WD:Q1704531", ids.contains("WD:Q1704531"), equalTo(true));
//		assertThat("FANC search contains MGI:4355566", ids.contains("MGI:4355566"), equalTo(true));
//		
//		ServerConceptWithDetails kbaRefC = kbaConceptsApi.getConceptDetails("WD:Q1704531", null); 
//		BeaconConceptWithDetails refC = refConceptsApi.getConceptDetails("wd:Q1704531").get(0);
//		assertThat(kbaRefC.getName(), equalTo(refC.getName()));
//		assertThat(kbaRefC.getType(), equalTo(refC.getCategory()));
//		assertThat(kbaRefC.getEntries().get(0).getDefinition(), equalTo(refC.getDefinition()));
//		
//		ServerConceptWithDetails kbaBiolinkC = kbaConceptsApi.getConceptDetails("MGI:4355566", null); 
//		BeaconConceptWithDetails biolinkC = biolinkConceptsApi.getConceptDetails("MGI:4355566").get(0);
//		assertThat(kbaBiolinkC.getName(), equalTo(biolinkC.getName()));
//		
//		// TODO: failing test: biolink should return appropriate type instead of Named Thing
//		//assertThat(kbaBiolinkC.getType(), equalTo(biolinkC.getType()));
//		assertThat(kbaBiolinkC.getEntries().get(0).getDefinition(), equalTo(biolinkC.getDefinition()));
//		
//	}
//	
////	@Test
////	// TODO: failing test - concept is missing
////	public void kbaDiabetes2() throws bio.knowledge.aggregator.client.ApiException, InterruptedException, ApiException {
////		
////		ApiResponse<ServerConceptsQuery> resp = kbaConceptsApi.postConceptsQueryWithHttpInfo("GDM", null, null);
////		assertThat(resp.getStatusCode(), equalTo(200));
////		
////		String id = resp.getData().getQueryId();
////		waitAndCheckForStatusCompletion(id);	
////		
////		List<ServerConcept> concepts = kbaConceptsApi.getConcepts(id, null, 1, 10).getResults();
////		List<BeaconConcept> biolinkConcepts = biolinkConceptsApi.getConcepts(toList("GDM"), null, null, null);
////		List<BeaconConcept> refConcepts = refConceptsApi.getConcepts(toList("GDM"), null, null, null);
////		
////		Set<String> ids = new HashSet<>(); 
////		for (ServerConcept concept : concepts) {
////			ids.add(concept.getClique());
////		}
////		
////		assertThat(ids.size(), equalTo(concepts.size()));
////		
////		for (BeaconConcept concept : refConcepts) {
////			String clique = kbaConceptsApi.getClique(concept.getId()).getCliqueId();
////			assertThat("RefBeacon: " + clique + " exists", ids.contains(clique), equalTo(true));
////			
////		}
////		
////		for (BeaconConcept concept : biolinkConcepts) {
////			String clique = kbaConceptsApi.getClique(concept.getId()).getCliqueId();
////			assertThat("Biolink: " + clique + " exists", ids.contains(clique), equalTo(true));
////		}
////		
////	}
//	
//	
//	/**
//	 * Completes when all statuses show that query is complete
//	 * Waits by sleeping the thread
//	 * TODO: aysnc waiting?
//	 * @param id - of query
//	 * @param int - wait time in seconds
//	 */
//	public void waitAndCheckForStatusCompletion (String id, int wait) throws InterruptedException, bio.knowledge.aggregator.client.ApiException {
//		
//		List<ServerConceptsQueryBeaconStatus> statuses = kbaConceptsApi.getConceptsQueryStatus(id, null).getStatus();
//		
//		for (int i = 0; i < statuses.size(); i++) {
//			while (true) {
//				
//				// currently checks if complete based on whether count has returned or not
//				ServerConceptsQueryBeaconStatus s = statuses.get(i);
//				if (s.getCount() != null) {
//					// status should give either 200 or 201 to indicate query is complete
//					assertThat(s.getStatus(), anyOf(equalTo(200), equalTo(201)));
//					break;
//				}
//	
//				// check status is correctly set to 102 
//				assertThat(s.getStatus(), equalTo(102));
//				TimeUnit.SECONDS.sleep(wait);
//				
//				// re-check query status
//				statuses = kbaConceptsApi.getConceptsQueryStatus(id, null).getStatus();
//			}
//			
//		}
//	}
//	
//}
//	
