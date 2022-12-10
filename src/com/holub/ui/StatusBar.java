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

	private StatusBar(){}


	private static boolean valid()
	{
		assert statusBarFrame != null : "statusBarFrame not established";
		assert statusBar != null : "StatusBar not established";
		assert turnStatus != null : "StatusBar not established";
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
		turnStatus.setBackground(Color.WHITE);
		statusBar.add( turnStatus );
		statusBarFrame.add( statusBar, BorderLayout.SOUTH );

		assert valid();
	}

	public static void updateTurnStatus(int score)
	{
		assert valid();
		turnStatus.setText( score + " turns" );
	}
}
