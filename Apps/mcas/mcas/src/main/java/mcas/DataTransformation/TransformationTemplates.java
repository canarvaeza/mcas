package mcas.DataTransformation;

import java.util.HashMap;
import java.util.Map;

public class TransformationTemplates {
	
	final static String mcas_template = "@prefix rr: <http://www.w3.org/ns/r2rml#>.\r\n" + 
		"@prefix  rml: <http://semweb.mmlab.be/ns/rml#> .\r\n" + 
		"@prefix crml:       <http://semweb.mmlab.be/ns/crml#> .\r\n" + 
		"@prefix ql: <http://semweb.mmlab.be/ns/ql#> .\r\n" + 
		"@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\r\n" + 
		"@base <http://example.com> .\r\n" + 
		"\r\n" + 
		"## --------------- DEPENDENCIES ----------- ###\r\n" + 
		"\r\n" + 
		"### ----- MCONTEXT ----- ###\r\n" + 
		"@prefix mcontext: <http://purl.org/m-context/ontologies/mContext#>.\r\n" + 
		"#@prefix alzheimer: <http://purl.org/m-context/ontologies/alzheimer#>.\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"### ------ SENSOR -------- ###\r\n" + 
		"@prefix sosa: <http://www.w3.org/ns/sosa/>.\r\n" + 
		"\r\n" + 
		"### ------ LOCATION -------- ###\r\n" + 
		"@prefix wgs84_pos: <http://www.w3.org/2003/01/geo/wgs84_pos#>.\r\n" + 
		"#@prefix schema: <http://schema.org/>.\r\n" + 
		"@prefix gn: <http://www.geonames.org/ontology#>.\r\n" + 
		"@prefix location: <http://purl.org/m-context/ontologies/location#> .\r\n" + 
		"\r\n" + 
		"### ------ MULTIMEDIA -------- ###\r\n" + 
		"@prefix multimedia: <http://purl.org/m-context/ontologies/multimedia#> .\r\n" + 
		"@prefix ma: <http://www.w3.org/ns/ma-ont#>.\r\n" + 
		"\r\n" + 
		"### ----- ACTIVITY ----- ###\r\n" + 
		"@prefix activity: <http://purl.org/m-context/ontologies/activity#>.\r\n" + 
		"\r\n" + 
		"### ----- TIME ----- ###\r\n" + 
		"@prefix time: <http://purl.org/m-context/ontologies/time#>.\r\n" + 
		"\r\n" + 
		"## ------------------------------------------###\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"## --------------- MAPPINGS --------------- ##\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"### ----- ACTIVITY ----- ###\r\n" + 
		"\r\n" + 
		"<#ActivityMapping>\r\n" + 
		"    rml:logicalSource [\r\n" + 
		"        rml:source \"<*input_json_file*>\";\r\n" + 
		"        rml:referenceFormulation ql:JSONPath;\r\n" + 
		"        rml:iterator \"$.Activity[*]\"\r\n" + 
		"    ];\r\n" + 
		"\r\n" + 
		"    rr:subjectMap [\r\n" + 
		"        rr:template \"http://{Id}\";\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"        rr:predicate rdf:type;\r\n" + 
		"        rr:objectMap\r\n" + 
		"        [\r\n" + 
		"            rr:template \"http://{Type}\";\r\n" + 
		"        ]\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    ## ----- BASIC ATRIBUTES ----- ##\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"        rr:predicate time:hasBeginningTime;\r\n" + 
		"        rr:objectMap\r\n" + 
		"        [\r\n" + 
		"            rml:reference \"Time.Beginning\";\r\n" + 
		"            rr:datatype xsd:dateTime\r\n" + 
		"        ]\r\n" + 
		"    ],[\r\n" + 
		"        rr:predicate time:hasEndingTime;\r\n" + 
		"        rr:objectMap\r\n" + 
		"        [\r\n" + 
		"            rml:reference \"Time.Ending\";\r\n" + 
		"            rr:datatype xsd:dateTime\r\n" + 
		"        ]\r\n" + 
		"    ];\r\n" + 
		"\r\n" + 
		"    ## ----- EXTERNAL ATRIBUTES ----- ##\r\n" + 
		"\r\n" + 
		"     rr:predicateObjectMap [\r\n" + 
		"        rr:predicate mcontext:hasActor;\r\n" + 
		"        rr:objectMap\r\n" + 
		"        [\r\n" + 
		"            rr:template \"http://{Person[*].Id}\";\r\n" + 
		"        ]\r\n" + 
		"    ],[\r\n" + 
		"        rr:predicate mcontext:isDescribedBy;\r\n" + 
		"        rr:objectMap\r\n" + 
		"        [\r\n" + 
		"            rr:template \"http://{Descriptor[*].Id}\";\r\n" + 
		"        ]\r\n" + 
		"    ].\r\n" + 
		"\r\n" + 
		"## ------ SSN ------ ###\r\n" + 
		"\r\n" + 
		"<#ObservationMapping>\r\n" + 
		"    \r\n" + 
		"    rml:logicalSource [\r\n" + 
		"		rml:source \"<*input_json_file*>\";\r\n" + 
		"	    rml:referenceFormulation ql:JSONPath;\r\n" + 
		"	    rml:iterator \"$.Ssn[*].Observation[*]\"\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    #define la uri en el sistema\r\n" + 
		"    rr:subjectMap [\r\n" + 
		"    	rr:template \"http://{Id}\";\r\n" + 
		"    	rr:class sosa:Observation\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"    	rr:predicate sosa:madeBySensor;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:template \"http://{Sensor}\"\r\n" + 
		"    	]\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    #VERIFICAR CÓMO TRATAR EL NÚMERO PORQUE NO ME SALE EL TIPO DE DATO\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"    	rr:predicate sosa:hasSimpleResult;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rml:reference \"Value\";\r\n" + 
		"    		rr:datatype xsd:decimal\r\n" + 
		"    	]\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"    	rr:predicate sosa:resultTime;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rml:reference \"Time\";\r\n" + 
		"    		rr:datatype xsd:dateTime\r\n" + 
		"    	]\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"	    rr:predicate mcontext:describe;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:template \"http://{RecEntities[*].Activity}\";\r\n" + 
		"    	]\r\n" + 
		"    ];\r\n" + 
		"\r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"        rr:predicate gn:locatedIn;\r\n" + 
		"        rr:objectMap\r\n" + 
		"        [\r\n" + 
		"        \r\n" + 
		"            rr:template \"http://{Location}\";            \r\n" + 
		"        ] \r\n" + 
		"    ].\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"### ----- MULTIMEDIA ----- ###\r\n" + 
		"\r\n" + 
		"<#MultimediaMapping>\r\n" + 
		"	rml:logicalSource [\r\n" + 
		"		rml:source \"<*input_json_file*>\";\r\n" + 
		"	    rml:referenceFormulation ql:JSONPath;\r\n" + 
		"	    rml:iterator \"$.Multimedia[*]\"\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    #define la uri en el sistema\r\n" + 
		"    rr:subjectMap [\r\n" + 
		"    	rr:template \"http://{Id}\";\r\n" + 
		"    ];\r\n" + 
		" \r\n" + 
		"     ## ----- BASIC ATRIB ----- ##       \r\n" + 
		"        \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"    	rr:predicate rdf:type;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:template \"http://{Type}\";\r\n" + 
		"    	]\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"    	rr:predicate ma:hasFormat;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:template \"http://{Format}\";\r\n" + 
		"\r\n" + 
		"			#rr:parentTriplesMap <#SensorMapping>;\r\n" + 
		"		] \r\n" + 
		"	],[\r\n" + 
		"    	rr:predicate multimedia:creationDate;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rml:reference \"CreationTime\";\r\n" + 
		"    		rr:datatype xsd:dateTime\r\n" + 
		"    	]\r\n" + 
		"    ],[\r\n" + 
		"    	rr:predicate ma:hasMultimediaDescriptor;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:parentTriplesMap <#DescriptorMapping>;\r\n" + 
		"    	]\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    ## ----- EXTERNAL ATRIB ----- ##\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"	    rr:predicate mcontext:describe;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:template \"http://{RecEntities[*].Activity}\";\r\n" + 
		"    	]\r\n" + 
		"    ],[\r\n" + 
		"    	rr:predicate gn:locatedIn;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:template \"http://{Location}\";			\r\n" + 
		"		] \r\n" + 
		"	].\r\n" + 
		"    \r\n" + 
		"    \r\n" + 
		"<#DescriptorMapping>\r\n" + 
		"	rml:logicalSource [\r\n" + 
		"		rml:source \"<*input_json_file*>\";\r\n" + 
		"	    rml:referenceFormulation ql:JSONPath;\r\n" + 
		"	    rml:iterator \"$.Multimedia[*].Descriptor\"\r\n" + 
		"	];\r\n" + 
		"\r\n" + 
		"	rr:subjectMap [ \r\n" + 
		"	  rr:template \"http://{Id}\"\r\n" + 
		"	];\r\n" + 
		"	\r\n" + 
		"	rr:predicateObjectMap [ \r\n" + 
		"		rr:predicate multimedia:descriptionText;\r\n" + 
		"		rr:objectMap\r\n" + 
		"		[\r\n" + 
		"			rml:reference \"Content\";\r\n" + 
		"			rr:datatype xsd:string;\r\n" + 
		"		] \r\n" + 
		"	].\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"### ----- LOCATION ----- ###\r\n" + 
		"<#LocationMapping>\r\n" + 
		"	rml:logicalSource [\r\n" + 
		"		rml:source \"<*input_json_file*>\";\r\n" + 
		"	    rml:referenceFormulation ql:JSONPath;\r\n" + 
		"	    rml:iterator \"$.Location[*]\"\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    #define la uri en el sistema\r\n" + 
		"    rr:subjectMap [\r\n" + 
		"    	rr:template \"http://{Id}\";\r\n" + 
		"    ];\r\n" + 
		"    \r\n" + 
		"    rr:predicateObjectMap [\r\n" + 
		"    	rr:predicate rdf:type;\r\n" + 
		"    	rr:objectMap\r\n" + 
		"    	[\r\n" + 
		"    		rr:template \"http://{Type}\";\r\n" + 
		"    	]\r\n" + 
		"    ];\r\n" + 
		"        \r\n" + 
		"	rr:predicateObjectMap \r\n" + 
		"  		[\r\n" + 
		"		rr:predicate wgs84_pos:lat;\r\n" + 
		"		rr:objectMap \r\n" + 
		"    	[\r\n" + 
		"      		rml:reference \"Coordinates.latitude\" \r\n" + 
		"    	]\r\n" + 
		"  	], [\r\n" + 
		"    	rr:predicate wgs84_pos:long;\r\n" + 
		"    	rr:objectMap \r\n" + 
		"    	[\r\n" + 
		"      	rml:reference \"Coordinates.longitude\" \r\n" + 
		"    	]\r\n" + 
		"  	].\r\n" + 
		"";
	
	public static final Map<String, String> transformTemplates = new HashMap<String, String>(){
		{
			put("mcas", mcas_template);
		}
	};
}
