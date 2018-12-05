package org.ntvru.streamingswitcher.customproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ntvru.switcher")
public class Script {
      
	private String path = "/opt/script";
	private String script = "";

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	
}
