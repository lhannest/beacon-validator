/*
 * Translator Knowledge Beacon API
 * This is the Translator Knowledge Beacon web service application programming interface (API). 
 *
 * OpenAPI spec version: 1.0.18
 * Contact: richard@starinformatics.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package bio.knowledge.client.model;

import java.util.Objects;
import bio.knowledge.client.model.BeaconConceptDetail;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * BeaconConceptWithDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-18T12:09:55.593-07:00")
public class BeaconConceptWithDetails {
  @SerializedName("id")
  private String id = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("category")
  private String category = null;

  @SerializedName("synonyms")
  private List<String> synonyms = new ArrayList<String>();

  @SerializedName("definition")
  private String definition = null;

  @SerializedName("details")
  private List<BeaconConceptDetail> details = new ArrayList<BeaconConceptDetail>();

  public BeaconConceptWithDetails id(String id) {
    this.id = id;
    return this;
  }

   /**
   * local object identifier for the concept in the specified knowledge source database 
   * @return id
  **/
  @ApiModelProperty(example = "null", value = "local object identifier for the concept in the specified knowledge source database ")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BeaconConceptWithDetails name(String name) {
    this.name = name;
    return this;
  }

   /**
   * canonical human readable name of the concept 
   * @return name
  **/
  @ApiModelProperty(example = "null", value = "canonical human readable name of the concept ")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BeaconConceptWithDetails category(String category) {
    this.category = category;
    return this;
  }

   /**
   * concept semantic type 
   * @return category
  **/
  @ApiModelProperty(example = "null", value = "concept semantic type ")
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public BeaconConceptWithDetails synonyms(List<String> synonyms) {
    this.synonyms = synonyms;
    return this;
  }

  public BeaconConceptWithDetails addSynonymsItem(String synonymsItem) {
    this.synonyms.add(synonymsItem);
    return this;
  }

   /**
   * list of synonyms for concept 
   * @return synonyms
  **/
  @ApiModelProperty(example = "null", value = "list of synonyms for concept ")
  public List<String> getSynonyms() {
    return synonyms;
  }

  public void setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
  }

  public BeaconConceptWithDetails definition(String definition) {
    this.definition = definition;
    return this;
  }

   /**
   * concept definition 
   * @return definition
  **/
  @ApiModelProperty(example = "null", value = "concept definition ")
  public String getDefinition() {
    return definition;
  }

  public void setDefinition(String definition) {
    this.definition = definition;
  }

  public BeaconConceptWithDetails details(List<BeaconConceptDetail> details) {
    this.details = details;
    return this;
  }

  public BeaconConceptWithDetails addDetailsItem(BeaconConceptDetail detailsItem) {
    this.details.add(detailsItem);
    return this;
  }

   /**
   * Get details
   * @return details
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<BeaconConceptDetail> getDetails() {
    return details;
  }

  public void setDetails(List<BeaconConceptDetail> details) {
    this.details = details;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BeaconConceptWithDetails beaconConceptWithDetails = (BeaconConceptWithDetails) o;
    return Objects.equals(this.id, beaconConceptWithDetails.id) &&
        Objects.equals(this.name, beaconConceptWithDetails.name) &&
        Objects.equals(this.category, beaconConceptWithDetails.category) &&
        Objects.equals(this.synonyms, beaconConceptWithDetails.synonyms) &&
        Objects.equals(this.definition, beaconConceptWithDetails.definition) &&
        Objects.equals(this.details, beaconConceptWithDetails.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, category, synonyms, definition, details);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BeaconConceptWithDetails {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    synonyms: ").append(toIndentedString(synonyms)).append("\n");
    sb.append("    definition: ").append(toIndentedString(definition)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

