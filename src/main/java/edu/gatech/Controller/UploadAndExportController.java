package edu.gatech.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import edu.gatech.MDI.Model.MDIModelFields;
import edu.gatech.Mapping.Service.CanaryValidationService;
import edu.gatech.Mapping.Service.FhirCMSToVRDRService;
import edu.gatech.Mapping.Service.NightingaleSubmissionService;
import edu.gatech.Mapping.Service.MDIToFhirCMSService;
import edu.gatech.Submission.Service.SubmitBundleService;

@Controller
public class UploadAndExportController {
	MDIToFhirCMSService mappingService;
	SubmitBundleService submitBundleService;
	FhirCMSToVRDRService fhirCMSToVRDRService;
	
	@Autowired
	public UploadAndExportController(MDIToFhirCMSService mappingService, SubmitBundleService submitBundleService,FhirCMSToVRDRService fhirCMSToVRDRService) {
		this.mappingService = mappingService;
		this.submitBundleService = submitBundleService;
		this.fhirCMSToVRDRService = fhirCMSToVRDRService;
	}
	
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, @Value(" ${fhircms.submit}") boolean submitFlag, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `InputField` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<MDIModelFields> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(MDIModelFields.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<MDIModelFields> inputFields = csvToBean.parse();
                String prettyFhirOutput = "";
                for(MDIModelFields inputField: inputFields) {
                	String jsonBundle = mappingService.convertToVRDRString(inputField);
                	System.out.println("JSON BUNDLE:");
                	System.out.println(jsonBundle);
                	ObjectMapper mapper = new ObjectMapper();
                    if(prettyFhirOutput.isEmpty()) {
                    	JsonNode node = mapper.readTree(jsonBundle);
                    	prettyFhirOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
                    }
                    // Submit to fhir server
                    inputField.setSuccess(false);
                    System.out.println(jsonBundle);
                    if(submitFlag) {
	                    try {
	    	                ResponseEntity<String> response = submitBundleService.submitBundle(jsonBundle);
	    	                // save users list on model
	    	                if(response.getStatusCode() == HttpStatus.OK ) {
	    	                	inputField.setSuccess(true);
	    	                }
	                    }
	                    catch (HttpStatusCodeException e) {
	                    	inputField.setSuccess(false);
	                    }
                    }
                }
                model.addAttribute("inputFields", inputFields);
                model.addAttribute("status", true);
                model.addAttribute("fhirOutput", prettyFhirOutput);

            } catch (Exception ex) {
            	ex.printStackTrace(System.out);
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }
        return "file-upload-status";
    }
    
    @GetMapping("submitEDRS")
    public ResponseEntity<JsonNode> submitEDRSRecord(@RequestParam(required = false) String patientIdentifier, @RequestParam(required = false) String systemIdentifier,
    		@RequestParam(required = false) String codeIdentifier, @RequestParam(defaultValue = "false") boolean validateOnly, @RequestParam(defaultValue = "false") boolean createOnly,
    		@RequestParam(defaultValue = "false") boolean submitOnly) {
    	JsonNode returnNode = JsonNodeFactory.instance.objectNode();
    	try {
			returnNode = fhirCMSToVRDRService.createRecordValidateAndSubmit(systemIdentifier, codeIdentifier, createOnly, validateOnly, submitOnly);
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
            ObjectNode exceptionNode = JsonNodeFactory.instance.objectNode();
            exceptionNode.put("Exception", ex.getMessage());
            ResponseEntity<JsonNode> response = new ResponseEntity<JsonNode>(exceptionNode, HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
		}
    	ResponseEntity<JsonNode> response = new ResponseEntity<JsonNode>(returnNode, HttpStatus.OK);
    	return response;
    }
}