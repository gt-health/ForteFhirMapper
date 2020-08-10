package edu.gatech.Mapping.Util;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;

public class FHIRCMSToVRDRUtil {
	// Any 2 codes that share 1 coding is deemed equal
	public static boolean codeableConceptsEqual(CodeableConcept c1, CodeableConcept c2) {
		for(Coding coding1:c1.getCoding()) {
			for(Coding coding2:c2.getCoding()) {
				if(codingEquals(coding1, coding2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Codings that have the same system and code are deemed equal
	public static boolean codingEquals(Coding c1, Coding c2) {
		if(c1.getCode().equals(c2.getCode()) && c1.getSystem().equals(c2.getSystem())) {
			return true;
		}
		return false;
	}
}
