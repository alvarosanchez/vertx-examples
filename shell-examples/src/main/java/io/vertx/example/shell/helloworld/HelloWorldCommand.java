package io.vertx.example.shell.helloworld;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;
import io.vertx.ext.shell.ShellService;
import io.vertx.ext.shell.ShellServiceOptions;
import io.vertx.ext.shell.command.Command;
import io.vertx.ext.shell.command.CommandBuilder;
import io.vertx.ext.shell.command.CommandProcess;
import io.vertx.ext.shell.net.TelnetOptions;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class HelloWorldCommand extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(HelloWorldCommand.class);
  }

  @Override
  public void start() throws Exception {

    Command helloWorld = CommandBuilder.command("hello-world").
        processHandler(process -> {
          process.write("hello world\n");
          process.end();
        }).build();

    ShellService service = ShellService.create(vertx, new ShellServiceOptions().setTelnetOptions(
        new TelnetOptions().setHost("localhost").setPort(3000)
    ));
    service.getCommandRegistry().registerCommand(helloWorld);
    service.start(ar -> {
      if (!ar.succeeded()) {
        ar.cause().printStackTrace();
      }
    });
  }
}
