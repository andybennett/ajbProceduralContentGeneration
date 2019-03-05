package ajb.ships;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import ajb.area.AreaUtils;
import ajb.area.GeometryUtils;
import ajb.images.ImageUtils;
import ajb.random.RandomInt;

public class ShipUtils {

	public static void main(String[] args) {

		ShipUtils.generateMultiple(10);

	}

	public static void generateMultiple(final int amount) {

		for (int i = 0; i < amount; i++) {

			Area area = generate();

			for (int x = 0; x < area.getBounds2D().getHeight() * 2; x++) {

				GeometryUtils.subtractRandomLine(area);

			}
			
			for (int x = 0; x < area.getBounds2D().getHeight() / 10; x++) {

				GeometryUtils.addRandomBlock(area);

			}
			
			for (int x = 0; x < area.getBounds2D().getHeight(); x++) {

				GeometryUtils.subtractRandomLine(area);

			}			

			area.add(AreaUtils.mirrorAlongX(0, area));
			area = AreaUtils.translateToTopLeft(area);

			ImageUtils.save(ImageUtils.drawArea(area), "png", "Ship" + i);

		}

	}

	public static Area generate() {

		// Starting block.

		Area result = new Area(new Rectangle2D.Double(0, 0, 10, 20));

		// Add X additional segments to the starting block.

		int count = RandomInt.anyRandomIntRange(0, 4);

		for (int i = 0; i < count; i++) {

			Area segment = createSegment();
			segment = AreaUtils.translateToPoint(segment, new Point2D.Double(0, result.getBounds2D().getMaxY()));
			result.add(segment);

		}

		// Mess it up a bit.

		count = RandomInt.anyRandomIntRange(50, 500);

		for (int i = 0; i < count; i++) {

			GeometryUtils.addRandomBlock(result);

		}

		// Mirror it.

		result.add(AreaUtils.mirrorAlongX(0, result));

		// Get the outline - removes empty areas within.

		result = AreaUtils.getOutline(result);

		// Move it to the top left.

		result = AreaUtils.translateToTopLeft(result);

		// Subtract the left hand side of the area.
		
		result.subtract(new Area(new Rectangle2D.Double(result.getBounds2D().getMinX(), result.getBounds2D().getMinY(),
				result.getBounds2D().getCenterX(), result.getBounds2D().getMaxY())));
		
		// Move it to the top left.		
		
		result = AreaUtils.translateToTopLeft(result);		

		return result;

	}

	private static Area createSegment() {

		Area result = new Area(
				new Rectangle2D.Double(0, 0, RandomInt.anyRandomIntRange(10, 20), RandomInt.anyRandomIntRange(20, 50)));

		return result;

	}

}
