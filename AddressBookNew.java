// -- after delete, clear exception occurred see inside
// -- the event handler is wrong 
// --  75
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;
//ID: 203249354
public class AddressBookNew 
  extends Application implements AddressBookNewFinals 
{	private AddressBookPane[] panes = 
	  new AddressBookPane[NUMBER_OF_OBJECTS];
    private Stage[] stages = new Stage[NUMBER_OF_OBJECTS];
    private Scene[] scenes = new Scene[NUMBER_OF_OBJECTS];
    public static void main(String[] args)
    { launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception
	{ try
      { for ( int i = 0; i < 1+NUMBER_OF_OBJECTS; i++ )
        { if (i >= NUMBER_OF_OBJECTS)
            System.out.println(SINGLETON_MESSAGE);
          else
          { panes[i] = AddressBookPane.getInstance();
            scenes[i] = new Scene(panes[i]);
            stages[i] = new Stage();
            stages[i].setX(i*200);
            stages[i].setY(i*200);
	        stages[i].setTitle(TITLE+" "+(i+1));
	        stages[i].setScene(scenes[i]);
	        stages[i].setResizable(true);
	        stages[i].show();
	        stages[i].setAlwaysOnTop(true);
	        stages[i].setOnCloseRequest(event ->
	          { AddressBookPane.reduceNumberOfObjects();
		      });
          } 
        }
     }  
     catch (Exception e)
     { AddressBookPane.resetNumberOfObjects();
     }
  }
}
class AddressBookPane extends GridPane 
  implements AddressBookNewFinals, AddressBookEvent
{ private EventHandler<ActionEvent> eventHandler;
  private EventHandler<ActionEvent> ae; 
  private Timeline showBook; 
  private static int number_of_objects = 0;
  private RandomAccessFile raf;
  private TextField jtfName = new TextField();
  private TextField jtfStreet = new TextField();
  private TextField jtfCity = new TextField();
  private TextField jtfState = new TextField();
  private TextField jtfZip = new TextField();
  private CommandButton[] buttonArray;
  private AddressBookPane(int paneNumber)
  { try
 	{ raf = new RandomAccessFile(FILE_NAME, FILE_MODE);
	} 
 	catch (IOException ex)
 	{ System.out.println(ex);
	  System.exit(0);
	}
    buttonArray = new CommandButton[] 
	  {new FirstButton(this, raf),
	   new AddButton(this, raf), 
	   new NextButton(this, raf), 
	   new PreviousButton(this, raf),
	   new LastButton(this, raf), 
	   new ClearButton(this, raf),
	   new DeleteButton(this, raf)};
    // -- wrong  
    eventHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			buttonArray[INDEX_OF_FIRST_BUTTON_IN_BUTTON_ARRAY].Execute();	
		}
	};
	showBook = new Timeline(new KeyFrame(Duration.millis(SHOW_TIME),eventHandler));
	getShowBook().setCycleCount(Animation.INDEFINITE);
	showBook.play();
	ae = e -> {
		getShowBook().pause(); //stops the timeline timer until action is done
		((Command) e.getSource()).Execute(); // perform the action
		getShowBook().play(); //restarts the timeline timer from last position
	};
    jtfState.setAlignment(Pos.CENTER_LEFT);
	jtfState.setPrefWidth(25);
	jtfZip.setPrefWidth(60);
	Label state = new Label(STATE);
	Label zp = new Label(ZIP);
	Label name = new Label(NAME);
	Label street = new Label(STREET);
	Label city = new Label(CITY);		
	GridPane p1 = new GridPane();
	p1.add(name, 0, 0);
	p1.add(street, 0, 1);
	p1.add(city, 0, 2);
	p1.setAlignment(Pos.CENTER_LEFT);
	p1.setVgap(8);
	p1.setPadding(new Insets(0, 2, 0, 2));
	GridPane.setVgrow(name, Priority.ALWAYS);
	GridPane.setVgrow(street, Priority.ALWAYS);
	GridPane.setVgrow(city, Priority.ALWAYS);
	GridPane adP = new GridPane();
	adP.add(jtfCity, 0, 0);
	adP.add(state, 1, 0);
	adP.add(jtfState, 2, 0);
	adP.add(zp, 3, 0);
	adP.add(jtfZip, 4, 0);
	adP.setAlignment(Pos.CENTER_LEFT);
	GridPane.setHgrow(jtfCity, Priority.ALWAYS);
	GridPane.setVgrow(jtfCity, Priority.ALWAYS);
	GridPane.setVgrow(jtfState, Priority.ALWAYS);
	GridPane.setVgrow(jtfZip, Priority.ALWAYS);
	GridPane.setVgrow(state, Priority.ALWAYS);
	GridPane.setVgrow(zp, Priority.ALWAYS);
	GridPane p4 = new GridPane();
	p4.add(jtfName, 0, 0);
	p4.add(jtfStreet, 0, 1);
	p4.add(adP, 0, 2);
	p4.setVgap(1);
	GridPane.setHgrow(jtfName, Priority.ALWAYS);
	GridPane.setHgrow(jtfStreet, Priority.ALWAYS);
	GridPane.setHgrow(adP, Priority.ALWAYS);
	GridPane.setVgrow(jtfName, Priority.ALWAYS);
	GridPane.setVgrow(jtfStreet, Priority.ALWAYS);
	GridPane.setVgrow(adP, Priority.ALWAYS);
	GridPane jpAddress = new GridPane();
	jpAddress.add(p1, 0, 0);
	jpAddress.add(p4, 1, 0);
	GridPane.setHgrow(p1, Priority.NEVER);
	GridPane.setHgrow(p4, Priority.ALWAYS);
	GridPane.setVgrow(p1, Priority.ALWAYS);
	GridPane.setVgrow(p4, Priority.ALWAYS);
	jpAddress.setStyle(STYLE_COMMAND);
	FlowPane jpButton = new FlowPane();
	jpButton.setHgap(5);
	for (int i=0; i < buttonArray.length; i++)
	{ if (eventType.values()[i].getDoEvent())
	  {	jpButton.getChildren().add(buttonArray[i]);
	    buttonArray[i].setOnAction(ae); 
	  }	
	}
	jpButton.setAlignment(Pos.CENTER);
	GridPane.setVgrow(jpButton, Priority.NEVER);
	GridPane.setVgrow(jpAddress, Priority.ALWAYS);
	GridPane.setHgrow(jpButton, Priority.ALWAYS);
	GridPane.setHgrow(jpAddress, Priority.ALWAYS);
	this.setVgap(5);
	this.add(jpAddress, 0, 0);
	this.add(jpButton, 0, 1);
	buttonArray[INDEX_OF_FIRST_BUTTON_IN_BUTTON_ARRAY].Execute();
  }
	public Timeline getShowBook()
	{ return showBook;
	}
    public CommandButton[] getButtonArray()
	{ return buttonArray; 
		
	}
    public void SetName(String text)
	{ jtfName.setText(text);
	}
	public void SetStreet(String text)
	{ jtfStreet.setText(text);
	}
	public void SetCity(String text)
	{ jtfCity.setText(text);
	}
	public void SetState(String text)
	{ jtfState.setText(text);
	}
	public void SetZip(String text)
	{ jtfZip.setText(text);
	}
	public String GetName()
	{ return jtfName.getText();
	}
	public String GetStreet()
	{ return jtfStreet.getText();
	}
	public String GetCity()
	{ return jtfCity.getText();
	}
	public String GetState()
	{ return jtfState.getText();
	}
	public String GetZip()
	{ return jtfZip.getText();
	}
	public void clearTextFields()
	{ jtfName.setText("");
	  jtfStreet.setText("");
	  jtfCity.setText("");
	  jtfState.setText("");
	  jtfZip.setText("");
	}
	public static AddressBookPane getInstance()
	{ if(number_of_objects>NUMBER_OF_OBJECTS)
		return null;
	number_of_objects++;
	return new AddressBookPane(number_of_objects);
	}
	public static void reduceNumberOfObjects()
	{ number_of_objects--;
	}
	public static int getNumberOfObjects()
	{ return number_of_objects;
	}
	public static void resetNumberOfObjects()
	{  number_of_objects = 0;
	}
}
interface Command
{ public void Execute();
}
class CommandButton extends Button 
  implements Command, AddressBookNewFinals
{ private AddressBookPane p;
  private RandomAccessFile raf;
  public CommandButton(AddressBookPane pane, RandomAccessFile r)
  {	super();
	p = pane;
	raf = r;
  }
  public AddressBookPane getPane()
  { return p;
  }
  public RandomAccessFile getFile()
  { return raf;
  }
  public void setPane(AddressBookPane p)
  { this.p = p;
  }
  @Override
  public void Execute()
  {
  }
  public void writeAddress(long position)
  {	try
    { getFile().seek(position);
	  FixedLengthStringIO.writeFixedLengthString(
		 getPane().GetName(), NAME_SIZE, getFile());
	  FixedLengthStringIO.writeFixedLengthString(
		getPane().GetStreet(), STREET_SIZE, getFile());
	  FixedLengthStringIO.writeFixedLengthString(
		getPane().GetCity(), CITY_SIZE, getFile());
	  FixedLengthStringIO.writeFixedLengthString(
		getPane().GetState(), STATE_SIZE, getFile());
	  FixedLengthStringIO.writeFixedLengthString(
		getPane().GetZip(), ZIP_SIZE, getFile());
	 } 
     catch (IOException ex)
     { ex.printStackTrace();
	 }
   }
   public void readAddress(long position) throws IOException
   { getFile().seek(position);
     getPane().SetName(FixedLengthStringIO.
	   readFixedLengthString(NAME_SIZE, getFile()));
	 getPane().SetStreet(FixedLengthStringIO.
	   readFixedLengthString(STREET_SIZE, getFile()));
	 getPane().SetCity(FixedLengthStringIO.
	   readFixedLengthString(CITY_SIZE, getFile()));
	 getPane().SetState(FixedLengthStringIO.
	   readFixedLengthString(STATE_SIZE, getFile()));
	 getPane().SetZip(FixedLengthStringIO.
	   readFixedLengthString(ZIP_SIZE, getFile()));
	}
}
class AddButton extends CommandButton
{ private AddressBookPane pane;
  public AddButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText(ADD);
	this.pane=pane;
  }
  public AddressBookPane getPane()
  { return pane;
  }
  @Override
  public void Execute()
  {	try
    { writeAddress(getFile().length());
    } 
    catch (IOException e)
    { e.printStackTrace();
    }
  }
}
class NextButton extends CommandButton
{ private AddressBookPane pane;
  public NextButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText(NEXT);
	this.pane = pane;
  }
  public AddressBookPane getPane()
  { return pane;
  }
  @Override
  public void Execute()
  {	try
    { long currentPosition = getFile().getFilePointer();
	  if (currentPosition < getFile().length())
		readAddress(currentPosition);
	} 
    catch (IOException ex)
    { ex.printStackTrace();
	}
  }
}
class PreviousButton extends CommandButton
{ private AddressBookPane pane;
  public PreviousButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText(PREVIOUS);
	this.pane = pane;
  }
  public AddressBookPane getPane()
  { return pane;
  }
  @Override
  public void Execute()
  {	try
    { long currentPosition = getFile().getFilePointer();
	  if (currentPosition - 2 * 2 * RECORD_SIZE >= 0)
	    readAddress(currentPosition - 2 * 2 * RECORD_SIZE);
	} 
    catch (IOException ex)
    {	ex.printStackTrace();
	}
  }
}  
class LastButton extends CommandButton
{ private AddressBookPane pane;
  public LastButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText(LAST);
	this.pane = pane;
  }
  public AddressBookPane getPane()
  { return pane;
  }
  @Override
  public void Execute()
  {	try
    { long lastPosition = getFile().length();
	  if (lastPosition > 0)
		readAddress(lastPosition - 2 * RECORD_SIZE);
	} 
    catch (IOException ex)
    { ex.printStackTrace();
	}
  }
}
class FirstButton extends CommandButton
{ private AddressBookPane pane;
  public FirstButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText(FIRST);
	this.pane = pane;
  }
  public AddressBookPane getPane()
  { return pane;
  }
  @Override
  public void Execute()
  {	try
    { if (getFile().length() > 0) readAddress(0);
      else getPane().clearTextFields();
	} 
    catch (IOException ex)
    { ex.printStackTrace();
	}
  }
}
class ClearButton extends CommandButton
{ private AddressBookPane pane;
  public ClearButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText(CLEAR);
	this.pane = pane;
  }
  public AddressBookPane getPane()
  { return pane;
  }
  @Override
  public void Execute()
  {	try
    { getFile().setLength(0);
    } 
    catch (IOException e)
    { e.printStackTrace();
    }
    getPane().clearTextFields();
  }
}
class DeleteButton extends CommandButton
{ private AddressBookPane pane;
  public DeleteButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText(DELETE);
	this.pane = pane;
  }
  public AddressBookPane getPane()
  { return pane;
  }
  public void Execute(){
	  try {
		long size = (getFile().length())/(2*RECORD_SIZE);
		long currentRecPos = getFile().getFilePointer()-(2*RECORD_SIZE);
		int currentRecNum = (int)(getFile().getFilePointer())/(2*RECORD_SIZE);
		if(size==0){
			return;
		}
		else if(size==1){
			getFile().setLength(0);
			getPane().clearTextFields();
			return;
		}
		else{
			for(int i = currentRecNum; i < size ; i++){
				readAddress(currentRecPos+(2*RECORD_SIZE));
				writeAddress(currentRecPos);
				currentRecPos += (2*RECORD_SIZE);
			}
			getFile().setLength(currentRecPos);
			readAddress(0);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
 
}  