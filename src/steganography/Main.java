/*
 * NOTE: THIS PROGRAM WILL NOT WORK WITH jpg or jpeg FILES DUE TO THEIR INHERENT
 * COMPRESSION, ANY BITS CHANGED WILL BE OVERWRITTEN BY THE jpg or jpeg's
 * LOSSY COMPRESSION ALGORYTHM
 */
package steganography;

public class Main {

    public static void main(String[] args) {
    	
        /*
         * Encrypt takes the following arguments (guest_image_path, host_image_path)
         * Decrypt takes the following arguments (original_guest_image_width, original_guest_image_height, encrypted_image_path)
         */
        steganography.encrypt("", "");
        steganography.decrypt(10, 10, "");
    }
    
}
