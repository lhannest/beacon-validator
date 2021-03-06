/*
 * Translator Knowledge Beacon Aggregator API
 * This is the Translator Knowledge Beacon Aggregator web service application programming interface (API) that provides integrated access to a pool of knowledge sources publishing concepts and relations through the Translator Knowledge Beacon API. This API is similar to that of the latter mentioned API with the addition of some extra informative endpoints plus session identifier and beacon indices. These latter identifiers are locally assigned numeric indices provided to track the use of specific registered beacons within the aggregator API itself. 
 *
 * OpenAPI spec version: 1.0.10
 * Contact: richard@starinformatics.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package bio.knowledge.aggregator.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ServerCliqueIdentifier
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-04T13:48:21.751-07:00")
public class ServerCliqueIdentifier {
  @SerializedName("cliqueId")
  private String cliqueId = null;

  public ServerCliqueIdentifier cliqueId(String cliqueId) {
    this.cliqueId = cliqueId;
    return this;
  }

   /**
   * CURIE identifying the equivalent concept clique to which the  input concept CURIE belongs. 
   * @return cliqueId
  **/
  @ApiModelProperty(example = "null", value = "CURIE identifying the equivalent concept clique to which the  input concept CURIE belongs. ")
  public String getCliqueId() {
    return cliqueId;
  }

  public void setCliqueId(String cliqueId) {
    this.cliqueId = cliqueId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerCliqueIdentifier serverCliqueIdentifier = (ServerCliqueIdentifier) o;
    return Objects.equals(this.cliqueId, serverCliqueIdentifier.cliqueId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cliqueId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServerCliqueIdentifier {\n");
    
    sb.append("    cliqueId: ").append(toIndentedString(cliqueId)).append("\n");
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

