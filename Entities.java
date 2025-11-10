public class Entities {
    protected String name;
    protected double x, y;
    protected double hp;
    protected double speed;
    protected int size;
    protected double maxHP;
    protected double attackDmg;

    public Entities(String name, double x, double y, double hp, int size, double maxHP, double attackDmg){
        this.name = name;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.size = size;
        this.maxHP = maxHP;
        this.attackDmg = attackDmg;
    }

    public double getHp() {
        return hp;
    }

    public double getSpeed() {
        return speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void move(double x, double y){
        this.x = x * speed;
        this.y = y * speed;
    }

    public void takeDamage(double dmg){
        hp -= dmg;
    }
    public void attack(Entities target){
        target.takeDamage(attackDmg);
    }

    @Override
    public String toString() {
        return "Entities{" +
                "name=" + name +
                ", x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", speed=" + speed +
                ", size=" + size +
                '}';
    }
}

