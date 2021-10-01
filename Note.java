import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class Note extends Group {
	private noteHeight noteheight;
	private int locationX;
	private int locationY;
	
	private int note_length_state;
	private String noteLength; // q, h, i, s, q., h.
	private char lift; //# or b
	private boolean lift_or_not;
	private int lift_state;
	
	private ImageView noteImage = new ImageView();
	private ImageView liftImage = new ImageView();
	private DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN,Color.YELLOW,10.0,0.8,0,0);
	
    //for lift image
    private static final String[] LiftName = {
    "./Image/png/lift.png","./Image/png/sharp.png","./Image/png/flat.png","./Image/png/natural.png"};
    private static final Image[] LiftSample = {
    		new Image(LiftName[0]),new Image(LiftName[1]),new Image(LiftName[2]),new Image(LiftName[3])};
    
 
  //for note image
    private static final String[] NoteName = {
    "./Image/png/quarter-note.png","./Image/png/quarter-note-2.png","./Image/png/half-note.png",
    "./Image/png/half-note-2.png","./Image/png/eighth-note-1.png","./Image/png/sixteenth-note.png",
    "./Image/png/whole.png"};
    private static final Image[] NoteSample = {
    		new Image(NoteName[0]),new Image(NoteName[1]),new Image(NoteName[2]),
    		new Image(NoteName[3]),new Image(NoteName[4]),new Image(NoteName[5]),
    		new Image(NoteName[6])
    };
    
    
	private String note=null;
	ExecutorService executorService = Executors.newCachedThreadPool();
	
	Note(int locationX,int locationY,int note_length_state,int lift_state){	
		this.locationX = locationX;
		this.locationY = locationY;
		this.lift_state = lift_state;
		this.note_length_state = note_length_state;
		//note_length_state
		//0: q
		//1: q.
		//2: h
		//3: h.
		//4: i
		//5: s
		setLength();
		if(lift_state==1 || lift_state==2) {
			if(lift_state==1) {
				lift='#';
			}else {
				lift='b';
			}
			lift_or_not = true;
		}else {
			lift_or_not = false;
		}		
		getnoteHeight();
		setNote();

		noteImage = new ImageView(NoteSample[note_length_state]);	
		if(lift_or_not || lift_state==3) {
			liftImage = new ImageView(LiftSample[lift_state]);
		}
		noteImage.setLayoutX(110+70*locationX);
		noteImage.setFitWidth(20);
		liftImage.setFitHeight(20);
		liftImage.setPreserveRatio(true);
		noteImage.setPreserveRatio(true);
		if(note_length_state!=6) {
			noteImage.setLayoutY(locationY-17);
		}else {
			noteImage.setLayoutY(locationY);
		}
		noteImage.setLayoutX(110+70*locationX+25);
		if(lift_state==2) {
			liftImage.setLayoutX(110+70*locationX+13);
			liftImage.setLayoutY(locationY-5);
		}else {
			liftImage.setLayoutX(110+70*locationX+10);
			liftImage.setLayoutY(locationY);
		}
		liftImage.setVisible(true);
		noteImage.setVisible(true);
		this.getChildren().add(liftImage);
		this.getChildren().add(noteImage);
	}	
	
	public void PlayNote() {
		executorService.execute(new playMusic(note));
	}
	
    public String getNote() {
    	return note;
    }
    
    public void setNote() {
    	setN();
    	if(lift_or_not) {	
    		String s1 = note.substring(0, note.length()-1);
        	String s2 = note.substring(1);
        	note = s1+lift+s2;
    	}
    	note+=noteLength;
    }
    
    public int getLocationY() {
    	return locationY;
    }
    //Key Up
    public void NoteUP() {
    	System.out.println("NoteUP");
    	if(locationY%360!=30) {
    		if(locationY%360==195) {
    			locationY-=55;
    		}else {
    			locationY-=10;
    			System.out.println("-=10");
    		}
    		note = null;
	    	
	    	getnoteHeight();
	    	setNote();
	    	PlayNote();
	    	noteImage.setLayoutY(locationY);
	    	if(lift_state==2) {
	    		liftImage.setLayoutY(locationY-5);
	    	}else {
	    		liftImage.setLayoutY(locationY);
	    	}
    	}else {
    		System.out.println("Reach the top!");
    	}
    	
    		
    }
    
    public void removeNoteEffect() {
    	noteImage.setEffect(null);
    	if(lift_or_not) {
    		liftImage.setEffect(null);
    	}
    }
    
    public void SetNoteEffect() {
    	noteImage.setEffect(dropShadow);
    	if(lift_or_not) {
    		liftImage.setEffect(dropShadow);
    	}
    }
    
    //Key DOWN
    public void NoteDown() {
    	System.out.println("NoteDown");
    	System.out.println(locationY);
    	if(locationY%360!=285) {
    		note = null;
        	if(locationY%360==140) {
        		locationY+=55;
        	}else {
        		locationY+=10;
        	}
        	
        	getnoteHeight();
        	setNote();
        	PlayNote();
        	noteImage.setLayoutY(locationY);
        	if(lift_state==2) {
        		liftImage.setLayoutY(locationY-5);
        	}else {
        		liftImage.setLayoutY(locationY);
        	}
    	}else {
    		System.out.println("reach the buttom");
    	}
    	
    }
    
    //多了升降記號
    public void setLift(int lift_state) {
    	//1:sharp #
    	//2:flat b
    	//3:back to natural    	
    	
		lift_or_not = true;
		if(lift_state==1) {   			
			lift = '#';
		}else if(lift_state==2) {
			lift = 'b';
		}else if(lift_state==3) {
			lift_or_not = false;
		}else {
			lift_or_not = false;
		}
		setNote();
    	
    	
    	//PlayNote();
    }
    
    public double getLength() {
    	if(noteLength=="q") {
    		return 1.0;
    	}else if(noteLength=="q.") {
    		return 2./3;
    	}else if(noteLength=="h") {
    		return 0.5;
    	}else if(noteLength=="h.") {
    		return 1./3;
    	}else if(noteLength=="i") {
    		return 2.0;
    	}else if(noteLength=="s") {
    		return 4.0;
    	}else {
    		return 1./4;
    	}
    }
    
    public void setLength() {
    	switch(note_length_state){
			case 0:
				noteLength = "q";
				break;
			case 1:
				noteLength = "q.";
				break;
			case 2:
				noteLength = "h";
				break;
			case 3:
				noteLength = "h.";
				break;
			case 4:
				noteLength = "i";
				break;
			case 5:
				noteLength = "s";
				break;
			case 6:
				noteLength = "w";
				break;
			default:
				break;
		}
    }
    
    public void setN() {
    	switch(noteheight) {
	    	case G5:
    			note = "G5";
    			break;
    		case F5:
    			note = "F5";
    			break;
    		case E5:
    			note = "E5";
    			break;
    		case D5:
    			note = "D5";
    			break;
    		case C5:
    			note = "C5";
    			break;
    		case B4:
    			note = "B4";
    			break;
    		case A4:
    			note = "A4";
    			break;
    		case G4:
    			note = "G4";
    			break;
    		case F4:
    			note = "F4";
    			break;
    		case E4:
    			note = "E4";
    			break;
    		case D4:
    			note = "D4";
    			break;
    		case C4:
    			note = "C4";
    			break;
    		case B3:
    			note = "B3";
    			break;
    		case A3:
    			note = "A3";
    			break;
    		case G3:
    			note = "G3";
    			break;
    		case F3:
    			note = "F3";
    			break;
    		case E3:
    			note = "E3";
    			break;
    		case D3:
    			note = "D3";
    			break;
    		case C3:
    			note = "C3";
    			break;
    		case B2:
    			note = "B2";
    			break;
    		case A2:
    			note = "A2";
    			break;
    		case G2:
    			note = "G2";
    			break;
    		default:
    			break;				
    	}
	
    }
    
    public String getnoteHeight() {
    	
    	switch(locationY%360) {
	    	case 30:
				noteheight = noteHeight.G5;
				return "G5"; 			
			case 40:
				noteheight = noteHeight.F5;
				return "F5";  			
			case 50:
				noteheight = noteHeight.E5;
				return "E5";   			
			case 60:
				noteheight = noteHeight.D5;
				return "D5";
			case 70:
				noteheight = noteHeight.C5;
				return "C5";
			case 80:
				noteheight = noteHeight.B4;
				return "B4";
			case 90:
				noteheight = noteHeight.A4;
				return "A4";			
			case 100:
				noteheight = noteHeight.G4;
				return "G4";	
			case 110:
				noteheight = noteHeight.F4; 
				return "F4"; 			
			case 120:
				noteheight = noteHeight.E4;
				return "E4";   			
			case 130:
				noteheight = noteHeight.D4;
				return "D4";  	
			case 140:
				noteheight = noteHeight.C4;
				return "C4";
			case 195:
				noteheight = noteHeight.B3;
				return "B3";
			case 205:
				noteheight = noteHeight.A3;
				return "A3";
			case 215:
				noteheight = noteHeight.G3;
				return "G3";
			case 225:
				noteheight = noteHeight.F3;
				return "F3";
			case 235:
				noteheight = noteHeight.E3;
				return "E3";
			case 245:
				noteheight = noteHeight.D3;
				return "D3";
			case 255:
				noteheight = noteHeight.C3;
				return "C3";
			case 265:
				noteheight = noteHeight.B2;
				return "B2";
			case 275:
				noteheight = noteHeight.A2;
				return "A2";
			case 285:
				noteheight = noteHeight.G2;
				return "G2";			
    		default:  
    			return "";
    	}
    }

	public int getLift_state() {
		return lift_state;
	}
    
	
}
