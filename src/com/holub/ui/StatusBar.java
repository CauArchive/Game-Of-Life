package com.holub.ui;

import com.holub.life.Universe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StatusBar
{
	private static JFrame		statusBarFrame	= null;
	private static JPanel 		statusBar	= null;
	private static JLabel 		turnStatus	= null;

	private static JLabel 		playStatus = null;

	private static ImageIcon 	startIcon	= new ImageIcon( new ImageIcon("images/play.png" ).getImage().getScaledInstance( 20, 20, Image.SCALE_SMOOTH ) );
	private static ImageIcon 	stopIcon	= new ImageIcon( new ImageIcon("images/stop.png" ).getImage().getScaledInstance( 20, 20, Image.SCALE_SMOOTH ) );
	private static ImageIcon 	pauseIcon	= new ImageIcon( new ImageIcon("images/pause.png" ).getImage().getScaledInstance( 20, 20, Image.SCALE_SMOOTH ) );

	private StatusBar(){}


	private static boolean valid()
	{
		assert statusBarFrame != null : "statusBarFrame not established";
		assert statusBar != null : "StatusBar not established";
		assert turnStatus != null : "StatusBar not established";
		assert playStatus != null : "StatusBar not established";
		return true;
	}

	public synchronized static void establish(JFrame container)
	{
		assert container != null;
		assert statusBarFrame == null:
							"Tried to establish more than one StatusBar";

		statusBarFrame = container;

		statusBar = new JPanel();

		turnStatus = new JLabel(Universe.score + " turns");
		turnStatus.setOpaque(true);
		turnStatus.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		statusBar.add( turnStatus );

		playStatus = new JLabel(stopIcon);
		playStatus.setOpaque(true);
		playStatus.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		statusBar.add( playStatus );

		statusBarFrame.add( statusBar, BorderLayout.SOUTH );

		assert valid();
	}

	public static void updateTurnStatus(int score)
	{
		assert valid();
		turnStatus.setText( score + " turns" );
	}

	public static void updatePlayStatus(int status)
	{
		assert valid();
		if(status == 0) {
			playStatus.setIcon(stopIcon);
		} else if(status == 1) {
			playStatus.setIcon(startIcon);
		} else {
			playStatus.setIcon(pauseIcon);
		}
	}
}
