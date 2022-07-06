package ItemVerification;

import java.util.Objects;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.annotation.JsonProperty;

@DataType()
public class Item {
	
	@Property()
	private String exporterName;
	@Property()
	private String dateAndTime;
	
	@Property()
	private String itemNumber;
	@Property()
	private String itemDescription;
	@Property()
	private String origin;
	@Property()
	private String packageNumber;
	@Property()
	private String packageType;
	@Property()
	private String quantity;
	@Property()
	private String grossWeight;
	@Property()
	private String invoiceDate;
	@Property()
	private String invoiceNumber;
	@Property()
	private Package package1;
	

	public String getExporterName() {
		return exporterName;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public String getOrigin() {
		return origin;
	}

	public String getPackageNumber() {
		return packageNumber;
	}

	public String getPackageType() {
		return packageType;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Package getPackage1() {
		return package1;
	}

	public Item(@JsonProperty("exporterName")final String exporterName,
			@JsonProperty("dateAndTime")final String dateAndTime,
			@JsonProperty("itemNumber")final String itemNumber,
			@JsonProperty("itemDescription")final String itemDescription,
			@JsonProperty("origin")final  String origin,
			@JsonProperty("packageNumber")final  String packageNumber,
			@JsonProperty("packageType")final String packageType,
			@JsonProperty("quantity")final  String quantity,
			@JsonProperty("grossWeight")final String grossWeight,
			@JsonProperty("invoiceDate")final String invoiceDate,
			@JsonProperty("invoiceNumber") final String invoiceNumber) {
		this.exporterName=exporterName;
		this.dateAndTime=dateAndTime;
		this.itemNumber = itemNumber;
		this.itemDescription = itemDescription;
		this.origin = origin;
		this.packageNumber = packageNumber;
		this.packageType = packageType;
		this.quantity = quantity;
		this.grossWeight = grossWeight;
		this.invoiceDate = invoiceDate;
		this.invoiceNumber = invoiceNumber;
		this.package1=new Package(itemNumber,itemDescription,origin,packageNumber,packageType,quantity,grossWeight,invoiceDate,invoiceNumber);

	}
	
	@Override
    public int hashCode() {
        return Objects.hash(getExporterName(),getDateAndTime(),getItemNumber(),getItemDescription(),getOrigin(),getPackageNumber(),getPackageType(),
        		getQuantity(),getGrossWeight(),getInvoiceDate(),getInvoiceNumber());
    }
	@Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Item other = (Item) obj;

        return Objects.deepEquals(new String[] {getExporterName(),getDateAndTime(),
        		getItemNumber(),getItemDescription(),getOrigin(),getPackageNumber(),getPackageType(),
        		getQuantity(),getGrossWeight(),getInvoiceDate(),getInvoiceNumber()},
                new String[] {other.getExporterName(),other.getDateAndTime(),
                other.getItemNumber(),other.getItemDescription(),other.getOrigin(),other.getPackageNumber(),other.getPackageType(),
                other.getQuantity(),other.getGrossWeight(),other.getInvoiceDate(),other.getInvoiceNumber()});
    }
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode())+
				"Exporter's Name: "+exporterName + "\nDate And Time" + dateAndTime + 
				"\nThe Package details: "+package1.toString() ; 
	}
	
}

class Package {
	
	private final String itemNumber;
	
	private final String itemDescription;
	
	private final String origin;
	
	private final String packageNumber;
	
	private final String packageType;
	
	private final String quantity;
	
	private final String grossWeight;
	
	private final String invoiceDate;
	
	private final String invoiceNumber;

	
	public String getItemNumber() {
		return itemNumber;
	}


	public String getItemDescription() {
		return itemDescription;
	}


	public String getOrigin() {
		return origin;
	}


	public String getPackageNumber() {
		return packageNumber;
	}


	public String getPackageType() {
		return packageType;
	}


	public String getQuantity() {
		return quantity;
	}


	public String getGrossWeight() {
		return grossWeight;
	}


	public String getInvoiceDate() {
		return invoiceDate;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public Package(String itemNumber,
			String itemDescription,
			String origin,
			String packageNumber,
			String packageType,
			String quantity, 
			String grossWeight,
			String invoiceDate, 
			String invoiceNumber) {
		this.itemNumber = itemNumber;
		this.itemDescription = itemDescription;
		this.origin = origin;
		this.packageNumber = packageNumber;
		this.packageType = packageType;
		this.quantity = quantity;
		this.grossWeight = grossWeight;
		this.invoiceDate = invoiceDate;
		this.invoiceNumber = invoiceNumber;
	}
	
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Package other = (Package) obj;

        return Objects.deepEquals(new String[] {getItemNumber(), getItemDescription(), getOrigin(),
        		getPackageNumber(),getPackageType(),getQuantity(),getGrossWeight(),
        		getInvoiceDate(),getInvoiceNumber()  },
                new String[] {other.getItemNumber(), other.getItemDescription(), other.getOrigin(),
                		other.getPackageNumber(),other.getPackageType(),other.getQuantity(),
                		other.getGrossWeight(),other.getInvoiceDate(),other.getInvoiceNumber() });
    }
	
    public int hashCode() {
        return Objects.hash(getItemNumber(), getItemDescription(), getOrigin(),
        		getPackageNumber(),getPackageType(),getQuantity(),getGrossWeight(),
        		getInvoiceDate(),getInvoiceNumber());
    }

	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + "Package [itemNumber=" + itemNumber + ", itemDescription=" + itemDescription + ", origin=" + origin
				+ ", packageNumber=" + packageNumber + ", packageType=" + packageType + ", quantity=" + quantity
				+ ", grossWeight=" + grossWeight + ", invoiceDate=" + invoiceDate + ", invoiceNumber=" + invoiceNumber
				+ "]";
	}
	
}