package Certification;
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
    private enum CertErrors {
        CERTIFICATION_NOT_FOUND,
        CERTIFICATION_ALREADY_EXISTS,
        CERTIFICATION_NOT_VALID
    }
    
    /**
     * Creates an initial cert on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        String[] certData = {
        		"{ \"name\": \"Company1\", \"address\": \"address1\", \"country\": \"country1\",\"name2\": \"Company2\", \"address2\": \"address2\", \"country2\": \"country2\" ,\"departureDate\": \"15-9-2020\", \"departureTime\": \"00:00\", \"vesselName\": \"Vessel1\" , \"portOfDischarge\": \"port1\" ,\"itemNumber\": \"item1\" , \"itemDescription\": \"descr1\" ,\"origin\": \"country3\" , \"packageNumber\": \"1\" ,\"packageType\": \"type1\" ,\"quantity\": \"100\",\"grossWeight\": \"1000\" ,\"invoiceDate\":\"15-1-2000\" ,\"invoiceNumber\": \"2\" }",
        };

        for (int i = 0; i < certData.length; i++) {
            String key = String.format("CERT%d", i);

            Certification cert = genson.deserialize(certData[i], Certification.class);
            String certState = genson.serialize(cert);
            stub.putStringState(key, certState);
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
    public Certification createCert(final Context ctx,
    		final String key, 
    		final String name, 
    		final String address,
    		final String country, 
			final String name2, 
			final String address2,
			final String country2,
			final String departureDate,
			final String departureTime,
			final String vesselName,
			final String portOfDischarge,
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
            throw new ChaincodeException(errorMessage, CertErrors.CERTIFICATION_ALREADY_EXISTS.toString());
        }

        Certification cert = new Certification(name, address,country, name2, address2,country2,departureDate,departureTime,vesselName,portOfDischarge,itemNumber,itemDescription,origin,packageNumber,packageType,quantity,grossWeight,invoiceDate,invoiceNumber);
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
    public Certification queryCert(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String certState = stub.getStringState(key);

        if (certState.isEmpty()) {
            String errorMessage = String.format("Cert %s does not exist", key);
            System.err.println(errorMessage);
            throw new ChaincodeException(errorMessage, CertErrors.CERTIFICATION_NOT_FOUND.toString());
        }

        Certification cert = genson.deserialize(certState, Certification.class);

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

        final String startKey = "CERT1";//start from 1 since 0 is the dummy data
        final String endKey = "CERT99";
        List<CertQueryResult> queryResults = new ArrayList<CertQueryResult>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);

        for (KeyValue result: results) {
            Certification cert = genson.deserialize(result.getStringValue(), Certification.class);
            queryResults.add(new CertQueryResult(result.getKey(), cert));
        }

        final String response = genson.serialize(queryResults);

        return response;
    }
    /**
     * Importers may validate recieving the products using this function
     * @param ctx
     * @param key
     * @return
     */
    @Transaction()
    public String validateTransaction(final Context ctx,final String key) {
    	ChaincodeStub stub = ctx.getStub();
        String certState = stub.getStringState(key);
        
        if (certState.isEmpty()) {
            String errorMessage = String.format("Cert %s Doesn't exist", key);
            System.err.println(errorMessage);
            throw new ChaincodeException(errorMessage, CertErrors.CERTIFICATION_NOT_FOUND.toString());
        }
        
        Certification cert = genson.deserialize(certState, Certification.class);

    	return key + " is properly recieved  by "+ cert.getName2();
    }
    

    
}

