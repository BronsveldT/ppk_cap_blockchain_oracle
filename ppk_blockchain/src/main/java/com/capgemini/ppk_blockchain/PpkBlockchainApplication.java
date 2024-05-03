package com.capgemini.ppk_blockchain;

import com.capgemini.ppk_blockchain.blockchain.credentials.EnrollAdmin;
import com.capgemini.ppk_blockchain.blockchain.credentials.RegisterUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PpkBlockchainApplication {

	public static void main(String[] args) {

		SpringApplication.run(PpkBlockchainApplication.class, args);
//		EnrollAdmin.enroll();
//		RegisterUser.enroll();
	}

}
