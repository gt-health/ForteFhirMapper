package edu.gatech.Submission.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.gatech.Submission.Entity.PatientIdentifier;
import edu.gatech.Submission.Entity.PatientSubmit;

@Repository
public interface PatientSubmitRepository extends CrudRepository<PatientSubmit, PatientIdentifier>{
	List<PatientSubmit> findByPatientIdentifierSystemAndPatientIdentifierCode(String system, String code);
}
