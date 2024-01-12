package champion;

public class DamageCounter {
    public float counter = 0;
    public void dealDamage(float damage) {
        counter += damage;
    }
    public float getDamage() {
        return counter;
    }
}
