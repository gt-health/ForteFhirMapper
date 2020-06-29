package edu.gatech.Mapping.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Address.AddressUse;
import org.hl7.fhir.r4.model.Age;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.DocumentReference.DocumentReferenceContentComponent;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.ListResource.ListEntryComponent;
import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationComponentComponent;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.StringType;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.parser.IParser;
import edu.gatech.Mapping.Util.OpenMDIToVRDRUtil;
import edu.gatech.OpenMDI.model.OpenMDIInputFields;
import edu.gatech.VRDR.context.VRDRFhirContext;
import edu.gatech.VRDR.model.AutopsyPerformedIndicator;
import edu.gatech.VRDR.model.CauseOfDeathCondition;
import edu.gatech.VRDR.model.CauseOfDeathPathway;
import edu.gatech.VRDR.model.Certifier;
import edu.gatech.VRDR.model.DeathDate;
import edu.gatech.VRDR.model.DeathLocation;
import edu.gatech.VRDR.model.Decedent;
import edu.gatech.VRDR.model.DecedentAge;
import edu.gatech.VRDR.model.DecedentEmploymentHistory;
import edu.gatech.VRDR.model.ExaminerContacted;
import edu.gatech.VRDR.model.InjuryIncident;
import edu.gatech.VRDR.model.InjuryLocation;
import edu.gatech.VRDR.model.MannerOfDeath;
import edu.gatech.VRDR.model.util.InjuryIncidentUtil;

@Service
public class OpenMDIToVRDRService {
	
	public String convertToVRDRString(OpenMDIInputFields inputFields) throws ParseException {
		Bundle fullBundle = convertToVRDR(inputFields);
		IParser parser = new VRDRFhirContext().getCtx().newJsonParser();
		String returnString = parser.encodeResourceToString(fullBundle);
		return returnString;
	}
	
