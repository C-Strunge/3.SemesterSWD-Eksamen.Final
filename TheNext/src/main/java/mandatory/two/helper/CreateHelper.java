package mandatory.two.helper;
import mandatory.two.model.User;
import java.util.ArrayList;

public class CreateHelper {

    public static boolean checkIfEmailNotExists(ArrayList<User> userArrayList){
        if (userArrayList.size() >= 1){
            return false;
        } else {
            return true;
        }
    }

}
