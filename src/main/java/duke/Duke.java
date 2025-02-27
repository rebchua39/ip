package duke;

import duke.command.CommandType;
import duke.command.CommandHandler;
import duke.command.Parser;
import duke.storage.Storage;
import duke.ui.Ui;
import duke.tasklist.TaskList;

import java.io.FileNotFoundException;

public class Duke {

    private TaskList tasks = new TaskList();
    private Storage storage;
    private Ui ui;
    private Parser parser;
    private CommandHandler commandHandler;

    public Duke() {
        ui = new Ui();
        storage = new Storage();
        ui.printWelcomeMessage();
        try {
            storage.getTasksFromFile(tasks.getTasks());
        } catch (FileNotFoundException e) {
            ui.printFileNotFoundMessage();
        }
    }

    public void run(){
        parser = new Parser();
        commandHandler = new CommandHandler();
        CommandType command = CommandType.DEFAULT;
        while (!command.equals(CommandType.EXIT)) {
            String input = ui.getInput();
            command = parser.parseCommand(input);
            commandHandler.handleCommand(command, input, tasks, storage);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
