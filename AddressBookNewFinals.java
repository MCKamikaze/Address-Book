//ID: 203249354
public interface AddressBookNewFinals
{ final static int SHOW_TIME = 10000;
  final static int NUMBER_OF_OBJECTS = 3;
  final static String SINGLETON_MESSAGE = 
	"Only " +  NUMBER_OF_OBJECTS 
	 + " stages can run concurrently.\n" 
	 + "Close the running stage by clicking on x (not the red square button).";
  final static int INDEX_OF_FIRST_BUTTON_IN_BUTTON_ARRAY = 0; 
  final static String TITLE = "AddressBookNew";
  final static String STYLES_CSS = "styles.css"; 
  final static String FILE_NAME = "address.dat";
  final static String FILE_MODE = "rw";
  final static String ADD = "Add";
  final static String FIRST = "First";
  final static String NEXT = "Next";
  final static String PREVIOUS = "Previous";
  final static String LAST = "Last";
  final static String CLEAR = "Clear";
  final static String REVERSE = "Reverse";
  final static String DELETE = "Delete";
  final static String ZIP = "Zip";
  final static String NAME = "Name";
  final static String STREET = "Street"; 
  final static String CITY = "City";
  final static String STATE = "State";
  final static int NAME_SIZE = 32;
  final static int STREET_SIZE = 32;
  final static int CITY_SIZE = 20;
  final static int STATE_SIZE = 2;
  final static int ZIP_SIZE = 5;
  final static int RECORD_SIZE = 
	(NAME_SIZE + STREET_SIZE + CITY_SIZE + STATE_SIZE + ZIP_SIZE);
  final static String STYLE_COMMAND = 
	"-fx-border-color: grey;"
	+ " -fx-border-width: 1;"
	+ " -fx-border-style: solid outside ;";
}
