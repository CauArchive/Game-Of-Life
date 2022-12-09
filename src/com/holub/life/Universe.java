package com.holub.life;

import java.io.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.holub.command.*;
import com.holub.inputstream.GridMatrixStreamAdapter;
import com.holub.io.Files;
import com.holub.ui.MenuSite;

import com.holub.life.Cell;
import com.holub.life.Storable;
import com.holub.life.Clock;
import com.holub.life.Neighborhood;
import com.holub.life.Resident;
import com.holub.visitor.CellPrintVisitor;
import com.holub.visitor.CellReverseVisitor;
import com.holub.visitor.CellVisitor;
import com.holub.visitor.GridMatrixConvertVisitor;

/**
 * The Universe is a mediator that sits between the Swing
 * event model and the Life classes. It is also a singleton,
 * accessed via Universe.instance(). It handles all
 * Swing events and translates them into requests to the
 * outermost Neighborhood. It also creates the Composite
 * Neighborhood.
 *
 * @include /etc/license.txt
 */

public class Universe extends JPanel

{	private static Cell outermostCell;

	public Cell getOutermostCell(){
		return outermostCell;
	}

	//private static	final Universe 	theInstance = new Universe();

	private CellPattern cellPattern;

	/** The default height and width of a Neighborhood in cells.
	 *  If it's too big, you'll run too slowly because
	 *  you have to update the entire block as a unit, so there's more
	 *  to do. If it's too small, you have too many blocks to check.
	 *  I've found that 8 is a good compromise.
	 */
	private static final int  DEFAULT_GRID_SIZE = 8;

	/** The size of the smallest "atomic" cell---a Resident object.
	 *  This size is extrinsic to a Resident (It's passed into the
	 *  Resident's "draw yourself" method.
	 */
	private static final int  DEFAULT_CELL_SIZE = 8;

	// The constructor is private so that the universe can be created
	// only by an outer-class method [Neighborhood.createUniverse()].
	// 테스트용 Universe 코드
	public Universe(int x) {
		// Create the nested Cells that comprise the "universe." A bug
		// in the current implementation causes the program to fail
		// miserably if the overall size of the grid is too big to fit
		// on the screen.
		setGridSize(DEFAULT_GRID_SIZE,DEFAULT_CELL_SIZE);

		addMouseListener                    //{=Universe.mouse}
				(new MouseAdapter() {
					 public void mousePressed(MouseEvent e) {
						 Rectangle bounds = getBounds();
						 bounds.x = 0;
						 bounds.y = 0;
						 cellPattern.executePattern(e.getPoint(), bounds);
						 repaint();
					 }
				 }
				);
	}
	private Universe()
	{	// Create the nested Cells that comprise the "universe." A bug
		// in the current implementation causes the program to fail
		// miserably if the overall size of the grid is too big to fit
		// on the screen.
		setGridSize(DEFAULT_GRID_SIZE,DEFAULT_CELL_SIZE);

		addMouseListener					//{=Universe.mouse}
		(	new MouseAdapter()
			{	public void mousePressed(MouseEvent e)
				{	Rectangle bounds = getBounds();
					bounds.x = 0;
					bounds.y = 0;
					cellPattern.executePattern(e.getPoint(), bounds);
					repaint();
				}
			}
		);

		MenuSite.addLine( this, "Grid", "Clear",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{	outermostCell.clear();
					repaint();
				}
			}
		);

