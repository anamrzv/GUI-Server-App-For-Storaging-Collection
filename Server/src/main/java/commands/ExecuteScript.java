package commands;

import other.CollectionsKeeper;
import other.ServerResponse;
import server.CommandHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ExecuteScript extends Command {

    private final CommandHandler ch;

    public ExecuteScript(CollectionsKeeper dc, CommandHandler ch) {
        super(dc);
        this.ch = ch;
    }

    @Override
    public ServerResponse execute(List<String> userDataAndOtherArgs) {
        if (userDataAndOtherArgs.size() == 3) {
            try {
                Path path = Paths.get(userDataAndOtherArgs.get(2));
                if (Files.exists(path) && !Files.isRegularFile(path)) {
                    return ServerResponse.builder().error("script error special file").command("execute_script").build();
                } else {
                    if (collectionsKeeper.getScriptNames().size() == 0) collectionsKeeper.addScriptName(userDataAndOtherArgs.get(2));
                    String dir = path.toString();
                    try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
                        Map<String, Command> commands = ch.getCommands();
                        String line;
                        while ((line = br.readLine()) != null) {
                            line = line.trim();
                            if (line.equals("execute_script " + userDataAndOtherArgs.get(2))) {
                                return ServerResponse.builder().error("script error recursive 1").command("execute_script").build();
                            } else if (line.startsWith("execute_script")) {
                                String cmd = getCommandName(line);
                                List<String> arguments = getArguments(line);
                                if (collectionsKeeper.addScriptName(arguments.get(2))) {
                                    Command command = commands.get(cmd);
                                    command.execute(arguments);
                                } else
                                    return ServerResponse.builder().error("script error recursive 2").command("execute_script").build();
                            } else {
                                String cmd = getCommandName(line);
                                List<String> arguments = getArguments(line);
                                Command command = commands.get(cmd);
                                command.execute(arguments);
                                System.out.println();
                            }
                        }
                        collectionsKeeper.clearScriptNames();
                    } catch (FileNotFoundException e) {
                        return ServerResponse.builder().error("script error file").command("execute_script").build();
                    } catch (Exception e) {
                        return ServerResponse.builder().error("script error command").command("execute_script").build();
                    }
                }
            } catch (Exception e) {
                return ServerResponse.builder().error("script error special file").command("execute_script").build();
            }
        } else {
            return ServerResponse.builder().error("script error args").command("execute_script").build();
        }
        return ServerResponse.builder().message("script success").command("execute_script").build();
    }

    public String getCommandName(String input) {
        String[] elements = input.split(" +");
        return elements[0].toLowerCase(); //только название команды
    }

    public List<String> getArguments(String input) {
        List<String> elements = Arrays.stream(input.split(" +")).collect(Collectors.toList());
        if (elements.size() > 1) {
            return elements.stream().skip(1).collect(Collectors.toList());
        } else return null;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("execute_script description");
    }
}
