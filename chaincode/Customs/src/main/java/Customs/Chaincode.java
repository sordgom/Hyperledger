package Customs;
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
    private enum CustomErrors {
        CUSTOM_NOT_FOUND,
        CUSTOM_ALREADY_EXISTS,
        CUSTOM_NOT_VALID
    }
    
    /**
     * Creates an initial cert on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        String[] customData = {
                "{ \"nameOfEmployee\": \"employee1\", \"identification\": \"id1\", \"organization\": \"org1\", \"timeOfApproval\": \"time1\", \"portOfApproval\": \"port1\", \"itemNumber\": \"item1\" , \"validity\": \"not valid\"}",

        };

        for (int i = 0; i < customData.length; i++) {
            String key = String.format("CUSTOM%d", i);

            Custom custom = genson.deserialize(customData[i], Custom.class);
            String customState = genson.serialize(custom);
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
    public Custom createCustomCert(final Context ctx,
            final String key, 
            final String nameOfEmployee,
            final String identification ,
            final String organization ,
            final String timeOfApproval ,
            final String portOfApproval , 
            final String itemNumber , 
            final String validity ) {
        ChaincodeStub stub = ctx.getStub();

        String customState = stub.getStringState(key);
        if (!customState.isEmpty()) {
            String errorMessage = String.format("Custom %s already exists", key);
            System.err.println(errorMessage);
            throw new ChaincodeException(errorMessage, CustomErrors.CUSTOM_ALREADY_EXISTS.toString());
        }

        Custom custom = new Custom(nameOfEmployee,identification , organization ,timeOfApproval ,portOfApproval , 
                itemNumber ,validity );
        customState = genson.serialize(custom);
        stub.putStringState(key, customState);

        return custom;
    }
    /**
     * Retrieves a certification with the specified key from the ledger.
     *
     * @param ctx the transaction context
     * @param key the key
     * @return the Cert found on the ledger if there was one
     */
    @Transaction()
    public Custom queryCustomCert(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String customState = stub.getStringState(key);

        if (customState.isEmpty()) {
            String errorMessage = String.format("Custom %s does not exist", key);
            System.err.println(errorMessage);
            throw new ChaincodeException(errorMessage, CustomErrors.CUSTOM_NOT_FOUND.toString());
        }
        Custom custom = genson.deserialize(customState, Custom.class);
        return custom;
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

        final String startKey = "CUSTOM0";//start from 1 since 0 is the dummy data
        final String endKey = "CUSTOM99";
        List<CustomQueryResult> queryResults = new ArrayList<CustomQueryResult>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);

        for (KeyValue result: results) {
            Custom custom = genson.deserialize(result.getStringValue(), Custom.class);

            queryResults.add(new CustomQueryResult(result.getKey(), custom));
        }

        final String response = genson.serialize(queryResults);

        return response;
    }

    
}