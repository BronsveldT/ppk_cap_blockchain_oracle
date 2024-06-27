package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.interfaceimpl.BlockchainProcessServiceImpl;
import com.capgemini.ppk_blockchain.web.db.models.ReverseRoads;
import com.capgemini.ppk_blockchain.web.db.models.Wegen;
import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoProcessService;
import com.capgemini.ppk_blockchain.web.repositories.ReverseRoadRepository;
import com.capgemini.ppk_blockchain.web.repositories.WegenRepository;
import com.capgemini.ppk_blockchain.web.restmodels.*;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.HashMap;

