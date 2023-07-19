package bubble.test.ex18;

public interface MoveAble {
    public abstract void left();
    public abstract void right();
    public abstract void up();
    default public void down() {}; // default를 사용하면 인터페이스도 본체가 있는 메서드를 만들수 있다. 
    //(다중 상속이 안되는 것이 많기에) 그래서 어댑터 패턴보다는 default를 사용하는것이 좋다.
    default public void attack() {}; // 플레이어가 어택을하면 버블은 공격을 한뿐이기때문에 디폴트로
}
