package ajb.ships;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import ajb.area.AreaUtils;
import ajb.colours.ColourUtils;
import ajb.images.ImageUtils;
import ajb.random.RandomInt;

public class ShipUtils {

	public static void main(String[] args) {

		ShipUtils.generateMultiple(10);

	}

	public static void generateMultiple(final int amount) {

		for (int i = 0; i < amount; i++) {

			Area result = generate();

			BufferedImage img = ImageUtils.create(result.getBounds2D().getMaxX(), result.getBounds2D().getMaxY());

			Graphics2D gr = (Graphics2D) img.getGraphics();

			gr.setColor(ColourUtils.background);
			gr.fillRect(0, 0, img.getWidth(), img.getHeight());

			gr.setColor(ColourUtils.gray);

			gr.fill(result);

			gr.dispose();

			ImageUtils.save(img, "png", "Vessel" + i);

		}

	}

	public static Area generate() {

		Area result = new Area(new Rectangle2D.Double(0, 0, 10, 20));

		int count = RandomInt.anyRandomIntRange(0, 4);

		for (int i = 0; i < count; i++) {

			Area segment = createSegment();
			segment = AreaUtils.translateToPoint(segment, new Point2D.Double(0, result.getBounds2D().getMaxY()));
			result.add(segment);

		}

		// Mess it up a bit

		count = RandomInt.anyRandomIntRange(50, 100);

		for (int i = 0; i < count; i++) {

			AreaUtils.addRandomBlock(result);

		}

		result.add(AreaUtils.mirrorAlongX(0, result));
		result = AreaUtils.getOutline(result);
		result = AreaUtils.translateToTopLeft(result);

		return result;

	}

	public static Area createSegment() {

		Area result = new Area(
				new Rectangle2D.Double(0, 0, RandomInt.anyRandomIntRange(10, 20), RandomInt.anyRandomIntRange(20, 50)));

		return result;

	}
}
