package org.uet.dse.rtlplus.objectdiagram;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.sys.MObject;
import org.uet.dse.rtlplus.mm.MRuleCollection.Side;
import org.w3c.dom.Element;

/**
 * This class stores information of lines presenting TGG rules
 * 
 * @author Duc-Hanh Dang
 */
public class RtlSwimlane {
	private RtlObjectDiagramView diagramView;

	private Line2D leftmostLine;
	private Line2D leftLine;
	private Line2D rightLine;
	private Line2D rightmostLine;
	private static final int Y2 = 1000;

	public RtlSwimlane(RtlObjectDiagramView rtlObjectDiagramView, int x1, int x2, int x3, int x4) {
		diagramView = rtlObjectDiagramView;
		Point lineStart, lineEnd;
		// Leftmost line
		lineStart = new Point(x1, 0);
		lineEnd = new Point(x1, Y2);
		leftmostLine = new Line2D.Double(lineStart, lineEnd);
		// Line between source and corr
		lineStart = new Point(x2, 0);
		lineEnd = new Point(x2, Y2);
		leftLine = new Line2D.Double(lineStart, lineEnd);
		// Line between target and corr
		lineStart = new Point(x3, 0);
		lineEnd = new Point(x3, Y2);
		rightLine = new Line2D.Double(lineStart, lineEnd);
		// Rightmost line
		lineStart = new Point(x4, 0);
		lineEnd = new Point(x4, Y2);
		rightmostLine = new Line2D.Double(lineStart, lineEnd);
	}

	public Line2D getLeftmostLine() {
		return leftmostLine;
	}

	public Line2D getLeftLine() {
		return leftLine;
	}

	public Line2D getRightLine() {
		return rightLine;
	}

	public Line2D getRightmostLine() {
		return rightmostLine;
	}

	/**
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @return The line corresponding to point (x,y)
	 */
	public int getLine(int x, int y) {
		if (leftmostLine.ptLineDist(x, y) <= 5)
			return 1;
		if (leftLine.ptLineDist(x, y) <= 5)
			return 2;
		if (rightLine.ptLineDist(x, y) <= 5)
			return 3;
		if (rightmostLine.ptLineDist(x, y) <= 5)
			return 4;
		return 0;
	}

	/**
	 * Gets which side a point belongs to. Used when checking an object node's
	 * position.
	 * 
	 * @param x
	 *            The point's x coordinate
	 * @return A side (source, target, correlation, other)
	 */
	public Side getSide(double x) {
		if (x < leftmostLine.getX1())
			return Side.OTHER;
		if (x < leftLine.getX1())
			return Side.SOURCE;
		if (x < rightLine.getX1())
			return Side.CORRELATION;
		if (x < rightmostLine.getX1())
			return Side.TARGET;
		return Side.OTHER;
	}

