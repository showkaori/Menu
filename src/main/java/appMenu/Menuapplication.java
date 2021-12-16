package appMenu;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class Menuapplication extends ResourceConfig{

	public Menuapplication() {
		packages("appMenu");
	}
}
