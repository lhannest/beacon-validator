package bio.knowledge.validator.containers;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bio.knowledge.client.ApiException;
import bio.knowledge.client.api.MetadataApi;
import bio.knowledge.client.model.BeaconConceptType;
import bio.knowledge.client.model.BeaconKnowledgeMapStatement;
import bio.knowledge.client.model.BeaconPredicate;
import bio.knowledge.validator.ApiClient;

@Component
public class MetadataContainer {
	
	@Value(value="${basePath}")
	public String BASE_PATH;
	
	private List<BeaconConceptType> types;
	private List<BeaconKnowledgeMapStatement> kmap;
	private List<BeaconPredicate> predicates;
	
	private MetadataApi metadataApi;
	
	@PostConstruct
	public void init() throws ApiException {
		metadataApi = new MetadataApi(new ApiClient(BASE_PATH));
	}
	
	public List<BeaconConceptType> getTypes() throws ApiException {
		if (types != null) {
			return this.types;
		} else {
			types = metadataApi.getConceptTypes();
			return types;
		}
	}
	
	public List<BeaconKnowledgeMapStatement> getKMap() throws ApiException {
		if (kmap != null) {
			return this.kmap;
		} else {
			kmap = metadataApi.getKnowledgeMap();
			return kmap;
		}
	}
	
	public List<BeaconPredicate> getPredicates() throws ApiException {
		if (predicates != null) {
			return this.predicates;
		} else {
			predicates = metadataApi.getPredicates();
			return predicates;
		}
	}

}
