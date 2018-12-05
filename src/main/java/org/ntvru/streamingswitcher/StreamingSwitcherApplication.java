package org.ntvru.streamingswitcher;

import org.ntvru.streamingswitcher.customproperties.Script;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Script.class)
public class StreamingSwitcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingSwitcherApplication.class, args);
	}
}
