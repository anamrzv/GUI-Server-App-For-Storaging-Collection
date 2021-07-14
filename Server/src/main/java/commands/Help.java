package commands;

import other.CollectionsKeeper;
import other.ServerResponse;
import server.CommandHandler;

import java.util.List;
import java.util.Map;

public class Help extends Command {

    private final CommandHandler ch;
    private String response = "Доступные вам команды:\n";

    public Help(CollectionsKeeper dc, CommandHandler ch) {
        super(dc);
        this.ch = ch;
    }

    public ServerResponse execute(List<String> args) {
        if (args.size() == 2) {
            Map<String, Command> commands = ch.getCommands();
            commands.values()
                    .forEach(c -> response += c.getDescription() + "\n");
            return ServerResponse.builder().message(response).command("help").build();
        } else {
            return ServerResponse.builder().error("У команды help нет аргументов. Введите команду снова.").command("help").build();

        }
    }

    public String getName() {
        return "help";
    }

    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }
}
