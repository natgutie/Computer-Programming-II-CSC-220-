package prog02;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 *
 * @author vjm
 */
public class Main {

    /**
     * Processes user's commands on a phone directory.
     *
     * @param fn The file containing the phone directory.
     * @param ui The UserInterface object to use
     *           to talk to the user.
     * @param pd The PhoneDirectory object to use
     *           to process the phone directory.
     */
    public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
        pd.loadData(fn);
        boolean changed = false;

        String[] commands = {"Add/Change Entry", "Look Up Entry", "Remove Entry", "Save Directory", "Exit"};

        String name, number, oldNumber;

        while (true) {
            int c = ui.getCommand(commands);
            switch (c) {
                case -1:
                    ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
                    break;
                case 0:
                    name = ui.getInfo("Enter the name ");

                    if (name == null)
                        break;
                    if(name.equals("") || name.length() == 0) {
                        ui.sendMessage("Blank names are not allowed");
                        break;
                    }

                    number = ui.getInfo("Enter the number ");

                    if (number == null)
                        break;
                    if(number.equals("") || number.length() == 0) {
                        ui.sendMessage("Blank numbers are not allowed");
                        break;
                    }

                    pd.addOrChangeEntry(name, number);
                    changed = true;

                    break;
                case 1:
                    name = ui.getInfo("Enter the name ");

                    if (name == null)
                        break;
                    if(name.equals("") || name.length() == 0) {
                        ui.sendMessage("Blank names are not allowed");
                        break;
                    }

                    number = pd.lookupEntry(name);
                    if(number == null)
                        ui.sendMessage(name + " is not in the directory");
                    else
                        ui.sendMessage(name + " has number " + number);
                    break;

                case 2:
                    name = ui.getInfo("Enter the name ");

                    if (name == null)
                        break;
                    if(name.equals("") || name.length() == 0) {
                        ui.sendMessage("Blank names are not allowed");
                        break;
                    }

                    number = pd.removeEntry(name);
                    if(number == null)
                        ui.sendMessage(name + " is not in the directory");
                    else {
                        ui.sendMessage("Removed entry with name " + name + " and number " + number);
                        changed = true;
                    }
                    break;

                case 3:
                    pd.save();
                    ui.sendMessage("Any changes to the directory has been saved.");
                    changed = false;

                    break;
                case 4:
                    if (changed){
                        ui.sendMessage("Are you sure you want to exit without saving? Press ok then you will be prompted to click yes or no.");

                        String [] yesOrNoPrompt = {"Yes", "No"};
                        int d = ui.getCommand(yesOrNoPrompt);

                        switch (d) {
                            case 0:
                                System.exit(0);
                                break;
                            case 1:
                                break;
                        }

                        break;
                    } else {
                        return;
                    }

            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fn = "csc220.txt";
         PhoneDirectory pd = new ArrayBasedPD();
        //PhoneDirectory pd = new SortedPD();
        //UserInterface ui = new ConsoleUI();

        UserInterface ui = new GUI("Phone Directory");
        //UserInterface ui = new TestUI("Phone Directory");
        processCommands(fn, ui, pd);
    }
}
