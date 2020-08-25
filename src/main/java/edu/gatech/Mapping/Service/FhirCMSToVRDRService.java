package edu.gatech.Mapping.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Composition;
import org.hl7.fhir.r4.model.Composition.CompositionAttestationMode;
import org.hl7.fhir.r4.model.Composition.CompositionAttesterComponent;
import org.hl7.fhir.r4.model.Composition.SectionComponent;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.ListResource;
import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.Parameters.ParametersParameterComponent;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.RelatedPerson;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import edu.gatech.Mapping.Util.FHIRCMSToVRDRUtil;
import edu.gatech.VRDR.context.VRDRFhirContext;
import edu.gatech.VRDR.model.AutopsyPerformedIndicator;
import edu.gatech.VRDR.model.BirthRecordIdentifier;
import edu.gatech.VRDR.model.CauseOfDeathCondition;
import edu.gatech.VRDR.model.CauseOfDeathPathway;
import edu.gatech.VRDR.model.Certifier;
import edu.gatech.VRDR.model.ConditionContributingToDeath;
import edu.gatech.VRDR.model.DeathCertificate;
import edu.gatech.VRDR.model.DeathCertificateDocument;
import edu.gatech.VRDR.model.DeathCertificateReference;
import edu.gatech.VRDR.model.DeathCertification;
import edu.gatech.VRDR.model.DeathDate;
import edu.gatech.VRDR.model.DeathLocation;
import edu.gatech.VRDR.model.DeathPronouncementPerformer;
import edu.gatech.VRDR.model.Decedent;
import edu.gatech.VRDR.model.DecedentAge;
import edu.gatech.VRDR.model.DecedentDispositionMethod;
import edu.gatech.VRDR.model.DecedentEducationLevel;
import edu.gatech.VRDR.model.DecedentFather;
import edu.gatech.VRDR.model.DecedentMilitaryService;
import edu.gatech.VRDR.model.DecedentMother;
import edu.gatech.VRDR.model.DecedentPregnancy;
import edu.gatech.VRDR.model.DecedentSpouse;
import edu.gatech.VRDR.model.DecedentTransportationRole;
import edu.gatech.VRDR.model.DecedentUsualWork;
import edu.gatech.VRDR.model.DispositionLocation;
import edu.gatech.VRDR.model.ExaminerContacted;
import edu.gatech.VRDR.model.InjuryIncident;
import edu.gatech.VRDR.model.InjuryLocation;
import edu.gatech.VRDR.model.MannerOfDeath;
import edu.gatech.VRDR.model.Mortician;
import edu.gatech.VRDR.model.TobaccoUseContributedToDeath;
import edu.gatech.VRDR.model.util.AutopsyPerformedIndicatorUtil;
import edu.gatech.VRDR.model.util.BirthRecordIdentifierUtil;
import edu.gatech.VRDR.model.util.CommonUtil;
import edu.gatech.VRDR.model.util.DeathCertificateUtil;
import edu.gatech.VRDR.model.util.DeathCertificationUtil;
import edu.gatech.VRDR.model.util.DeathDateUtil;
import edu.gatech.VRDR.model.util.DecedentAgeUtil;
import edu.gatech.VRDR.model.util.DecedentDispositionMethodUtil;
import edu.gatech.VRDR.model.util.DecedentEducationLevelUtil;
import edu.gatech.VRDR.model.util.DecedentFatherUtil;
import edu.gatech.VRDR.model.util.DecedentMilitaryServiceUtil;
import edu.gatech.VRDR.model.util.DecedentMotherUtil;
import edu.gatech.VRDR.model.util.DecedentPregnancyUtil;
import edu.gatech.VRDR.model.util.DecedentSpouseUtil;
import edu.gatech.VRDR.model.util.DecedentTransportationRoleUtil;
import edu.gatech.VRDR.model.util.DecedentUsualWorkUtil;
import edu.gatech.VRDR.model.util.ExaminerContactedUtil;
import edu.gatech.VRDR.model.util.InjuryIncidentUtil;
import edu.gatech.VRDR.model.util.MannerOfDeathUtil;
import edu.gatech.VRDR.model.util.TobaccoUseContributedToDeathUtil;

@Service
public class FhirCMSToVRDRService {

