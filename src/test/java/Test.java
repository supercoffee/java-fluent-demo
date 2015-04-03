/**
 * Created by ben on 4/3/15.
 */
public class Test {

    public static void main(String[] args) {
        FragileStateMachine stateMachine = new FragileStateMachine
                .Builder()
                .step1("myfile") // specify a file
                .step2("-f") //supply some arguments
                .optionalStep3(1) // set some flags
                .step4(); // do some internal stuff

        // now we have a ready-to-go state machine.
        stateMachine.doSomeThingCool();

    }
}
