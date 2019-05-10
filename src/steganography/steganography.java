/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Schmidt
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

            
//			pathGuest = "C:/Users/Sam/Documents/GitHub/Guest.png"; //Hard code option
//			pathHost = "C:/Users/Sam/Documents/GitHub/Host.png";  //Hard code option

            //Checks if the file is an image, throws an error if the filetype isn't
            try {
                    imgHost = ImageIO.read(new File(pathHost));
                    imgGuest = ImageIO.read(new File(pathGuest));

                    System.out.println("duplicating host");
                    encryptedImg = imgHost;
                    System.out.println("host duplicated");

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
                                val = rng.getNextInt(heightHost, widthHost);

                                hostx = val % widthHost;
                                hosty = val / widthHost;
                                //saves the pixel RGB value at location (j, i)
                                int guestPix = imgGuest.getRGB(j, i);
                                int hostPix = imgHost.getRGB(hostx, hosty);

                                //converts the compressed RGB value to readable values from 0-256
                                //int a = (pix>>24) & 0xff; //This is the alpha channel which isn't needed
                                int guestR = (guestPix>>16) & 0xff;
                                int guestG = (guestPix>>8) & 0xff;
                                int guestB = (guestPix) & 0xff;

                                int hostR = (hostPix>>16) & 0xff;
                                int hostG = (hostPix>>8) & 0xff;
                                int hostB = (hostPix) & 0xff;
                                for(int offset = 0; offset < 8; offset++){
                                    int gR = (guestR >> offset) & 0x01;
                                    int hR = (hostR & 0xFE) | gR;
                                    int gG = (guestG >> offset) & 0x01;
                                    int hG = (hostG & 0xFE) | gG;
                                    int gB = (guestB >> offset) & 0x01;
                                    int hB = (hostB & 0xFE) | gB;
                                    encryptedImg.setRGB(hostx, hosty, new Color(hR, hG, hB).getRGB());
                                }
                                //System.out.printf("%dx%d -- R:%s, G:%s, B:%s\n", i, j, guestR, guestG, guestB);
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

            //remove the below line when implemented
//		pathEncrypted = "C:\\Users\\Randy J Villafuerte\\Pictures\\blitzrip.PNG";

            //Checks if the file is an image, throws an error if the filetype isn't
            try {
                    encryptedImg = ImageIO.read(new File(pathEncrypted));			
            }catch(IOException e){
                    System.out.println("Improper file type");
            }
               
            //These lines don't work, they are implimentable though.
            //int widthEncrypted = encryptedImg.getWidth(null);
            //int heightEncrypted = encryptedImg.getHeight(null);
            int val;
            //int sizeEncrypted = widthEncrypted * heightEncrypted; //not needed, copy/pasted from encrypt.
            
            int enR, enG, enB;
            int encryptedx, encryptedy;
            rng.ResetLFSR();
            for(int i = 0; i < decryptedImg.getHeight(); i++) {
                    for(int j = 0; j < decryptedImg.getWidth(); j++) {

                            val = rng.getNextInt(encryptedImg.getHeight(), encryptedImg.getWidth());
                            encryptedx = val % encryptedImg.getWidth();
                            encryptedy = val / encryptedImg.getWidth();
                            //System.out.printf("x = %d, y = %d", encryptedx, encryptedy);

                            /*Gets the rgb values of the encripted image's pixel using the LFSR
                                parses the encrypted value into 255 values in encryptedR, encryptedG, and encryptedB
                                and then sets the decrypted image shell's value to that RGB value
                                
                                This needs to be modifyed in order to only take the least significant
                                bit and modify it into the decrypted image. Will investigate further in
                                the future.
                                Currently doesn't work as intended.
                            */
                            int encryptedPix = encryptedImg.getRGB(encryptedx, encryptedy);
                            System.out.println(encryptedPix);
                            int encryptedR = (encryptedPix>>16) & 0xff;
                            int encryptedG = (encryptedPix>>8) & 0xff;
                            int encryptedB = (encryptedPix) & 0xff;
                            enR = 0;
                            enG = 0;
                            enB = 0;
                            for(int offset = 0; offset < 8; offset++){
                                
                                
                                int eR = (encryptedR & 0x01) << offset;
                                int eG = (encryptedG & 0x01) << offset;
                                int eB = (encryptedB & 0x01) << offset;
                                System.out.printf("eRLSB %d, eGLSB %d, eBLSB %d", eR, eG, eB);
                                enR |= eR;
                                enG |= eG;
                                enB |= eB;
                                
                                decryptedImg.setRGB(j, i, new Color(enR, enG, enB).getRGB());
                                }
                    }
            }

            saveImage(decryptedImg, "decrypted_image.png");
            return decryptedImg;
    }
}

