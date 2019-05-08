package steganography;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class steganography {
	
	public static void main(String[] args) {
		try {
			
			//add the paths to the methods in quotation marks in the following order
			//("Path of the guest image (smaller)", "Path of the host image (bigger image)")
			encrypt("", "");
			
			//(Original guest image width, original guest image height, "Encrypted image path")
			decrypt(10, 10, ""); //for test purposes, the guest image will always be 10x10.
			
		} catch (IOException e) {
			// TODO Auto-generated catch block catches any errors and displays them in console.
			e.printStackTrace();
		}		
	}
	
	public static void lfsr() {
		//Insert LFSR here, might need a new class.
	}
	
	public static void saveImage(BufferedImage img, String imgName) {
		try {
			ImageIO.write(img, "png", new File(imgName));
		} catch (IOException e) {
        	System.out.println(e);
		}
	}

	public static BufferedImage encrypt(String pathGuest, String pathHost) throws IOException {
		BufferedImage encryptedImg = null;
		BufferedImage imgHost = null;
		BufferedImage imgGuest = null;
		
		//Comment out the below 2 lines when implemented
		pathGuest = "C:/Users/Sam/Documents/GitHub/Guest.png";
		pathHost = "C:/Users/Sam/Documents/GitHub/Host.png";
		
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
		
		
		//Checks that the size of the host is big enough to map guest to it
		if((sizeHost) >= (8 * sizeGuest)) {
			System.out.println("Compatable images detected");
			//nested loop iterates through each pixel of the image from left to right
			//then top to bottom.
			for(int i = 0; i < heightGuest; i++) {
				for(int j = 0; j < widthGuest; j++) {
					
					//placeholder for host values while LFSR is not present.
					hostx = j;
					hosty = i;
					//saves the pixel RGB value at location (j, i)
					int guestPix = imgGuest.getRGB(j, i);
					int hostPix = imgHost.getRGB(hostx, hosty);
					
					//converts the compressed RGB value to readable values from 0-256
					//int a = (pix>>24) & 0xff; //This is the alpha channel which isn't needed
					int guestR = (guestPix>>16) & 0xff;
					int guestG = (guestPix>>8) & 0xff;
					int guestB = (guestPix>>0) & 0xff;
					
					int hostR = (hostPix>>16) & 0xff;
					int hostG = (hostPix>>8) & 0xff;
					int hostB = (hostPix>>0) & 0xff;

					encryptedImg.setRGB(hostx, hosty, new Color(hostR, hostG, hostB).getRGB());
					
					System.out.printf("%dx%d -- R:%s, G:%s, B:%s\n", i, j, guestR, guestG, guestB);
				}
			}

			
		}else if((sizeHost) < (8 * sizeGuest)) {
			System.out.println("The host file is too small to accomodate the guest file");
		}
		saveImage(encryptedImg, "encrypted_image.png");
		return encryptedImg;
	}
	public static BufferedImage decrypt(int width, int height, String pathEncrypted) { //width and height are the dimensions of the original guest image
		BufferedImage imgEncrypted = null;
		BufferedImage decryptedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//remove the below line when implemented
		pathEncrypted = "C:/Users/Sam/Documents/GitHub/encrypted_image.png";
		
		//Checks if the file is an image, throws an error if the filetype isn't
		try {
			imgEncrypted = ImageIO.read(new File(pathEncrypted));			
		}catch(IOException e){
			System.out.println("Improper file type");
		}
		
		int widthEncrypted = imgEncrypted.getWidth(null);
		int heightEncrypted = imgEncrypted.getHeight(null);
		
		//int sizeEncrypted = widthEncrypted * heightEncrypted; //not needed, copy/pasted from encrypt.
		
		int encryptedx, encryptedy;
		for(int i = 0; i < heightEncrypted; i++) {
			for(int j = 0; j < widthEncrypted; j++) {
				
				//placeholder for encrypted values while LFSR is not present.
				encryptedx = j;
				encryptedy = i;
				
				int encryptedPix = imgEncrypted.getRGB(encryptedx, encryptedy);
				
				int encryptedR = (encryptedPix>>16) & 0xff;
				int encryptedG = (encryptedPix>>8) & 0xff;
				int encryptedB = (encryptedPix>>0) & 0xff;
				
				decryptedImg.setRGB(encryptedx, encryptedy, new Color(encryptedR, encryptedG, encryptedB).getRGB());
			}
		}
		
		saveImage(decryptedImg, "decrypted_image.png");
		return decryptedImg;
	}
}
