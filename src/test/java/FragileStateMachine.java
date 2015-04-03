/**
 * This state machine object must be initialized in the following order:
 * doFirstThing()
 * doSecondThing()
 * doOptionalThirdThing()
 * doForthThing()
 *
 * If the order is not followed, an exception will be thrown.  Only once this sequence has been
 * followed will it be able to do something cool. Hence why it is a fragile state machine.
 *
 * For the purpose of real worldness, imagine that our FragileStateMachine is a MediaPlayer that takes a filename,
 * opens the file, decodes the audio from the file, lets you specify a few flags, and finally lets you play back
 * an audio track.  However, you have to call all those methods in the right order otherwise the whole thing
 * blows up.
 *
 * Notice that the FragileStateMachine has no references to it's builder.  That means that we have a one
 * way relationship here.  The FragileStateMachine has no influence over the builder.  Also notice how the only way
 * to get a ready-to-use FragileStateMachine is to follow the method chain required by the builder.  No more
 * complex state transition checks needed.
 *
 */
public class FragileStateMachine {

    private void doFirstThing(String file){
        System.out.println("Opening file : " + file);
    }

    private void doSecondThing(String args){
        System.out.println("Setting arguments: "+ args);
    }

    private void doOptionalThirdThing(int flags){
        System.out.println("Setting flags: "+ flags);
    }

    private void doFourthThing(){
        System.out.println("Running magical decoding algorithm");
    }

    public void doSomeThingCool(){
        System.out.println("Something cooooooool!!!");
    }

    public static class Builder{

        private FragileStateMachine mStateMachine;

        public Builder(){
            mStateMachine = new FragileStateMachine();
        }

        /**
         * Do the first thing
         */
        public BuilderStep2 step1(String filename){
            return new BuilderStep1().step1(filename);
        }

        /**
         * The actual logic of the first step is encapsulated by the BuilderStep1 class.
         * Any data validation logic could go in here too.
         */
        public class BuilderStep1{
            public BuilderStep2 step1(String filename){
                mStateMachine.doFirstThing(filename);
                return new BuilderStep2();
            }
        }

        /**
         * Just do step2 and return a BuilderStep3
         */
        public class BuilderStep2{
            public BuilderStep3 step2(String args){
                mStateMachine.doSecondThing(args);
                return new BuilderStep3();
            }
        }

        /**
         * Because this step 3 on the FragileStateMachine is optional,
         * we implement the optional step here, and delegate the next step's implementation
         * to the BuilderStep4 class.
         */
        public class BuilderStep3{
            public BuilderStep4 optionalStep3(int flags){
                if(flags > 0){ // some mock data validation
                    mStateMachine.doOptionalThirdThing(flags);
                }
                return new BuilderStep4();
            }

            /**
             * A shortcut method to skip over the optional step which
             * delegates it's implementation to BuilderStep4.
             * @return a ready to go FragileStateMachine
             */
            public FragileStateMachine doFourthThing(){
              return new BuilderStep4().step4();
            }
        }
        public class BuilderStep4{

            /**
             * @return A ready to go FragileStateMachine.
             */
            public FragileStateMachine step4(){
                mStateMachine.doFourthThing();
                return mStateMachine; //now we are finally done with the builder.
            }
        }
    }
}
