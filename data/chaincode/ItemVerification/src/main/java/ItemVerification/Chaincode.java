package ItemVerification;
import java.util.ArrayList;
import java.util.List;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;

@Contract(
        name = "Get Certified!",
        info = @Info(
                title = "My contract",
                description = "Certificate of origin contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "cert@example.com",
                        name = "Fabric cert",
                        url = "https://hyperledger.example.com")))
@Default
public final class Chaincode implements ContractInterface{
    
    private final Genson genson = new Genson();
    
    //common certification issues in the blockchain
    private enum ItemErrors {
        EXPORTER_NOT_FOUND,
        ITEM_ALREADY_VALIDATED,
        CERTIFICATION_NOT_FOUND,
        
    }
    
    /**
     * Creates an initial cert on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        String[] itemData = {
        		"{ \"exporterName\": \"mohmad\" ,\"dateAndTime\": \"daba\" , \"itemNumber\": \"item1\" , \"itemDescription\": \"descr1\" ,\"origin\": \"country3\" , \"packageNumber\": \"1\" ,\"packageType\": \"type1\" ,\"quantity\": \"100\",\"grossWeight\": \"1000\" ,\"invoiceDate\":\"15-1-2000\" ,\"invoiceNumber\": \"2\" }",

        };

        for (int i = 0; i < itemData.length; i++) {
            String key = String.format("ITEM%d", i);

           Item item = genson.deserialize(itemData[i], Item.class);
            String customState = genson.serialize(item);
            stub.putStringState(key, customState);
        }
    }
    
    /**
     * Creates a new cert on the ledger.
     *
     * @param ctx the transaction context
     * @param key the key for the new cert
     * @param origin the origin of the new cert
     * @param exporter the exporter of the new cert
     * @param importer the importer of the new cert
     * @param dateAndTime the dateAndTime of the new cert
     * @param description the description of the new cert
     * @param valid the state of validity of the new cert
     * @return the created Cert
     */
    @Transaction()
    public Item createCert(final Context ctx,
    		final String key, 
    		final String exporterName,
    		final String dateAndTime,
			final String itemNumber,
			final String itemDescription,
			final String origin,
			final String packageNumber,
			final String packageType,
			final String quantity,
			final String grossWeight,
			final String invoiceDate,
			final String invoiceNumber ) {
        ChaincodeStub stub = ctx.getStub();

        String certState = stub.getStringState(key);
        if (!certState.isEmpty()) {
            String errorMessage = String.format("Cert %s already exists", key);
            System.err.println(errorMessage);
            throw new ChaincodeException(errorMessage, ItemErrors.ITEM_ALREADY_VALIDATED.toString());
        }

        Item cert = new Item(exporterName,dateAndTime,itemNumber,itemDescription,origin,packageNumber,packageType,quantity,grossWeight,invoiceDate,invoiceNumber);
        certState = genson.serialize(cert);
        stub.putStringState(key, certState);

        return cert;
    }
    /**
     * Retrieves a certification with the specified key from the ledger.
     *
     * @param ctx the transaction context
     * @param key the key
     * @return the Cert found on the ledger if there was one
     */
    @Transaction()
    public Item queryCert(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String certState = stub.getStringState(key);

        if (certState.isEmpty()) {
            String errorMessage = String.format("ITEM %s does not exist", key);
            System.err.println(errorMessage);
            throw new ChaincodeException(errorMessage, ItemErrors.CERTIFICATION_NOT_FOUND.toString());
        }

        Item cert = genson.deserialize(certState, Item.class);

        return cert;
    }
    
    /**
     * Retrieves all certs from the ledger.
     *
     * @param ctx the transaction context
     * @return array of Certs found on the ledger
     */
    @Transaction()
    public String queryAllCerts(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        final String startKey = "ITEM1";//start from 1 since 0 is the dummy data
        final String endKey = "ITEM99";
        List<ItemQueryResult> queryResults = new ArrayList<ItemQueryResult>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);

        for (KeyValue result: results) {
            Item cert = genson.deserialize(result.getStringValue(), Item.class);
            queryResults.add(new ItemQueryResult(result.getKey(), cert));
        }

        final String response = genson.serialize(queryResults);

        return response;
    }
    
    
    

    
}
