import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.TextField;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.jfugue.player.Player;

import javafx.scene.input.MouseEvent;
import javafx.scene.Group;

import java.awt.ScrollPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.Alert;

//Timer
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.util.Duration;
import javafx.animation.KeyFrame; 
import javafx.scene.effect.Glow;  
import javafx.scene.effect.DropShadow;  
import javafx.scene.effect.BlurType;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;
/*
//Slide Pane
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import javafx.scene.layout.VBox;*/

public class Demo2Controller
{
	@FXML private Button playButton;
    @FXML private Button newbarButton;
    @FXML private MenuButton noteButton;
    @FXML private ImageView noteImage;
    @FXML private MenuButton liftButton;
    @FXML private ImageView liftImage;
    @FXML private MenuButton restButton;
    @FXML private ImageView restImage;
    @FXML private Pane pane;
    @FXML private Button deleteButton;
    @FXML private Button saveButton;
    @FXML private Button editButton;
    @FXML private MenuButton SaveMenu;
    @FXML private TextField instrument;
    @FXML private Button pageButton;
    @FXML private MenuButton speedButton;
    @FXML private RadioMenuItem Grave;
    @FXML private RadioMenuItem Larghetto;
    @FXML private RadioMenuItem Adagio;
    @FXML private RadioMenuItem Andante;
    @FXML private RadioMenuItem Andantino;
    @FXML private RadioMenuItem Allegretto;
    @FXML private RadioMenuItem Allegro;
    @FXML private RadioMenuItem Vivace;
    @FXML private RadioMenuItem Presto;
    @FXML private RadioMenuItem Pretissimo;
    @FXML private ImageView playImage;

    @FXML private MenuButton M_m;
   
    
    private ArrayList<Bar> bar_list = new ArrayList<Bar>();
    private Button_state button_state; //Button state overall => 0:Non 1:Save 2:play 3:new_bar 4:note 5:lift 6:rest 7:delete
    private int note_state = 0;
    private int lift_state = 0;
    private int rest_state = 0;
    private int bar_number = 0;
    
    private Rectangle h = new Rectangle(1230,20);
    private Rectangle v = new Rectangle(70,280);
    private boolean note_or_rest = true;
    
    //Major/minor
    private Major_minor MmState;
    private String Mm="";
    
    //play
    private Rectangle playBar = new Rectangle(70,280);
    private int m_th=0;
    private int n_th=0;
    private String speedString="Allegro";
    private int speed=500;
    private int temp=0;
    
