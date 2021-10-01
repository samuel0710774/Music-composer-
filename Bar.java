import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;

public class Bar extends Group
{
	ImageView notation_h;
	ImageView notation_l;
	ImageView brace;
	ArrayList<Rectangle> rec_list = new ArrayList<Rectangle>();
	ArrayList<Line> line_list = new ArrayList<Line>();
	Rectangle horizontal;
	Rectangle vertical;
	private ImageView []flat;
	private ImageView []sharp;
	
	private int n_th;
	/*Image []flatImg = {new Image("./Image/png/flat.png"),
		new Image("./Image/png/flat.png"),
		new Image("./Image/png/flat.png"),
		new Image("./Image/png/flat.png"),
		new Image("./Image/png/flat.png"),
		new Image("./Image/png/flat.png"),
		new Image("./Image/png/flat.png"),};
	Image []sharpImg = {new Image("./Image/png/flat.png"),
			new Image("./Image/png/flat.png"),
			new Image("./Image/png/flat.png"),
			new Image("./Image/png/flat.png"),
			new Image("./Image/png/flat.png"),
			new Image("./Image/png/flat.png"),
			new Image("./Image/png/flat.png"),};*/
	
	
	public Bar(int n_th)
	{
		this.n_th = n_th;
		//horizontal rec
		horizontal = new Rectangle(1230,20);
		horizontal.setFill(Color.TRANSPARENT);
		horizontal.setVisible(false);
	      
		//ArrayList<Rectangle>-->Upper
		for(int i=0 ; i<12 ; i++)
		{	
			rec_list.add(new Rectangle(20 , 120*n_th+10*i , 1230 , 10));
			this.getChildren().add(rec_list.get(i));
			rec_list.get(i).setLayoutX(0);
			rec_list.get(i).setLayoutY(35+240*n_th);
			rec_list.get(i).setFill(Color.TRANSPARENT);
			
			rec_list.get(i).addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
				Rectangle r = (Rectangle)e.getSource();
				double x = r.getLayoutX() + r.getX();
				double y = r.getLayoutY() + r.getY()-5;
				//System.out.println(e.getY());
				horizontal.setLayoutX(x);
				horizontal.setLayoutY(y);
				horizontal.setVisible(true);
            });	
		}
		
		//ArrayList<Rectangle>-->Lower
		for(int i=12 ; i<22 ; i++)
		{	
			rec_list.add(new Rectangle(20 , 120*n_th+10*i , 1230 , 10));
			this.getChildren().add(rec_list.get(i));
			rec_list.get(i).setLayoutX(0);
			rec_list.get(i).setLayoutY(80+240*n_th);
			rec_list.get(i).setFill(Color.TRANSPARENT);
			
			rec_list.get(i).addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
				Rectangle r = (Rectangle)e.getSource();
				double x = r.getLayoutX() + r.getX();
				double y = r.getLayoutY() + r.getY()-5;
				//System.out.println(e.getY());
				horizontal.setLayoutX(x);
				horizontal.setLayoutY(y);
				horizontal.setVisible(true);
            });	
		}
		
		//Start line
		Line boarderStart = new Line(20,120*n_th,20,245+120*n_th);
		boarderStart.setLayoutX(0);
		boarderStart.setLayoutY(50+240*n_th);
		boarderStart.setStroke(Color.BLACK);
		boarderStart.setStrokeWidth(1);
		this.getChildren().add(boarderStart);
		//End Line
		Line boarderEnd = new Line(1230,120*n_th,1230,245+120*n_th);
		boarderEnd.setLayoutX(0);
		boarderEnd.setLayoutY(50+240*n_th);
		boarderEnd.setStroke(Color.BLACK);
		boarderEnd.setStrokeWidth(1);
		this.getChildren().add(boarderEnd);
		
		
		//Brace
		brace = new ImageView(new Image("./Image/png/brace.png"));
		brace.setFitWidth(30);
		brace.setFitHeight(240);
		brace.setLayoutX(0);
		brace.setLayoutY(50+360*n_th);
		this.getChildren().add(brace);
		
		//ArrayList<Line>
		for(int i =0 ; i<5 ; i++)
		{
			line_list.add(new Line(20 , 120*n_th+10*(2*i+1) , 1230 , 120*n_th+10*(2*i+1)));
			this.getChildren().add(line_list.get(i));
			line_list.get(i).setLayoutX(0);
			line_list.get(i).setLayoutY(40+240*n_th);
			line_list.get(i).setStroke(Color.BLACK);
		}
		
		//ArrayList<Line>
		for(int i =5 ; i<10 ; i++)
		{
			line_list.add(new Line(20 , 65+120*n_th+10*(2*i+1) , 1230 , 65+120*n_th+10*(2*i+1)));
			this.getChildren().add(line_list.get(i));
			line_list.get(i).setLayoutX(0);
			line_list.get(i).setLayoutY(40+240*n_th);
			line_list.get(i).setStroke(Color.BLACK);
		}
		
	    
		
		//notation
		notation_h = new ImageView(new Image("./Image/png/treble-clef.png"));
		notation_l = new ImageView(new Image("./Image/png/bass-clef.png"));
		
		notation_h.setFitWidth(70);
		notation_h.setFitHeight(100);
		notation_h.setLayoutX(10);
		notation_h.setLayoutY(50+360*n_th);
		this.getChildren().add(notation_h);
		
		notation_l.setFitWidth(60);
		notation_l.setFitHeight(60);
		notation_l.setLayoutX(20);
		notation_l.setLayoutY(220+360*n_th);
		this.getChildren().add(notation_l);
		
		this.getChildren().add(horizontal);
		
		
		flat = new ImageView[14];
		for(int i=0;i<14;i++) {	
			//System.out.println("flat");
			flat[i] = new ImageView(new Image("./Image/png/flat_4.png"));
			flat[i].setFitWidth(10);
			flat[i].setPreserveRatio(true);
			flat[i].setVisible(false);
		}
		//System.out.println("Inside if loop");
		
		flat[0].setLayoutX(75);           //B
		flat[0].setLayoutY(80+360*n_th);
		flat[1].setLayoutX(80);			//E
		flat[1].setLayoutY(50+360*n_th);
		flat[2].setLayoutX(85);			//A
		flat[2].setLayoutY(90+360*n_th);
		flat[3].setLayoutX(90);			//D
		flat[3].setLayoutY(60+360*n_th);
		flat[4].setLayoutX(95);			//G
		flat[4].setLayoutY(100+360*n_th);
		flat[5].setLayoutX(100);			//C
		flat[5].setLayoutY(70+360*n_th);
		flat[6].setLayoutX(105);			//F
		flat[6].setLayoutY(110+360*n_th);
		
		//Lower
		flat[7].setLayoutX(75);			//D
		flat[7].setLayoutY(245+360*n_th);
		flat[8].setLayoutX(80);			//G
		flat[8].setLayoutY(215+360*n_th);
		flat[9].setLayoutX(85);			//C
		flat[9].setLayoutY(255+360*n_th);
		flat[10].setLayoutX(90);			//F
		flat[10].setLayoutY(225+360*n_th);
		flat[11].setLayoutX(95);			//B
		flat[11].setLayoutY(265+360*n_th);
		flat[12].setLayoutX(100);			//E
		flat[12].setLayoutY(235+360*n_th);
		flat[13].setLayoutX(105);			//A
		flat[13].setLayoutY(275+360*n_th);
		this.getChildren().addAll(flat);
		
		sharp = new ImageView[14];
		for(int i=0;i<14;i++) {	
			//System.out.println("sharp");
			sharp[i] = new ImageView(new Image("./Image/png/sharp3.png"));
			sharp[i].setFitWidth(10);
			sharp[i].setPreserveRatio(true);
			sharp[i].setVisible(false);
		}
		//System.out.println("Inside if loop");
		
		sharp[0].setLayoutX(75);           //B
		sharp[0].setLayoutY(75+360*n_th);
		sharp[1].setLayoutX(80);			//E
		sharp[1].setLayoutY(45+360*n_th);
		sharp[2].setLayoutX(85);			//A
		sharp[2].setLayoutY(85+360*n_th);
		sharp[3].setLayoutX(90);			//D
		sharp[3].setLayoutY(55+360*n_th);
		sharp[4].setLayoutX(95);			//G
		sharp[4].setLayoutY(25+360*n_th);
		sharp[5].setLayoutX(100);			//C
		sharp[5].setLayoutY(65+360*n_th);
		sharp[6].setLayoutX(105);			//F
		sharp[6].setLayoutY(35+360*n_th);
		
		//Lower
		sharp[7].setLayoutX(75);			//D
		sharp[7].setLayoutY(240+360*n_th);
		sharp[8].setLayoutX(80);			//G
		sharp[8].setLayoutY(210+360*n_th);
		sharp[9].setLayoutX(85);			//C
		sharp[9].setLayoutY(250+360*n_th);
		sharp[10].setLayoutX(90);			//F
		sharp[10].setLayoutY(220+360*n_th);
		sharp[11].setLayoutX(95);			//B
		sharp[11].setLayoutY(260+360*n_th);
		sharp[12].setLayoutX(100);			//E
		sharp[12].setLayoutY(230+360*n_th);
		sharp[13].setLayoutX(105);			//A
		sharp[13].setLayoutY(270+360*n_th);
		this.getChildren().addAll(sharp);
	}
	
	public void setM_m(Major_minor Mm) {
		//System.out.println("setM_m");
		for(int i=0;i<14;i++) {
			flat[i].setVisible(false);
			sharp[i].setVisible(false);
		}
		if(Mm == Major_minor.aflatminor) {
			for(int i=0;i<14;i++) {
				flat[i].setVisible(true);
			}
		}else if(Mm == Major_minor.dminor) {
			flat[11].setVisible(true);
			flat[0].setVisible(true);
		}else if(Mm==Major_minor.gminor) {
			flat[11].setVisible(true);
			flat[0].setVisible(true);
			flat[1].setVisible(true);
			flat[12].setVisible(true);
		}else if(Mm==Major_minor.cminor) {
			flat[11].setVisible(true);
			flat[0].setVisible(true);
			flat[1].setVisible(true);
			flat[12].setVisible(true);
			flat[13].setVisible(true);
			flat[2].setVisible(true);
		}else if(Mm==Major_minor.fminor) {
			flat[11].setVisible(true);
			flat[0].setVisible(true);
			flat[1].setVisible(true);
			flat[12].setVisible(true);
			flat[13].setVisible(true);
			flat[2].setVisible(true);
			flat[7].setVisible(true);
			flat[3].setVisible(true);
		}
		else if(Mm==Major_minor.bflatminor) {
			flat[11].setVisible(true);
			flat[0].setVisible(true);
			flat[1].setVisible(true);
			flat[12].setVisible(true);
			flat[13].setVisible(true);
			flat[2].setVisible(true);
			flat[7].setVisible(true);
			flat[3].setVisible(true);
			flat[8].setVisible(true);
			flat[4].setVisible(true);
		}
		else if(Mm==Major_minor.eflatminor) {
			flat[11].setVisible(true);
			flat[0].setVisible(true);
			flat[1].setVisible(true);
			flat[12].setVisible(true);
			flat[13].setVisible(true);
			flat[2].setVisible(true);
			flat[7].setVisible(true);
			flat[3].setVisible(true);
			flat[8].setVisible(true);
			flat[4].setVisible(true);
			flat[8].setVisible(true);
			flat[4].setVisible(true);
			flat[9].setVisible(true);
			flat[5].setVisible(true);
		}else if(Mm==Major_minor.NON) {
			
		}else if(Mm==Major_minor.CsharpMajor) {
			for(int i=0;i<14;i++) {
				sharp[i].setVisible(true);
			}
		}else if(Mm==Major_minor.GMajor) {
			sharp[6].setVisible(true);
			sharp[10].setVisible(true);
		}else if(Mm==Major_minor.DMajor) {
			sharp[6].setVisible(true);
			sharp[10].setVisible(true);
			sharp[9].setVisible(true);
			sharp[5].setVisible(true);
		}else if(Mm==Major_minor.AMajor) {
			sharp[10].setVisible(true);
			sharp[6].setVisible(true);
			sharp[9].setVisible(true);
			sharp[5].setVisible(true);
			sharp[8].setVisible(true);
			sharp[4].setVisible(true);
		}else if(Mm==Major_minor.EMajor) {
			sharp[10].setVisible(true);
			sharp[6].setVisible(true);
			sharp[9].setVisible(true);
			sharp[5].setVisible(true);
			sharp[8].setVisible(true);
			sharp[4].setVisible(true);
			sharp[7].setVisible(true);
			sharp[3].setVisible(true);
		}else if(Mm==Major_minor.BMajor) {
			sharp[10].setVisible(true);
			sharp[6].setVisible(true);
			sharp[9].setVisible(true);
			sharp[5].setVisible(true);
			sharp[8].setVisible(true);
			sharp[4].setVisible(true);
			sharp[13].setVisible(true);
			sharp[2].setVisible(true);
			sharp[7].setVisible(true);
			sharp[3].setVisible(true);
		}else if(Mm==Major_minor.FsharpMajor) {
			sharp[10].setVisible(true);
			sharp[6].setVisible(true);
			sharp[9].setVisible(true);
			sharp[5].setVisible(true);
			sharp[8].setVisible(true);
			sharp[4].setVisible(true);
			sharp[13].setVisible(true);
			sharp[2].setVisible(true);
			sharp[7].setVisible(true);
			sharp[3].setVisible(true);
			sharp[12].setVisible(true);
			sharp[1].setVisible(true);
		}
		
		
	}
	
	private void setM_mImage(ImageView []imgView,boolean M_or_m,int n) {
		//M:true
		//m:false
		//System.out.println("setM_mImage");
		for(int i=0;i<n;i++) {
			if(M_or_m) {
				//System.out.println("sharp");
				flat[i] = new ImageView(new Image("./Image/png/sharp.png"));
			}else {
				//System.out.println("flat");
				flat[i] = new ImageView(new Image("./Image/png/flat.png"));
			}
			flat[i].setFitWidth(30);
			flat[i].setPreserveRatio(true);
		}
		
	}
	
	public double getHorizonRecX()
	{
		return horizontal.getLayoutX();
	}
	public double getHorizonRecY()
	{
		return horizontal.getLayoutY();
	}
	public double getNotationY()
	{
		return notation_h.getLayoutY();
	}
}
