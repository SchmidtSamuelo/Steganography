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
		
		int sizeGuest = imgGuest.getWidth(null) * imgGuest.getHeight(null);
		int sizeHost = imgHost.getWidth(null) * imgHost.getHeight(null);
		
		if((sizeHost) >= (8 * sizeGuest)) {
			int pix = ((BufferedImage) imgGuest).getRGB(0, 0);
			int a = (pix>>24) & 0xff;
			int r = (pix>>16) & 0xff;
			int g = (pix>>8) & 0xff;
			int b = (pix>>0) & 0xff;
			System.out.println(r + " " + g + " " + b);
			//BufferedImage image = (BufferedImage) imgGuest;
			//ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			//ImageIO.write(image, "png", outputStream);
			//byte[] guestArray = outputStream.toByteArray();
			//System.out.println("array " + guestArray);
			//System.out.println(image);

			
		}else if((sizeHost) < (8 * sizeGuest)) {
			System.out.println("The host file is too small to accomodate the guest file");
		}
		
		System.exit(0);
	}
		
}
