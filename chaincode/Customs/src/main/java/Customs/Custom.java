package Customs;
import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public class Custom {
	@Property()
	private String nameOfEmployee;
	@Property()
	private String identification;
	@Property()
	private String organization;
	@Property()
	private String timeOfApproval;
	@Property()
	private String portOfApproval;
	@Property()
	private String itemNumber;
	@Property()
	private String validity;
	
	public String getNameOfEmployee() {
		return nameOfEmployee;
	}
	public String getIdentification() {
		return identification;
	}
	public String getOrganization() {
		return organization;
	}
	public String getTimeOfApproval() {
		return timeOfApproval;
	}
	public String getPortOfApproval() {
		return portOfApproval;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public String getValidity() {
		return validity;
	}
	public Custom(@JsonProperty("nameOfEmployee") final String nameOfEmployee,
			@JsonProperty("identification") final String identification ,
			@JsonProperty("organization") final String organization ,
			@JsonProperty("timeOfApproval") final String timeOfApproval ,
			@JsonProperty("portOfApproval") final String portOfApproval , 
			@JsonProperty("itemNumber") final String itemNumber , 
			@JsonProperty("validity") final String validity) {
		this.nameOfEmployee=nameOfEmployee;
		this.identification=identification;
		this.organization=organization;
		this.timeOfApproval=timeOfApproval;
		this.portOfApproval=portOfApproval;
		this.itemNumber=itemNumber;
		this.validity=validity;
	}
	@Override
    public int hashCode() {
        return Objects.hash(getNameOfEmployee(),getIdentification(),getOrganization(),
        		getTimeOfApproval(),getPortOfApproval(),getItemNumber(),getValidity());
    }
	@Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Custom other = (Custom) obj;

        return Objects.deepEquals(new String[] {getNameOfEmployee(),getIdentification(),getOrganization(),
        		getTimeOfApproval(),getPortOfApproval(),getItemNumber(),getValidity()},
                new String[] {other.getNameOfEmployee(),other.getIdentification(),other.getOrganization(),
                		other.getTimeOfApproval(),other.getPortOfApproval(),other.getItemNumber(),other.getValidity()});
    }
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode())+"Custom [nameOfEmployee=" + nameOfEmployee + ", identification=" + identification + ", organization="
				+ organization + ", timeOfApproval=" + timeOfApproval + ", portOfApproval=" + portOfApproval
				+ ", itemNumber=" + itemNumber + ", validity=" + validity + "]";
	}
	
	
}
