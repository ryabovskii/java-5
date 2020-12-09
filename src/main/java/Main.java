import pack.Injector;
import pack.SomeBean;

public class Main {

    public static void main(String[] args) {
        SomeBean sb = (SomeBean) (new Injector()).inject(new SomeBean());
        sb.foo();
    }
}
