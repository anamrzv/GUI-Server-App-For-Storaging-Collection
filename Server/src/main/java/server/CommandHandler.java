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
    private final CollectionsKeeper ck;

    private DataBaseManager manager = null;

    public CommandHandler(String commandName, List<String> commandArgs, CollectionsKeeper collectionsKeeper) {
        ck = collectionsKeeper;
        this.commandArgs = commandArgs;
        this.commandName = commandName;
        this.person = null;
        createCommandCollection();
    }

    public CommandHandler(String commandName, Person newPerson, List<String> commandArgs, CollectionsKeeper collectionsKeeper) {
        ck = collectionsKeeper;
        this.commandArgs = commandArgs;
        this.commandName = commandName;
        this.person = newPerson;
        createCommandCollection();
    }

    private void createCommandCollection() {
        Command c = new AddIfMax(ck);
        commands.put("add_if_max", c);
        c = new AddIfMin(ck);
        commands.put("add_if_min", c);
        c = new Clear(ck);
        commands.put("clear", c);
        c = new CountLessPass(ck);
        commands.put("count_less_than_passport_id", c);
        c = new ExecuteScript(ck, this);
        commands.put("execute_script", c);
        c = new Head(ck);
        commands.put("head", c);
        c = new Help(ck, this);
        commands.put("help", c);
        c = new Info(ck);
        commands.put("info", c);
        c = new RemoveByID(ck);
        commands.put("remove_by_id", c);
        c = new RemoveByPass(ck);
        commands.put("remove_all_by_passport_id", c);
        c = new Show(ck);
        commands.put("show", c);
        c = new SumOfWeight(ck);
        commands.put("sum_of_weight", c);
        c = new SimpleAdd(ck);
        commands.put("add", c);
        c = new Update(ck);
        commands.put("update", c);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    private ServerResponse run() {
        Command c;
        switch (commandName) {
            case "register":
                return manager.addUserToDB(commandArgs);
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

    public ServerResponse setRun() {
        return run();
    }

    public void setManager(DataBaseManager manager) {
        this.manager = manager;
    }

}