	// public void moveSwimlane(int draggingSwimlane, int dx, int dy) {
	// switch (draggingSwimlane) {
	// case 1: // fSource_Border;
	// if (fSource_Border == null)
	// break;
	// Point2D p1 = fSource_Border.getP1();
	// Point2D p2 = fSource_Border.getP2();
	// if ((0 < p1.getX() + dx) && (p1.getX() + dx < fSource_Corr.getX1())) {
	// p1.setLocation(p1.getX() + dx, p1.getY());
	// p2.setLocation(p2.getX() + dx, p2.getY());
	// if (dx > 0) {
	// List<MObject> objects =
	// this.diagramView.getTggRule().getSrcRule().getAllObjects();
	// boolean ok = tryMoveLeftRightObjects(objects, dx);
	// if (!ok) {
	// p1.setLocation(p1.getX() - dx, p1.getY());
	// p2.setLocation(p2.getX() - dx, p2.getY());
	// }
	// }
	// fSource_Border.setLine(p1, p2);
	// }
	//
	// break;
	// case 2: // fSource_Corr;
	// p1 = fSource_Corr.getP1();
	// p2 = fSource_Corr.getP2();
	// if ((getMinX() < p1.getX() + dx) && (p1.getX() + dx < fTarget_Corr.getX1()))
	// {
	// // try to move the swimlane, move objects, and rollback in case an error
	// occurs
	// p1.setLocation(p1.getX() + dx, p1.getY());
	// p2.setLocation(p2.getX() + dx, p2.getY());
	// List<MObject> objects;
	// if (dx < 0) {
	// objects = this.diagramView.getTggRule().getSrcRule().getAllObjects();
	// } else {
	// objects = this.diagramView.getTggRule().getCorrRule().getAllObjects();
	// }
	// boolean ok = tryMoveLeftRightObjects(objects, dx);
	// if (!ok) {
	// p1.setLocation(p1.getX() - dx, p1.getY());
	// p2.setLocation(p2.getX() - dx, p2.getY());
	// }
	// fSource_Corr.setLine(p1, p2);
	// }
	// break;
	// case 3: // fTarget_Corr;
	// p1 = fTarget_Corr.getP1();
	// p2 = fTarget_Corr.getP2();
	// if ((fSource_Corr.getX1() < p1.getX() + dx) && (p1.getX() + dx <
	// fTarget_Border.getX1())) {
	// // try to move the swimlane, move objects, and rollback in case an error
	// occurs
	// p1.setLocation(p1.getX() + dx, p1.getY());
	// p2.setLocation(p2.getX() + dx, p2.getY());
	// List<MObject> objects;
	// if (dx < 0) {
	// objects = this.diagramView.getTggRule().getCorrRule().getAllObjects();
	// } else {
	// objects = this.diagramView.getTggRule().getTrgRule().getAllObjects();
	// }
	// boolean ok = tryMoveLeftRightObjects(objects, dx);
	// if (!ok) {
	// p1.setLocation(p1.getX() - dx, p1.getY());
	// p2.setLocation(p2.getX() - dx, p2.getY());
	// }
	// fTarget_Corr.setLine(p1, p2);
	// }
	// break;
	// case 4: // fTarget_Border;
	// p1 = fTarget_Border.getP1();
	// p2 = fTarget_Border.getP2();
	// if (fTarget_Corr.getX1() < p1.getX() + dx) {
	// // try to move the swimlane, move objects, and rollback in case an error
	// occurs
	// p1.setLocation(p1.getX() + dx, p1.getY());
	// p2.setLocation(p2.getX() + dx, p2.getY());
	// boolean ok = true;
	// if (dx < 0) {
	// List<MObject> objects =
	// this.diagramView.getTggRule().getTrgRule().getAllObjects();
	// ok = tryMoveLeftRightObjects(objects, dx);
	// if (!ok) {
	// p1.setLocation(p1.getX() - dx, p1.getY());
	// p2.setLocation(p2.getX() - dx, p2.getY());
	// }
	// }
	// fTarget_Border.setLine(p1, p2);
	// if (ok) {
	// if (fTopTggLine != null) {
	// fTopTggLine.setLine(fTopTggLine.getX1(), fTopTggLine.getY1(),
	// fTopTggLine.getX2() + dx,
	// fTopTggLine.getY2());
	// }
	// fMiddleTggLine.setLine(fMiddleTggLine.getX1(), fMiddleTggLine.getY1(),
	// fMiddleTggLine.getX2() + dx,
	// fMiddleTggLine.getY2());
	// fBottomTggLine.setLine(fBottomTggLine.getX1(), fBottomTggLine.getY1(),
	// fBottomTggLine.getX2() + dx,
	// fBottomTggLine.getY2());
	// if (fBottomLine != null) {
	// fBottomLine.setLine(fBottomLine.getX1(), fBottomLine.getY1(),
	// fBottomLine.getX2() + dx,
	// fBottomLine.getY2());
	// }
	// }
	// }
	// break;
	// case 10: // fTopTggLine;
	// if (fTopTggLine == null)
	// break;
	// p1 = fTopTggLine.getP1();
	// p2 = fTopTggLine.getP2();
	// if ((0 < p1.getY() + dy) && (p1.getY() + dy < fMiddleTggLine.getY1())) {
	// // try to move the swimlane, move objects, and rollback in case an error
	// occurs
	// p1.setLocation(p1.getX(), p1.getY() + dy);
	// p2.setLocation(p2.getX(), p2.getY() + dy);
	// Set<MObject> objects =
	// this.diagramView.getTggRule().getSystemStateLHS().allObjects();
	// boolean ok = tryMoveUpDownObjects(objects, dy);
	// if (!ok) {
	// p1.setLocation(p1.getX(), p1.getY() - dy);
	// p2.setLocation(p2.getX(), p2.getY() - dy);
	// }
	// fTopTggLine.setLine(p1, p2);
	// }
	// break;
	// case 20: // fMiddleTggLine;
	// p1 = fMiddleTggLine.getP1();
	// p2 = fMiddleTggLine.getP2();
	// if ((getMinY(draggingSwimlane) < p1.getY() + dy) && (p1.getY() + dy <
	// fBottomTggLine.getY1())) {
	// // try to move the swimlane, move objects, and rollback in case an error
	// occurs
	// p1.setLocation(p1.getX(), p1.getY() + dy);
	// p2.setLocation(p2.getX(), p2.getY() + dy);
	// Set<MObject> objects;
	// if (dy < 0) {
	// objects = this.diagramView.getTggRule().getSystemStateLHS().allObjects();
	// } else {
	// objects = this.diagramView.getTggRule().getSystemStateRHS().allObjects();
	// }
	// boolean ok = tryMoveUpDownObjects(objects, dy);
	// if (!ok) {
	// p1.setLocation(p1.getX(), p1.getY() - dy);
	// p2.setLocation(p2.getX(), p2.getY() - dy);
	// }
	// fMiddleTggLine.setLine(p1, p2);
	// }
	// break;
	// case 30: // fBottomTggLine;
	// p1 = fBottomTggLine.getP1();
	// p2 = fBottomTggLine.getP2();
	// if ((fMiddleTggLine.getY1() < p1.getY() + dy) && (p1.getY() + dy <
	// getMaxY())) {
	// // try to move the swimlane, move objects, and rollback in case an error
	// occurs
	// p1.setLocation(p1.getX(), p1.getY() + dy);
	// p2.setLocation(p2.getX(), p2.getY() + dy);
	// boolean ok = true;
	// if (dy < 0) {
	// Set<MObject> objects =
	// this.diagramView.getTggRule().getSystemStateRHS().allObjects();
	// ok = tryMoveUpDownObjects(objects, dy);
	// if (!ok) {
	// p1.setLocation(p1.getX(), p1.getY() - dy);
	// p2.setLocation(p2.getX(), p2.getY() - dy);
	// }
	// }
	// fBottomTggLine.setLine(p1, p2);
	// if (ok && (fBottomLine == null)) {
	// if (fSource_Border != null) {
	// fSource_Border.setLine(fSource_Border.getX1(), fSource_Border.getY1(),
	// fSource_Border.getX2(),
	// fSource_Border.getY2() + dy);
	// }
	// fSource_Corr.setLine(fSource_Corr.getX1(), fSource_Corr.getY1(),
	// fSource_Corr.getX2(),
	// fSource_Corr.getY2() + dy);
	// fTarget_Corr.setLine(fTarget_Corr.getX1(), fTarget_Corr.getY1(),
	// fTarget_Corr.getX2(),
	// fTarget_Corr.getY2() + dy);
	// fTarget_Border.setLine(fTarget_Border.getX1(), fTarget_Border.getY1(),
	// fTarget_Border.getX2(),
	// fTarget_Border.getY2() + dy);
	// }
	// }
	// break;
	// case 40: // fBottomLine;
	// if (fBottomLine == null)
	// break;
	// p1 = fBottomLine.getP1();
	// p2 = fBottomLine.getP2();
	// if (fBottomTggLine.getY1() < p1.getY() + dy) {
	// p1.setLocation(p1.getX(), p1.getY() + dy);
	// p2.setLocation(p2.getX(), p2.getY() + dy);
	// fBottomLine.setLine(p1, p2);
	// if (fSource_Border != null) {
	// fSource_Border.setLine(fSource_Border.getX1(), fSource_Border.getY1(),
	// fSource_Border.getX2(),
	// fSource_Border.getY2() + dy);
	// }
	// fSource_Corr.setLine(fSource_Corr.getX1(), fSource_Corr.getY1(),
	// fSource_Corr.getX2(),
	// fSource_Corr.getY2() + dy);
	// fTarget_Corr.setLine(fTarget_Corr.getX1(), fTarget_Corr.getY1(),
	// fTarget_Corr.getX2(),
	// fTarget_Corr.getY2() + dy);
	// fTarget_Border.setLine(fTarget_Border.getX1(), fTarget_Border.getY1(),
	// fTarget_Border.getX2(),
	// fTarget_Border.getY2() + dy);
	// }
	// break;
	// default:
	// }
	//
	// }