	@Autowired
	private VRDRFhirContext vrdrFhirContext;
	@Autowired
	private CanaryValidationService canaryValidationService;
	@Autowired
	private NightingaleSubmissionService nightingaleSubmissionService;
	private IGenericClient client;
	private IParser parser;
	@Autowired
	public FhirCMSToVRDRService(VRDRFhirContext vrdrFhirContext, @Value("${fhircms.url}") String url,
	@Value("${fhircms.basicAuth.username}") String basicUsername,
	@Value("${fhircms.basicAuth.password}") String basicPassword) {
		this.vrdrFhirContext = vrdrFhirContext;
		client = vrdrFhirContext.getCtx().newRestfulGenericClient(url);
		IClientInterceptor authInterceptor = new BasicAuthInterceptor(basicUsername, basicPassword);
		client.registerInterceptor(authInterceptor);
		// Create a logging interceptor
		LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

		// Optionally you may configure the interceptor (by default only
		// summary info is logged)
		loggingInterceptor.setLogRequestSummary(true);
		loggingInterceptor.setLogRequestBody(true);
		client.registerInterceptor(loggingInterceptor);
		parser = vrdrFhirContext.getCtx().newJsonParser();
	}
	
	public JsonNode createRecordValidateAndSubmit(String patientIdentifierSystem, String patientIdentifierCode, boolean createOnly, boolean validateOnly, boolean submitOnly) throws Exception {
		ObjectNode returnNode = JsonNodeFactory.instance.objectNode();
		DeathCertificateDocument dcd = createDCDFromBaseFhirServer(patientIdentifierSystem, patientIdentifierCode);
		String VRDRJson = parser.encodeResourceToString(dcd);
		System.out.println("VRDR Submission Document:");
		System.out.println(VRDRJson);
		if(createOnly) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode VRDROnly = mapper.readTree(VRDRJson);
			return VRDROnly;
		}
		returnNode.put("VRDR", VRDRJson);
		if(!submitOnly) {
			JsonNode canaryIssues = canaryValidationService.validateVRDRAgainstCanary(VRDRJson);
			returnNode.set("validationIssues",canaryIssues);
		}
		if(validateOnly) {
			return returnNode;
		}
		JsonNode nightingaleResponse = nightingaleSubmissionService.submitRecord(VRDRJson);
		//Pattern pattern = Pattern.compile(".*(\\d+).*");
		String inputString = nightingaleResponse.get("message").asText().replaceAll(" ", "");
		returnNode.put("Nightingale Response", nightingaleResponse.get("message").asText());
		//Matcher matcher = pattern.matcher(inputString);
		//String nightingaleId = matcher.group(1);
		//Decedent decedent = findDecedentInDCD(dcd);
		//decedent.addIdentifier(new Identifier().setSystem(nightingaleSubmissionService.getNightingaleURL()).setValue(nightingaleId));
		//TODO: Update the composition as well
		//returnNode.put("VRDR",parser.encodeResourceToString(dcd));
		return returnNode;
	}
	
	public void testPatientEverything() {
		Parameters patientEverythingOutParameters = client.operation()
				.onInstance(new IdType("Patient","66416286-5bda-4424-b28a-75ef3aadc9c8"))
				.named("everything")
				.withNoParameters(Parameters.class)
				.useHttpGet()
				.execute();
		for(ParametersParameterComponent paramComp:patientEverythingOutParameters.getParameter()) {
			System.out.print("Parameter Component Name:"+paramComp.getName());
		}
	}
	
	public DeathCertificateDocument createDCDFromBaseFhirServer(String patientIdentifierSystem, String patientIdentifierCode) throws Exception {

		DeathCertificateDocument deathCertificateDocument = new DeathCertificateDocument();
		//Find the specific patient in the server with it's identifier
		Bundle patientBundle = client.search()
				.forResource(Patient.class)
				.where(Patient.IDENTIFIER.exactly().systemAndCode(patientIdentifierSystem, patientIdentifierCode))
				.returnBundle(Bundle.class)
				.execute();
		Patient patient = null;
		if(patientBundle.getEntryFirstRep().getResource() != null) {
			patient = (Patient)patientBundle.getEntryFirstRep().getResource();
		}
		else {
			throw new Exception("Patient not found in cms");
		}
		//Find the Death Certificate Composition in the system if it allready exists
		Bundle compositionBundle = client.search()
		.forResource(Composition.class)
		.where(Composition.PATIENT.hasId(patient.getIdElement().getIdPart()))
		.returnBundle(Bundle.class)
		.execute();
		DeathCertificate deathCertificate = new DeathCertificate();
		if(compositionBundle.hasEntry()) {
			Composition currentComposition = (Composition)compositionBundle.getEntryFirstRep().getResource();
			if(FHIRCMSToVRDRUtil.codeableConceptsEqual(DeathCertificateUtil.typeFixedValue,currentComposition.getType())){
				deathCertificate = (DeathCertificate)currentComposition;
			}
		}
		deathCertificateDocument.addEntry(new BundleEntryComponent().setResource(deathCertificate));
		//Find everything on the patient within the system
		Parameters patientEverythingOutParameters = client.operation()
				.onInstance(patient.getIdElement())
				.named("everything")
				.withNoParameters(Parameters.class)
				.useHttpGet()
				.execute();
		Bundle patientEverythingBundle = (Bundle)patientEverythingOutParameters.getParameterFirstRep().getResource();
		for(BundleEntryComponent bec: patientEverythingBundle.getEntry()) {
			Resource resource = bec.getResource();
			switch(resource.getResourceType()) {
				case CodeSystem:
					break;
				case Condition:
					addConditionToDeathCertificate((Condition)resource, deathCertificate, deathCertificateDocument);
					break;
				case DocumentReference:
					addDocumentReferenceToDeathCertificate((DocumentReference)resource, deathCertificate, deathCertificateDocument);
					break;
				case Encounter:
					break;
				case List:
					addListToDeathCertificate((ListResource)resource, deathCertificate, deathCertificateDocument);
					break;
				case Location:
					addLocationToDeathCertificate((Location)resource, deathCertificate, deathCertificateDocument);
					break;
				case Medication:
					break;
				case MedicationAdministration:
					break;
				case MedicationDispense:
					break;
				case MedicationRequest:
					break;
				case MedicationStatement:
					break;
				case Observation:
					addObservationToDeathCertificate((Observation)resource, deathCertificate, deathCertificateDocument, client);
					break;
				case Patient:
					addPatientToDeathCertificate((Patient)resource, deathCertificate, deathCertificateDocument);
					break;
				case Practitioner:
					addPractitionerToDeathCertificate((Practitioner)resource, deathCertificate, deathCertificateDocument);
					break;
				case Procedure:
					addProcedureToDeathCertificate((Procedure)resource, deathCertificate, deathCertificateDocument);
					break;
				case RelatedPerson:
					addRelatedPersonToDeathCertificate((RelatedPerson)resource, deathCertificate, deathCertificateDocument);
					break;
				default:
					break;
			}
		}
		
		//TODO: Check for the identifier on the composition and use an UPDATE to the nightingale system
		//TODO: Create a new identifier for nightingale's identifier
		//TODO: Submit VRDR back to FHIR server via $transaction operation
		//TODO: 
		return deathCertificateDocument;
	}
	
	public void addConditionToDeathCertificate(Condition condition, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(condition instanceof CauseOfDeathCondition){
			CauseOfDeathCondition profiledCond = (CauseOfDeathCondition)condition;
			addReferenceToDeathCertificate(deathCertificate, profiledCond);
			CommonUtil.addBundleEntry(dcd, profiledCond);
		}
		else if(condition instanceof ConditionContributingToDeath){
			ConditionContributingToDeath profiledCond = (ConditionContributingToDeath)condition;
			addReferenceToDeathCertificate(deathCertificate, profiledCond);
			CommonUtil.addBundleEntry(dcd, profiledCond);
		}
	}
	
	public void addDocumentReferenceToDeathCertificate(DocumentReference documentReference, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(documentReference instanceof DeathCertificateReference){
			DeathCertificateReference profiledCond = (DeathCertificateReference)documentReference;
			addReferenceToDeathCertificate(deathCertificate, profiledCond);
			CommonUtil.addBundleEntry(dcd, profiledCond);
		}
	}
	
	public void addListToDeathCertificate(ListResource listResource, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(listResource instanceof CauseOfDeathPathway) {
			CauseOfDeathPathway profiledList = (CauseOfDeathPathway)listResource;
			addReferenceToDeathCertificate(deathCertificate, profiledList);
			CommonUtil.addBundleEntry(dcd, profiledList);
		}
	}
	
	public void addLocationToDeathCertificate(Location location, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(location instanceof DispositionLocation){
			DeathLocation profiledLocation = (DeathLocation)location;
			addReferenceToDeathCertificate(deathCertificate, profiledLocation);
			CommonUtil.addBundleEntry(dcd, profiledLocation);
		}
	}
	
	public void addObservationToDeathCertificate(Observation observation, DeathCertificate deathCertificate, DeathCertificateDocument dcd, IGenericClient client) {
		if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), AutopsyPerformedIndicatorUtil.code)){
			AutopsyPerformedIndicator profiledObs = (AutopsyPerformedIndicator)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), BirthRecordIdentifierUtil.code)){
			BirthRecordIdentifier profiledObs = (BirthRecordIdentifier)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DeathDateUtil.code)){
			DeathDate profiledObs = (DeathDate)observation;
			// Pull in referenced DeathLocation
			if(profiledObs.getPatientLocationExtension() != null) {
				Reference locationRef = (Reference)profiledObs.getPatientLocationExtension().getValue();
				Location location = client.read().resource(Location.class).withId(locationRef.getReference()).execute();
				DeathLocation deathLocation = new DeathLocation(location.getName(),location.getDescription(),
						location.getTypeFirstRep(),location.getAddress(),location.getPhysicalType());
				addReferenceToDeathCertificate(deathCertificate, deathLocation);
				CommonUtil.addBundleEntry(dcd, deathLocation);
			}
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DecedentAgeUtil.code)){
			addReferenceToDeathCertificate(deathCertificate, observation);
			CommonUtil.addBundleEntry(dcd, observation);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DecedentDispositionMethodUtil.code)){
			DecedentDispositionMethod profiledObs = (DecedentDispositionMethod)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DecedentEducationLevelUtil.code)){
			DecedentEducationLevel profiledObs = (DecedentEducationLevel)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DecedentMilitaryServiceUtil.code)){
			DecedentMilitaryService profiledObs = (DecedentMilitaryService)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DecedentPregnancyUtil.code)){
			DecedentPregnancy profiledObs = (DecedentPregnancy)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DecedentTransportationRoleUtil.code)){
			DecedentTransportationRole profiledObs = (DecedentTransportationRole)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), DecedentUsualWorkUtil.code)){
			addReferenceToDeathCertificate(deathCertificate, observation);
			CommonUtil.addBundleEntry(dcd, observation);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), ExaminerContactedUtil.code)){
			ExaminerContacted profiledObs = (ExaminerContacted)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), InjuryIncidentUtil.code)){
			InjuryIncident profiledObs = (InjuryIncident)observation;
			CommonUtil.addSectionEntry(deathCertificate, profiledObs);
			if(profiledObs.getPatientLocationExtension() != null) {
				Reference locationRef = (Reference)profiledObs.getPatientLocationExtension().getValue();
				Location location = client.read().resource(Location.class).withId(locationRef.getId()).execute();
				InjuryLocation injuryLocation = new InjuryLocation(location.getName(),location.getDescription(),
						location.getTypeFirstRep(),location.getAddress(),location.getPhysicalType());
				CommonUtil.addSectionEntry(deathCertificate, injuryLocation);
				CommonUtil.addBundleEntry(dcd, injuryLocation);
			}
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), MannerOfDeathUtil.code)){
			MannerOfDeath profiledObs = (MannerOfDeath)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(observation.getCode(), TobaccoUseContributedToDeathUtil.code)){
			TobaccoUseContributedToDeath profiledObs = (TobaccoUseContributedToDeath)observation;
			addReferenceToDeathCertificate(deathCertificate, profiledObs);
			CommonUtil.addBundleEntry(dcd, profiledObs);
		}
	}
	
	public void addPatientToDeathCertificate(Patient patient, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(patient instanceof Decedent){
			Decedent profiledPatient = (Decedent)patient;
			addReferenceToDeathCertificate(deathCertificate, profiledPatient);
			CommonUtil.addBundleEntry(dcd, profiledPatient);
		}
	}
	
	public void addPractitionerToDeathCertificate(Practitioner practitioner, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(practitioner instanceof Certifier){
			Certifier profiledPrac = (Certifier)practitioner;
			addReferenceToDeathCertificate(deathCertificate, profiledPrac);
			//Hack around to update the reference from "Prac/123" to "123"
			CompositionAttesterComponent oldcac = null;
			if(deathCertificate.hasAttester()) {
				oldcac = deathCertificate.getAttesterFirstRep();
				deathCertificate.setAttester(new ArrayList());
			}
			CompositionAttesterComponent newcac = new CompositionAttesterComponent();
			newcac.setMode(CompositionAttestationMode.LEGAL);
			if(oldcac != null && oldcac.getTime() != null) {
				newcac.setTime(oldcac.getTime());
			}
			else {
				newcac.setTime(new Date());
			}
			Reference certifierReference = new Reference(profiledPrac.getIdElement().getIdPart());
			newcac.setParty(certifierReference);
			deathCertificate.addAttester(newcac);
			CommonUtil.addBundleEntry(dcd, profiledPrac);
		}
		else if(practitioner instanceof DeathPronouncementPerformer){
			DeathPronouncementPerformer profiledPrac = (DeathPronouncementPerformer)practitioner;
			addReferenceToDeathCertificate(deathCertificate, profiledPrac);
			CommonUtil.addBundleEntry(dcd, profiledPrac);
		}
		else if(practitioner instanceof Mortician){
			Mortician profiledPrac = (Mortician)practitioner;
			addReferenceToDeathCertificate(deathCertificate, profiledPrac);
			CommonUtil.addBundleEntry(dcd, profiledPrac);
		}
	}
	
	public void addProcedureToDeathCertificate(Procedure procedure, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(FHIRCMSToVRDRUtil.codeableConceptsEqual(procedure.getCode(), DeathCertificationUtil.codeFixedValue)){
			DeathCertification profiledProc = (DeathCertification)procedure;
			addReferenceToDeathCertificate(deathCertificate, profiledProc);
			CommonUtil.addBundleEntry(dcd, profiledProc);
		}
	}
	
	public void addRelatedPersonToDeathCertificate(RelatedPerson relatedPerson, DeathCertificate deathCertificate, DeathCertificateDocument dcd) {
		if(FHIRCMSToVRDRUtil.codeableConceptsEqual(relatedPerson.getRelationshipFirstRep(), DecedentFatherUtil.code)){
			DecedentFather profiledRelatedPerson= (DecedentFather)relatedPerson;
			addReferenceToDeathCertificate(deathCertificate, profiledRelatedPerson);
			CommonUtil.addBundleEntry(dcd, profiledRelatedPerson);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(relatedPerson.getRelationshipFirstRep(), DecedentMotherUtil.code)){
			DecedentMother profiledRelatedPerson= (DecedentMother)relatedPerson;
			addReferenceToDeathCertificate(deathCertificate, profiledRelatedPerson);
			CommonUtil.addBundleEntry(dcd, profiledRelatedPerson);
		}
		else if(FHIRCMSToVRDRUtil.codeableConceptsEqual(relatedPerson.getRelationshipFirstRep(), DecedentSpouseUtil.code)){
			DecedentSpouse profiledRelatedPerson= (DecedentSpouse)relatedPerson;
			addReferenceToDeathCertificate(deathCertificate, profiledRelatedPerson);
			CommonUtil.addBundleEntry(dcd, profiledRelatedPerson);
		}
	}
	
	private void addReferenceToDeathCertificate(DeathCertificate deathCertificate, Resource resource) {
		//Check if resource is allready in certificate
		boolean allreadyInCertificate = false;
		for(SectionComponent sc:deathCertificate.getSection()) {
			for(Reference reference:sc.getEntry()) {
				String referenceString = reference.getReference();
				String resourceIdString = resource.getId();
				if(referenceString.equals(resourceIdString)) {
					allreadyInCertificate = true;
				}
			}
		}
		if(!allreadyInCertificate) {
			CommonUtil.addSectionEntry(deathCertificate, resource);
		}
	}
	
	private Decedent findDecedentInDCD(DeathCertificateDocument dcd) {
		for(BundleEntryComponent bec:dcd.getEntry()) {
			Resource resource = bec.getResource();
			if(resource instanceof Decedent) {
				Decedent decedent = (Decedent)resource;
				return decedent;
			}
		}
		return null;
	}
}