    private DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN,Color.YELLOW,10.0,0.8,0,0);

    
    //save the note
    List<List<Integer>> noteList = new ArrayList<List<Integer>>();
    List<List<String>> patternString = new ArrayList<List<String>>();
    private ArrayList<ArrayList<ArrayList<Note>>> note_List = new ArrayList<ArrayList<ArrayList<Note>>>();
    private ArrayList<ArrayList<ArrayList<Rest>>> rest_List = new ArrayList<ArrayList<ArrayList<Rest>>>();

    noteHeight noteH;
    
    //Play the note it clicked on
    Player player = new Player();
    ExecutorService executorService = Executors.newCachedThreadPool();
    private String playList = "";
    private playMusic p = new playMusic();
    
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
    
    @FXML
    void cacelNoteSelected(ActionEvent event) {
    	button_state = Button_state.NON;
    	setButtonEffect();
    	//noteButton.setEffect(null);
    	//noteImage.setImage(null);
    }
    
    @FXML 
    void quarternoteSelected(ActionEvent event) 
    {
    	button_state = Button_state.NOTE;
    	setButtonEffect();
    	
    	note_or_rest = true;
    	noteImage.setImage(NoteSample[0]);note_state = 0;
    	
    	if(note_or_rest&&lift_state>0) {
    		//liftButton.setEffect(dropShadow);
    	}    
    }
    @FXML 
    void quarterdotnoteSelected(ActionEvent event) 
    {
    	button_state = Button_state.NOTE;
    	setButtonEffect();
    	
    	
    	note_or_rest = true;
    	noteImage.setImage(NoteSample[1]);note_state = 1;
    	//if(note_or_rest&&lift_state>0)
    		//liftButton.setEffect(dropShadow);
    }
    @FXML 
    void halfnoteSelected(ActionEvent event) 
    {
    	button_state = Button_state.NOTE;
    	setButtonEffect();
    	
    	note_or_rest = true;
    	noteImage.setImage(NoteSample[2]);note_state = 2;
    	//if(note_or_rest&&lift_state>0)
    		//liftButton.setEffect(dropShadow);
    }
    @FXML 
    void halfdotnoteSelected(ActionEvent event) 
    {
    	button_state = Button_state.NOTE;
    	setButtonEffect();
    	
    	note_or_rest = true;
    	noteImage.setImage(NoteSample[3]);note_state = 3;
    	//if(note_or_rest&&lift_state>0)
    		//liftButton.setEffect(dropShadow);
    }
    @FXML 
    void eighthnoteSelected(ActionEvent event) 
    {
    	button_state = Button_state.NOTE;
    	setButtonEffect();
    	
    	note_or_rest = true;
    	noteImage.setImage(NoteSample[4]);note_state = 4;

    	//if(note_or_rest&&lift_state>0)
    	//	liftButton.setEffect(dropShadow);
    }
    @FXML 
    void sixteenthnoteSelected(ActionEvent event) 
    {
    	button_state = Button_state.NOTE;
    	setButtonEffect();
    	
    	note_or_rest = true;
    	noteImage.setImage(NoteSample[5]);note_state = 5;
    	//if(note_or_rest&&lift_state>0)
    	//	liftButton.setEffect(dropShadow);
    }
    @FXML 
    void wholenoteSelected(ActionEvent event) 
    {
    	button_state = Button_state.NOTE;
    	setButtonEffect();
    	
    	note_or_rest = true;
    	noteImage.setImage(NoteSample[6]);note_state = 6;
    	//if(note_or_rest&&lift_state>0)
    	//	liftButton.setEffect(dropShadow);
    }
    
    //for lift image
    private static final String[] LiftName = {
    "./Image/png/lift.png","./Image/png/sharp.png","./Image/png/flat.png","./Image/png/natural.png"};
    private static final Image[] LiftSample = {
    		new Image(LiftName[0]),new Image(LiftName[1]),new Image(LiftName[2]),new Image(LiftName[3])};
    
    

    
    @FXML 
    void noneliftSelected(ActionEvent event) 
    {
    	button_state = Button_state.LIFT;
    	lift_state = 0;
    	setButtonEffect();
    }
    @FXML 
    void sharpliftSelected(ActionEvent event) 
    {
    	button_state = Button_state.LIFT;
    	liftImage.setImage(LiftSample[1]);
    	lift_state = 1;
    	setButtonEffect();
    	//if(note_or_rest)
    		//liftButton.setEffect(dropShadow);
    }
    @FXML 
    void flatliftSelected(ActionEvent event) 
    {
    	button_state = Button_state.LIFT;
    	liftImage.setImage(LiftSample[2]);
    	lift_state = 2;
    	setButtonEffect();

    }
    @FXML 
    void naturalliftSelected(ActionEvent event) 
    {
    	button_state = Button_state.LIFT;
    	liftImage.setImage(LiftSample[3]);
    	lift_state = 3;
    	setButtonEffect();

    }
    
    //for rest image
    private static final String[] RestName = {
    "./Image/png/quarter-note-rest.png","./Image/png/half-rest.png",
    "./Image/png/eight-note-rest.png","./Image/png/whole-rest.png"};
    private static final Image[] RestSample = {
    new Image(RestName[0]),new Image(RestName[1]),new Image(RestName[2]),new Image(RestName[3])};
    
    
    @FXML
    void cacelRestSelected(ActionEvent event) {
    	button_state = Button_state.NON;
    	//restButton.setEffect(null);
    	setButtonEffect();
    	note_or_rest = true;
    	
    }
    
    @FXML 
    void quarterrestSelected(ActionEvent event) 
    {
    	button_state = Button_state.REST;
    	restImage.setImage(RestSample[0]);rest_state = 0;
    	setButtonEffect();
    	note_or_rest = false;
    }
    @FXML 
    void halfrestSelected(ActionEvent event) 
    {
    	button_state = Button_state.REST;
    	restImage.setImage(RestSample[1]);rest_state = 1;
    	setButtonEffect();

    	note_or_rest = false;
    }
    @FXML 
    void eighthrestSelected(ActionEvent event) 
    {
    	button_state = Button_state.REST;
    	restImage.setImage(RestSample[2]);rest_state = 2;
    	setButtonEffect();

    	note_or_rest = false;
    }
    @FXML 
    void wholerestSelected(ActionEvent event) 
    {
    	button_state = Button_state.REST;
    	restImage.setImage(RestSample[3]);rest_state = 3;
    	setButtonEffect();
    	note_or_rest = false;
    }
    
    //Major and minor settup
    @FXML
    void Aflatmajor_fminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.fminor;
    		Mm="-ABDE";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Amajor_fsharpminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.AMajor;
    		Mm="CFG";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Bflatmajor_gminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.gminor;
    		Mm="-BE";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Bmajor_gsharpminor(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.BMajor;
    		Mm="ACDFG";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Cflatmajor_aflatminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.aflatminor;
    		Mm="-FGABCDE";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Csharpmajor_asharpminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.CsharpMajor;
    		Mm="ABCDEFG";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Dflatmajor_bflatminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.bflatminor;
    		Mm="-GABDE";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Dmajor_hminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.DMajor;
    		Mm="CF";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Eflatmajor_cminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.cminor;
    		Mm="-ABE";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Emajor_csharpminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.EMajor;
    		Mm="CDFG";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
        	setNoteListM_m(Mm);
    	}
    	
    }

    @FXML
    void Fmajor_dminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.dminor;
    		Mm="-B";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Fsharpmajor_dsharpminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.FsharpMajor;
    		Mm="ACDEFG";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Gflatmajor_eflatminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.eflatminor;
    		Mm="-GABCDE";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }

    @FXML
    void Gmajor_eminorAction(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.GMajor;
    		Mm="F";
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		setNoteListM_m("");
    		setNoteListM_m(Mm);
    	}
    }
    
    @FXML
    void MmCancel(ActionEvent event) {
    	if(bar_number>0) {
    		MmState = Major_minor.NON;
    		for(int i=0;i<bar_number;i++) {
    			bar_list.get(i).setM_m(MmState);
    		}
    		Mm="";
    		setNoteListM_m(Mm);
    	}
    }
    

    @FXML
    void guitarAction(ActionEvent event) {
    	instrument.setEditable(false);
    	instrument.setText("GUITAR");
    }
    
    @FXML
    void pianoAction(ActionEvent event) {
    	instrument.setEditable(false);
    	instrument.setText("PIANO");
    }

    @FXML
    void violinAction(ActionEvent event) {
    	instrument.setEditable(false);
    	instrument.setText("VIOLIN");
    }
    
    ImageView noteimg = new ImageView();
    ImageView liftimg = new ImageView();
    ImageView restimg = new ImageView();
    ImageView deleteimg = new ImageView();
    @FXML
    void mouseEnteredPane(MouseEvent event) 
    {
    	System.out.println(button_state);
    	//image follow mouse
    	if(button_state == Button_state.NOTE || button_state == Button_state.REST || button_state == Button_state.DELETE) {
    		//note
        	if(note_or_rest)
        	{
        		noteimg.setFitWidth(20);
    	    	noteimg.setPreserveRatio(true);
    	    	liftimg.setFitWidth(15);
    	    	liftimg.setPreserveRatio(true);
    	    	noteimg.setImage(NoteSample[note_state]);
    	    	if(lift_state>0)
    	    		liftimg.setImage(LiftSample[lift_state]);
    	    	else
    	    		liftimg.setImage(null);
    	    	pane.getChildren().add(noteimg);
    	    	pane.getChildren().add(liftimg);
        	}
        	//rest
        	else if(!note_or_rest)
        	{
        		restimg.setFitWidth(20);
    	    	restimg.setPreserveRatio(true);
    	    	restimg.setImage(RestSample[rest_state]);
    	    	pane.getChildren().add(restimg);
        	}
        	pane.setOnMouseMoved(new EventHandler<MouseEvent>() 
    	    {
    	    	  @Override 
    	    	  public void handle(MouseEvent e) 
    	    	  {
    	    	   	  int i_th = (int)(e.getY() / 360.5);
    	    	   	  int i = getnoteHeight((int)bar_list.get(i_th).getHorizonRecY()%360);    		
    	    		  int j = (int)((e.getX()-110)/70);
    	    	   	  
    	    		  h.setLayoutX(bar_list.get(i_th).getHorizonRecX());
    	      		  h.setLayoutY(bar_list.get(i_th).getHorizonRecY());

    	      		  
    	      		  int j_th = (int)((e.getX()-110)/70);
    	      		  
    	    		  if((e.getX()-110)/70 >= 0 )
    	      		  {
    	      			  v.setLayoutX(110+70*(j_th));
    	      			  v.setLayoutY(bar_list.get(i_th).getNotationY()-20);
    	     			  v.setVisible(true);
    	    		  }
    	      		  else {v.setVisible(false);}
    	    		  
    	    		  if(button_state.equals(Button_state.DELETE)) {

    	    		  }else {
    	    			  if(note_or_rest) 
	    	    		  {
	    		    		  if(note_state<6)	{noteimg.setLayoutY(e.getY()-20);}
	    		    		  else				{noteimg.setLayoutY(e.getY()-10);}
	    		    		  noteimg.setLayoutX(e.getX()-10);
	    		    	    	  
	    		    	   	  liftimg.setLayoutX(e.getX()-20);
	    		    	   	  if(lift_state == 2)	{liftimg.setLayoutY(e.getY()-10);}
	    		    		  else					{liftimg.setLayoutY(e.getY());}
	    		    	   	  h.setVisible(true);
	    	    		  } 
	    	    		  else
	    	    		  {
	    	    			  restimg.setLayoutX(e.getX()-10);
	    		    	   	  restimg.setLayoutY(e.getY()-10);
	    		    	   	  h.setVisible(false);
	    	    		  }
    	    		  }
    	    		  
    	    	  }
    	    });
    	    
    	    //other pane listener
    	    pane.addEventFilter(MouseEvent.MOUSE_CLICKED, clickedFilter);
        	
    	}
    	
	    
    }
    
    EventHandler clickedFilter = new EventHandler<MouseEvent>() {
    	public void handle(MouseEvent e) {
    		//int temp=0;
    		int i_th = (int)(e.getY() / 360.5);
    		int i = getnoteHeight((int)bar_list.get(i_th).getHorizonRecY()%360);    		
    		int j = (int)((e.getX()-110)/70);
    		
    		if(e.getButton()==MouseButton.PRIMARY) {
    			if(button_state==Button_state.DELETE) {
        			//System.out.println("deleteButton");
        			if((noteList.get(i_th).get(j)&(int)Math.pow(2,i))==0 && (noteList.get(i_th).get(j)&(int)Math.pow(2,30))==0 ) {
        				System.out.println("No note here");
        			}else if((noteList.get(i_th).get(j)&(int)Math.pow(2,30))==0 ){
	
        				for(temp=0;temp<note_List.get(i_th).get(j).size();temp++) {
        					if(note_List.get(i_th).get(j).get(temp).getLocationY()==(int)bar_list.get(i_th).getHorizonRecY()) {
        						break;
        					}
        				}
        				
                		System.out.println("Temp="+temp);
                		if(temp==note_List.get(i_th).get(j).size() && rest_List.get(i_th).get(j).size()==0) {
                			System.out.println("No note for delete");
                			rest_List.get(i_th).get(j).get(0).setRestEffect();
                		}else {
                			if(note_List.get(i_th).get(j).size()>0) {
                				note_List.get(i_th).get(j).get(temp).SetNoteEffect();
                			}else {
                				rest_List.get(i_th).get(j).get(0).setRestEffect();;
                			}
                			
                			pane.requestFocus();
                			pane.setOnKeyPressed(event->{
    	        				System.out.println("Space pressed");
    	        				if(event.getCode()==KeyCode.SPACE) {
    	        					if((noteList.get(i_th).get(j)&(int)Math.pow(2,30))==1) {
    	        						noteList.get(i_th).set(j, 0);
    	        						pane.getChildren().remove(rest_List.get(i_th).get(j).get(0));
    	        						rest_List.get(i_th).get(j).remove(0);
    	        						
    	        					}else {
    	        						noteList.get(i_th).set(j, noteList.get(i_th).get(j) & ~(1<<i));
    	        						if(Integer.bitCount(noteList.get(i_th).get(j))==1) {
        	        						noteList.get(i_th).set(j, 0);
        	        						System.out.println("Delete the last note");
        	        					}
        	        					System.out.println(Integer.toBinaryString(noteList.get(i_th).get(j)));
        	        					pane.getChildren().remove(note_List.get(i_th).get(j).get(temp));
        	        					note_List.get(i_th).get(j).remove(temp);
    	        					}
    	        					
    	        					
    	        				 
    	        					//note_List.get(i_th).get(j).get(temp).NoteUP();
    	        				}
                			});
                		}
        				//System.out.println(noteList.get(i_th).get(j));
        				//noteList.get(i_th).set(j, noteList.get(i_th).get(j)-(int)Math.pow(2, i));
        				//setpattern(i_th,j);
        	    		//setlength(i_th,j);
        			}else {

            			rest_List.get(i_th).get(j).get(0).setRestEffect();;

            			pane.requestFocus();
            			pane.setOnKeyPressed(event->{
	        				System.out.println("Space pressed");
	        				if(event.getCode()==KeyCode.SPACE) {
        						noteList.get(i_th).set(j, 0);
        						pane.getChildren().remove(rest_List.get(i_th).get(j).get(0));
        						rest_List.get(i_th).get(j).remove(0);	
	        				}
            			});
                	}
        			
        			
    	    		
    	    		
        		}
        		if(e.getSource()==pane && button_state!=Button_state.DELETE && button_state!=Button_state.EDIT) {
        				
        			if(note_or_rest)
        	    	{
        	    		//(j,i)
        				if(noteList.get(i_th).get(j)==0) {

        					noteList.get(i_th).set(j, (int)(Math.pow(2, i)+Math.pow(2, 22+note_state)));
        					
            	    		//Note note = new Note(j,(int)bar_list.get(i_th).getHorizonRecY(),note_state,lift_state);
            	    		note_List.get(i_th).get(j).add(new Note(j,(int)bar_list.get(i_th).getHorizonRecY(),note_state,lift_state));
            	    		
            	    		//note_List.get(i_th).get(j).get(note_List.get(i_th).get(j).size()-1).setLift(i);;
            	    		
            	    		int count=0;
            	    		count = note_List.get(i_th).get(j).size();
            	    		if(MmState !=Major_minor.NON) {
            	    			note_List.get(i_th).get(j).get(count-1).setLift(setNoteM_m(note_List.get(i_th).get(j).get(count-1).getNote()));
            	    		}
            	    		pane.getChildren().add(note_List.get(i_th).get(j).get(count-1));	
            	    		executorService.execute(new playMusic(PlayNode(i_th,j)));
 				
        				}
        				else if((((noteList.get(i_th).get(j))&(int)Math.pow(2, i) )== 0) && ((noteList.get(i_th).get(j)&(int)Math.pow(2,22+note_state))>0))
        				{
        					System.out.println("Rest note clicked");
        					noteList.get(i_th).set(j, noteList.get(i_th).get(j)|(int)Math.pow(2, i));
        					//Note note = new Note(j,(int)bar_list.get(i_th).getHorizonRecY(),note_state,lift_state);
            	    		note_List.get(i_th).get(j).add(new Note(j,(int)bar_list.get(i_th).getHorizonRecY(),note_state,lift_state));
            	    		//System.out.println(PlayNode(i_th,j));
            	    		executorService.execute(new playMusic(PlayNode(i_th,j)));
            	    		int count=0;
            	    		count = note_List.get(i_th).get(j).size();
            	    		
            	    		pane.getChildren().add(note_List.get(i_th).get(j).get(count-1));
        				}
        	    		
        		    }
        			
        			
        	    	else //rest
        	    	{
        	    		//(j,i)
        				if(noteList.get(i_th).get(j)==0) {
        					//setpattern(i_th,j);
        					//setlength(i_th,j);
        					
        					//noteList.get(i_th).set(j, -1); //-1 :rest
        					noteList.get(i_th).set(j, (int)(Math.pow(2, 30)+Math.pow(2, rest_state)));
            	    		//executorService.execute(new playMusic(patternString.get(i_th).get(j)));
        					//System.out.println("Integer.toBinaryString((int)(Math.pow(2, 30)+Math.pow(2, rest_state)) = "+ Integer.toBinaryString((int)(Math.pow(2, 30)+Math.pow(2, rest_state))));
        					//System.out.println("Integer.toBinaryString((int)(Math.pow(2, 31)+Math.pow(2, rest_state)) = "+ Integer.toBinaryString((int)(Math.pow(2, 31)+Math.pow(2, rest_state))));

        					//Note note = new Note(j,(int)bar_list.get(i_th).getHorizonRecY(),note_state,lift_state);
        					rest_List.get(i_th).get(j).add(new Rest(j,i_th,rest_state));
        					int count=0;
            	    		count = rest_List.get(i_th).get(j).size();
            	    		
            	    		pane.getChildren().add(rest_List.get(i_th).get(j).get(count-1));
            	    		
            	    		/*ImageView r_u = new ImageView(restimg.getImage());
            	    		r_u.setFitWidth(20);
            		       	r_u.setPreserveRatio(true);
            		       	r_u.setLayoutX(v.getLayoutX()+25);
            		       	r_u.setLayoutY(80+360*i_th);
            		       	pane.getChildren().add(r_u);
            		       	ImageView r_d = new ImageView(restimg.getImage());
            	    		r_d.setFitWidth(20);
            		       	r_d.setPreserveRatio(true);
            		       	r_d.setLayoutX(v.getLayoutX()+25);
            		       	r_d.setLayoutY(245+360*i_th);
            		       	pane.getChildren().add(r_d);*/
        				}	
        	    	}
            		e.consume();
            	}else if(button_state==Button_state.EDIT) {
            		System.out.println("Editing");
            		for(temp=0;temp<note_List.get(i_th).get(j).size();temp++) {
    					if(note_List.get(i_th).get(j).get(temp).getLocationY()==(int)bar_list.get(i_th).getHorizonRecY()) {
    						break;
    					}else {
    						
    					}
    				}
            		System.out.println("Temp="+temp);
            		if(temp==note_List.get(i_th).get(j).size()) {
            			System.out.println("No note for edit");
            		}else {
            			pane.requestFocus();
            			pane.setOnKeyReleased(event->{
	        				System.out.println("Key pressed");
	        				if(event.getCode()==KeyCode.UP) {
	        					note_List.get(i_th).get(j).get(temp).NoteUP();
	        				}else if(event.getCode()==KeyCode.DOWN) {
	        					note_List.get(i_th).get(j).get(temp).NoteDown();
	        				}
            			});
            		}
            	}
    		}else if(e.getButton()==MouseButton.SECONDARY) {
    			
				/*for(temp=0;temp<note_List.get(i_th).get(j).size();temp++) {
					if(note_List.get(i_th).get(j).get(temp).getLocationY()==(int)bar_list.get(i_th).getHorizonRecY()) {
						break;
					}
				}
				System.out.println("Secondary pressed");
				pane.requestFocus();
				
				pane.setOnKeyPressed(event->{
    				//System.out.println("Key pressed");
    				if(event.getCode()==KeyCode.UP) {
    					note_List.get(i_th).get(j).get(temp).NoteUP();
    				}else if(event.getCode()==KeyCode.DOWN) {
    					note_List.get(i_th).get(j).get(temp).NoteDown();
    				}
    			});*/
    		}
		
    	}  	
    };
    
    
    @FXML
    void mouseExitedPane(MouseEvent event) 
    {
    	pane.getChildren().remove(noteimg);
    	pane.getChildren().remove(liftimg);
    	pane.getChildren().remove(restimg);
    	pane.removeEventFilter(MouseEvent.MOUSE_CLICKED, clickedFilter);
    }
    
    /*@FXML
    void paneKeyPressed(KeyEvent event) {
    	if(event.getCode()==KeyCode.UP) {
    		
    	}else if(event.getCode()==KeyCode.DOWN) {
    		
    	}
    }*/
    
    @FXML
    void editOnAction(ActionEvent event) {
    	button_state = Button_state.EDIT;
    	setButtonEffect();
    	System.out.println("Edit pressed");
    	
    	pane.addEventFilter(MouseEvent.MOUSE_CLICKED, clickedFilter);
    	
    }
    
    @FXML
    void saveFileSelected(ActionEvent event) throws FileNotFoundException {
    	System.out.println("saveFileSelected");
    	button_state = Button_state.FILE;
    	setButtonEffect();
    	
    	String fileName;
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("");
    	dialog.setHeaderText("Save");
    	dialog.setContentText("Please enter the file name:");

    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    		fileName = result.get();
    		try {
    			//./Image/png/whole-rest.png
    			File dir = new File("./Save");
    			dir.mkdirs();
    			File file = new File(dir,fileName+".txt");
    			if(!file.exists()) {
					file.createNewFile();
    			}
    			
    			PrintWriter pw = new PrintWriter(file);
    			
    			pw.println(Mm);
    			
    			//Save the note data by binary number
    			for(int i=0;i<noteList.size();i++) {
    				for(int j=0;j<16;j++) {
    					pw.print(noteList.get(i).get(j)+" ");
    				}
    				pw.println("");
    			}
    			
    			//Save the note data by String pattern
    			for(int i=0;i<note_List.size();i++) {
    	      		  for(int j=0;j<note_List.get(i).size();j++) {
    	      			  if(note_List.get(i).get(j).size()>1) {
    	      				  pw.print(PlayNode(i,j)+" ");
    	      			  }else if(note_List.get(i).get(j).size()==1){
    	      				  pw.print(note_List.get(i).get(j).get(0).getNote()+" ");
    	    	      	  }else if(rest_List.get(i).get(j).size()==1){
    	    	      		  pw.print(rest_List.get(i).get(j).get(0).getRest()+" ");
    	    	      	  }else{
    	      				  pw.print("_ ");
    	      			  }
    	      		  }
    	      		  pw.println("");
    	         }

    			pw.close();
    		}catch (IOException e) {
				e.printStackTrace();
			}

    	}
    	
    	
    	
    	
    }
    
    @FXML
    void newFileSelected(ActionEvent event) {
    	System.out.println("newFileSelected");
    	button_state = Button_state.FILE;
    	setButtonEffect();
    	final Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save the file first？", ButtonType.YES, ButtonType.NO); 
    	alert.setTitle("Warning"); 
    	alert.setHeaderText(""); 
    	final Optional<ButtonType> opt = alert.showAndWait();
    	final ButtonType rtn = opt.get(); 
    	if (rtn == ButtonType.YES) {
    	    try {
				this.saveFileSelected(null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} 
		pane.getChildren().clear();
		bar_list.clear();
		note_List.clear();
		noteList.clear();
		patternString.clear();
		playList="";
		bar_number=0;	
    }

    @FXML
    void loadFileSelected(ActionEvent event) {
    	System.out.println("loadFileSelected");
    	button_state = Button_state.FILE;
    	setButtonEffect();
    	
    	final Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save the file first？", ButtonType.YES, ButtonType.NO); 
    	alert.setTitle("Warning"); 
    	alert.setHeaderText(""); 
    	final Optional<ButtonType> opt = alert.showAndWait();
    	final ButtonType rtn = opt.get(); 
    	if (rtn == ButtonType.YES) {
    	    try {
				this.saveFileSelected(null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} 
		pane.getChildren().clear();
		bar_list.clear();
		note_List.clear();
		noteList.clear();
		patternString.clear();
		playList="";
		bar_number=0;	
		String text = "";
    	int counter = 0;
    	
    	FileChooser fc = new FileChooser();
    	
    	File parentDir = new File(".");
    	fc.setInitialDirectory(new File(parentDir.getAbsolutePath()+"\\Save")); 
    	fc.getExtensionFilters().addAll(new ExtensionFilter("TXT Files","*.txt"));
    	File selectedFile = fc.showOpenDialog(null);
    	
    	if(selectedFile != null) {
    		System.out.println(selectedFile.getAbsolutePath());
    		//listview.getItems().add(selectedFile.getAbsolutePath());
    		
    		try {
    			Scanner s = new Scanner(new File(selectedFile.getAbsolutePath()));
    			Mm = s.nextLine();
    			while(s.hasNextLine()) {
    				counter++;
    				//stringToNote(s.nextLine());
    				text = text.concat(s.nextLine() + "\n");
    			}
    			System.out.println(text);
    		}catch(FileNotFoundException e) {
    			System.out.println("File not found");
    		}
    	}else {
    		System.out.println("file is not valid");
    	}
    	
    	Scanner patternScanner = new Scanner(text);
    	
    	for(int i=0;i<counter/2;i++) {
    		this.newbarButtonClicked(null);
    		//System.out.println(i+patternScanner.nextLine());
    		stringToNoteList(patternScanner.nextLine(),i);
    	}
    	
    	//System.out.println(noteList);
    	for(int i=0;i<counter/2;i++) {
    		
    		//System.out.println(i+patternScanner.nextLine());
    		stringToNote(patternScanner.nextLine(),i);	
    	}
    	
    	System.out.println("Number");
    	MmToMajor_minor(Mm);
		setNoteListM_m(Mm);
    }
    
    
    
    @FXML
    void playButtonOnAction(ActionEvent event) {
 	  
    	  
    	  button_state = Button_state.PLAY;
    	  int []runtime = new int[bar_number*16];
    	  setButtonEffect();
    	  
    	  playList = "";
    	  
    	  for(int i=0;i<note_List.size();i++) {
    		  for(int j=0;j<note_List.get(i).size();j++) {
    			  if(note_List.get(i).get(j).size()>1) {
    				  patternString.get(i).set(j, PlayNode(i,j));
    				  runtime[i*16+j] = (int)(speed/note_List.get(i).get(j).get(0).getLength());
    			  }else if(note_List.get(i).get(j).size()==1){
    				  patternString.get(i).set(j, note_List.get(i).get(j).get(0).getNote());
    				  runtime[i*16+j] = (int)(speed/note_List.get(i).get(j).get(0).getLength());
    			  }else if(rest_List.get(i).get(j).size()==1) {
    				  patternString.get(i).set(j, rest_List.get(i).get(j).get(0).getRest());
    				  runtime[i*16+j] = (int)(speed/rest_List.get(i).get(j).get(0).getLength());
    			  }else {
    				  runtime[i*16+j] = 100;
    				  patternString.get(i).set(j, "_");
    			  }
    		  }
    	  }
    	  if(instrument.getText().isEmpty())
    		  playList=playList+"I[PIANO] ";
    	  else
    		  playList=playList+"I["+instrument.getText()+"] ";
    	  playList+="T["+speedString+"] ";
    	  for(int i=0;i<patternString.size();i++) {
    		  for(int j=0;j<16;j++) {
    			  if(patternString.get(i).size()==0) {
    				  playList+="_";
    			  }else{
    				  playList+=patternString.get(i).get(j);
    			  }
    			  playList+=" ";
    		  }
    	  }
    	  System.out.println(playList);
    	  System.out.println(noteList);
    	  System.out.println(patternString);
    	  
    	  
    	  int i_th = 0;
		  int j_th = 0;
		  
		  playBar.setLayoutX(110+70*(i_th));
		  playBar.setLayoutY(bar_list.get(i_th).getNotationY()-20);
		  playBar.setFill(Color.RED);
		  playBar.setOpacity(0.5);
		  
		  pane.getChildren().add(playBar);
		  
		  playImage.setImage(new Image("./Image/png/pause.png"));
		  
		  /*AnimationTimer timer = new AnimationTimer() {
			  //int velocity = 60; //used to scale the change
			  
			  //long previousTimer = System.nanoTime();
			  
			  @Override
			  public void handle(long now) {
				  //double elapsedTime = (now-previousTimer) / 1000000000.0;
				  //previousTimer = now;
				  //double scale = elapsedTime*velocity;
				  executorService.(new playMusic("C4w+E4q E4w+G3h"));
			  }execute
		  };
		  
		  timer.start();*/
		  //System.out.println("PlayList="+playList);
		  executorService.execute(new playMusic(playList));
		  //p.setPatterns(playList);
		  //executorService.execute(p);
		  //System.out.println(speed);
		  /*Timeline timeline = new Timeline();
		  
		 
		  for(int i=0;i<runtime.length;i++) {
			  System.out.print(runtime[i]+" ");
		  }
		  System.out.println("");
		  int m=0,n=0;
		  for(int i=0;i<runtime.length;i++) {
			  final int index=i;
			  
			  timeline.getKeyFrames().add(new KeyFrame(Duration.millis(runtime[i]),new EventHandler<ActionEvent>(){
				  
				  @Override
				  public void handle(ActionEvent event) {
					  //System.out.println(speed);
					  executorService.execute(new playMusic(patternString.get(n_th).get(m_th)));
					  if(m_th<15) {
						  
						  System.out.println(m_th);
						  playBar.setLayoutX(110+70*(m_th));
						  playBar.setLayoutY(bar_list.get(n_th).getNotationY()-20);
						  //executorService.execute(new playMusic(patternString.get(i_th).get(j)));
						  m_th++;
						  //j++;
					  }else {
						  //executorService.execute(new playMusic(patternString.get(i_th).get(j)));
						  playBar.setLayoutX(110+70*(m_th));
						  playBar.setLayoutY(bar_list.get(n_th).getNotationY()-20);
						  //i_th++;
						  n_th++;
						  m_th=0;
						  //j=0;
						 
						  //playBar.setLayoutX(110+70*(m_th));
						  //playBar.setLayoutY(bar_list.get(n_th).getNotationY()-20);				  
					  }
		  
					  
				  }
				  
			  }));
			  timeline.setCycleCount(1);
		  }
		  
		  */
		  Timeline timeline = new Timeline(
				  new KeyFrame(Duration.millis(speed),new EventHandler<ActionEvent>() {
					  int i_th=0,j=0;
					  @Override
					  public void handle(ActionEvent arg0) {

						  //executorService.execute(new playMusic(patternString.get(n_th).get(m_th)));
						  if(m_th<15) {
							  
							  //System.out.println(m_th);
							  playBar.setLayoutX(110+70*(m_th));
							  playBar.setLayoutY(bar_list.get(n_th).getNotationY()-20);
							  //executorService.execute(new playMusic(patternString.get(i_th).get(j)));
							  m_th++;
							  j++;
						  }else {
							  playBar.setLayoutX(110+70*(m_th));
							  playBar.setLayoutY(bar_list.get(n_th).getNotationY()-20);
							  i_th++;
							  n_th++;
							  m_th=0;
							  j=0;			  
						  }
						  
						  
					  }
				  })
			  );
			  
			  timeline.setCycleCount(bar_number*16);
			  timeline.play();
		  
		  m_th=0;
		  n_th=0;
		  pane.requestFocus();
		  pane.setOnKeyPressed(e->{			    
			    System.out.println("Key pause");
				if(e.getCode()==KeyCode.SPACE) {
					if(timeline.getStatus()==Animation.Status.RUNNING) {
						
						timeline.stop();
					//System.out.println(timeline.getStatus());
					}else if(timeline.getStatus()==Animation.Status.STOPPED) {
						timeline.play();
						//System.out.println(timeline.getStatus());
					}
					
				}
				if(e.getCode()==KeyCode.ENTER) {
					//直接結數
				}
			});
		  
		  /*playBar.layoutXProperty().addListener(new ChangeListener() {
			  @Override
			  public void changed(ObservableValue o,Object oldval,Object newVal) {
				  if(note_List.get(n_th).size()>(m_th)){
				  
					  if(note_List.get(n_th).get(m_th).size()>0) {
						  timeline.setRate(note_List.get(n_th).get(m_th).get(0).getLength());
						  //System.out.println(note_List.get(n_th).get(m_th+1).get(0).getLength());
					  }
				  
				  }
			  }
		  });*/
		  
		  
		  timeline.setOnFinished((e)->{
			  
			  pane.getChildren().remove(playBar);
			  playButton.setEffect(null);
			  playList = "";
			  playImage.setImage(new Image("./Image/png/start.png"));
			  System.out.println("finished");
		  });
	  
		  //待完成
		  //Button button = new Button("Stop");
		  //button.setOnAction(e -> timeline.stop());
		  //
    }
    
    
    
    @FXML
    void restButtonClicked(ActionEvent event)
    {
    	button_state = Button_state.REST;
  	    note_or_rest = false;
  	    System.out.println(note_or_rest);
  	    //setButtonEffect();
	  	
	  	
    	ImageView restimg = new ImageView();
    	restimg.setImage(RestSample[0]);
    	restimg.setFitWidth(30);
    	restimg.setFitHeight(30);
    	restimg.setLayoutX(100);
    	restimg.setLayoutY(100);
    	pane.getChildren().add(restimg);
    }
    @FXML
    void pageButtonOnAction(ActionEvent event) 
    {
    	JFrame frame = new JFrame();
    	//ImageIcon icon = new ImageIcon("D:\\eclipse\\6_14demo\\src\\Image\\reference.png");
    	ImageIcon icon = new ImageIcon(getClass().getResource("./Image/reference.png"));
    	JLabel label = new JLabel();
    	label.setIcon(icon);
    	frame.add(label);
    	frame.pack();
    	frame.setVisible(true);
    	instrument.setEditable(true);
    }
    @FXML
    void newbarButtonClicked(ActionEvent event) 
    {
    	
    	//Array list note initialize
    	noteList.add(new ArrayList<Integer>());
    	for(int i=0;i<16;i++) {
    		noteList.get(bar_number).add(0);
    	}
    	//Array list pattern string initialize
    	patternString.add(new ArrayList<String>());
    	for(int i=0;i<16;i++) {
    		patternString.get(bar_number).add("");
    	}
    	note_List.add(new ArrayList<ArrayList<Note>>());
    	for(int i=0;i<16;i++) {
    		note_List.get(bar_number).add(new ArrayList<Note>());
    	}
    	
    	rest_List.add(new ArrayList<ArrayList<Rest>>());
    	for(int i=0;i<16;i++) {
    		rest_List.get(bar_number).add(new ArrayList<Rest>());
    	}
    	
    	if(bar_number == 0)
    	{
    		v.setFill(Color.rgb(0,0,255,0.1));
    		v.setVisible(false);
    		pane.getChildren().add(v);
    	}
    	bar_list.add(new Bar(bar_number));
    	bar_list.get(bar_number).setM_m(MmState);
    	pane.getChildren().add(bar_list.get(bar_number));
    	if(bar_number == 0)
    	{
    		h.setFill(Color.rgb(0,0,255,0.1));
    		h.setVisible(false);
    		pane.getChildren().add(h);
    	}
    	bar_number++;
    	
    }
    
    @FXML
    void deleteOnAction(ActionEvent event) {
    	button_state = Button_state.DELETE;
    	setButtonEffect();
    	deleteimg.setImage(new Image("./Image/png/Delete.png"));
    	liftimg.setImage(null);
    	noteimg.setImage(null);
    }
    
    @FXML
    void graveSelected(ActionEvent event) {
    	speedString = "Grave";
    	speedButton.setText(speedString);
    	speed = (60/40)*1000;
    }
    
    @FXML
    void larghettoSelected(ActionEvent event) {
    	speedString = "Larghetto";
    	speedButton.setText(speedString);
    	speed = 1000*60/50;
    }
    
    @FXML
    void adagioSelected(ActionEvent event) {
    	speedString = "Adagio";
    	speedButton.setText(speedString);
    	speed = 1000*60/60;
    }

    @FXML
    void andanteSelected(ActionEvent event) {
    	speedString = "Andante";
    	speedButton.setText(speedString);
    	speed = 1000*60/70;
    }
    
    @FXML
    void andantinoSelected(ActionEvent event) {
    	speedString = "Andantino";
    	speedButton.setText(speedString);
    	speed = 1000*60/80;
    }

    @FXML
    void allegrettoSelected(ActionEvent event) {
    	speedString = "Allegretto";
    	speedButton.setText(speedString);
    	speed = 1000*60/110;
    }
    
    @FXML
    void allegroSelected(ActionEvent event) {
    	speedString = "Allegro";
    	speedButton.setText(speedString);
    	speed = 1000*60/120;
    }

    @FXML
    void vivaceSelected(ActionEvent event) {
    	speedString = "Vivace";
    	speedButton.setText(speedString);
    	speed = 1000*60/145;
    }
    @FXML
    void prestoSelected(ActionEvent event) {
    	speedString = "Presto";
    	speedButton.setText(speedString);
    	speed = 1000*60/180;
    }

    @FXML
    void pretissimoSelected(ActionEvent event) {
    	speedString = "Pretissimo";
    	speedButton.setText(speedString);
    	speed = 1000*60/220;
    }

    public void MmToMajor_minor(String Mm) {
    	switch(Mm){
    		case "-ABDE":
    			MmState = Major_minor.fminor;
    			break;
    		case "CFG":
    			MmState = Major_minor.AMajor;
    			break;
    		case "-BE":
    			MmState = Major_minor.gminor;
    			break;
    		case "ACDFG":
    			MmState = Major_minor.BMajor;
    			break;
    		case "-FGABCDE":
    			MmState = Major_minor.aflatminor;
    			break;
    		case "ABCDEFG":
    			MmState = Major_minor.CsharpMajor;
    			break;
    		case "-GABDE":
    			MmState = Major_minor.bflatminor;
    			break;
    		case "CF":
    			MmState = Major_minor.DMajor;
    			break;
    		case "-ABE":
    			MmState = Major_minor.cminor;
    			break;
    		case "CDFG":
    			MmState = Major_minor.EMajor;
    			break;
    		case "-B":
    			MmState = Major_minor.dminor;
    			break;
    		case "ACDEFG":
    			MmState = Major_minor.FsharpMajor;
    			break;
    		case "-GABCDE":
    			MmState = Major_minor.eflatminor;
    			break;
    		case "F":
    			MmState = Major_minor.GMajor;
    			break;
    		case "":
    			MmState = Major_minor.NON;
    			break;
    		default:
    			MmState = Major_minor.NON;
    			break;
    	}
    }
    
    public void setButtonEffect() {
    	//System.out.println(button_state);
    	switch(button_state) {
    	case NON:
    		playButton.setEffect(null);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(null);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(null);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(null);
    		break;
    	case SAVE:
    		playButton.setEffect(null);
    		//saveButton.setEffect(dropShadow);
    		newbarButton.setEffect(null);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(null);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(null);
    		break;
    	case PLAY:
    		playButton.setEffect(dropShadow);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(null);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(null);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(null);
    		break;
    	case BAR:
    		playButton.setEffect(null);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(dropShadow);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(null);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(null);
    		break;
    	case NOTE:
    	case LIFT:
    		if(lift_state==0) {
        		noteButton.setEffect(dropShadow);
        		liftButton.setEffect(null);
    		}else {
        		noteButton.setEffect(dropShadow);
        		liftButton.setEffect(dropShadow);      		
    		}
    		playButton.setEffect(null);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(null);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(null);
    		button_state = Button_state.NOTE;
    		break;
    	case REST:
    		playButton.setEffect(null);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(null);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(dropShadow);
    		deleteButton.setEffect(null);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(null);
    		break;
    	case DELETE:
    		playButton.setEffect(null);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(null);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(dropShadow);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(null);
    		break;
    	case EDIT:
    		playButton.setEffect(null);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(null);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(null);
    		editButton.setEffect(dropShadow);
    		SaveMenu.setEffect(null);
    	case FILE:
    		playButton.setEffect(null);
    		//saveButton.setEffect(null);
    		newbarButton.setEffect(null);
    		noteButton.setEffect(null);
    		liftButton.setEffect(null);
    		restButton.setEffect(null);
    		deleteButton.setEffect(null);
    		editButton.setEffect(null);
    		SaveMenu.setEffect(dropShadow);
		default:
			break;
    	}
    }
    
    
    //note_List.get(i_th).get(j).add(new Note(j,(int)bar_list.get(i_th).getHorizonRecY(),note_state,lift_state));
    
    void stringToNote(String Pattern,int i_th) {
    	String[] patterns = Pattern.split(" ");
    	//String[] notes = 
    	
    	for(int i=0;i<16;i++) {
    		
    		String[] notes = patterns[i].split("\\+");
    		patternString.get(i_th).set(i,patterns[i]);
    		playList+=patterns[i]+" ";
    		if(Note_or_Rest_or_NON(notes[0])==0) {
    			for(int j=0;j<notes.length;j++) {
    				//System.out.println(i+","+getLoactionY(notes[j],i_th)+","+getNoteLengthState(notes[j])+","+getNoteLiftState(notes[j]));
    				note_List.get(i_th).get(i).add(new Note(i,getLoactionY(notes[j],i_th),getNoteLengthState(notes[j]),getNoteLiftState(notes[j])));			
    				pane.getChildren().add(note_List.get(i_th).get(i).get(j));
    			}
    		}else if(Note_or_Rest_or_NON(notes[0])==1) {
    			rest_List.get(i_th).get(i).add(new Rest(i,i_th,getRestLengthState(notes[0])));
    			pane.getChildren().add(rest_List.get(i_th).get(i).get(0));
    		}else {
    			//System.out.println("notes[0]="+notes[0]);
    		}
    		
    	}
    	
    }
    
    int setNoteM_m(String note) {
    	int liftstate=0;
    	String temp = Mm;
    	if(temp=="") {
    		liftstate=0;
    	}else {
    		if(temp.contains("-")) {
    			liftstate = 2;
    			temp = temp.substring(1);
    		}else {
    			liftstate = 1;
    		}

    	}
    	for(int i=0;i<temp.length();i++) {
    		if(note.contains(Character.toString(temp.charAt(i)))) {
    			return liftstate;
    		}
    	}
    	return 0;
    }
    
    void setNoteListM_m(String Mandm) {
    	int liftstate=0;
    	
    	if(Mandm.length()==0) {
    		liftstate=0;
    	}else {
    		if(Mandm.contains("-")) {
    			liftstate = 2;
    			Mandm = Mandm.substring(1);
    		}else {
    			liftstate = 1;
    		}
    	}
    	
    	for(int i=0;i<note_List.size();i++) {
    		for(int j=0;j<note_List.get(i).size();j++) {
    			for(int k=0;k<note_List.get(i).get(j).size();k++) {
    				if(liftstate==0) {
    					if(note_List.get(i).get(j).get(k).getLift_state()==0) {
							note_List.get(i).get(j).get(k).setLift(liftstate);
						}
					}else {
						for(int m=0;m<Mandm.length();m++) {
							if(note_List.get(i).get(j).get(k).getNote().contains(Character.toString(Mandm.charAt(m)))) {
								System.out.println(note_List.get(i).get(j).get(k).getNote());
								if(note_List.get(i).get(j).get(k).getLift_state()==0) {
									note_List.get(i).get(j).get(k).setLift(liftstate);
								}
    							
    						}
						}
					}
    				
    				
    				
    			}
    		}
    	}
    }
    
    void stringToNoteList(String numbers,int i_th) {
    	String[] number = numbers.split(" ");
    	for(int i=0;i<16;i++) {
    		noteList.get(i_th).set(i,Integer.parseInt(number[i]));
    	}
    }
    
   
    
    int getLoactionY(String note,int i_th) {
    	i_th*=360;
    	int y;
    	String noteHeight;
    	if(note.contains("#")) {
    		note = note.substring(0,note.indexOf('#'))+note.substring(note.indexOf('#') + 1);
    	}else if(note.contains("b")) {
    		note = note.substring(0,note.indexOf('b'))+note.substring(note.indexOf('b') + 1);
    	}
    	noteHeight = note.substring(0,2);
    	//System.out.println("noteHeight="+noteHeight);
    	switch(noteHeight) {
	    	case "G5":
	    		return 30+i_th;
	    	case "F5":
	    		return 40+i_th;
	    	case "E5":
	    		return 50+i_th;
	    	case "D5":
	    		return 60+i_th;
	    	case "C5":
	    		return 70+i_th;
	    	case "B4":
	    		return 80+i_th;
	    	case "A4":
	    		return 90+i_th;
	    	case "G4":
	    		return 100+i_th;
	    	case "F4":
	    		return 110+i_th;
	    	case "E4":
	    		return 120+i_th;
	    	case "D4":
	    		return 130+i_th;
	    	case "C4":
	    		return 140+i_th;
	    	case "B3":
	    		return 195+i_th;
	    	case "A3":
	    		return 205+i_th;
	    	case "G3":
	    		return 215+i_th;
	    	case "F3":
	    		return 225+i_th;
	    	case "E3":
	    		return 235+i_th;
	    	case "D3":
	    		return 245+i_th;
	    	case "C3":
	    		return 255+i_th;
	    	case "B2":
	    		return 265+i_th;
	    	case "A2":
	    		return 275+i_th;
	    	case "G2":
	    		return 285+i_th;
	    	default:
	    		return 0;
    	}
    }
    
    int getRestLengthState(String rest) {
    	if(rest.contains("q")) {
    		return 0;
    	}else if(rest.contains("h")) {
    		return 1;
    	}else if(rest.contains("i")){
    		return 2;
    	}else {
    		return 3;
    	}
    }
    
    int getNoteLengthState(String note) {
    	//System.out.println("getNoteLengthState:"+note);
    	if(note.contains("q.")) {
    		return 1;
    	}else if(note.contains("h.")) {
    		return 3;
    	}else if(note.contains("q")) {
    		return 0;
    	}else if(note.contains("h")) {
    		return 2;
    	}else if(note.contains("i")) {
    		return 4;
    	}else if(note.contains("s")) {
    		return 5;
    	}else if(note.contains("w")) {
    		return 6;
    	}else {
    		return 0;
    	}
    }
    
    int getNoteLiftState(String note) {
    	//System.out.println("getNoteLiftState:"+note);
    	int i=note.indexOf('#');
    	if(i<0) {
    		i=note.indexOf('b');
    	}else {
    		return 1;
    	}
    	if(i<0) {
    		return 0;
    	}else {
    		return 2;
    	}
    }
    
    int Note_or_Rest_or_NON(String note) {
    	//0: note
    	//1: rest
    	//2: NON
    	if(note.contains("R")) {
    		return 1;
    	}else if(note.contains("_")) {
    		return 2;
    	}else {
    		return 0;
    	}
    }
    
    public int getnoteHeight(int YPos) {
    	
    	switch(YPos) {
	    	case 30:
				noteH = noteHeight.G5;
				return 22; 			
			case 40:
				noteH = noteHeight.F5;
				return 21;  			
			case 50:
				noteH = noteHeight.E5;
				return 20;   			
			case 60:
				noteH = noteHeight.D5;
				return 19;
			case 70:
				noteH = noteHeight.C5;
				return 18;
			case 80:
				noteH = noteHeight.B4;
				return 17;
			case 90:
				noteH = noteHeight.A4;
				return 16;			
			case 100:
				noteH = noteHeight.G4;
				return 15;	
			case 110:
				noteH = noteHeight.F4; 
				return 14; 			
			case 120:
				noteH = noteHeight.E4;
				return 13;   			
			case 130:
				noteH = noteHeight.D4;
				return 12;  	
			case 140:
				noteH = noteHeight.C4;
				return 10;
			case 195:
				noteH = noteHeight.B3;
				return 9;
			case 205:
				noteH = noteHeight.A3;
				return 8;
			case 215:
				noteH = noteHeight.G3;
				return 7;
			case 225:
				noteH = noteHeight.F3;
				return 6;
			case 235:
				noteH = noteHeight.E3;
				return 5;
			case 245:
				noteH = noteHeight.D3;
				return 4;
			case 255:
				noteH = noteHeight.C3;
				return 3;
			case 265:
				noteH = noteHeight.B2;
				return 2;
			case 275:
				noteH = noteHeight.A2;
				return 1;
			case 285:
				noteH = noteHeight.G2;
				return 0; 			
    		default:  
    			return 0;
    	}
    	//bar_list.get(i_th).getNotationY()-10
    }
    
    public String PlayNode(int i_th,int j) {
    	String pattern  = "";
    	
    	for(int m=0;m<note_List.get(i_th).get(j).size();m++) {
    		pattern+=note_List.get(i_th).get(j).get(m).getNote();
    		if(m!=note_List.get(i_th).get(j).size()-1) {
    			pattern+="+";
    		}
    	}
    	return pattern;
    }
    
    
    
}
