package com.ridecoin;

import com.google.bitcoin.core.*;
import com.google.bitcoin.store.BlockStoreException;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import java.io.IOException;
import java.math.BigInteger;

public class Main {	
	
	static final String QUEUE = "1G1ngSTGPfKG7HrNPPfUie4Lofv9UPajuQ";
	
    public static void main(String[] args ) {
    	BitcoinClient client;
		try {
			client = new BitcoinClient();
			
	    	try {
	    		// Listen to the RideCoin queue address for transactions.
	    		Address addr = new Address(client.params, QUEUE);
				client.kit.wallet().addWatchedAddress(addr);
			} catch (AddressFormatException e) { e.printStackTrace(); }
	    	
	        // We want to know when we receive money.
	        client.kit.wallet().addEventListener(new AbstractWalletEventListener() {
	            @Override
	            public void onCoinsReceived(Wallet w, Transaction tx, BigInteger prevBalance, BigInteger newBalance) {
	                // Runs in the dedicated "user thread" (see bitcoinj docs for more info on this).
	            	
	                // The transaction "tx" can either be pending, or included into a block (we didn't see the broadcast).
	                BigInteger value = tx.getValueSentToMe(w);
	                System.out.println("Received tx for " + value.toString() + ": " + tx);
	                System.out.println("Transaction will be forwarded after it confirms.");
	                
	                System.out.println(tx.getOutputs().toString());
	                
	                // Wait until it's made it into the block chain (may run immediately if it's already there).
	                Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<Transaction>() {
						public void onFailure(Throwable arg0) {
							System.out.println("Transaction failed; something went horribly wrong :(");
						}
						public void onSuccess(Transaction arg0) {
							System.out.println("Transaction confirmed!");
						}
	                });
	            }
	        });
	        
		} catch (BlockStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
