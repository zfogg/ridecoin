package com.ridecoin;

import java.io.File;
import java.io.IOException;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.AddressFormatException;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.core.Wallet;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.params.MainNetParams;
import com.google.bitcoin.store.BlockStoreException;


public class BitcoinClient {
	public WalletAppKit kit;
	
	NetworkParameters params;
	
    public BitcoinClient() throws BlockStoreException, IOException {
    	params = MainNetParams.get();
    	
    	kit = new WalletAppKit(params, new File("."), "ridecoin");
    	
    	kit.setUserAgent("RideCoin", "0.0.1-BETA");
    	
    	kit.setAutoSave(true);
    	
    	System.out.println("WALLET :: Starting! Wait for it . . .");
    	kit.startAndWait();
    	
    	System.out.println("WALLET :: Done! My wallet looks like: " + kit.wallet().toString());
    }
}
