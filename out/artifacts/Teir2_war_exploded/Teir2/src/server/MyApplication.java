package server;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/")
/**
 * The java class declares root resource and provider classes
 * given as artifacts to the JBOSS service
 *
 * the ApplicationPath defines the base URI for all resource URIs.
 */
public class MyApplication extends Application{
    /**
     *     The method returns a non-empty collection with classes, that must be
     *     included in the published JAX-RS application
     */
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( PartyPlannerService.class );

        return h;
    }

}