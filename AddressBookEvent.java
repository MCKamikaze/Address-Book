//ID: 203249354
interface AddressBookEvent
{ enum eventType
  { FIRST(true),  ADD(true), NEXT(true), PREVIOUS(true), 
	LAST(true), CLEAR(true), DELETE(true); 
    private boolean doEvent;
    eventType(boolean doEvent)
    { this.doEvent = doEvent;
    }
    boolean getDoEvent()
    {  return doEvent;
    }
  }  
}