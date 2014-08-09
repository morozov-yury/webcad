package diploma.webcad.core.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	public static byte[] resize(byte[] srcData, int width, int height, String imgType) throws IOException {
		BufferedImage srcImg = ImageIO.read(new ByteArrayInputStream(srcData));
		int srcWidth = srcImg.getWidth();
		int srcHeight = srcImg.getHeight();
		float wCoef = (float) width / srcWidth;
		float hCoef = (float) height / srcHeight;
		float coef = wCoef < hCoef ? wCoef : hCoef;
		int dstWidth = (int) (srcWidth * coef);
		int dstHeight = (int) (srcHeight * coef);
		BufferedImage dstImg = new BufferedImage(dstWidth, dstHeight,
				srcImg.getType());
		Graphics2D graphics2D = dstImg.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(srcImg, 0, 0, dstWidth, dstHeight, null);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(dstImg, imgType, out);
		return out.toByteArray();
	}
	
	public static Dimension scale(int srcHeight, int srcWidth, int dstHeight, int dstWidth) {
		float wCoef = (float)dstWidth / srcWidth;
		float hCoef = (float)dstHeight / srcHeight;
		float coef = wCoef < hCoef ? wCoef : hCoef;
		return new Dimension((int) (srcWidth * coef), (int)(srcHeight * coef));
	}
}
