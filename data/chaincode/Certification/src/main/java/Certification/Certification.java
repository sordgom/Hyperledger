package Certification;
import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Certification {
	
	@Property()
	private String name ;
	@Property()
	private String address;
	@Property()
	private String country;
	
	@Property()
    private final Company exporter=new Company(name,address,country);
	@Property()
	private String name2;
	@Property()
	private String address2;
	@Property()
	private String country2;
	
	@Property()
	private final Company importer=new Company(name2,address2,country2);
    
	@Property()
	private final String departureDate;

	@Property()
	private final String departureTime;
	
	@Property()
	private final String vesselName;
	
	@Property()
	private final String portOfDischarge;
    
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
	private final Item item;
	
//	@Property()
//	private ArrayList<Item> itemList=new ArrayList<Item>();
	//add the item to the items list
	//itemList.add(item);
	
	public String getExporter() {
		return exporter.toString();
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCountry() {
		return country;
	}

	public String getName2() {
		return name2;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCountry2() {
		return country2;
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

	public String getImporter() {
		return importer.toString();
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public String getVesselName() {
		return vesselName;
	}

	public String getPortOfDischarge() {
		return portOfDischarge;
	}
	public Item getItem() {
		return item;
	}

	public Certification(@JsonProperty("name")final String name,
			@JsonProperty("address")final String address,
			@JsonProperty("country")final String country, 
			@JsonProperty("name2")final String name2, 
			@JsonProperty("address2")final String address2,
			@JsonProperty("country2")final  String country2,
			@JsonProperty("departureDate") final String departureDate,
			@JsonProperty("departureTime") final String departureTime,
			@JsonProperty("vesselName") final String vesselName,
			@JsonProperty("portOfDischarge") final String portOfDischarge,
			@JsonProperty("itemNumber")final String itemNumber,
			@JsonProperty("itemDescription")final String itemDescription,
			@JsonProperty("origin")final  String origin,
			@JsonProperty("packageNumber")final  String packageNumber,
			@JsonProperty("packageType")final String packageType,
			@JsonProperty("quantity")final  String quantity,
			@JsonProperty("grossWeight")final String grossWeight,
			@JsonProperty("invoiceDate")final String invoiceDate,
			@JsonProperty("invoiceNumber") final String invoiceNumber) {
		this.name = name;
		this.address = address;
		this.country = country;
		this.name2 = name2;
		this.address2 = address2;
		this.country2 = country2;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.vesselName = vesselName;
		this.portOfDischarge = portOfDischarge;
		this.itemNumber = itemNumber;
		this.itemDescription = itemDescription;
		this.origin = origin;
		this.packageNumber = packageNumber;
		this.packageType = packageType;
		this.quantity = quantity;
		this.grossWeight = grossWeight;
		this.invoiceDate = invoiceDate;
		this.invoiceNumber = invoiceNumber;
		this.item=new Item(itemNumber,itemDescription,origin,packageNumber,packageType,quantity,grossWeight,invoiceDate,invoiceNumber);

	}

	@Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(),getCountry(),getName2(),getAddress2(),getCountry2(),
        		getDepartureDate(),getDepartureTime(),getVesselName(),getPortOfDischarge(),
        		getItemNumber(),getItemDescription(),getOrigin(),getPackageNumber(),getPackageType(),
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

        Certification other = (Certification) obj;

        return Objects.deepEquals(new String[] {getName(), getAddress(),getCountry(),getName2(),getAddress2(),getCountry2(),
        		getDepartureDate(),getDepartureTime(),getVesselName(),getPortOfDischarge(),
        		getItemNumber(),getItemDescription(),getOrigin(),getPackageNumber(),getPackageType(),
        		getQuantity(),getGrossWeight(),getInvoiceDate(),getInvoiceNumber()},
                new String[] {other.getName(), other.getAddress(), other.getCountry(), other.getName2(), other.getAddress2(), other.getCountry2(),
                other.getDepartureDate(),other.getDepartureTime(),other.getVesselName(),other.getPortOfDischarge(),
                other.getItemNumber(),other.getItemDescription(),other.getOrigin(),other.getPackageNumber(),other.getPackageType(),
                other.getQuantity(),other.getGrossWeight(),other.getInvoiceDate(),other.getInvoiceNumber()});
    }
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode())+"Certification [exporter="+ exporter + ", importer=" +  importer + ", departureDate=" + departureDate + ", departureTime=" + departureTime + ", vesselName="
				+ vesselName + ", portOfDischarge=" + portOfDischarge + " Item="+ item+ ", itemNumber=" + itemNumber
				+ ", itemDescription=" + itemDescription + ", origin=" + origin + ", packageNumber=" + packageNumber
				+ ", packageType=" + packageType + ", quantity=" + quantity + ", grossWeight=" + grossWeight
				+ ", invoiceDate=" + invoiceDate + ", invoiceNumber=" + invoiceNumber + ", item=" + item +  "]";
	}
	
}

class Company{
	private final String name;
	private final String address;
	private final String country;

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCountry() {
		return country;
	}
	
	public Company(final String name,final String address, final String country  ) {
		this.name=name;
		this.address=address;
		this.country=country;
	}
	
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Company other = (Company) obj;

        return Objects.deepEquals(new String[] {getName(), getAddress(), getCountry()},
                new String[] {other.getName(), other.getAddress(), other.getCountry()});
    }
	
	
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getCountry());
    }
	
	
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [Name=" + name + ", address="
                + address + ", country=" + country +"]";
    }
	
}
class Item {
	
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


	public Item(String itemNumber,
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

        Item other = (Item) obj;

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
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + "Item [itemNumber=" + itemNumber + ", itemDescription=" + itemDescription + ", origin=" + origin
				+ ", packageNumber=" + packageNumber + ", packageType=" + packageType + ", quantity=" + quantity
				+ ", grossWeight=" + grossWeight + ", invoiceDate=" + invoiceDate + ", invoiceNumber=" + invoiceNumber
				+ "]";
	}
	
}

