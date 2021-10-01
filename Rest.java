import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Rest extends Group{
	private int locationX;
    private int locationY;
    
    private int rest_length_state;
    private String restLength; //q, h, i, w
    
    private ImageView restImageUpper = new ImageView();
    private ImageView restImageLower = new ImageView();
    
    //for rest image
    private static final String[] RestName = {
    "./Image/png/quarter-note-rest.png","./Image/png/half-rest.png",
    "./Image/png/eight-note-rest.png","./Image/png/whole-rest.png"};
    private static final Image[] RestSample = {
    new Image(RestName[0]),new Image(RestName[1]),new Image(RestName[2]),new Image(RestName[3])};
    
    private DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN,Color.YELLOW,10.0,0.8,0,0);
    
    private String rest="";
    ExecutorService executorSurvice = Executors.newCachedThreadPool();
    
    Rest(int locationX,int i_th,int rest_length_state){
    	this.locationX = locationX;
    	this.locationY = locationY;
    	this.rest_length_state = rest_length_state;
    	setLength();
    	setRest();
    	restImageUpper=new ImageView(RestSample[rest_length_state]);
    	restImageLower=new ImageView(RestSample[rest_length_state]);
    	restImageUpper.setFitWidth(20);
    	restImageUpper.setPreserveRatio(true);
    	restImageLower.setFitWidth(20);
    	restImageLower.setPreserveRatio(true);
    	restImageUpper.setLayoutX(110+70*locationX+25);
    	restImageUpper.setLayoutY(80+360*i_th);
    	restImageLower.setLayoutX(110+70*locationX+25);
    	restImageLower.setLayoutY(245+360*i_th);
    	restImageUpper.setVisible(true);
    	restImageLower.setVisible(true);
    	this.getChildren().add(restImageLower);
    	this.getChildren().add(restImageUpper);
    	System.out.println("New rest ");
    }
    
    public String getRest() {
    	return rest;
    }
    
    public void setRest() {
    	rest="R"+restLength;
    }
    
    public void setLength() {
    	switch(rest_length_state){
			case 0:
				restLength = "q";
				break;
			case 1:
				restLength = "h";
				break;
			case 2:
				restLength = "i";
				break;
			case 3:
				restLength = "w";
				break;
			default:
				break;
		}
    }
    
    public double getLength() {
    	if(restLength=="q") {
    		return 1.0;
    	}else if(restLength=="h") {
    		return 0.5;
    	}else if(restLength=="i") {
    		return 2.0;
    	}else {
    		return 1./4;
    		//noteLength==w
    	}
    }
    public void setRestEffect() {
    	restImageLower.setEffect(dropShadow);
    	restImageUpper.setEffect(dropShadow);
    }
    
    
}
