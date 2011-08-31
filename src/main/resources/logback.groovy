import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender


import static ch.qos.logback.classic.Level.INFO


appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{HH:mm:ss.SSS} %level %logger{36} [%thread] - %msg%n"
  }
}

root(INFO, ["STDOUT"])