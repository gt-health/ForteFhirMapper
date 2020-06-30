package edu.gatech.TestUpload.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import edu.gatech.Mapping.Service.OpenMDIToVRDRService;
import edu.gatech.OpenMDI.Model.OpenMDIInputFields;
import edu.gatech.Submission.Service.SubmitBundleService;

@Controller
public class UploadController {
	OpenMDIToVRDRService mappingService;
	SubmitBundleService submitBundleService;
	
	@Autowired
	public UploadController(OpenMDIToVRDRService mappingService, SubmitBundleService submitBundleService) {
		this.mappingService = mappingService;
		this.submitBundleService = submitBundleService;
	}
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `InputField` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<OpenMDIInputFields> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(OpenMDIInputFields.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<OpenMDIInputFields> inputFields = csvToBean.parse();
                String prettyFhirOutput = "";
                for(OpenMDIInputFields inputField: inputFields) {
                	String jsonBundle = mappingService.convertToVRDRString(inputField);
                	System.out.println("JSON BUNDLE:");
                	System.out.println(jsonBundle);
                	ObjectMapper mapper = new ObjectMapper();
                    if(prettyFhirOutput.isEmpty()) {
                    	JsonNode node = mapper.readTree(jsonBundle);
                    	prettyFhirOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
                    }
                    // Submit to fhir server
                    try {
    	                ResponseEntity<String> response = submitBundleService.submitBundle(jsonBundle);
    	                // save users list on model
    	                if(response.getStatusCode() == HttpStatus.OK ) {
    	                	inputField.setSuccess(true);
    	                }
    	                else {
    	                	inputField.setSuccess(false);
    	                }
    	                
                    }
                    catch (HttpStatusCodeException e) {
                    	inputField.setSuccess(false);
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
}