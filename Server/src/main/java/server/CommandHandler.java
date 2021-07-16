package server;

import commands.*;
import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    private final String commandName;
    private final List<String> commandArgs;
    private final Person person;

    private final Map<String, Command> commands = new HashMap<>();
    private final CollectionsKeeper collectionsKeeper;

    private DataBaseManager manager = null;

    public CommandHandler(String commandName, List<String> commandArgs, CollectionsKeeper collectionsKeeper) {
        this.collectionsKeeper = collectionsKeeper;
        this.commandArgs = commandArgs;
        this.commandName = commandName;
        this.person = null;
        fullCommandCollection();
    }

    public CommandHandler(String commandName, Person newPerson, List<String> commandArgs, CollectionsKeeper collectionsKeeper) {
        this.collectionsKeeper = collectionsKeeper;
        this.commandArgs = commandArgs;
        this.commandName = commandName;
        this.person = newPerson;
        fullCommandCollection();
    }

    private void fullCommandCollection() {
        Command c = new AddIfMax(collectionsKeeper);
        commands.put("add_if_max", c);
        c = new AddIfMin(collectionsKeeper);
        commands.put("add_if_min", c);
        c = new Clear(collectionsKeeper);
        commands.put("clear", c);
        c = new CountLessPass(collectionsKeeper);
        commands.put("count_less_than_passport_id", c);
        c = new ExecuteScript(collectionsKeeper, this);
        commands.put("execute_script", c);
        c = new Head(collectionsKeeper);
        commands.put("head", c);
        c = new Help(collectionsKeeper, this);
        commands.put("help", c);
        c = new Info(collectionsKeeper);
        commands.put("info", c);
        c = new RemoveByID(collectionsKeeper);
        commands.put("remove_by_id", c);
        c = new RemoveByPass(collectionsKeeper);
        commands.put("remove_all_by_passport_id", c);
        c = new Show(collectionsKeeper);
        commands.put("show", c);
        c = new SumOfWeight(collectionsKeeper);
        commands.put("sum_of_weight", c);
        c = new SimpleAdd(collectionsKeeper);
        commands.put("add", c);
        c = new Update(collectionsKeeper);
        commands.put("update", c);
    }

    private ServerResponse run() {
        Command c;
        switch (commandName) {
            case "register":
                return manager.tryToAddUserToDB(commandArgs);
            case "login":
                return manager.checkUserInDB(commandArgs);
            case "add_if_max":
                c = commands.get("add_if_max");
                c.setPerson(person);
                return c.execute(manager, commandArgs);
            case "add_if_min":
                c = commands.get("add_if_min");
                c.setPerson(person);
                return c.execute(manager, commandArgs);
            case "remove_all_by_passport_id":
                c = commands.get("remove_all_by_passport_id");
                return c.execute(manager, commandArgs);
            case "clear":
                c = commands.get("clear");
                return c.execute(manager, commandArgs);
            case "count_less_than_passport_id":
                c = commands.get("count_less_than_passport_id");
                return c.execute(commandArgs);
            case "execute_script":
                c = commands.get("execute_script");
                return c.execute(commandArgs);
            case "head":
                c = commands.get("head");
                return c.execute(commandArgs);
            case "help":
                c = commands.get("help");
                return c.execute(commandArgs);
            case "info":
                c = commands.get("info");
                return c.execute(commandArgs);
            case "remove_by_id":
                c = commands.get("remove_by_id");
                return c.execute(manager, commandArgs);
            case "show":
                c = commands.get("show");
                return c.execute(commandArgs);
            case "sum_of_weight":
                c = commands.get("sum_of_weight");
                return c.execute(commandArgs);
            case "update":
                c = commands.get("update");
                c.setPerson(person);
                return c.execute(manager, commandArgs);
            case "add":
                c = commands.get("add");
                c.setPerson(person);
                return c.execute(manager, commandArgs);
            default:
                return ServerResponse.builder().error("Команды не существует.").build();
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public ServerResponse setRun() {
        return run();
    }

    public void setManager(DataBaseManager manager) {
        this.manager = manager;
    }

}