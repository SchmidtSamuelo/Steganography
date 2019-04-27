import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class stegMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Image imgHost = null;
		Image imgGuest = null;
		
		File pathGuest = new File("C:/Users/Sam/Documents/GitHub/Guest.png");
		File pathHost = new File("C:/Users/Sam/Documents/GitHub/Host.png");
		
		try {
			imgHost = ImageIO.read(pathHost);
			imgGuest = ImageIO.read(pathGuest);
		}catch(IOException e){
			System.out.println("Improper file type");
		}
		int widthGuest = imgGuest.getWidth(null);
		int widthHost = imgHost.getWidth(null);
		int heightGuest = imgGuest.getHeight(null);
		int heightHost = imgHost.getHeight(null);
		int sizeGuest = widthGuest * heightGuest;
		int sizeHost = widthHost * heightHost;
		
		//3D array that will hold the RGB values of the guest image for each pixel.
		//first array is the i location, the second array is the j location, and the
		//third array is the RGB values in binary.
		String[][][] rgbGuestArr = new String[widthGuest][heightGuest][2];
		
		//Checks that the size of the host is big enough to map guest to it
		if((sizeHost) >= (8 * sizeGuest)) {
			
			//nested loop iterates through each pixel of the image from left to right
			//then top to bottom.
			for(int i = 0; i < heightGuest; i++) {
				for(int j = 0; j < widthGuest; j++) {
					
					//saves the pixel RGB value at location (j, i)
					int pix = ((BufferedImage) imgGuest).getRGB(j, i);
					//converts the compressed ARGB value to readable values from 0-256
					//int a = (pix>>24) & 0xff;
					int r = (pix>>16) & 0xff;
					int g = (pix>>8) & 0xff;
					int b = (pix>>0) & 0xff;
					
					String binR = Integer.toBinaryString(r);
					String binG = Integer.toBinaryString(g);
					String binB = Integer.toBinaryString(b);
					
					/*rgbGuestArr[j][i][0] = binR;
					rgbGuestArr[j][i][1] = binG;
					rgbGuestArr[j][i][2] = binB;*/
					
					System.out.printf("%dx%d -- R:%s, G:%s, B:%s\n", i, j, binR, binG, binB);
				}
			}

			
		}else if((sizeHost) < (8 * sizeGuest)) {
			System.out.println("The host file is too small to accomodate the guest file");
		}
		
		System.exit(0);
	}
		
}
