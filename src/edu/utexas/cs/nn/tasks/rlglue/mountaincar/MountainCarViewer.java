package edu.utexas.cs.nn.tasks.rlglue.mountaincar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.rlcommunity.environments.mountaincar.messages.MCGoalRequest;
import org.rlcommunity.environments.mountaincar.messages.MCGoalResponse;
import org.rlcommunity.environments.mountaincar.visualizer.MountainCarVisualizer;

import edu.utexas.cs.nn.graphics.DrawingPanel;
import edu.utexas.cs.nn.networks.TWEANN;
import rlVizLib.utilities.UtilityShop;
import rlVizLib.visualization.VizComponentChangeListener;

public final class MountainCarViewer {

	public static MountainCarViewer current = null;
	public static final int HEIGHT = 500;
	public static final int WIDTH = 500;
	public static final String TITLE = "Mountain_Car";
	public DrawingPanel panel;

	//private MountainCarVisualizer theVizualizer = null; // Never is initialized; probably causing the NullPointerException. TODO: Find out when, where, and how to initialize it.
	private Vector<Double> theHeights = null;
	private double theGoalPosition = 0.0d;
	private double theGoalHeight = 0.0d;
	boolean everDrawn = false;
	
	/**
	 * Sets up the viewer for MountainCar
	 */
	public MountainCarViewer() {
		panel = new DrawingPanel(WIDTH, HEIGHT, TITLE);
		panel.setLocation(TWEANN.NETWORK_VIEW_DIM, 0);
		//theVizualizer = new MountainCarVisualizer();
		reset();
		current = this;
	}
	
	/**
	 * Resets the graphics for the view
	 */
	public void reset() {
		Graphics2D g = panel.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		render(g);
	}
	
	public void render(Graphics2D g) {
		if (!everDrawn) {
			theGoalPosition = 10; //theVizualizer.getGoalPosition(); // TODO: Fix NullPointerException Error
			Vector<Double> tempVec = new Vector<Double>();
			tempVec.add(theGoalPosition);

//			Vector<Double> returnVector = theVizualizer.getHeightsForPositions(tempVec);
			Vector<Double> returnVector = getHeightsForPositions(tempVec);
			theGoalHeight = returnVector.get(0);
			everDrawn = true;

		}

		AffineTransform theScaleTransform = new AffineTransform();

		// Draw a rectangle
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1, 1);

		double scaleFactor = 1000.0d;
		theScaleTransform.scale(1.0d / scaleFactor, 1.0d / scaleFactor);
		AffineTransform origTransform = g.getTransform();

		AffineTransform x = g.getTransform();
		x.concatenate(theScaleTransform);
		g.setTransform(x);

//		theHeights = theVizualizer.getSampleHeights();
		theHeights = getSampleHeights();

		double sizeEachComponent = 1.0 / (double) theHeights.size();

		g.setColor(Color.BLACK);

		double lastX = 0.0d;
//		double lastY = 1.0 - UtilityShop.normalizeValue(theHeights.get(0), theVizualizer.getMinHeight(),
//				theVizualizer.getMaxHeight());
		double lastY = 1.0 - UtilityShop.normalizeValue(1, getMinHeight(), getMaxHeight());
		for (int i = 1; i < theHeights.size(); i++) {
			double thisX = lastX + sizeEachComponent;
//			double thisY = 1.0 - UtilityShop.normalizeValue(theHeights.get(i), theVizualizer.getMinHeight(),
//					theVizualizer.getMaxHeight());
			double thisY = 1.0 - UtilityShop.normalizeValue(theHeights.get(i), getMinHeight(), getMaxHeight());

			
			Line2D thisLine = new Line2D.Double(scaleFactor * lastX, scaleFactor * lastY, scaleFactor * thisX,
					scaleFactor * thisY);

			lastX = thisX;
			lastY = thisY;

			g.draw(thisLine);
		}

		g.setTransform(origTransform);

		g.setColor(Color.GREEN);

		// to bring things back into the window
//		double minPosition = theVizualizer.getMinValueForDim(0);
//		double maxPosition = theVizualizer.getMaxValueForDim(0);
		double minPosition = getMinValueForDim(0);
		double maxPosition = getMaxValueForDim(0);

		double transX = UtilityShop.normalizeValue(theGoalPosition, minPosition, maxPosition);
		// need to get he actual height ranges
//		double transY = UtilityShop.normalizeValue(theGoalHeight, theVizualizer.getMinHeight(),
//				theVizualizer.getMaxHeight());
		double transY = UtilityShop.normalizeValue(theGoalHeight, getMinHeight(), getMaxHeight());
		transY = (1.0 - transY);

		double rectWidth = .05 / 4;
		double rectHeight = 0.1;
		Rectangle2D fillRect = new Rectangle2D.Double(transX - rectWidth / 2.0d, transY - rectHeight / 2.0d, rectWidth, rectHeight);
		//Rectangle2D fillRect = new Rectangle2D.Double(1, 2, 10, 10);
		g.fill(fillRect);

	}

	private double getMaxValueForDim(int i) { //TODO
		return 400;
	}

	private double getMinValueForDim(int i) { //TODO
		return 100;
	}

	private Vector<Double> getHeightsForPositions(Vector<Double> tempVec) { //TODO
		return tempVec;
	}

	private Vector<Double> getSampleHeights() { //TODO
		Vector<Double> theVect = new Vector<Double>();
		theVect.add(10.0);
		theVect.add(30.0);
		theVect.add(0.0);
		theVect.add(40.0);
		theVect.add(50.0);
		theVect.add(20.0);
		return theVect;
	}

	private double getMaxHeight() { //TODO
		return 100;
	}

	private double getMinHeight() { //TODO
		return 0;
	}
	
	
}
