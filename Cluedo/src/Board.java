/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;

// line 40 "model.ump"
// line 127 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private Cluedo cluedo;
  private List<Player> players;
  private List<Cells> cells;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Cluedo aCluedo)
  {
    if (aCluedo == null || aCluedo.getBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aCluedo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cluedo = aCluedo;
    players = new ArrayList<Player>();
    cells = new ArrayList<Cells>();
  }

  public Board(Envelope aEnvelopeForCluedo)
  {
    cluedo = new Cluedo(aEnvelopeForCluedo, this);
    players = new ArrayList<Player>();
    cells = new ArrayList<Cells>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Cluedo getCluedo()
  {
    return cluedo;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Cells getCell(int index)
  {
    Cells aCell = cells.get(index);
    return aCell;
  }

  public List<Cells> getCells()
  {
    List<Cells> newCells = Collections.unmodifiableList(cells);
    return newCells;
  }

  public int numberOfCells()
  {
    int number = cells.size();
    return number;
  }

  public boolean hasCells()
  {
    boolean has = cells.size() > 0;
    return has;
  }

  public int indexOfCell(Cells aCell)
  {
    int index = cells.indexOf(aCell);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 6;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(Hand aHand)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(this, aHand);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Board existingBoard = aPlayer.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aPlayer.setBoard(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a board
    if (this.equals(aPlayer.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (3)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCells()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Cells addCell(Location aLocation)
  {
    return new Cells(aLocation, this);
  }

  public boolean addCell(Cells aCell)
  {
    boolean wasAdded = false;
    if (cells.contains(aCell)) { return false; }
    Board existingBoard = aCell.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
    if (isNewBoard)
    {
      aCell.setBoard(this);
    }
    else
    {
      cells.add(aCell);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCell(Cells aCell)
  {
    boolean wasRemoved = false;
    //Unable to remove aCell, as it must always have a board
    if (!this.equals(aCell.getBoard()))
    {
      cells.remove(aCell);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCellAt(Cells aCell, int index)
  {  
    boolean wasAdded = false;
    if(addCell(aCell))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCells()) { index = numberOfCells() - 1; }
      cells.remove(aCell);
      cells.add(index, aCell);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCellAt(Cells aCell, int index)
  {
    boolean wasAdded = false;
    if(cells.contains(aCell))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCells()) { index = numberOfCells() - 1; }
      cells.remove(aCell);
      cells.add(index, aCell);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCellAt(aCell, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Cluedo existingCluedo = cluedo;
    cluedo = null;
    if (existingCluedo != null)
    {
      existingCluedo.delete();
    }
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
    for(int i=cells.size(); i > 0; i--)
    {
      Cells aCell = cells.get(i - 1);
      aCell.delete();
    }
  }

}