		MenuSite.addLine( this, "Grid", "Reverse",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					CellVisitor visitor = new CellReverseVisitor();
					outermostCell.accept(visitor);
					repaint();
				}
			}
		);

		MenuSite.addLine			// {=Universe.load.setup}
		(	this, "Grid", "Load",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{	doLoad();
				}
			}
		);

		MenuSite.addLine
		(	this, "Grid", "Store",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{	doStore();
				}
			}
		);

		MenuSite.addLine
		(	this, "Grid", "Exit",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
		        {	System.exit(0);
		        }
			}
		);

		Clock.instance().addClockListener //{=Universe.clock.subscribe}
		(	new Clock.Listener()
			{	public void tick()
				{	if( outermostCell.figureNextState
						   ( Cell.DUMMY,Cell.DUMMY,Cell.DUMMY,Cell.DUMMY,
							 Cell.DUMMY,Cell.DUMMY,Cell.DUMMY,Cell.DUMMY
						   )
					  )
					{	if( outermostCell.transition() )
							refreshNow();
					}
				}
			}
		);

		MenuSite.addLine
		(	this, "Patterns", "single",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					cellPattern = new SingleCellPattern(outermostCell);
				}
			}
		);

		MenuSite.addLine
		(	this, "Patterns", "Boat",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					cellPattern = new BoatCellPattern(outermostCell);
				}
			}
		);

		MenuSite.addLine
		(	this, "Patterns", "Glider",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					cellPattern = new GliderCellPattern(outermostCell);
				}
			}
		);

		MenuSite.addLine
		(	this, "Patterns", "Hammerhead",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					cellPattern = new HammerHeadCellPattern(outermostCell);
				}
			}
		);


		MenuSite.addLine
		(	this, "Resize", "change grid size to 8",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					setGridSize(8,8);
				}
			}
		);

		MenuSite.addLine
		(	this, "Resize", "change grid size to 10",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					setGridSize(10, 10);
				}
			}
		);

		MenuSite.addLine
		(	this, "Resize", "change grid size to 12",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					setGridSize(12, 12);
				}
			}
		);

		MenuSite.addLine( this, "Print", "Print Grid to output.txt file",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					CellPrintVisitor visitor = new CellPrintVisitor();
					outermostCell.accept(visitor);
					try {
						visitor.setPrintWriter(new PrintWriter(new FileWriter("output.txt")));
					}catch (Exception exception){
						exception.printStackTrace();
					}
					visitor.print(outermostCell.getGrid().length);
				}
			}
		);

		MenuSite.addLine( this, "Print", "print stream_output.txt using stream adaptor",
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					GridMatrixConvertVisitor gridMatrixConvertVisitor = new GridMatrixConvertVisitor();
					outermostCell.accept(gridMatrixConvertVisitor);

					boolean[][] gridMatrix =  gridMatrixConvertVisitor.getGridMatrix();

					try {
						int c;
						File file = new File("stream_output.txt");
						InputStream in = new GridMatrixStreamAdapter(gridMatrix);
						PrintStream out = new PrintStream(new FileOutputStream(file), true, "UTF-8");
						while ((c = in.read()) >= 0){
							char cc = (char) c;
							out.print((char)c);
						}
						out.close();
					} catch (Exception exception){
						exception.printStackTrace();
					}
				}
			}
		);
	}

	/** Singleton Accessor. The Universe object itself is manufactured
	 *  in Neighborhood.createUniverse()
	 */

	public static Universe instance()
	{	return new Universe();
	}

	private void doLoad()
	{	try
		{
			FileInputStream in = new FileInputStream(
			   Files.userSelected(".",".life","Life File","Load"));

			Clock.instance().stop();		// stop the game and
			outermostCell.clear();			// clear the board.

			Storable memento = outermostCell.createMemento();
			memento.load( in );
			outermostCell.transfer( memento, new Point(0,0), Cell.LOAD );

			in.close();
		}
		catch( IOException theException )
		{	JOptionPane.showMessageDialog( null, "Read Failed!",
					"The Game of Life", JOptionPane.ERROR_MESSAGE);
		}
		repaint();
	}

	private void doStore()
	{	try
		{
			FileOutputStream out = new FileOutputStream(
				  Files.userSelected(".",".life","Life File","Write"));

			Clock.instance().stop();		// stop the game

			Storable memento = outermostCell.createMemento();
			outermostCell.transfer( memento, new Point(0,0), Cell.STORE );
			memento.flush(out);

			out.close();
		}
		catch( IOException theException )
		{	JOptionPane.showMessageDialog( null, "Write Failed!",
					"The Game of Life", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** Override paint to ask the outermost Neighborhood
	 *  (and any subcells) to draw themselves recursively.
	 *  All knowledge of screen size is also encapsulated.
	 *  (The size is passed into the outermost <code>Cell</code>.)
	 */

	public void paint(Graphics g)
	{
		Rectangle panelBounds = getBounds();
		Rectangle clipBounds  = g.getClipBounds();

		// The panel bounds is relative to the upper-left
		// corner of the screen. Pretend that it's at (0,0)
		panelBounds.x = 0;
		panelBounds.y = 0;
		outermostCell.redraw(g, panelBounds, true);		//{=Universe.redraw1}
	}

	/** Force a screen refresh by queing a request on
	 *  the Swing event queue. This is an example of the
	 *  Active Object pattern (not covered by the Gang of Four).
	 *  This method is called on every clock tick. Note that
	 *  the redraw() method on a given <code>Cell</code>
	 *  does nothing if the <code>Cell</code> doesn't
	 *  have to be refreshed.
	 */

	private void refreshNow()
	{	SwingUtilities.invokeLater
		(	new Runnable()
			{	public void run()
				{	Graphics g = getGraphics();
					if( g == null )		// Universe not displayable
						return;
					try
					{
						Rectangle panelBounds = getBounds();
						panelBounds.x = 0;
						panelBounds.y = 0;
						outermostCell.redraw(g, panelBounds, false); //{=Universe.redraw2}
					}
					finally
					{	g.dispose();
					}
				}
			}
		);
	}

	private void setClickPattern(CellPattern cellPattern){
		this.cellPattern = cellPattern;
	}

	private void setGridSize(int gridSize, int cellSize){
		outermostCell = new Neighborhood
						(	gridSize,
							new Neighborhood
							(	gridSize,
								new Resident()
							)
						);
		cellPattern = new SingleCellPattern(outermostCell);

		/** width, height 여유 줘야 정상적으로 resize 됨*/
		final Dimension PREFERRED_SIZE =
						new Dimension
						(  outermostCell.widthInCells() * cellSize + 5,
						   outermostCell.widthInCells() * cellSize + 5
						);

		addComponentListener
		(	new ComponentAdapter()
			{	public void componentResized(ComponentEvent e)
				{
					// Make sure that the cells fit evenly into the
					// total grid size so that each cell will be the
					// same size. For example, in a 64x64 grid, the
					// total size must be an even multiple of 63.

					Rectangle bounds = getBounds();
					bounds.height /= outermostCell.widthInCells();
					bounds.height *= outermostCell.widthInCells();
					bounds.width  =  bounds.height;
					setBounds( bounds );
				}
			}
		);

		setBackground	( Color.white	 );
		setPreferredSize( PREFERRED_SIZE );
		setMaximumSize	( PREFERRED_SIZE );
		setMinimumSize	( PREFERRED_SIZE );
		setOpaque		( true			 );
		revalidate();
		repaint();
	}
}
