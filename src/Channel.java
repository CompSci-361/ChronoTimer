
public class Channel {
	private boolean state;
	public Channel() {
		state = false;
	}
	public void toggle() {
		state = true;
	}
	public boolean isEnabled() {
		return state;
	}
}