	public boolean tryMoveLeftRightObjects(List<MObject> objects, int dx) {
		boolean res = true;
		List<ObjectNode> movedObjects = new ArrayList<>();
		for (MObject obj : objects) {
			ObjectNode node = diagramView.getDiagram().getObjectNode(obj);
			int gap = dx > 0 ? 10 : -10;
			if (!node.checkNewPositition(node.getX() - gap)) {
				if (node.checkNewPositition(node.getX() + dx + gap)) {
					node.setPosition(node.getX() + dx, node.getY());
					movedObjects.add(node);
				} else {
					res = false;
					break;
				}
			}
		}
		if (!res) {
			// rolling back
			for (ObjectNode node : movedObjects) {
				node.setPosition(node.getX() - dx, node.getY());
			}
		}
		return res;
	}

	public void updateSwimlane(int x1, int x2, int x3, int x4) {
		leftmostLine.setLine(x1, 0, x1, Y2);
		leftLine.setLine(x2, 0, x2, Y2);
		rightLine.setLine(x3, 0, x3, Y2);
		rightmostLine.setLine(x4, 0, x4, Y2);
	}

	// Save line position to restore
	public void storePlacementInfo(org.tzi.use.gui.util.PersistHelper helper, Element parent) {
		Element swimlaneElement = helper.createChild(parent, "rtlswimlane");
		helper.appendChild(swimlaneElement, "X1", Integer.toString((int) leftmostLine.getX1()));
		helper.appendChild(swimlaneElement, "X2", Integer.toString((int) leftLine.getX1()));
		helper.appendChild(swimlaneElement, "X3", Integer.toString((int) rightLine.getX1()));
		helper.appendChild(swimlaneElement, "X4", Integer.toString((int) rightmostLine.getX1()));
	}

	/**
	 * Generates a random position inside the correct lane
	 * 
	 * @param pos
	 *            The position to modify
	 * @param w
	 *            Diagram width
	 * @param h
	 *            Diagram side
	 * @param side
	 *            Which lane the object belongs to
	 */
	public void getRandomNextPosition(Point2D.Double pos, int w, int h, Side side) {
		switch (side) {
		case SOURCE:
			pos.x = leftmostLine.getX1() + Math.random() * (leftLine.getX1() - leftmostLine.getX1());
			break;
		case CORRELATION:
			pos.x = leftLine.getX1() + Math.random() * (rightLine.getX1() - leftLine.getX1());
			break;
		case TARGET:
			pos.x = rightLine.getX1() + Math.random() * (rightmostLine.getX1() - rightLine.getX1());
			break;
		default:
			pos.x = Math.random() * Math.max(100, w - 100);
		}
		pos.y = Math.random() * Math.max(100, h - 100);
	}
}