import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class stegMain {
	
	public static void main(String[] args) {
		try {
			encrypt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public static void saveImage(BufferedImage img, String imgName) {
		try {
			ImageIO.write(img, "png", new File(imgName));
		} catch (IOException e) {
        	System.out.println(e);
		}
	}

	public static BufferedImage encrypt(/*String pathGuest, String pathHost*/) throws IOException {
		BufferedImage encryptedImg = null;
		BufferedImage imgHost = null;
		BufferedImage imgGuest = null;
		
		String pathGuest = "C:/Users/Sam/Documents/GitHub/Guest.png";
		String pathHost = "C:/Users/Sam/Documents/GitHub/Host.png";
		
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
}