	public Bundle convertToVRDR(OpenMDIInputFields inputFields) throws ParseException {
		Bundle returnBundle = new Bundle();
		returnBundle.setType(BundleType.BATCH);
		// Handle Decedent
		Stream<String> decedentFields = Stream.of(inputFields.FIRSTNAME,inputFields.MIDNAME,inputFields.LASTNAME
				,inputFields.AGE,inputFields.AGEUNIT,inputFields.RACE,inputFields.GENDER
				,inputFields.ETHNICITY,inputFields.BIRTHDATE,inputFields.SSNUMBER
				,inputFields.MARITAL,inputFields.POSSIBLEID,inputFields.RESSTREET
				,inputFields.RESCITY,inputFields.RESCOUNTY,inputFields.RESSTATE
				,inputFields.RESZIP,inputFields.RESNAME,inputFields.LKAWHERE,inputFields.HOSPNAME
				,inputFields.CASEID);
		Decedent decedentResource = null;
		Reference decedentReference = null;
		if(!decedentFields.allMatch(x -> x == null || x.isEmpty())) {
			decedentResource = createDecedent(inputFields);
			decedentReference = new Reference(decedentResource.getId());
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, decedentResource);
		}
		// Handle DecedentAge
		DecedentAge decedentAge = null;
		Stream<String> ageFields = Stream.of(inputFields.AGE,inputFields.AGEUNIT, inputFields.PRNDATE, inputFields.PRNTIME);
		if(!ageFields.allMatch(x -> x == null || x.isEmpty())) {
			decedentAge = createDecedentAge(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, decedentAge);
		}
		// Handle Employment History
		Stream<String> employmentHistoryFields = Stream.of(inputFields.JOBTITLE);
		DecedentEmploymentHistory decedentEmploymentHistory = null;
		if(!employmentHistoryFields.allMatch(x -> x == null || x.isEmpty())) {
			decedentEmploymentHistory = createDecedentEmploymentHistory(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, decedentEmploymentHistory);
		}
		// Handle Cause Of Death Pathway
		Stream<String> causeOfDeathFields = Stream.of(inputFields.CAUSEA, inputFields.CAUSEB, inputFields.CAUSEC,
				inputFields.CAUSED,inputFields.OSCOND, inputFields.DURATIONA, inputFields.DURATIONB,
				inputFields.DURATIONC, inputFields.DURATIOND);
		if(!causeOfDeathFields.allMatch(x -> x == null || x.isEmpty())) {
			CauseOfDeathPathway causeOfDeathPathway = createCauseOfDeathPathway(inputFields, returnBundle, decedentReference, null);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, causeOfDeathPathway);
		}
		// Handle Injury Location
		Stream<String> injuryLocFields = Stream.of(inputFields.CINJSTREET,inputFields.CINJCITY,inputFields.CINJCOUNTY
				,inputFields.CINJSTATE,inputFields.CINJZIP);
		InjuryLocation injuryLocation = null;
		if(!injuryLocFields.allMatch(x -> x == null || x.isEmpty())) {
			injuryLocation = createInjuryLocation(inputFields);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, injuryLocation);
		}
		// Handle Injury Incident
		Stream<String> injuryIncidentFields = Stream.of(inputFields.CHOWNINJURY, inputFields.ATWORK,
				inputFields.JOBRELATED, inputFields.EVENTDATE, inputFields.EVENTTIME, inputFields.CIDATEFLAG);
		if(!injuryIncidentFields.allMatch(x -> x == null || x.isEmpty())) {
			InjuryIncident injuryIncident = createInjuryIncident(inputFields, decedentReference, injuryLocation);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, injuryIncident);
		}
		// Handle Manner Of Death
		Stream<String> mannerFields = Stream.of(inputFields.MANNER);
		if(!mannerFields.allMatch(x -> x == null || x.isEmpty())) {
			MannerOfDeath manner = createMannerOfDeath(inputFields, decedentReference, null);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, manner);
		}
		// Handle CaseNotes
		if(inputFields.CASENOTES != null && !inputFields.CASENOTES.isEmpty()) {
			for(String caseNote: inputFields.CASENOTES.split(";")) {
				DocumentReference caseNoteResource = createCaseNote(caseNote,decedentReference);
				OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, caseNoteResource);
			}
		}
		// Handle ExaminerContacted
		Stream<String> examinerContactedFields = Stream.of(inputFields.REPORTDATE, inputFields.REPORTTIME);
		if(!examinerContactedFields.allMatch(x -> x == null || x.isEmpty())) {
			ExaminerContacted examinerContacted = createExaminerContacted(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, examinerContacted);
		}
		// Handle Found Observation
		Stream<String> foundFields = Stream.of(inputFields.FOUNDDATE, inputFields.FOUNDTIME);
		if(!foundFields.allMatch(x -> x == null || x.isEmpty())) {
			Observation found = createFoundObs(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, found);
		}
		// Handle Death Location
		DeathLocation deathLocation = null;
		Stream<String> deathLocFields = Stream.of(inputFields.DEATHPLACE, inputFields.EVENTPLACE
				,inputFields.FOUNDADDR_STREET,inputFields.FOUNDADDR_CITY,inputFields.FOUNDADDR_COUNTY,
				inputFields.FOUNDADDR_STATE,inputFields.FOUNDADDR_ZIP,inputFields.PRNPLACE,inputFields.PRNSTREET,
				inputFields.PRNCITY,inputFields.PRNCOUNTY,inputFields.PRNSTATE,inputFields.PRNZIP,
				inputFields.SCENEADDR_STREET,inputFields.SCENEADDR_CITY,inputFields.SCENEADDR_COUNTY,
				inputFields.SCENEADDR_STATE,inputFields.SCENEADDR_ZIP);
		if(!deathLocFields.allMatch(x -> x == null || x.isEmpty())) {
			deathLocation = createDeathLocation(inputFields);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, deathLocation);
		}
		// Handle Death Date
		Stream<String> deathDateFields = Stream.of(inputFields.PRNDATE, inputFields.PRNTIME, inputFields.CDEATHFLAG,
				inputFields.CDEATHDATE);
		if(!deathDateFields.allMatch(x -> x == null || x.isEmpty())) {
			DeathDate deathDate = createDeathDate(inputFields, decedentReference, deathLocation);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, deathDate);
		}
		// Handle Date Examined Observation
		Stream<String> dateExaminedFields = Stream.of(inputFields.EXAMDATE);
		if(!dateExaminedFields.allMatch(x -> x == null || x.isEmpty())) {
			Observation dateExamined = createDateExaminedObs(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, dateExamined);
		}
		// Handle Last Known Alive Observation
		Stream<String> lkaFields = Stream.of(inputFields.LKADATE, inputFields.LKATIME);
		if(!lkaFields.allMatch(x -> x == null || x.isEmpty())) {
			Observation lastKnownAlive = createLKAObs(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, lastKnownAlive);
		}
		// Handle Case Year
		Stream<String> caseYearFields = Stream.of(inputFields.CASEYEAR);
		if(!caseYearFields.allMatch(x -> x == null || x.isEmpty())) {
			Observation caseYear = createCaseYearObs(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, caseYear);
		}
		// Handle Hospital DateTime
		Stream<String> hospDTFields = Stream.of(inputFields.ATHOSPDATE, inputFields.ATHOSPTIME);
		if(!hospDTFields.allMatch(x -> x == null || x.isEmpty())) {
			Observation caseYear = createCaseYearObs(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, caseYear);
		}
		// Handle Surgery
		Stream<String> surgeryFields = Stream.of(inputFields.SURGERY,inputFields.SURGDATE);
		if(!surgeryFields.allMatch(x -> x == null || x.isEmpty())) {
			Procedure surgery = createSurgeryProc(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, surgery);
		}
		// Handle Autopsy
		Stream<String> autopsyFields = Stream.of(inputFields.CAUTOPSY,inputFields.AUTOPUSED);
		if(!autopsyFields.allMatch(x -> x == null || x.isEmpty())) {
			AutopsyPerformedIndicator autopsy = createAutopsy(inputFields, decedentReference);
			OpenMDIToVRDRUtil.addResourceToBundle(returnBundle, autopsy);
		}
		return returnBundle;
	}
	
	private Decedent createDecedent(OpenMDIInputFields inputFields) throws ParseException {
		Decedent returnDecedent = new Decedent();
		Stream<String> caseIdFields = Stream.of(inputFields.SYSTEMID,inputFields.CASEID);
		if(!caseIdFields.allMatch(x -> x == null || x.isEmpty())) {
			Identifier identifier = new Identifier().setSystem(inputFields.SYSTEMID);
			identifier.setValue(inputFields.CASEID);
			identifier.setType(new CodeableConcept().addCoding(new Coding().setCode("1000007").setSystem("urn:mdi:temporary:code-caseNumber").setDisplay("Case Number")));
			returnDecedent.addIdentifier(identifier);
		}
		Stream<String> nameFields = Stream.of(inputFields.FIRSTNAME,inputFields.LASTNAME,inputFields.MIDNAME,inputFields.POSSIBLEID);
		if(!nameFields.allMatch(x -> x == null || x.isEmpty())) {
			HumanName name = new HumanName();
			name.addGiven(inputFields.FIRSTNAME);
			name.setFamily(inputFields.LASTNAME);
			name.addGiven(inputFields.MIDNAME);
			returnDecedent.addName(name);
		}
		if(inputFields.RACE != null && !inputFields.RACE.isEmpty()) {
			if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.RACE, "White")) {
				returnDecedent.setRace("2106-3", "", "White");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.RACE, "Hawaiian") || OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.RACE, "Pacific")) {
				returnDecedent.setRace("2076-8", "", "Native Hawaiian or Other Pacific Islander");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.RACE, "Asian")) {
				returnDecedent.setRace("2028-9", "", "Asian");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.RACE, "Indian") || OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.RACE, "Native")) {
				returnDecedent.setRace("1002-5", "", "American Indian or Alaska Native");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.RACE, "Black")) {
				returnDecedent.setRace("2054-5", "", "Black");
			}
		}
		if(inputFields.ETHNICITY != null && !inputFields.ETHNICITY.isEmpty()) {
			if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.ETHNICITY, "Not Hispanic")) {
				returnDecedent.setEthnicity("2186-5", "", "Not Hispanic or Latino");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.ETHNICITY, "Hispanic") || OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.ETHNICITY, "Cuban") || OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.ETHNICITY, "Latino")) {
				returnDecedent.setEthnicity("2135-2", "", "Hispanic or Latino");
			}
		}
		if(inputFields.GENDER != null && !inputFields.GENDER.isEmpty()) {
			if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.GENDER, "Female")) {
				returnDecedent.setGender(AdministrativeGender.FEMALE);
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.GENDER, "Male")) {
				returnDecedent.setGender(AdministrativeGender.MALE);
			}
			else{
				returnDecedent.setGender(AdministrativeGender.UNKNOWN);
			}
		}
		if(inputFields.BIRTHDATE != null && !inputFields.BIRTHDATE.isEmpty()) {
			Date birthDate = OpenMDIToVRDRUtil.parseDate(inputFields.BIRTHDATE);
			returnDecedent.setBirthDate(birthDate);
		}
		if(inputFields.SSNUMBER != null && !inputFields.SSNUMBER.isEmpty()) {
			Identifier identifier = new Identifier().setSystem("http://hl7.org/fhir/sid/us-ssn")
					.setValue(inputFields.SSNUMBER);
			identifier.setType(new CodeableConcept().addCoding(new Coding().setCode("SS")
					.setSystem("http://terminology.hl7.org/CodeSystem/v2-0203").setDisplay("Social Security number")));
			returnDecedent.addIdentifier(identifier);
		}
		if(inputFields.MARITAL != null && !inputFields.MARITAL.isEmpty()) {
			Coding martialCoding = new Coding();
			martialCoding.setSystem("http://terminology.hl7.org/CodeSystem/v3-MaritalStatus");
			if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Annul")) {
				martialCoding.setCode("A");
				martialCoding.setDisplay("Annulled");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Divorce")) {
				martialCoding.setCode("D");
				martialCoding.setDisplay("Divorced");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Interlocut")) {
				martialCoding.setCode("I");
				martialCoding.setDisplay("Interlocutory");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Interlocut")) {
				martialCoding.setCode("I");
				martialCoding.setDisplay("Interlocutory");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Never Polygamous")) {
				martialCoding.setCode("P");
				martialCoding.setDisplay("Polygamous");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Never Married") || OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "No")) {
				martialCoding.setCode("U");
				martialCoding.setDisplay("unmarried");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Domestic Partner")) {
				martialCoding.setCode("T");
				martialCoding.setDisplay("Domestic partner");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Widow")) {
				martialCoding.setCode("W");
				martialCoding.setDisplay("Widowed");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MARITAL, "Married")) {
				martialCoding.setCode("M");
				martialCoding.setDisplay("Married");
			}
			else {
				martialCoding.setSystem("http://terminology.hl7.org/CodeSystem/v3-NullFlavor");
				martialCoding.setCode("UNK");
				martialCoding.setDisplay("Unknown");
			}
		}
		Address residentAddress = OpenMDIToVRDRUtil.createAddress(inputFields.RESSTREET,
				inputFields.RESCITY, inputFields.RESCOUNTY, inputFields.RESSTATE, inputFields.RESZIP);
		if(inputFields.RESNAME != null && !inputFields.RESNAME.isEmpty()) {
			Extension resNameExt = new Extension();
			resNameExt.setUrl("urn:oid:2.16.840.1.113883.11.20.9.49");
			Extension resNameTextExt = new Extension();
			resNameTextExt.setUrl("Text");
			resNameTextExt.setValue(new StringType(inputFields.RESNAME));
			resNameExt.addExtension(resNameTextExt);
			residentAddress.addExtension(resNameTextExt);
		}
		residentAddress.setUse(AddressUse.HOME);
		returnDecedent.addAddress(residentAddress);
		if(inputFields.LKAWHERE != null && !inputFields.LKAWHERE.isEmpty()) {
			Extension lkaExt = new Extension();
			lkaExt.setUrl("urn:mdi:temporary:code:last-known-to-be-alive-or-okay-place");
			lkaExt.setValue(new StringType(inputFields.LKAWHERE));
			returnDecedent.addExtension(lkaExt);
		}
		if(inputFields.HOSPNAME != null && !inputFields.HOSPNAME.isEmpty()) {
			Extension lkaExt = new Extension();
			lkaExt.setUrl("urn:mdi:temporary:code:hospital-name-decedent-was-first-taken");
			lkaExt.setValue(new StringType(inputFields.HOSPNAME));
			returnDecedent.addExtension(lkaExt);
		}
		if(inputFields.CASEID != null && !inputFields.CASEID.isEmpty()) {
			Identifier caseId = new Identifier();
			caseId.setSystem("urn:mdi:temporary:code-caseNumber");
			caseId.setValueElement(new StringType(inputFields.CASEID));
		}
		return returnDecedent;
	}
	
	private DecedentAge createDecedentAge(OpenMDIInputFields inputFields, Reference decedentReference) {
		DecedentAge returnObservation = new DecedentAge();
		returnObservation.setSubject(decedentReference);
		Quantity ageQuantity = new Quantity();
		ageQuantity.setValue(new BigDecimal(inputFields.AGE)); //TODO: Check valid decimal
		ageQuantity.setUnit(inputFields.AGEUNIT); //TODO: Check to make sure it's in system
		ageQuantity.setSystem("http://unitsofmeasure.org");
		ageQuantity.setCode(OpenMDIToVRDRUtil.convertUnitOfMeasureStringToCode(inputFields.AGEUNIT));
		returnObservation.setValue(ageQuantity);
		return returnObservation;
	}
	
	private DecedentEmploymentHistory createDecedentEmploymentHistory(OpenMDIInputFields inputFields, Reference decedentReference) {
		DecedentEmploymentHistory returnEmploymentHistory = new DecedentEmploymentHistory();
		returnEmploymentHistory.setSubject(decedentReference);
		CodeableConcept usualOccupation = new CodeableConcept();
		usualOccupation.setText(inputFields.JOBTITLE);
		returnEmploymentHistory.addUsualOccupation(usualOccupation);
		return returnEmploymentHistory;
	}
	
	private InjuryIncident createInjuryIncident(OpenMDIInputFields inputFields, Reference decedentReference, Location injuryLocation) throws ParseException {
		InjuryIncident returnIncident = new InjuryIncident();
		returnIncident.setSubject(decedentReference);
		returnIncident.addPatientLocationExtension(injuryLocation);
		if(inputFields.ATWORK !=  null && !inputFields.ATWORK.isEmpty()) {
			boolean atWork = OpenMDIToVRDRUtil.parseBoolean(inputFields.ATWORK);
			returnIncident.addInjuredAtWorkBooleanComponent(OpenMDIToVRDRUtil.parseBooleanAndCreateCode(inputFields.ATWORK));
			if(inputFields.JOBRELATED !=  null && !inputFields.JOBRELATED.isEmpty()) {
				//Search for the atWork component we just made
				for (ObservationComponentComponent component: returnIncident.getComponent()) {
					if(component.getCode().equalsShallow(InjuryIncidentUtil.componentInjuryAtWorkCode)) {
						Extension jobRelatedExtension = new Extension();
						jobRelatedExtension.setUrl("urn:mdi:temporary:code:constitute-osha-injury-at-work");
						BooleanType value = new BooleanType(OpenMDIToVRDRUtil.parseBoolean(inputFields.JOBRELATED));
						jobRelatedExtension.setValue(value);
						component.addModifierExtension(jobRelatedExtension);
					}
				}
			}
		}
		if(inputFields.EVENTDATE != null && !inputFields.EVENTDATE.isEmpty()) {
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.EVENTDATE);
			if(inputFields.EVENTTIME != null && !inputFields.EVENTTIME.isEmpty()) {
				OpenMDIToVRDRUtil.addTimeToDate(reportDate, inputFields.EVENTTIME);
			}
			returnIncident.setEffective(new DateTimeType(reportDate));
		}
		if(inputFields.CIDATEFLAG != null && !inputFields.CIDATEFLAG.isEmpty()) {
			Extension qualificationExtension = new Extension();
			qualificationExtension.setUrl("urn:mdi:temporary:code:qualifiction-of-injury-date");
			qualificationExtension.setValue(new StringType(inputFields.CIDATEFLAG));
			returnIncident.addExtension(qualificationExtension);
		}
		if(inputFields.CHOWNINJURY !=  null && !inputFields.CHOWNINJURY.isEmpty()) {
			returnIncident.setValue(new StringType(inputFields.CHOWNINJURY));
		}
		
		return returnIncident;
	}
	
	private CauseOfDeathPathway createCauseOfDeathPathway(OpenMDIInputFields inputFields, Bundle bundle, Reference decedentReference, Certifier certifier) {
		CauseOfDeathPathway returnCoDPathway = new CauseOfDeathPathway();
		returnCoDPathway.setSubject(decedentReference);
		if (certifier != null) {
			Reference certifierReference = new Reference(certifier);
			returnCoDPathway.setSource(certifierReference);
		}
		List<String> causes = new ArrayList<String>(Arrays.asList(inputFields.CAUSEA,inputFields.CAUSEB,inputFields.CAUSEC,inputFields.CAUSED));
		String[] otherCauses = inputFields.OSCOND.split(";");
		List<String> listArrayOtherCauses = Arrays.asList(otherCauses);
		causes.addAll(listArrayOtherCauses);
		List<String> durations = Arrays.asList(inputFields.DURATIONA,inputFields.DURATIONB,inputFields.DURATIONC,inputFields.DURATIOND);
		for(int i = 0; i < causes.size(); i++) {
			String cause = causes.get(i);
			String duration = (i < durations.size()) ? durations.get(i) : "";
			if(cause != null && !cause.isEmpty()) {
				CauseOfDeathCondition causeOfDeathCondition = new CauseOfDeathCondition();
				if(decedentReference != null) {
					causeOfDeathCondition.setSubject(decedentReference);
				}
				if(certifier != null) {
					causeOfDeathCondition.setCertifier(certifier);
				}
				causeOfDeathCondition.setCode(new CodeableConcept().setText(cause));
				if(!duration.isEmpty()) {
					Age durationAge = OpenMDIToVRDRUtil.parseAge(duration);
					if(durationAge != null) {
						causeOfDeathCondition.setOnset(durationAge);
					}
					else {
						causeOfDeathCondition.setOnset(new StringType(duration));
					}
				}
				OpenMDIToVRDRUtil.addResourceToBundle(bundle, causeOfDeathCondition);
				returnCoDPathway.addEntry(new ListEntryComponent().setItem(new Reference(causeOfDeathCondition.getId())));
			}
		}
		return returnCoDPathway;
	}
	
	private MannerOfDeath createMannerOfDeath(OpenMDIInputFields inputFields, Reference decedentReference, Reference certifierReference) {
		MannerOfDeath manner = new MannerOfDeath();
		manner.addPerformer(certifierReference);
		Coding mannerCoding = new Coding();
		if(inputFields.MANNER != null && !inputFields.MANNER.isEmpty()) {
			mannerCoding.setSystem("urn:mdi:temporary:code-MannerTypeVS");
			if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MANNER, "Homicide")) {
				mannerCoding.setCode("H");
				mannerCoding.setDisplay("Homicide");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MANNER, "Suicide")) {
				mannerCoding.setCode("S");
				mannerCoding.setDisplay("Suicide");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MANNER, "Accident")) {
				mannerCoding.setCode("A");
				mannerCoding.setDisplay("Accident");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.MANNER, "Natural")) {
				mannerCoding.setCode("N");
				mannerCoding.setDisplay("Natural");
			}
			else {
				mannerCoding.setCode("U");
				mannerCoding.setDisplay("Unknown");
			}
		}
		manner.setCode(new CodeableConcept().addCoding(mannerCoding));
		manner.setSubject(decedentReference);
		return manner;
	}
	
	private DocumentReference createCaseNote(String caseNoteString, Reference decedentReference) {
		DocumentReference caseNote = new DocumentReference();
		caseNote.setType(new CodeableConcept().
				addCoding(new Coding("http://loinc.org","47046-8","Summary of death note")));
		caseNote.setSubject(decedentReference);
		caseNote.setDate(new Date());
		DocumentReferenceContentComponent contentComponent = new DocumentReferenceContentComponent();
		Attachment attachment = new Attachment();
		attachment.setContentType("text/plain");
		attachment.setLanguage("en-US");
		attachment.setData(caseNoteString.getBytes());
		contentComponent.setAttachment(attachment);
		caseNote.addContent(contentComponent);
		return caseNote;
	}
	
	private ExaminerContacted createExaminerContacted(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		ExaminerContacted examinerContacted = new ExaminerContacted(true);
		examinerContacted.setSubject(decedentReference);
		if(inputFields.REPORTDATE != null && !inputFields.REPORTDATE.isEmpty()) {
			ObservationComponentComponent component = new ObservationComponentComponent();
			component.setCode(new CodeableConcept().addCoding(new Coding(
					"urn:oid:2.16.840.1.113883.6.96", "399651003", "Report of Date")));
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.REPORTDATE);
			if(inputFields.REPORTTIME != null && !inputFields.REPORTTIME.isEmpty()) {
				OpenMDIToVRDRUtil.addTimeToDate(reportDate, inputFields.REPORTTIME);
			}
			component.setValue(new DateTimeType(reportDate));
			examinerContacted.addComponent(component);
		}
		return examinerContacted;
	}
	
	private Observation createFoundObs(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		Observation returnObs = new Observation();
		returnObs.setStatus(ObservationStatus.FINAL);
		returnObs.setSubject(decedentReference);
		returnObs.setCode(new CodeableConcept().addCoding(new Coding(
				"urn:mdi:temporary:code", "1000001", "Date and Time found dead, unconcious and in distress")));
		if(inputFields.FOUNDDATE != null && !inputFields.FOUNDDATE.isEmpty()) {
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.FOUNDDATE);
			if(inputFields.FOUNDTIME != null && !inputFields.FOUNDTIME.isEmpty()) {
				OpenMDIToVRDRUtil.addTimeToDate(reportDate, inputFields.FOUNDTIME);
			}
			returnObs.setValue(new DateTimeType(reportDate));
		}
		return returnObs;
	}
	
	private DeathDate createDeathDate(OpenMDIInputFields inputFields, Reference decedentReference, Location location) throws ParseException {
		DeathDate returnDeathDate = new DeathDate();
		returnDeathDate.setSubject(decedentReference);
		returnDeathDate.addPatientLocationExtension(location);
		//TODO: Add loction reference
		if(inputFields.PRNDATE != null && !inputFields.PRNDATE.isEmpty()) {
			Date prnDate = OpenMDIToVRDRUtil.parseDate(inputFields.PRNDATE);
			if(inputFields.PRNTIME != null && !inputFields.PRNTIME.isEmpty()) {
				OpenMDIToVRDRUtil.addTimeToDate(prnDate, inputFields.PRNTIME);
			}
			returnDeathDate.addDatePronouncedDead(new DateTimeType(prnDate));
		}
		if(inputFields.CDEATHFLAG != null && !inputFields.CDEATHFLAG.isEmpty()) {
			Extension qualificationExtension = new Extension();
			qualificationExtension.setUrl("urn:mdi:temporary:code:qualifiction-of-death-date");
			qualificationExtension.setValue(new StringType(inputFields.CDEATHFLAG));
			returnDeathDate.addExtension(qualificationExtension);
		}
		if(inputFields.CDEATHDATE != null && !inputFields.CDEATHDATE.isEmpty()) {
			Extension isCertifiedExt = new Extension();
			isCertifiedExt.setUrl("urn:mdi:temporary:code:is-certified");
			isCertifiedExt.setValue(new BooleanType(OpenMDIToVRDRUtil.parseBoolean(inputFields.CDEATHDATE)));
		}
		return returnDeathDate;
	}
	
	private Observation createDateExaminedObs(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		Observation returnObs = new Observation();
		returnObs.setStatus(ObservationStatus.FINAL);
		returnObs.setSubject(decedentReference);
		returnObs.setCode(new CodeableConcept().addCoding(new Coding(
				"urn:mdi:temporary:code", "1000003", "Date of examination or case review")));
		if(inputFields.EXAMDATE != null && !inputFields.EXAMDATE.isEmpty()) {
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.EXAMDATE);
			returnObs.setValue(new DateTimeType(reportDate));
		}
		return returnObs;
	}
	
	private Observation createLKAObs(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		Observation returnObs = new Observation();
		returnObs.setStatus(ObservationStatus.FINAL);
		returnObs.setSubject(decedentReference);
		returnObs.setCode(new CodeableConcept().addCoding(new Coding(
				"urn:mdi:temporary:code", "1000004", "Date and time of last known alive or alert")));
		if(inputFields.LKADATE != null && !inputFields.LKADATE.isEmpty()) {
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.LKADATE);
			if(inputFields.LKATIME != null && !inputFields.LKATIME.isEmpty()) {
				OpenMDIToVRDRUtil.addTimeToDate(reportDate, inputFields.LKATIME);
			}
			returnObs.setValue(new DateTimeType(reportDate));
		}
		return returnObs;
	}
	
	private Observation createCaseYearObs(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		Observation returnObs = new Observation();
		returnObs.setStatus(ObservationStatus.FINAL);
		returnObs.setSubject(decedentReference);
		returnObs.setCode(new CodeableConcept().addCoding(new Coding(
				"urn:mdi:temporary:code", "1000005", "Year by which case is categorized")));
		if(inputFields.EXAMDATE != null && !inputFields.EXAMDATE.isEmpty()) {
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.CASEYEAR);
			returnObs.setValue(new DateTimeType(reportDate));
		}
		return returnObs;
	}
	
	private Observation createHospitalDateTime(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		Observation returnObs = new Observation();
		returnObs.setStatus(ObservationStatus.FINAL);
		returnObs.setSubject(decedentReference);
		returnObs.setCode(new CodeableConcept().addCoding(new Coding(
				"urn:mdi:temporary:code", "1000006", "Date and time decedent arrived at hospital")));
		if(inputFields.ATHOSPDATE != null && !inputFields.ATHOSPDATE.isEmpty()) {
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.CASEYEAR);
			if(inputFields.ATHOSPTIME != null && !inputFields.ATHOSPTIME.isEmpty()) {
				OpenMDIToVRDRUtil.addTimeToDate(reportDate, inputFields.ATHOSPTIME);
			}
			returnObs.setValue(new DateType(reportDate));
		}
		return returnObs;
	}
	
	private DeathLocation createDeathLocation(OpenMDIInputFields inputFields) {
		DeathLocation returnDeathLocation = new DeathLocation();
		if(inputFields.DEATHPLACE != null && !inputFields.DEATHPLACE.isEmpty()) {
			Extension deathPlace = new Extension();
			deathPlace.setUrl("urn:mdi:temporary:code:categorization-of-death-place");
			deathPlace.setValue(new StringType(inputFields.DEATHPLACE));
			returnDeathLocation.addExtension(deathPlace);
		}
		if(inputFields.EVENTPLACE != null && !inputFields.EVENTPLACE.isEmpty()) {
			CodeableConcept physicalTypeCode = new CodeableConcept();
			Coding physicalTypeCoding = new Coding();
			physicalTypeCoding.setSystem("http://hl7.org/fhir/ValueSet/location-physical-type");
			if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "Site")) {
				physicalTypeCoding.setCode("si");
				physicalTypeCoding.setDisplay("Site");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "Building")) {
				physicalTypeCoding.setCode("bu");
				physicalTypeCoding.setDisplay("Building");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "wing")) {
				physicalTypeCoding.setCode("wi");
				physicalTypeCoding.setDisplay("Wing");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "ward")) {
				physicalTypeCoding.setCode("wa");
				physicalTypeCoding.setDisplay("Ward");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "level")) {
				physicalTypeCoding.setCode("lvl");
				physicalTypeCoding.setDisplay("Level");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "corridor")) {
				physicalTypeCoding.setCode("co");
				physicalTypeCoding.setDisplay("Corridor");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "room")) {
				physicalTypeCoding.setCode("ro");
				physicalTypeCoding.setDisplay("Room");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "bed")) {
				physicalTypeCoding.setCode("bd");
				physicalTypeCoding.setDisplay("Bed");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "vechicle")) {
				physicalTypeCoding.setCode("ve");
				physicalTypeCoding.setDisplay("Vechicle");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "house")) {
				physicalTypeCoding.setCode("ho");
				physicalTypeCoding.setDisplay("House");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "cabinet")) {
				physicalTypeCoding.setCode("ca");
				physicalTypeCoding.setDisplay("Cabinet");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "Road")) {
				physicalTypeCoding.setCode("rd");
				physicalTypeCoding.setDisplay("road");
			}
			else if(OpenMDIToVRDRUtil.containsIgnoreCase(inputFields.EVENTPLACE, "Jurisdiction")) {
				physicalTypeCoding.setCode("jdn");
				physicalTypeCoding.setDisplay("Jurisidiction");
			}
			else {
				physicalTypeCoding.setCode("area");
				physicalTypeCoding.setDisplay("area");
			}
			
			physicalTypeCode.addCoding(physicalTypeCoding);
			returnDeathLocation.setPhysicalType(physicalTypeCode);
		}
		Extension foundAddrExt = new Extension();
		foundAddrExt.setUrl("urn:mdi:temporary:code:address-where-found-dead-unconscious-or-in-distress");
		Address foundAddr = OpenMDIToVRDRUtil.createAddress(inputFields.FOUNDADDR_STREET, inputFields.FOUNDADDR_CITY,
				inputFields.FOUNDADDR_COUNTY, inputFields.FOUNDADDR_STATE, inputFields.FOUNDADDR_ZIP);
		foundAddrExt.setValue(foundAddr);
		returnDeathLocation.addExtension(foundAddrExt);
		Extension prnExt = new Extension();
		prnExt.setUrl("urn:mdi:temporary:code:pronounced-death-place");
		Address pronouncedDeadAddr = OpenMDIToVRDRUtil.createAddress(inputFields.PRNSTREET, inputFields.PRNCITY,
				inputFields.PRNCOUNTY, inputFields.PRNSTATE, inputFields.PRNZIP);
		prnExt.setValue(pronouncedDeadAddr);
		returnDeathLocation.addExtension(prnExt);
		Extension sceneExt = new Extension();
		sceneExt.setUrl("urn:mdi:temporary:code:scene-address");
		Address sceneAddr = OpenMDIToVRDRUtil.createAddress(inputFields.SCENEADDR_STREET, inputFields.SCENEADDR_CITY,
				inputFields.SCENEADDR_COUNTY, inputFields.SCENEADDR_STATE, inputFields.SCENEADDR_ZIP);
		prnExt.setValue(pronouncedDeadAddr);
		returnDeathLocation.addExtension(prnExt);
		return returnDeathLocation;
	}
	
	private InjuryLocation createInjuryLocation(OpenMDIInputFields inputFields) {
		InjuryLocation returnInjuryLocation = new InjuryLocation();
		Address eventAddress = OpenMDIToVRDRUtil.createAddress(inputFields.CINJSTREET, inputFields.CINJCITY, inputFields.CINJCOUNTY,
				inputFields.CINJSTATE, inputFields.CINJZIP);
		returnInjuryLocation.setAddress(eventAddress);
		return returnInjuryLocation;
	}
	
	private Procedure createSurgeryProc(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		Procedure returnProcedure = new Procedure();
		returnProcedure.setSubject(decedentReference);
		returnProcedure.setCategory(new CodeableConcept().addCoding(new Coding(
				"http://snomed.info/sct","387713003","Surgical procedure")));
		
		if(inputFields.SURGERY != null && !inputFields.SURGERY.isEmpty()) {
			returnProcedure.setCode(new CodeableConcept().setText(inputFields.SURGERY));
		}
		
		if(inputFields.SURGDATE != null && !inputFields.SURGDATE.isEmpty()) {
			Date reportDate = OpenMDIToVRDRUtil.parseDate(inputFields.SURGDATE);
			returnProcedure.setPerformed(new DateType(reportDate));
		}
		return returnProcedure;
	}
	
	private AutopsyPerformedIndicator createAutopsy(OpenMDIInputFields inputFields, Reference decedentReference) throws ParseException {
		AutopsyPerformedIndicator autopsy = new AutopsyPerformedIndicator();
		autopsy.setSubject(decedentReference);
		if(inputFields.CAUTOPSY != null && !inputFields.CAUTOPSY.isEmpty()) {
			autopsy.setValue(OpenMDIToVRDRUtil.parseBooleanAndCreateCode(inputFields.CAUTOPSY));
		}
		if(inputFields.AUTOPUSED != null && !inputFields.AUTOPUSED.isEmpty()) {
			Extension autopsyUsedExt = new Extension();
			autopsyUsedExt.setUrl("urn:mdi:temporary:code:autopsy-findings-were-used");
			autopsyUsedExt.setValue(new BooleanType(OpenMDIToVRDRUtil.parseBoolean(inputFields.AUTOPUSED)));
			autopsy.addExtension(autopsyUsedExt);
		}
		return autopsy;
	}
}