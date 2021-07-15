package commands;

import other.CollectionsKeeper;
import other.ServerResponse;
import server.CommandHandler;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Help extends Command {

    private final CommandHandler commandHandler;
    private ResourceBundle bundle;

    private String response = "";

    public Help(CollectionsKeeper collectionsKeeper, CommandHandler commandHandler) {
        super(collectionsKeeper);
        this.commandHandler = commandHandler;
    }

    public ServerResponse execute(List<String> userDataAndOtherArgs) {
        if (userDataAndOtherArgs.size() == LENGTH_WITH_ONLY_USER_DATA+1) {
            bundle = ResourceBundle.getBundle("bundles.Language", Locale.forLanguageTag(userDataAndOtherArgs.get(2).replace("_","-")));
            Map<String, Command> commands = commandHandler.getCommands();
            commands.remove("show");
            commands.values()
                    .forEach(command -> response += command.getDescription(bundle) + "\n");
            return ServerResponse.builder().message(response).command("help").build();
        } else {
            return ServerResponse.builder().error("error").command("help").build();
        }
    }

    public String getName() {
        return "help";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("help description");
    }
}
