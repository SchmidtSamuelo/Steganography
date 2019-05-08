/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;
/**
 *
 * @author stRa222Vi2277
 */
public class Main {
    public static void main(String[] args) {
        steganography stg = new steganography();
        
        LFSR rng = new LFSR();
		for (int i = 1; i <= 16; i++) {
			int next = rng.nextInt();
			System.out.println(next);
    }
    
}
}
