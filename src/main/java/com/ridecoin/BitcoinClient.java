package com.ridecoin;

import java.io.File;
import java.io.IOException;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.params.MainNetParams;
import com.google.bitcoin.store.BlockStoreException;


public class BitcoinClient {
	public WalletAppKit kit;
	
    public BitcoinClient() throws BlockStoreException, IOException {
    	NetworkParameters params = MainNetParams.get();
    	
    	kit = new WalletAppKit(params, new File("."), "ridecoin");
    	
    	kit.setAutoSave(true);
    	
    	System.out.println("WALLET :: Starting! Wait for it . . .");
    	kit.startAndWait();
    	
    	System.out.println("WALLET :: Done! My address is " + kit.wallet().getChangeAddress().toString());
    }
}
