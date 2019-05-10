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
         * the encrypted image is always saved as "encrypted_image" in the same directory as the program, so it can be chosen
         * with just "encrypted_image.png"
         */
        steganography.encrypt("Guest.png", "Host.png");
        steganography.decrypt(10, 10, "encrypted_image.png");
    }
    
}
