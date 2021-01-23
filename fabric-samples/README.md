[//]: # (SPDX-License-Identifier: CC-BY-4.0)
# Choice of technology
Using hyperledger fabric 2.3 , and building it using solely Java.

# Hyperledger Fabric Samples

You can use Fabric samples to get started working with Hyperledger Fabric, explore important Fabric features, and learn how to build applications that can interact with blockchain networks using the Fabric SDKs. To learn more about Hyperledger Fabric, visit the [Fabric documentation](https://hyperledger-fabric.readthedocs.io/en/latest).


## Test network

The [Fabric test network](test-network) in the samples repository provides a Docker Compose based test network with two
Organization peers and an ordering service node. You can use it on your local machine to run the samples listed below.
You can also use it to deploy and test your own Fabric chaincodes and applications. To get started, see
the [test network tutorial](https://hyperledger-fabric.readthedocs.io/en/latest/test_network.html).

## Steps to make

in /test-network run the commands : 
	* ./network.sh down 					# Shut down the network
	* ./network.sh up createChannel -c $channelname -ca -s couchdb  # Open the network 
	* ./installCertificationChaincode.sh   	# Run certificaiton chaincode lifecycle 
	* ./installCustomsChaincode.sh 		   	# Run customs chaincode lifecycle 
	* ./installItemChaincode.sh 			# Run ItemVerification chaincode lifecycle 
	* ./addPeer.sh $peer $port $org 		# Add a certain peer to the channel and install the chaincode on it 
											# Example: ./addPeer.sh peer2 9071 org2 Customs Org2MSP 9051 8054
WHERE:
$channelname is whatever name you want to give to your channel. It is mychannel by default
$peer $port $org is peer port and org 

## Stuff to try:
	* ./addPeer.sh isn't completed So:
		 -it won't run queries for all the chaincodes
		 -If some red flags appear , run it again
	* docker ps -a 							# Check the situation of your docker containers hosting peers
	* /chaincode/$CHANNEL_NAME and run gradle installDist
											# To update the chaincode
	* jar tf /chaincode/$CHANNEL_NAME/build/install/$CHANNEL_NAME/*.jar
											#This allows you to read classes

## Run tests
	* source original.sh
	* CHANGE THE CHAINCODE NAME next to -n , and put what function you want next to Args:

	* order from peer0,org1 ,Certification chaincode :(change the port to change the peer)

	CORE_PEER_ADDRESS=localhost:7051 peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA -C mychannel -n Certification --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1 --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2  -c '{"Args":[]}' 

	*Methods available:
		Certification: createCert(CERT3,name, address,country, name2, address2,country2,departureDate,departureTime,vesselName,portOfDischarge,itemNumber,itemDescription,origin,packageNumber,packageType,quantity,grossWeight,invoiceDate,invoiceNumber),queryCert(CERT0),queryAllCerts,validateTransaction(CERT0)
		ItemVerification: createItemValidationCert(ITEM3,exporterName,dateAndTime,itemNumber,itemDescription,origin,packageNumber,packageType,quantity,grossWeight,invoiceDate,invoiceNumber),queryAllCerts,queryCert(ITEM0)
		Customs: createCustomCert(CUSTOM3,nameOfEmployee,identification , organization ,timeOfApproval ,portOfApproval , 
                itemNumber ,validity ),queryCustomCert,queryAllCerts
	 

## License <a name="license"></a>

Hyperledger Project source code files are made available under the Apache
License, Version 2.0 (Apache-2.0), located in the [LICENSE](LICENSE) file.
Hyperledger Project documentation files are made available under the Creative
Commons Attribution 4.0 International License (CC-BY-4.0), available at http://creativecommons.org/licenses/by/4.0/.