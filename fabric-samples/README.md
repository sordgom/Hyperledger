[//]: # (SPDX-License-Identifier: CC-BY-4.0)

# Hyperledger Fabric Samples

You can use Fabric samples to get started working with Hyperledger Fabric, explore important Fabric features, and learn how to build applications that can interact with blockchain networks using the Fabric SDKs. To learn more about Hyperledger Fabric, visit the [Fabric documentation](https://hyperledger-fabric.readthedocs.io/en/latest).


## Test network

The [Fabric test network](test-network) in the samples repository provides a Docker Compose based test network with two
Organization peers and an ordering service node. You can use it on your local machine to run the samples listed below.
You can also use it to deploy and test your own Fabric chaincodes and applications. To get started, see
the [test network tutorial](https://hyperledger-fabric.readthedocs.io/en/latest/test_network.html).

## Steps to make

in /test-network run the commands : 
	* ./network.sh down 
	* ./network.sh up createChannel -c $channelname -ca -s couchdb 
	* ./installCertificationChaincode.sh   	# Run certificaiton chaincode lifecycle 
	* ./installCustomsChaincode.sh 		   	# Run customs chaincode lifecycle 
	* ./installItemChaincode.sh 			# Run ItemVerification chaincode lifecycle 
	* ./addPeer.sh $peer $port $org 		# Add a certain peer to the channel and install the chaincode on it 
WHERE:
$channelname is whatever name you want to give to your channel. It is mychannel by default
$peer $port $org is peer port and org 

## License <a name="license"></a>

Hyperledger Project source code files are made available under the Apache
License, Version 2.0 (Apache-2.0), located in the [LICENSE](LICENSE) file.
Hyperledger Project documentation files are made available under the Creative
Commons Attribution 4.0 International License (CC-BY-4.0), available at http://creativecommons.org/licenses/by/4.0/.

## Run tests
	* source original.sh
	* CHANGE THE CHAINCODE NAME next to -n , and put what function you want next to Args:
	peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA -C mychannel -n Certification --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1 --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2  -c '{"Args":[]}' 