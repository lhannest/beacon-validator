package bio.knowledge.validator.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bio.knowledge.client.ApiException;
import bio.knowledge.client.api.ConceptsApi;
import bio.knowledge.client.model.BeaconConcept;
import bio.knowledge.client.model.BeaconConceptCategory;
import bio.knowledge.validator.ApiClient;
import bio.knowledge.validator.Utils;

@Component
public class FilterSetContainer {
	
	@Value(value="${basePath}")
	public String BASE_PATH;
	
	@Autowired MetadataContainer metadataContainer;
	
	private FilterSet filterSet;
	
	public class FilterSet {
		private List<List<String>> types;
		
		private List<List<String>> keywords;
		
		public List<List<String>> getTypesList() {
			return types;
		}
		
		public List<List<String>> getKeywordsList() {
			return keywords;
		}
		
		private FilterSet(List<List<String>> types, List<List<String>> keywords) {
			this.types = types;
			this.keywords = keywords;
		}
		
	}
	
	private FilterSet buildFilterSet() throws ApiException {
		ApiClient apiClient = new ApiClient(BASE_PATH);
		ConceptsApi conceptsApi = new ConceptsApi(apiClient);
		
		List<BeaconConceptCategory> conceptTypes = metadataContainer.getCategories();
		
		List<String> types = conceptTypes.stream().map(t -> t.getCategory()).collect(Collectors.toList());
		
		// There will be keywords for each type that produces concepts
		List<String> keywords = new ArrayList<String>();
		
		for (String type : types) {
			if (type.isEmpty()) {
				continue;
			}
			
			List<BeaconConcept> concepts = conceptsApi.getConcepts(Utils.asList("e"), Utils.asList(type), 100);
			
			for (BeaconConcept concept : concepts) {
				if (concept.getCategory().equals(type)) {
					keywords.add(concept.getName());
					keywords.addAll(concept.getSynonyms());
					break;
				}
			}
		}
		
		List<List<String>> multiTypes = new ArrayList<List<String>>();
		List<List<String>> multiKeywords = new ArrayList<List<String>>();
		
		int size = 3;
		
		for (int i = 0; i < types.size(); i += size) {
			multiTypes.add(types.subList(i, Math.min(i + size, types.size())));
		}
		
		for (int i = 0; i < keywords.size(); i += size) {
			multiKeywords.add(keywords.subList(i, Math.min(i + size, keywords.size())));
		}

		return new FilterSet(multiTypes, multiKeywords);
	}
	
	public FilterSet getFilterSet() throws ApiException {
		if (filterSet != null) {
			return filterSet;
		} else {
			filterSet = buildFilterSet();
			return filterSet;
		}
	}

}
