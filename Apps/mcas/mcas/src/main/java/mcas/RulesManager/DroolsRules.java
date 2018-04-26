package mcas.RulesManager;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import mcas.Model.*;

public class DroolsRules {
	
	static String root = "src/main/resources/rules/drools/";
	
	public static void executeDroolsRules() {
		try {
			
			KieContainer kContainer = loadContainer();
			
			// drools session base on the xml configuration kmodule.xml
			KieSession kSession = kContainer.newKieSession(); // PORQUÉ SIRVE SIN EL NOMBRE "ksession-serviceRules"

			/*
			 * add the event listener in this case we'll use the DefaultAgendaEventListener,
			 * that is a implementation of AgendaEventListener
			 */

//			kSession.addEventListener(new DefaultAgendaEventListener() {
//				// this event will be executed after the rule matches with the model data
//				public void afterMatchFired(AfterMatchFiredEvent event) {
//					super.afterMatchFired(event);
//					System.out.println("siempre entra acá");
//					System.out.println(event.getMatch().getRule().getName());// prints the rule name
//				}
//			});

//			// add the model with the data that will match with the rule condition
//			kSession.insert(new Service(20, "Emergencia"));
//			kSession.insert(new Service(21, "DobleMedicacion"));

			// fire all the rules
//			kSession.fireAllRules();

		} catch (

		Throwable t) {
			t.printStackTrace();
		}
	}
	
	
	public static KieContainer loadContainer() {
		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();

		Resource resource = ks.getResources().newFileSystemResource(root + "serviceRules.drl")
				.setResourceType(ResourceType.DRL);

		kfs.write(resource);

		KieBuilder Kiebuilder = ks.newKieBuilder(kfs);

		Kiebuilder.buildAll();

		// KieContainer kContainer = ks.getKieClasspathContainer();
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());

		return kContainer;
	}

	
	
}
