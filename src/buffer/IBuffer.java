package buffer;

public interface IBuffer {
    public void onHit();
    public void onCast();
    public int getInput();
    public int getTriggerOn();
    public int getStacks();
}
