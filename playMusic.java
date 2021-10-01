
import org.jfugue.player.Player;

public class playMusic implements Runnable{
	private String patterns;
	private Player player = new Player();
	
	
	public playMusic(String patterns){
		this.patterns = patterns;
	}
	
	public playMusic(){
		
	}
	
	public void setPatterns(String patterns){
		this.patterns = patterns;
	}
	
	@Override 
	public void run() {
		player.play(patterns);
	}
	
	public void playerStop() {
		
	}
}
