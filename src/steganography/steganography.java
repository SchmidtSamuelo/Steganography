package steganography;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Schmidt
 * 
 */

public class steganography {
    static LFSR rng = new LFSR();
    
    public static void saveImage(BufferedImage img, String imgName) {
            try {
            	ImageIO.write(img, "png", new File(imgName));
            } catch (IOException e) {
            	System.out.println(e);
            }
    }

    public static BufferedImage encrypt(String pathGuest, String pathHost) {
            BufferedImage encryptedImg = null;
            BufferedImage imgHost = null;
            BufferedImage imgGuest = null;

            //pathGuest = "C:/Users/Sam/Documents/GitHub/Guest.png"; //Hard code option
            //pathHost = "C:/Users/Sam/Documents/GitHub/Host.png";  //Hard code option

            //Checks if the file is an image, throws an error if the filetype isn't
            try {
                    imgHost = ImageIO.read(new File(pathHost));
                    imgGuest = ImageIO.read(new File(pathGuest));

                    System.out.println("Duplicating host");
                    encryptedImg = imgHost;
                    System.out.println("Host duplicated");

            }catch(IOException e){
                    System.out.println("Improper file type");
            }
            
            int widthGuest = imgGuest.getWidth(null);
            int widthHost = imgHost.getWidth(null);
            int heightGuest = imgGuest.getHeight(null);
            int heightHost = imgHost.getHeight(null);

            int sizeGuest = widthGuest * heightGuest;
            int sizeHost = widthHost * heightHost;
            int hostx, hosty;
            rng.ResetLFSR();

            //Checks that the size of the host is big enough to map guest to it
            if((sizeHost) >= (8 * sizeGuest)) {

            	System.out.println("Compatable images detected");

                int val;
                //nested loop iterates through each pixel of the image from left to right
                //then top to bottom.
                for(int i = 0; i < heightGuest; i++) {
                    for(int j = 0; j < widthGuest; j++) {
                    	
                    	//gets the next int using the LFSR and calculates the x and y values that the operation will be performed on.
                    	val = rng.getNextInt(heightHost, widthHost);
                        hostx = val % widthHost;
                        hosty = val / widthHost;
                        
                        //saves the pixel RGB value at location (j, i)
                        int guestPix = imgGuest.getRGB(j, i);
                        int hostPix = imgHost.getRGB(hostx, hosty);

                        //converts the compressed RGB value to readable values from 0-255
                        //int a = (pix>>24) & 0xff; //Alpha channel, may implement in the future
                        int guestR = (guestPix>>16) & 0xff;
                        int guestG = (guestPix>>8) & 0xff;
                        int guestB = (guestPix) & 0xff;

                        int hostR = (hostPix>>16) & 0xff;
                        int hostG = (hostPix>>8) & 0xff;
                        int hostB = (hostPix) & 0xff;
                        
                        /* 
                         * performs bit rotation in order to save all of the bits one at a time into the
                         * least significant bit of the host image. Variables that start with g are for guest,
                         * and variables that start with h are for host.
                         */
                        for(int offset = 0; offset < 8; offset++){
                            int gR = (guestR >> offset) & 0x01;
                            int hR = (hostR & 0xFE) | gR;
                            int gG = (guestG >> offset) & 0x01;
                            int hG = (hostG & 0xFE) | gG;
                            int gB = (guestB >> offset) & 0x01;
                            int hB = (hostB & 0xFE) | gB;
                            encryptedImg.setRGB(hostx, hosty, new Color(hR, hG, hB).getRGB());
                        }
                    }
                }


            }else if((sizeHost) < (8 * sizeGuest)) {
                    System.out.println("The host file is too small to accomodate the guest file");
            }
            saveImage(encryptedImg, "encrypted_image.png");
            return encryptedImg;
    }
    public static BufferedImage decrypt(int width, int height, String pathEncrypted) { //width and height are the dimensions of the original guest image
            BufferedImage encryptedImg = null;
            BufferedImage decryptedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //creates a shell for the decrypted image to be saved to.

            //Checks if the file is an image, throws an error if the filetype isn't
            try {
                    encryptedImg = ImageIO.read(new File(pathEncrypted));			
            }catch(IOException e){
                    System.out.println("Improper file type");
            }
               
            int val;
            //int sizeEncrypted = widthEncrypted * heightEncrypted; //not needed, copy/pasted from encrypt.
            
            int decryptedR, decryptedG, decryptedB;
            int encryptedx, encryptedy;
            rng.ResetLFSR();
            for(int i = 0; i < decryptedImg.getHeight(); i++) {
                for(int j = 0; j < decryptedImg.getWidth(); j++) {

                	//gets the next int using the LFSR and calculates the x and y values that the operation will be performed on.
                    val = rng.getNextInt(encryptedImg.getHeight(), encryptedImg.getWidth());
                    encryptedx = val % encryptedImg.getWidth();
                    encryptedy = val / encryptedImg.getWidth();

                    /*
                     * Gets the rgb values of the encripted image's pixel using the LFSR
                     * parses the encrypted value into 255 values in encryptedR, encryptedG, and encryptedB
                     * and then sets the decrypted image shell's value to that RGB value
                     */
                    int encryptedPix = encryptedImg.getRGB(encryptedx, encryptedy);
                    System.out.println(encryptedPix);
                    int encryptedR = (encryptedPix>>16) & 0xff;
                    int encryptedG = (encryptedPix>>8) & 0xff;
                    int encryptedB = (encryptedPix) & 0xff;
                    
                    /* 
                     * resets the values of the decrypted R, G, and B values in preparation to
                     * perform the operation on the next pixel.
                     */
                    decryptedR = 0; decryptedG = 0; decryptedB = 0;
                    
                    for(int offset = 0; offset < 8; offset++){
                        
                    	/*
                    	 * rotates the encrypted R, G, and B values from the encrypted image and
                    	 * selects the least significant bit of the R, G, and B values where the 
                    	 * encrypted image was saved.
                    	 */
                        int eR = (encryptedR & 0x01) << offset;
                        int eG = (encryptedG & 0x01) << offset;
                        int eB = (encryptedB & 0x01) << offset;
                        
                        /* 
                         * takes the current decrypted R, G, and B values and 'or' operates them
                         * with one bit of the encrypted value at a time.
                         */
                        decryptedR |= eR;
                        decryptedG |= eG;
                        decryptedB |= eB;
                        
                        //sets the RGB values to the decrypted image shell.
                        decryptedImg.setRGB(j, i, new Color(decryptedR, decryptedG, decryptedB).getRGB());
                    }
                }
            }
        saveImage(decryptedImg, "decrypted_image.png");
        return decryptedImg;
    }
}

