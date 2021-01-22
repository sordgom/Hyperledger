#!/bin/bash
peer=$1
port=$2
org=$3
CHAINCODE_NAME=$4
ORG_MSP=$5
PEER0_PORT=$6
CA_PORT=$7
new_port=$(($port + 1))  
peerpw=$peer"pw"

CHANNEL_NAME="mychannel"


export CORE_PEER_TLS_ENABLED=true
export CORE_PEER_LOCALMSPID=$ORG_MSP
export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/$org.example.com/peers/peer0.$org.example.com/tls/ca.crt
export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/$org.example.com/users/Admin@$org.example.com/msp
export CORE_PEER_ADDRESS=localhost:$PEER0_PORT
export ORDERER_ADDRESS=localhost:7050
export CORE_PEER_TLS_ROOTCERT_FILE_ORG1=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_TLS_ROOTCERT_FILE_ORG2=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt


export PATH=$PATH:$PWD/../bin/

export FABRIC_CFG_PATH=$PWD/../config/
export ORDERER_CA=${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

rm docker/docker-compose-$peer.$org.yaml
rm -r organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com

export PATH=$PATH:${PWD}/../bin
export FABRIC_CA_CLIENT_HOME=${PWD}/organizations/peerOrganizations/$org.example.com/

echo "###################################"""
echo "REGISTER SECTION"
echo "###################################"""

fabric-ca-client register --caname ca-$org --id.name $peer --id.secret $peerpw --id.type peer --tls.certfiles ${PWD}/organizations/fabric-ca/$org/tls-cert.pem

mkdir -p organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com

echo "###################################"""
echo "ENROLL SECTION"
echo "###################################"""

fabric-ca-client enroll -u https://$peer:$peerpw@localhost:$CA_PORT --caname ca-$org -M ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/msp --csr.hosts $peer.$org.example.com --tls.certfiles ${PWD}/organizations/fabric-ca/$org/tls-cert.pem

cp ${PWD}/organizations/peerOrganizations/$org.example.com/msp/config.yaml ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/msp/config.yaml

fabric-ca-client enroll -u https://$peer:$peerpw@localhost:$CA_PORT --caname ca-$org -M ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls --enrollment.profile tls --csr.hosts $peer.$org.example.com --csr.hosts localhost --tls.certfiles ${PWD}/organizations/fabric-ca/$org/tls-cert.pem

cp ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls/tlscacerts/* ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls/ca.crt
cp ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls/signcerts/* ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls/server.crt
cp ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls/keystore/* ${PWD}/organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls/server.key

echo "###################################"""
echo "DOCKER SECTION"
echo "###################################"""

echo "
# Copyright IBM Corp. All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
#

version: '2'

volumes:
  $peer.$org.example.com:

networks:
  test:

services:

  $peer.$org.example.com:
    container_name: $peer.$org.example.com
    image: hyperledger/fabric-peer:2.3.0
    environment:
      #Generic peer variables
      - CORE_VM_ENDPOINT=unix:///host/var/run/docker.sock
      # the following setting starts chaincode containers on the same
      # bridge network as the peers
      # https://docs.docker.com/compose/networking/
      - CORE_VM_DOCKER_HOSTCONFIG_NETWORKMODE=${COMPOSE_PROJECT_NAME}_test
      - FABRIC_LOGGING_SPEC=INFO
      #- FABRIC_LOGGING_SPEC=DEBUG
      - CORE_PEER_TLS_ENABLED=true
      - CORE_PEER_PROFILE_ENABLED=true
      - CORE_PEER_TLS_CERT_FILE=/etc/hyperledger/fabric/tls/server.crt
      - CORE_PEER_TLS_KEY_FILE=/etc/hyperledger/fabric/tls/server.key
      - CORE_PEER_TLS_ROOTCERT_FILE=/etc/hyperledger/fabric/tls/ca.crt
      # Peer specific variabes
      - CORE_PEER_ID=$peer.$org.example.com
      - CORE_PEER_ADDRESS=$peer.$org.example.com:$port
      - CORE_PEER_LISTENADDRESS=0.0.0.0:$port
      - CORE_PEER_CHAINCODEADDRESS=$peer.$org.example.com:$new_port
      - CORE_PEER_CHAINCODELISTENADDRESS=0.0.0.0:$new_port
      - CORE_PEER_GOSSIP_BOOTSTRAP=$peer.$org.example.com:$port
      - CORE_PEER_GOSSIP_EXTERNALENDPOINT=$peer.$org.example.com:$port
      - CORE_PEER_LOCALMSPID=$ORG_MSP
    volumes:
        - /var/run/:/host/var/run/
        - ../organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/msp:/etc/hyperledger/fabric/msp
        - ../organizations/peerOrganizations/$org.example.com/peers/$peer.$org.example.com/tls:/etc/hyperledger/fabric/tls
        - $peer.$org.example.com:/var/hyperledger/production
    working_dir: /opt/gopath/src/github.com/hyperledger/fabric/peer
    command: peer node start
    ports:
      - $port:$port
    networks:
      - test
" > docker/docker-compose-$peer.$org.yaml

docker-compose -f docker/docker-compose-$peer.$org.yaml up -d


echo "#############"
echo "Packaging"
echo "#############"

CORE_PEER_ADDRESS=localhost:$port peer channel join -b channel-artifacts/mychannel.block

echo "#############"
echo "Installing"
echo "#############"
CORE_PEER_ADDRESS=localhost:$port peer lifecycle chaincode install ${CHAINCODE_NAME}.tar.gz

echo "#############"
echo "Initialise"
echo "#############"
CORE_PEER_ADDRESS=localhost:$port peer chaincode invoke -o $ORDERER_ADDRESS --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED\
    --cafile $ORDERER_CA -C ${CHANNEL_NAME} --name ${CHAINCODE_NAME}\
    --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1\
    --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2\
    -c '{"Args":["initLedger"]}'

echo "#############"
echo "Testing the new peer"
echo "#############"
echo $port
CORE_PEER_ADRRESS=localhost:$port peer chaincode invoke -o $ORDERER_ADDRESS\
    --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED\
    --cafile $ORDERER_CA -C ${CHANNEL_NAME} --name ${CHAINCODE_NAME}\
    --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1\
    --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2\
    -c '{"Args":["queryAllCerts"]}'