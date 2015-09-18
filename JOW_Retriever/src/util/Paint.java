package util;

import java.awt.Color;
import java.awt.Graphics;

import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.wrappers.Timer;
import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.listeners.InventoryListener;

public class Paint implements PaintListener, InventoryListener {
	
	private Timer t = new Timer();
	
	private int counter;
	
	@Override
	public void onRepaint(Graphics g) {	
		Color c = new Color(0, 0, 0, 0.7f);
		
		g.setColor(c);
		
		g.fillRect(5, 30, 132, 55);
		
		g.setColor(Color.CYAN);
		
		g.drawString("Runtime: " + t.getTimeRunningString(), 20, 50);
		g.drawString("Jugs retrieved: " + counter, 20, 70);
	}

	@Override
	public void itemsAdded(InventoryEvent e) {
		if (e.getItem().getName().equals("Jug of water")) {
			counter++;
		}
	}

	@Override public void itemsRemoved(InventoryEvent e) {}
}
