package teamHarambe;

import java.io.IOException;

/**
 * Created by Daylon on 10/21/2016.
 */
public class MethodProvider {

   public static boolean checkForSetup() throws IOException
    {
        //CHECK IF DATABASE HAS BEEN SETUP
        //RETURN TRUE IF IT HAS BEEN SETUP
        //ELSE RETURN FALSE
        //Check if DB Exists
        Client.toServer.println("Does_DB_Exist");
        String exists = Client.fromServer.readLine();
        return (exists.equals("true"));
    }
}
