import com.rk.fsp.VerEntity;
import com.rk.fsp.Verifier;
import com.rk.fsp.annotations.RequireSwitchingToFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by roman on 6/1/15.
 */
//@Configuration
//@EnableAspectJAutoProxy
public class FrameSwitcherApp {

    private ApplicationContext context;

    private static FrameSwitcherApp frameSwitcherApp;

    private FrameSwitcherApp(){
        context =  new ClassPathXmlApplicationContext("frame-switcher.xml");
    }

    public static void init(){
        if(frameSwitcherApp == null){
            frameSwitcherApp = new FrameSwitcherApp();
        }
        //TODO: initialize spring context

        // TODO: try additional initialization, not trough xml
    }

    public static void main(String[] args) {
        FrameSwitcherApp.init();
//        frameSwitcherApp.context.getBean("verifier", Verifier.class).foo().bar();
        new VerEntity().bar();
//        new Verifier().foo();

    }
}
