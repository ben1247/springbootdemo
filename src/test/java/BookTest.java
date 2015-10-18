import impl.ThinkingJava;
import interfaces.IBook;

/**
 * Component:
 * Description:
 * Date: 15/10/15
 *
 * @author yue.zhang
 */
public class BookTest {

    public static void main(String [] args){

        IBook book = new ThinkingJava(){
            @Override
            public void printBookName() {
                System.out.println("thinking java2");
            }
        };
        book.printBookName();

    }
}
