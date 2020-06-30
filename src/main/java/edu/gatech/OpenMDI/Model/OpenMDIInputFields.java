package edu.gatech.OpenMDI.Model;

import com.opencsv.bean.CsvBindByName;

public class OpenMDIInputFields {
	@CsvBindByName
	public String SYSTEMID = "";
	@CsvBindByName
	public String CASEID = "";
	@CsvBindByName
	public String FIRSTNAME = "";
	@CsvBindByName
	public String MIDNAME = "";
	@CsvBindByName
	public String LASTNAME = "";
	@CsvBindByName
	public String AGE = "";
	@CsvBindByName
	public String AGEUNIT = "";
	@CsvBindByName
	public String RACE = "";
	@CsvBindByName
	public String GENDER = "";
	@CsvBindByName
	public String ETHNICITY = "";
	@CsvBindByName
	public String BIRTHDATE = "";
	@CsvBindByName
	public String MRNNUMBER = "";
	@CsvBindByName
	public String JOBTITLE = "";
	@CsvBindByName
	public String MARITAL = "";
	@CsvBindByName
	public String POSSIBLEID = "";
	@CsvBindByName
	public String CAUSEA = "";
	@CsvBindByName
	public String CAUSEB = "";
	@CsvBindByName
	public String CAUSEC = "";
	@CsvBindByName
	public String CAUSED = "";
	@CsvBindByName
	public String OSCOND = ""; //Expecting a semicolon deliniated list
	@CsvBindByName
	public String MANNER = "";
	@CsvBindByName
	public String CHOWNINJURY = "";
	@CsvBindByName
	public String DURATIONA = "";
	@CsvBindByName
	public String DURATIONB = "";
	@CsvBindByName
	public String DURATIONC = "";
	@CsvBindByName
	public String DURATIOND = "";
	@CsvBindByName
	public String CASENOTES = "";
	@CsvBindByName
	public String ATWORK = "";
	@CsvBindByName
	public String JOBRELATED = "";
	@CsvBindByName
	public String REPORTDATE = "";
	@CsvBindByName
	public String REPORTTIME = "";
	@CsvBindByName
	public String FOUNDDATE = "";
	@CsvBindByName
	public String FOUNDTIME = "";
	@CsvBindByName
	public String EVENTDATE = "";
	@CsvBindByName
	public String EVENTTIME = "";
	@CsvBindByName
	public String PRNDATE = "";
	@CsvBindByName
	public String PRNTIME = "";
	@CsvBindByName
	public String EXAMDATE = "";
	@CsvBindByName
	public String CINJDATE = "";
	@CsvBindByName
	public String CINJTIME = "";
	@CsvBindByName
	public String CIDATEFLAG = "";
	@CsvBindByName
	public String CDEATHFLAG = "";
	@CsvBindByName
	public String CDEATHTIME = "";
	@CsvBindByName
	public String LKADATE = "";
	@CsvBindByName
	public String LKATIME = "";
	@CsvBindByName
	public String CASEYEAR = "";
	@CsvBindByName
	public String ATHOSPDATE = "";
	@CsvBindByName
	public String ATHOSPTIME = "";
	@CsvBindByName
	public String RESSTREET = "";
	@CsvBindByName
	public String RESCITY = "";
	@CsvBindByName
	public String RESCOUNTY = "";
	@CsvBindByName
	public String RESSTATE = "";
	@CsvBindByName
	public String RESZIP = "";
	@CsvBindByName
	public String DEATHPLACE = "";
	@CsvBindByName
	public String EVENTPLACE = "";
	@CsvBindByName
	public String FOUNDADDR_STREET = "";
	@CsvBindByName
	public String FOUNDADDR_CITY = "";
	@CsvBindByName
	public String FOUNDADDR_COUNTY = "";
	@CsvBindByName
	public String FOUNDADDR_STATE = "";
	@CsvBindByName
	public String FOUNDADDR_ZIP = "";
	@CsvBindByName
	public String EVENTADDR_STREET = "";
	@CsvBindByName
	public String EVENTADDR_CITY = "";
	@CsvBindByName
	public String EVENTADDR_COUNTY = "";
	@CsvBindByName
	public String EVENTADDR_STATE = "";
	@CsvBindByName
	public String EVENTADDR_ZIP = "";
	@CsvBindByName
	public String PRNPLACE = "";
	@CsvBindByName
	public String PRNSTREET = "";
	@CsvBindByName
	public String PRNCITY = "";
	@CsvBindByName
	public String PRNCOUNTY = "";
	@CsvBindByName
	public String PRNSTATE = "";
	@CsvBindByName
	public String PRNZIP = "";
	@CsvBindByName
	public String CINJPLACE = "";
	@CsvBindByName
	public String CINJSTREET = "";
	@CsvBindByName
	public String CINJCITY = "";
	@CsvBindByName
	public String CINJCOUNTY = "";
	@CsvBindByName
	public String CINJSTATE = "";
	@CsvBindByName
	public String CINJZIP = "";
	@CsvBindByName
	public String CDEATHDATE = "";
	@CsvBindByName
	public String RESNAME = "";
	@CsvBindByName
	public String LKAWHERE = "";
	@CsvBindByName
	public String HOSPNAME = "";
	@CsvBindByName
	public String SCENEADDR_STREET = "";
	@CsvBindByName
	public String SCENEADDR_CITY = "";
	@CsvBindByName
	public String SCENEADDR_COUNTY = "";
	@CsvBindByName
	public String SCENEADDR_STATE = "";
	@CsvBindByName
	public String SCENEADDR_ZIP = "";
	@CsvBindByName
	public String SURGERY = "";
	@CsvBindByName
	public String SURGDATE = "";
	@CsvBindByName
	public String HCPROVIDER = "";
	@CsvBindByName
	public String CAUTOPSY = "";
	@CsvBindByName
	public String AUTOPUSED = "";
	@CsvBindByName
	public String CUSTODY = "";
	public boolean success = false;
	
	public OpenMDIInputFields() {
		
	}

	public String getSYSTEMID() {
		return SYSTEMID;
	}

	public void setSYSTEMID(String sYSTEMID) {
		SYSTEMID = sYSTEMID;
	}
	
	public String getCASEID() {
		return CASEID;
	}

	public void setCASEID(String cASENUMBER) {
		CASEID = cASENUMBER;
	}
	
	public String getFIRSTNAME() {
		return FIRSTNAME;
	}

	public void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME = fIRSTNAME;
	}

	public String getMIDNAME() {
		return MIDNAME;
	}

	public void setMIDNAME(String mIDNAME) {
		MIDNAME = mIDNAME;
	}

	public String getLASTNAME() {
		return LASTNAME;
	}

	public void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}

	public String getAGE() {
		return AGE;
	}

	public void setAGE(String aGE) {
		AGE = aGE;
	}

	public String getAGEUNIT() {
		return AGEUNIT;
	}

	public void setAGEUNIT(String aGEUNIT) {
		AGEUNIT = aGEUNIT;
	}

	public String getRACE() {
		return RACE;
	}

	public void setRACE(String rACE) {
		RACE = rACE;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getETHNICITY() {
		return ETHNICITY;
	}

	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}

	public String getBIRTHDATE() {
		return BIRTHDATE;
	}

	public void setBIRTHDATE(String bIRTHDATE) {
		BIRTHDATE = bIRTHDATE;
	}

	public String getMRNNUMBER() {
		return MRNNUMBER;
	}

	public void setMRNNUMBER(String mRNNUMBER) {
		MRNNUMBER = mRNNUMBER;
	}

	public String getJOBTITLE() {
		return JOBTITLE;
	}

	public void setJOBTITLE(String jOBTITLE) {
		JOBTITLE = jOBTITLE;
	}

	public String getMARITAL() {
		return MARITAL;
	}

	public void setMARITAL(String mARITAL) {
		MARITAL = mARITAL;
	}

	public String getPOSSIBLEID() {
		return POSSIBLEID;
	}

	public void setPOSSIBLEID(String pOSSIBLEID) {
		POSSIBLEID = pOSSIBLEID;
	}

	public String getCAUSEA() {
		return CAUSEA;
	}

	public void setCAUSEA(String cAUSEA) {
		CAUSEA = cAUSEA;
	}

	public String getCAUSEB() {
		return CAUSEB;
	}

	public void setCAUSEB(String cAUSEB) {
		CAUSEB = cAUSEB;
	}

	public String getCAUSEC() {
		return CAUSEC;
	}

	public void setCAUSEC(String cAUSEC) {
		CAUSEC = cAUSEC;
	}

	public String getCAUSED() {
		return CAUSED;
	}

	public void setCAUSED(String cAUSED) {
		CAUSED = cAUSED;
	}

	public String getOSCOND() {
		return OSCOND;
	}

	public void setOSCOND(String oSCOND) {
		OSCOND = oSCOND;
	}

	public String getMANNER() {
		return MANNER;
	}

	public void setMANNER(String mANNER) {
		MANNER = mANNER;
	}

	public String getCHOWNINJURY() {
		return CHOWNINJURY;
	}

	public void setCHOWNINJURY(String cHOWNINJURY) {
		CHOWNINJURY = cHOWNINJURY;
	}

	public String getDURATIONA() {
		return DURATIONA;
	}

	public void setDURATIONA(String dURATIONA) {
		DURATIONA = dURATIONA;
	}

	public String getDURATIONB() {
		return DURATIONB;
	}

	public void setDURATIONB(String dURATIONB) {
		DURATIONB = dURATIONB;
	}

	public String getDURATIONC() {
		return DURATIONC;
	}

	public void setDURATIONC(String dURATIONC) {
		DURATIONC = dURATIONC;
	}

	public String getDURATIOND() {
		return DURATIOND;
	}

	public void setDURATIOND(String dURATIOND) {
		DURATIOND = dURATIOND;
	}

	public String getCASENOTES() {
		return CASENOTES;
	}

	public void setCASENOTES(String cASENOTES) {
		CASENOTES = cASENOTES;
	}

	public String getATWORK() {
		return ATWORK;
	}

	public void setATWORK(String aTWORK) {
		ATWORK = aTWORK;
	}

	public String getJOBRELATED() {
		return JOBRELATED;
	}

	public void setJOBRELATED(String jOBRELATED) {
		JOBRELATED = jOBRELATED;
	}

	public String getREPORTDATE() {
		return REPORTDATE;
	}

	public void setREPORTDATE(String rEPORTDATE) {
		REPORTDATE = rEPORTDATE;
	}

	public String getREPORTTIME() {
		return REPORTTIME;
	}

	public void setREPORTTIME(String rEPORTTIME) {
		REPORTTIME = rEPORTTIME;
	}

	public String getFOUNDDATE() {
		return FOUNDDATE;
	}

	public void setFOUNDDATE(String fOUNDDATE) {
		FOUNDDATE = fOUNDDATE;
	}

	public String getFOUNDTIME() {
		return FOUNDTIME;
	}

	public void setFOUNDTIME(String fOUNDTIME) {
		FOUNDTIME = fOUNDTIME;
	}

	public String getEVENTDATE() {
		return EVENTDATE;
	}

	public void setEVENTDATE(String eVENTDATE) {
		EVENTDATE = eVENTDATE;
	}

	public String getEVENTTIME() {
		return EVENTTIME;
	}

	public void setEVENTTIME(String eVENTTIME) {
		EVENTTIME = eVENTTIME;
	}

	public String getPRNDATE() {
		return PRNDATE;
	}

	public void setPRNDATE(String pRNDATE) {
		PRNDATE = pRNDATE;
	}

	public String getPRNTIME() {
		return PRNTIME;
	}

	public void setPRNTIME(String pRNTIME) {
		PRNTIME = pRNTIME;
	}

	public String getEXAMDATE() {
		return EXAMDATE;
	}

	public void setEXAMDATE(String eXAMDATE) {
		EXAMDATE = eXAMDATE;
	}

	public String getCINJDATE() {
		return CINJDATE;
	}

	public void setCINJDATE(String cINJDATE) {
		CINJDATE = cINJDATE;
	}

	public String getCINJTIME() {
		return CINJTIME;
	}

	public void setCINJTIME(String cINJTIME) {
		CINJTIME = cINJTIME;
	}

	public String getCIDATEFLAG() {
		return CIDATEFLAG;
	}

	public void setCIDATEFLAG(String cIDATEFLAG) {
		CIDATEFLAG = cIDATEFLAG;
	}

	public String getCDEATHFLAG() {
		return CDEATHFLAG;
	}

	public void setCDEATHFLAG(String cDEATHFLAG) {
		CDEATHFLAG = cDEATHFLAG;
	}

	public String getCDEATHTIME() {
		return CDEATHTIME;
	}

	public void setCDEATHTIME(String cDEATHTIME) {
		CDEATHTIME = cDEATHTIME;
	}

	public String getLKADATE() {
		return LKADATE;
	}

	public void setLKADATE(String lKADATE) {
		LKADATE = lKADATE;
	}

	public String getLKATIME() {
		return LKATIME;
	}

	public void setLKATIME(String lKATIME) {
		LKATIME = lKATIME;
	}

	public String getCASEYEAR() {
		return CASEYEAR;
	}

	public void setCASEYEAR(String cASEYEAR) {
		CASEYEAR = cASEYEAR;
	}

	public String getATHOSPDATE() {
		return ATHOSPDATE;
	}

	public void setATHOSPDATE(String aTHOSPDATE) {
		ATHOSPDATE = aTHOSPDATE;
	}

	public String getATHOSPTIME() {
		return ATHOSPTIME;
	}

	public void setATHOSPTIME(String aTHOSPTIME) {
		ATHOSPTIME = aTHOSPTIME;
	}

	public String getRESSTREET() {
		return RESSTREET;
	}

	public void setRESSTREET(String rESSTREET) {
		RESSTREET = rESSTREET;
	}

	public String getRESCITY() {
		return RESCITY;
	}

	public void setRESCITY(String rESCITY) {
		RESCITY = rESCITY;
	}

	public String getRESCOUNTY() {
		return RESCOUNTY;
	}

	public void setRESCOUNTY(String rESCOUNTY) {
		RESCOUNTY = rESCOUNTY;
	}

	public String getRESSTATE() {
		return RESSTATE;
	}

	public void setRESSTATE(String rESSTATE) {
		RESSTATE = rESSTATE;
	}

	public String getRESZIP() {
		return RESZIP;
	}

	public void setRESZIP(String rESZIP) {
		RESZIP = rESZIP;
	}

	public String getDEATHPLACE() {
		return DEATHPLACE;
	}

	public void setDEATHPLACE(String dEATHPLACE) {
		DEATHPLACE = dEATHPLACE;
	}

	public String getEVENTPLACE() {
		return EVENTPLACE;
	}

	public void setEVENTPLACE(String eVENTPLACE) {
		EVENTPLACE = eVENTPLACE;
	}

	public String getFOUNDADDR_STREET() {
		return FOUNDADDR_STREET;
	}

	public void setFOUNDADDR_STREET(String fOUNDADDR_STREET) {
		FOUNDADDR_STREET = fOUNDADDR_STREET;
	}

	public String getFOUNDADDR_CITY() {
		return FOUNDADDR_CITY;
	}

	public void setFOUNDADDR_CITY(String fOUNDADDR_CITY) {
		FOUNDADDR_CITY = fOUNDADDR_CITY;
	}

	public String getFOUNDADDR_COUNTY() {
		return FOUNDADDR_COUNTY;
	}

	public void setFOUNDADDR_COUNTY(String fOUNDADDR_COUNTY) {
		FOUNDADDR_COUNTY = fOUNDADDR_COUNTY;
	}

	public String getFOUNDADDR_STATE() {
		return FOUNDADDR_STATE;
	}

	public void setFOUNDADDR_STATE(String fOUNDADDR_STATE) {
		FOUNDADDR_STATE = fOUNDADDR_STATE;
	}

	public String getFOUNDADDR_ZIP() {
		return FOUNDADDR_ZIP;
	}

	public void setFOUNDADDR_ZIP(String fOUNDADDR_ZIP) {
		FOUNDADDR_ZIP = fOUNDADDR_ZIP;
	}

	public String getEVENTADDR_STREET() {
		return EVENTADDR_STREET;
	}

	public void setEVENTADDR_STREET(String eVENTADDR_STREET) {
		EVENTADDR_STREET = eVENTADDR_STREET;
	}

	public String getEVENTADDR_CITY() {
		return EVENTADDR_CITY;
	}

	public void setEVENTADDR_CITY(String eVENTADDR_CITY) {
		EVENTADDR_CITY = eVENTADDR_CITY;
	}

	public String getEVENTADDR_COUNTY() {
		return EVENTADDR_COUNTY;
	}

	public void setEVENTADDR_COUNTY(String eVENTADDR_COUNTY) {
		EVENTADDR_COUNTY = eVENTADDR_COUNTY;
	}

	public String getEVENTADDR_STATE() {
		return EVENTADDR_STATE;
	}

	public void setEVENTADDR_STATE(String eVENTADDR_STATE) {
		EVENTADDR_STATE = eVENTADDR_STATE;
	}

	public String getEVENTADDR_ZIP() {
		return EVENTADDR_ZIP;
	}

	public void setEVENTADDR_ZIP(String eVENTADDR_ZIP) {
		EVENTADDR_ZIP = eVENTADDR_ZIP;
	}

	public String getPRNPLACE() {
		return PRNPLACE;
	}

	public void setPRNPLACE(String pRNPLACE) {
		PRNPLACE = pRNPLACE;
	}

	public String getPRNSTREET() {
		return PRNSTREET;
	}

	public void setPRNSTREET(String pRNSTREET) {
		PRNSTREET = pRNSTREET;
	}

	public String getPRNCITY() {
		return PRNCITY;
	}

	public void setPRNCITY(String pRNCITY) {
		PRNCITY = pRNCITY;
	}

	public String getPRNCOUNTY() {
		return PRNCOUNTY;
	}

	public void setPRNCOUNTY(String pRNCOUNTY) {
		PRNCOUNTY = pRNCOUNTY;
	}

	public String getPRNSTATE() {
		return PRNSTATE;
	}

	public void setPRNSTATE(String pRNSTATE) {
		PRNSTATE = pRNSTATE;
	}

	public String getPRNZIP() {
		return PRNZIP;
	}

	public void setPRNZIP(String pRNZIP) {
		PRNZIP = pRNZIP;
	}

	public String getCINJPLACE() {
		return CINJPLACE;
	}

	public void setCINJPLACE(String cINJPLACE) {
		CINJPLACE = cINJPLACE;
	}

	public String getCINJSTREET() {
		return CINJSTREET;
	}

	public void setCINJSTREET(String cINJSTREET) {
		CINJSTREET = cINJSTREET;
	}

	public String getCINJCITY() {
		return CINJCITY;
	}

	public void setCINJCITY(String cINJCITY) {
		CINJCITY = cINJCITY;
	}

	public String getCINJCOUNTY() {
		return CINJCOUNTY;
	}

	public void setCINJCOUNTY(String cINJCOUNTY) {
		CINJCOUNTY = cINJCOUNTY;
	}

	public String getCINJSTATE() {
		return CINJSTATE;
	}

	public void setCINJSTATE(String cINJSTATE) {
		CINJSTATE = cINJSTATE;
	}

	public String getCINJZIP() {
		return CINJZIP;
	}

	public void setCINJZIP(String cINJZIP) {
		CINJZIP = cINJZIP;
	}

	public String getCDEATHDATE() {
		return CDEATHDATE;
	}

	public void setCDEATHDATE(String cDEATHDATE) {
		CDEATHDATE = cDEATHDATE;
	}

	public String getRESNAME() {
		return RESNAME;
	}

	public void setRESNAME(String rESNAME) {
		RESNAME = rESNAME;
	}

	public String getLKAWHERE() {
		return LKAWHERE;
	}

	public void setLKAWHERE(String lKAWHERE) {
		LKAWHERE = lKAWHERE;
	}

	public String getHOSPNAME() {
		return HOSPNAME;
	}

	public void setHOSPNAME(String hOSPNAME) {
		HOSPNAME = hOSPNAME;
	}

	public String getSCENEADDR_STREET() {
		return SCENEADDR_STREET;
	}

	public void setSCENEADDR_STREET(String sCENEADDR_STREET) {
		SCENEADDR_STREET = sCENEADDR_STREET;
	}

	public String getSCENEADDR_CITY() {
		return SCENEADDR_CITY;
	}

	public void setSCENEADDR_CITY(String sCENEADDR_CITY) {
		SCENEADDR_CITY = sCENEADDR_CITY;
	}

	public String getSCENEADDR_COUNTY() {
		return SCENEADDR_COUNTY;
	}

	public void setSCENEADDR_COUNTY(String sCENEADDR_COUNTY) {
		SCENEADDR_COUNTY = sCENEADDR_COUNTY;
	}

	public String getSCENEADDR_STATE() {
		return SCENEADDR_STATE;
	}

	public void setSCENEADDR_STATE(String sCENEADDR_STATE) {
		SCENEADDR_STATE = sCENEADDR_STATE;
	}

	public String getSCENEADDR_ZIP() {
		return SCENEADDR_ZIP;
	}

	public void setSCENEADDR_ZIP(String sCENEADDR_ZIP) {
		SCENEADDR_ZIP = sCENEADDR_ZIP;
	}

	public String getSURGERY() {
		return SURGERY;
	}

	public void setSURGERY(String sURGERY) {
		SURGERY = sURGERY;
	}

	public String getSURGDATE() {
		return SURGDATE;
	}

	public void setSURGDATE(String sURGDATE) {
		SURGDATE = sURGDATE;
	}

	public String getHCPROVIDER() {
		return HCPROVIDER;
	}

	public void setHCPROVIDER(String hCPROVIDER) {
		HCPROVIDER = hCPROVIDER;
	}

	public String getCAUTOPSY() {
		return CAUTOPSY;
	}

	public void setCAUTOPSY(String cAUTOPSY) {
		CAUTOPSY = cAUTOPSY;
	}

	public String getAUTOPUSED() {
		return AUTOPUSED;
	}

	public void setAUTOPUSED(String aUTOPUSED) {
		AUTOPUSED = aUTOPUSED;
	}

	public String getCUSTODY() {
		return CUSTODY;
	}

	public void setCUSTODY(String cUSTODY) {
		CUSTODY = cUSTODY;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AGE == null) ? 0 : AGE.hashCode());
		result = prime * result + ((AGEUNIT == null) ? 0 : AGEUNIT.hashCode());
		result = prime * result + ((ATHOSPDATE == null) ? 0 : ATHOSPDATE.hashCode());
		result = prime * result + ((ATHOSPTIME == null) ? 0 : ATHOSPTIME.hashCode());
		result = prime * result + ((ATWORK == null) ? 0 : ATWORK.hashCode());
		result = prime * result + ((AUTOPUSED == null) ? 0 : AUTOPUSED.hashCode());
		result = prime * result + ((BIRTHDATE == null) ? 0 : BIRTHDATE.hashCode());
		result = prime * result + ((CASEID == null) ? 0 : CASEID.hashCode());
		result = prime * result + ((CASENOTES == null) ? 0 : CASENOTES.hashCode());
		result = prime * result + ((CASEYEAR == null) ? 0 : CASEYEAR.hashCode());
		result = prime * result + ((CAUSEA == null) ? 0 : CAUSEA.hashCode());
		result = prime * result + ((CAUSEB == null) ? 0 : CAUSEB.hashCode());
		result = prime * result + ((CAUSEC == null) ? 0 : CAUSEC.hashCode());
		result = prime * result + ((CAUSED == null) ? 0 : CAUSED.hashCode());
		result = prime * result + ((CAUTOPSY == null) ? 0 : CAUTOPSY.hashCode());
		result = prime * result + ((CDEATHDATE == null) ? 0 : CDEATHDATE.hashCode());
		result = prime * result + ((CDEATHFLAG == null) ? 0 : CDEATHFLAG.hashCode());
		result = prime * result + ((CDEATHTIME == null) ? 0 : CDEATHTIME.hashCode());
		result = prime * result + ((CHOWNINJURY == null) ? 0 : CHOWNINJURY.hashCode());
		result = prime * result + ((CIDATEFLAG == null) ? 0 : CIDATEFLAG.hashCode());
		result = prime * result + ((CINJCITY == null) ? 0 : CINJCITY.hashCode());
		result = prime * result + ((CINJCOUNTY == null) ? 0 : CINJCOUNTY.hashCode());
		result = prime * result + ((CINJDATE == null) ? 0 : CINJDATE.hashCode());
		result = prime * result + ((CINJPLACE == null) ? 0 : CINJPLACE.hashCode());
		result = prime * result + ((CINJSTATE == null) ? 0 : CINJSTATE.hashCode());
		result = prime * result + ((CINJSTREET == null) ? 0 : CINJSTREET.hashCode());
		result = prime * result + ((CINJTIME == null) ? 0 : CINJTIME.hashCode());
		result = prime * result + ((CINJZIP == null) ? 0 : CINJZIP.hashCode());
		result = prime * result + ((CUSTODY == null) ? 0 : CUSTODY.hashCode());
		result = prime * result + ((DEATHPLACE == null) ? 0 : DEATHPLACE.hashCode());
		result = prime * result + ((DURATIONA == null) ? 0 : DURATIONA.hashCode());
		result = prime * result + ((DURATIONB == null) ? 0 : DURATIONB.hashCode());
		result = prime * result + ((DURATIONC == null) ? 0 : DURATIONC.hashCode());
		result = prime * result + ((DURATIOND == null) ? 0 : DURATIOND.hashCode());
		result = prime * result + ((ETHNICITY == null) ? 0 : ETHNICITY.hashCode());
		result = prime * result + ((EVENTADDR_CITY == null) ? 0 : EVENTADDR_CITY.hashCode());
		result = prime * result + ((EVENTADDR_COUNTY == null) ? 0 : EVENTADDR_COUNTY.hashCode());
		result = prime * result + ((EVENTADDR_STATE == null) ? 0 : EVENTADDR_STATE.hashCode());
		result = prime * result + ((EVENTADDR_STREET == null) ? 0 : EVENTADDR_STREET.hashCode());
		result = prime * result + ((EVENTADDR_ZIP == null) ? 0 : EVENTADDR_ZIP.hashCode());
		result = prime * result + ((EVENTDATE == null) ? 0 : EVENTDATE.hashCode());
		result = prime * result + ((EVENTPLACE == null) ? 0 : EVENTPLACE.hashCode());
		result = prime * result + ((EVENTTIME == null) ? 0 : EVENTTIME.hashCode());
		result = prime * result + ((EXAMDATE == null) ? 0 : EXAMDATE.hashCode());
		result = prime * result + ((FIRSTNAME == null) ? 0 : FIRSTNAME.hashCode());
		result = prime * result + ((FOUNDADDR_CITY == null) ? 0 : FOUNDADDR_CITY.hashCode());
		result = prime * result + ((FOUNDADDR_COUNTY == null) ? 0 : FOUNDADDR_COUNTY.hashCode());
		result = prime * result + ((FOUNDADDR_STATE == null) ? 0 : FOUNDADDR_STATE.hashCode());
		result = prime * result + ((FOUNDADDR_STREET == null) ? 0 : FOUNDADDR_STREET.hashCode());
		result = prime * result + ((FOUNDADDR_ZIP == null) ? 0 : FOUNDADDR_ZIP.hashCode());
		result = prime * result + ((FOUNDDATE == null) ? 0 : FOUNDDATE.hashCode());
		result = prime * result + ((FOUNDTIME == null) ? 0 : FOUNDTIME.hashCode());
		result = prime * result + ((GENDER == null) ? 0 : GENDER.hashCode());
		result = prime * result + ((HCPROVIDER == null) ? 0 : HCPROVIDER.hashCode());
		result = prime * result + ((HOSPNAME == null) ? 0 : HOSPNAME.hashCode());
		result = prime * result + ((JOBRELATED == null) ? 0 : JOBRELATED.hashCode());
		result = prime * result + ((JOBTITLE == null) ? 0 : JOBTITLE.hashCode());
		result = prime * result + ((LASTNAME == null) ? 0 : LASTNAME.hashCode());
		result = prime * result + ((LKADATE == null) ? 0 : LKADATE.hashCode());
		result = prime * result + ((LKATIME == null) ? 0 : LKATIME.hashCode());
		result = prime * result + ((LKAWHERE == null) ? 0 : LKAWHERE.hashCode());
		result = prime * result + ((MANNER == null) ? 0 : MANNER.hashCode());
		result = prime * result + ((MARITAL == null) ? 0 : MARITAL.hashCode());
		result = prime * result + ((MIDNAME == null) ? 0 : MIDNAME.hashCode());
		result = prime * result + ((MRNNUMBER == null) ? 0 : MRNNUMBER.hashCode());
		result = prime * result + ((OSCOND == null) ? 0 : OSCOND.hashCode());
		result = prime * result + ((POSSIBLEID == null) ? 0 : POSSIBLEID.hashCode());
		result = prime * result + ((PRNCITY == null) ? 0 : PRNCITY.hashCode());
		result = prime * result + ((PRNCOUNTY == null) ? 0 : PRNCOUNTY.hashCode());
		result = prime * result + ((PRNDATE == null) ? 0 : PRNDATE.hashCode());
		result = prime * result + ((PRNPLACE == null) ? 0 : PRNPLACE.hashCode());
		result = prime * result + ((PRNSTATE == null) ? 0 : PRNSTATE.hashCode());
		result = prime * result + ((PRNSTREET == null) ? 0 : PRNSTREET.hashCode());
		result = prime * result + ((PRNTIME == null) ? 0 : PRNTIME.hashCode());
		result = prime * result + ((PRNZIP == null) ? 0 : PRNZIP.hashCode());
		result = prime * result + ((RACE == null) ? 0 : RACE.hashCode());
		result = prime * result + ((REPORTDATE == null) ? 0 : REPORTDATE.hashCode());
		result = prime * result + ((REPORTTIME == null) ? 0 : REPORTTIME.hashCode());
		result = prime * result + ((RESCITY == null) ? 0 : RESCITY.hashCode());
		result = prime * result + ((RESCOUNTY == null) ? 0 : RESCOUNTY.hashCode());
		result = prime * result + ((RESNAME == null) ? 0 : RESNAME.hashCode());
		result = prime * result + ((RESSTATE == null) ? 0 : RESSTATE.hashCode());
		result = prime * result + ((RESSTREET == null) ? 0 : RESSTREET.hashCode());
		result = prime * result + ((RESZIP == null) ? 0 : RESZIP.hashCode());
		result = prime * result + ((SCENEADDR_CITY == null) ? 0 : SCENEADDR_CITY.hashCode());
		result = prime * result + ((SCENEADDR_COUNTY == null) ? 0 : SCENEADDR_COUNTY.hashCode());
		result = prime * result + ((SCENEADDR_STATE == null) ? 0 : SCENEADDR_STATE.hashCode());
		result = prime * result + ((SCENEADDR_STREET == null) ? 0 : SCENEADDR_STREET.hashCode());
		result = prime * result + ((SCENEADDR_ZIP == null) ? 0 : SCENEADDR_ZIP.hashCode());
		result = prime * result + ((SURGDATE == null) ? 0 : SURGDATE.hashCode());
		result = prime * result + ((SURGERY == null) ? 0 : SURGERY.hashCode());
		result = prime * result + ((SYSTEMID == null) ? 0 : SYSTEMID.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenMDIInputFields other = (OpenMDIInputFields) obj;
		if (AGE == null) {
			if (other.AGE != null)
				return false;
		} else if (!AGE.equals(other.AGE))
			return false;
		if (AGEUNIT == null) {
			if (other.AGEUNIT != null)
				return false;
		} else if (!AGEUNIT.equals(other.AGEUNIT))
			return false;
		if (ATHOSPDATE == null) {
			if (other.ATHOSPDATE != null)
				return false;
		} else if (!ATHOSPDATE.equals(other.ATHOSPDATE))
			return false;
		if (ATHOSPTIME == null) {
			if (other.ATHOSPTIME != null)
				return false;
		} else if (!ATHOSPTIME.equals(other.ATHOSPTIME))
			return false;
		if (ATWORK == null) {
			if (other.ATWORK != null)
				return false;
		} else if (!ATWORK.equals(other.ATWORK))
			return false;
		if (AUTOPUSED == null) {
			if (other.AUTOPUSED != null)
				return false;
		} else if (!AUTOPUSED.equals(other.AUTOPUSED))
			return false;
		if (BIRTHDATE == null) {
			if (other.BIRTHDATE != null)
				return false;
		} else if (!BIRTHDATE.equals(other.BIRTHDATE))
			return false;
		if (CASEID == null) {
			if (other.CASEID != null)
				return false;
		} else if (!CASEID.equals(other.CASEID))
			return false;
		if (CASENOTES == null) {
			if (other.CASENOTES != null)
				return false;
		} else if (!CASENOTES.equals(other.CASENOTES))
			return false;
		if (CASEYEAR == null) {
			if (other.CASEYEAR != null)
				return false;
		} else if (!CASEYEAR.equals(other.CASEYEAR))
			return false;
		if (CAUSEA == null) {
			if (other.CAUSEA != null)
				return false;
		} else if (!CAUSEA.equals(other.CAUSEA))
			return false;
		if (CAUSEB == null) {
			if (other.CAUSEB != null)
				return false;
		} else if (!CAUSEB.equals(other.CAUSEB))
			return false;
		if (CAUSEC == null) {
			if (other.CAUSEC != null)
				return false;
		} else if (!CAUSEC.equals(other.CAUSEC))
			return false;
		if (CAUSED == null) {
			if (other.CAUSED != null)
				return false;
		} else if (!CAUSED.equals(other.CAUSED))
			return false;
		if (CAUTOPSY == null) {
			if (other.CAUTOPSY != null)
				return false;
		} else if (!CAUTOPSY.equals(other.CAUTOPSY))
			return false;
		if (CDEATHDATE == null) {
			if (other.CDEATHDATE != null)
				return false;
		} else if (!CDEATHDATE.equals(other.CDEATHDATE))
			return false;
		if (CDEATHFLAG == null) {
			if (other.CDEATHFLAG != null)
				return false;
		} else if (!CDEATHFLAG.equals(other.CDEATHFLAG))
			return false;
		if (CDEATHTIME == null) {
			if (other.CDEATHTIME != null)
				return false;
		} else if (!CDEATHTIME.equals(other.CDEATHTIME))
			return false;
		if (CHOWNINJURY == null) {
			if (other.CHOWNINJURY != null)
				return false;
		} else if (!CHOWNINJURY.equals(other.CHOWNINJURY))
			return false;
		if (CIDATEFLAG == null) {
			if (other.CIDATEFLAG != null)
				return false;
		} else if (!CIDATEFLAG.equals(other.CIDATEFLAG))
			return false;
		if (CINJCITY == null) {
			if (other.CINJCITY != null)
				return false;
		} else if (!CINJCITY.equals(other.CINJCITY))
			return false;
		if (CINJCOUNTY == null) {
			if (other.CINJCOUNTY != null)
				return false;
		} else if (!CINJCOUNTY.equals(other.CINJCOUNTY))
			return false;
		if (CINJDATE == null) {
			if (other.CINJDATE != null)
				return false;
		} else if (!CINJDATE.equals(other.CINJDATE))
			return false;
		if (CINJPLACE == null) {
			if (other.CINJPLACE != null)
				return false;
		} else if (!CINJPLACE.equals(other.CINJPLACE))
			return false;
		if (CINJSTATE == null) {
			if (other.CINJSTATE != null)
				return false;
		} else if (!CINJSTATE.equals(other.CINJSTATE))
			return false;
		if (CINJSTREET == null) {
			if (other.CINJSTREET != null)
				return false;
		} else if (!CINJSTREET.equals(other.CINJSTREET))
			return false;
		if (CINJTIME == null) {
			if (other.CINJTIME != null)
				return false;
		} else if (!CINJTIME.equals(other.CINJTIME))
			return false;
		if (CINJZIP == null) {
			if (other.CINJZIP != null)
				return false;
		} else if (!CINJZIP.equals(other.CINJZIP))
			return false;
		if (CUSTODY == null) {
			if (other.CUSTODY != null)
				return false;
		} else if (!CUSTODY.equals(other.CUSTODY))
			return false;
		if (DEATHPLACE == null) {
			if (other.DEATHPLACE != null)
				return false;
		} else if (!DEATHPLACE.equals(other.DEATHPLACE))
			return false;
		if (DURATIONA == null) {
			if (other.DURATIONA != null)
				return false;
		} else if (!DURATIONA.equals(other.DURATIONA))
			return false;
		if (DURATIONB == null) {
			if (other.DURATIONB != null)
				return false;
		} else if (!DURATIONB.equals(other.DURATIONB))
			return false;
		if (DURATIONC == null) {
			if (other.DURATIONC != null)
				return false;
		} else if (!DURATIONC.equals(other.DURATIONC))
			return false;
		if (DURATIOND == null) {
			if (other.DURATIOND != null)
				return false;
		} else if (!DURATIOND.equals(other.DURATIOND))
			return false;
		if (ETHNICITY == null) {
			if (other.ETHNICITY != null)
				return false;
		} else if (!ETHNICITY.equals(other.ETHNICITY))
			return false;
		if (EVENTADDR_CITY == null) {
			if (other.EVENTADDR_CITY != null)
				return false;
		} else if (!EVENTADDR_CITY.equals(other.EVENTADDR_CITY))
			return false;
		if (EVENTADDR_COUNTY == null) {
			if (other.EVENTADDR_COUNTY != null)
				return false;
		} else if (!EVENTADDR_COUNTY.equals(other.EVENTADDR_COUNTY))
			return false;
		if (EVENTADDR_STATE == null) {
			if (other.EVENTADDR_STATE != null)
				return false;
		} else if (!EVENTADDR_STATE.equals(other.EVENTADDR_STATE))
			return false;
		if (EVENTADDR_STREET == null) {
			if (other.EVENTADDR_STREET != null)
				return false;
		} else if (!EVENTADDR_STREET.equals(other.EVENTADDR_STREET))
			return false;
		if (EVENTADDR_ZIP == null) {
			if (other.EVENTADDR_ZIP != null)
				return false;
		} else if (!EVENTADDR_ZIP.equals(other.EVENTADDR_ZIP))
			return false;
		if (EVENTDATE == null) {
			if (other.EVENTDATE != null)
				return false;
		} else if (!EVENTDATE.equals(other.EVENTDATE))
			return false;
		if (EVENTPLACE == null) {
			if (other.EVENTPLACE != null)
				return false;
		} else if (!EVENTPLACE.equals(other.EVENTPLACE))
			return false;
		if (EVENTTIME == null) {
			if (other.EVENTTIME != null)
				return false;
		} else if (!EVENTTIME.equals(other.EVENTTIME))
			return false;
		if (EXAMDATE == null) {
			if (other.EXAMDATE != null)
				return false;
		} else if (!EXAMDATE.equals(other.EXAMDATE))
			return false;
		if (FIRSTNAME == null) {
			if (other.FIRSTNAME != null)
				return false;
		} else if (!FIRSTNAME.equals(other.FIRSTNAME))
			return false;
		if (FOUNDADDR_CITY == null) {
			if (other.FOUNDADDR_CITY != null)
				return false;
		} else if (!FOUNDADDR_CITY.equals(other.FOUNDADDR_CITY))
			return false;
		if (FOUNDADDR_COUNTY == null) {
			if (other.FOUNDADDR_COUNTY != null)
				return false;
		} else if (!FOUNDADDR_COUNTY.equals(other.FOUNDADDR_COUNTY))
			return false;
		if (FOUNDADDR_STATE == null) {
			if (other.FOUNDADDR_STATE != null)
				return false;
		} else if (!FOUNDADDR_STATE.equals(other.FOUNDADDR_STATE))
			return false;
		if (FOUNDADDR_STREET == null) {
			if (other.FOUNDADDR_STREET != null)
				return false;
		} else if (!FOUNDADDR_STREET.equals(other.FOUNDADDR_STREET))
			return false;
		if (FOUNDADDR_ZIP == null) {
			if (other.FOUNDADDR_ZIP != null)
				return false;
		} else if (!FOUNDADDR_ZIP.equals(other.FOUNDADDR_ZIP))
			return false;
		if (FOUNDDATE == null) {
			if (other.FOUNDDATE != null)
				return false;
		} else if (!FOUNDDATE.equals(other.FOUNDDATE))
			return false;
		if (FOUNDTIME == null) {
			if (other.FOUNDTIME != null)
				return false;
		} else if (!FOUNDTIME.equals(other.FOUNDTIME))
			return false;
		if (GENDER == null) {
			if (other.GENDER != null)
				return false;
		} else if (!GENDER.equals(other.GENDER))
			return false;
		if (HCPROVIDER == null) {
			if (other.HCPROVIDER != null)
				return false;
		} else if (!HCPROVIDER.equals(other.HCPROVIDER))
			return false;
		if (HOSPNAME == null) {
			if (other.HOSPNAME != null)
				return false;
		} else if (!HOSPNAME.equals(other.HOSPNAME))
			return false;
		if (JOBRELATED == null) {
			if (other.JOBRELATED != null)
				return false;
		} else if (!JOBRELATED.equals(other.JOBRELATED))
			return false;
		if (JOBTITLE == null) {
			if (other.JOBTITLE != null)
				return false;
		} else if (!JOBTITLE.equals(other.JOBTITLE))
			return false;
		if (LASTNAME == null) {
			if (other.LASTNAME != null)
				return false;
		} else if (!LASTNAME.equals(other.LASTNAME))
			return false;
		if (LKADATE == null) {
			if (other.LKADATE != null)
				return false;
		} else if (!LKADATE.equals(other.LKADATE))
			return false;
		if (LKATIME == null) {
			if (other.LKATIME != null)
				return false;
		} else if (!LKATIME.equals(other.LKATIME))
			return false;
		if (LKAWHERE == null) {
			if (other.LKAWHERE != null)
				return false;
		} else if (!LKAWHERE.equals(other.LKAWHERE))
			return false;
		if (MANNER == null) {
			if (other.MANNER != null)
				return false;
		} else if (!MANNER.equals(other.MANNER))
			return false;
		if (MARITAL == null) {
			if (other.MARITAL != null)
				return false;
		} else if (!MARITAL.equals(other.MARITAL))
			return false;
		if (MIDNAME == null) {
			if (other.MIDNAME != null)
				return false;
		} else if (!MIDNAME.equals(other.MIDNAME))
			return false;
		if (MRNNUMBER == null) {
			if (other.MRNNUMBER != null)
				return false;
		} else if (!MRNNUMBER.equals(other.MRNNUMBER))
			return false;
		if (OSCOND == null) {
			if (other.OSCOND != null)
				return false;
		} else if (!OSCOND.equals(other.OSCOND))
			return false;
		if (POSSIBLEID == null) {
			if (other.POSSIBLEID != null)
				return false;
		} else if (!POSSIBLEID.equals(other.POSSIBLEID))
			return false;
		if (PRNCITY == null) {
			if (other.PRNCITY != null)
				return false;
		} else if (!PRNCITY.equals(other.PRNCITY))
			return false;
		if (PRNCOUNTY == null) {
			if (other.PRNCOUNTY != null)
				return false;
		} else if (!PRNCOUNTY.equals(other.PRNCOUNTY))
			return false;
		if (PRNDATE == null) {
			if (other.PRNDATE != null)
				return false;
		} else if (!PRNDATE.equals(other.PRNDATE))
			return false;
		if (PRNPLACE == null) {
			if (other.PRNPLACE != null)
				return false;
		} else if (!PRNPLACE.equals(other.PRNPLACE))
			return false;
		if (PRNSTATE == null) {
			if (other.PRNSTATE != null)
				return false;
		} else if (!PRNSTATE.equals(other.PRNSTATE))
			return false;
		if (PRNSTREET == null) {
			if (other.PRNSTREET != null)
				return false;
		} else if (!PRNSTREET.equals(other.PRNSTREET))
			return false;
		if (PRNTIME == null) {
			if (other.PRNTIME != null)
				return false;
		} else if (!PRNTIME.equals(other.PRNTIME))
			return false;
		if (PRNZIP == null) {
			if (other.PRNZIP != null)
				return false;
		} else if (!PRNZIP.equals(other.PRNZIP))
			return false;
		if (RACE == null) {
			if (other.RACE != null)
				return false;
		} else if (!RACE.equals(other.RACE))
			return false;
		if (REPORTDATE == null) {
			if (other.REPORTDATE != null)
				return false;
		} else if (!REPORTDATE.equals(other.REPORTDATE))
			return false;
		if (REPORTTIME == null) {
			if (other.REPORTTIME != null)
				return false;
		} else if (!REPORTTIME.equals(other.REPORTTIME))
			return false;
		if (RESCITY == null) {
			if (other.RESCITY != null)
				return false;
		} else if (!RESCITY.equals(other.RESCITY))
			return false;
		if (RESCOUNTY == null) {
			if (other.RESCOUNTY != null)
				return false;
		} else if (!RESCOUNTY.equals(other.RESCOUNTY))
			return false;
		if (RESNAME == null) {
			if (other.RESNAME != null)
				return false;
		} else if (!RESNAME.equals(other.RESNAME))
			return false;
		if (RESSTATE == null) {
			if (other.RESSTATE != null)
				return false;
		} else if (!RESSTATE.equals(other.RESSTATE))
			return false;
		if (RESSTREET == null) {
			if (other.RESSTREET != null)
				return false;
		} else if (!RESSTREET.equals(other.RESSTREET))
			return false;
		if (RESZIP == null) {
			if (other.RESZIP != null)
				return false;
		} else if (!RESZIP.equals(other.RESZIP))
			return false;
		if (SCENEADDR_CITY == null) {
			if (other.SCENEADDR_CITY != null)
				return false;
		} else if (!SCENEADDR_CITY.equals(other.SCENEADDR_CITY))
			return false;
		if (SCENEADDR_COUNTY == null) {
			if (other.SCENEADDR_COUNTY != null)
				return false;
		} else if (!SCENEADDR_COUNTY.equals(other.SCENEADDR_COUNTY))
			return false;
		if (SCENEADDR_STATE == null) {
			if (other.SCENEADDR_STATE != null)
				return false;
		} else if (!SCENEADDR_STATE.equals(other.SCENEADDR_STATE))
			return false;
		if (SCENEADDR_STREET == null) {
			if (other.SCENEADDR_STREET != null)
				return false;
		} else if (!SCENEADDR_STREET.equals(other.SCENEADDR_STREET))
			return false;
		if (SCENEADDR_ZIP == null) {
			if (other.SCENEADDR_ZIP != null)
				return false;
		} else if (!SCENEADDR_ZIP.equals(other.SCENEADDR_ZIP))
			return false;
		if (SURGDATE == null) {
			if (other.SURGDATE != null)
				return false;
		} else if (!SURGDATE.equals(other.SURGDATE))
			return false;
		if (SURGERY == null) {
			if (other.SURGERY != null)
				return false;
		} else if (!SURGERY.equals(other.SURGERY))
			return false;
		if (SYSTEMID == null) {
			if (other.SYSTEMID != null)
				return false;
		} else if (!SYSTEMID.equals(other.SYSTEMID))
			return false;
		if (success != other.success)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OpenMDIInputFields [SYSTEMID=" + SYSTEMID + ", CASEID=" + CASEID + ", FIRSTNAME=" + FIRSTNAME
				+ ", MIDNAME=" + MIDNAME + ", LASTNAME=" + LASTNAME + ", AGE=" + AGE + ", AGEUNIT=" + AGEUNIT
				+ ", RACE=" + RACE + ", GENDER=" + GENDER + ", ETHNICITY=" + ETHNICITY + ", BIRTHDATE=" + BIRTHDATE
				+ ", MRNNUMBER=" + MRNNUMBER + ", JOBTITLE=" + JOBTITLE + ", MARITAL=" + MARITAL + ", POSSIBLEID="
				+ POSSIBLEID + ", CAUSEA=" + CAUSEA + ", CAUSEB=" + CAUSEB + ", CAUSEC=" + CAUSEC + ", CAUSED=" + CAUSED
				+ ", OSCOND=" + OSCOND + ", MANNER=" + MANNER + ", CHOWNINJURY=" + CHOWNINJURY + ", DURATIONA="
				+ DURATIONA + ", DURATIONB=" + DURATIONB + ", DURATIONC=" + DURATIONC + ", DURATIOND=" + DURATIOND
				+ ", CASENOTES=" + CASENOTES + ", ATWORK=" + ATWORK + ", JOBRELATED=" + JOBRELATED + ", REPORTDATE="
				+ REPORTDATE + ", REPORTTIME=" + REPORTTIME + ", FOUNDDATE=" + FOUNDDATE + ", FOUNDTIME=" + FOUNDTIME
				+ ", EVENTDATE=" + EVENTDATE + ", EVENTTIME=" + EVENTTIME + ", PRNDATE=" + PRNDATE + ", PRNTIME="
				+ PRNTIME + ", EXAMDATE=" + EXAMDATE + ", CINJDATE=" + CINJDATE + ", CINJTIME=" + CINJTIME
				+ ", CIDATEFLAG=" + CIDATEFLAG + ", CDEATHFLAG=" + CDEATHFLAG + ", CDEATHTIME=" + CDEATHTIME
				+ ", LKADATE=" + LKADATE + ", LKATIME=" + LKATIME + ", CASEYEAR=" + CASEYEAR + ", ATHOSPDATE="
				+ ATHOSPDATE + ", ATHOSPTIME=" + ATHOSPTIME + ", RESSTREET=" + RESSTREET + ", RESCITY=" + RESCITY
				+ ", RESCOUNTY=" + RESCOUNTY + ", RESSTATE=" + RESSTATE + ", RESZIP=" + RESZIP + ", DEATHPLACE="
				+ DEATHPLACE + ", EVENTPLACE=" + EVENTPLACE + ", FOUNDADDR_STREET=" + FOUNDADDR_STREET
				+ ", FOUNDADDR_CITY=" + FOUNDADDR_CITY + ", FOUNDADDR_COUNTY=" + FOUNDADDR_COUNTY + ", FOUNDADDR_STATE="
				+ FOUNDADDR_STATE + ", FOUNDADDR_ZIP=" + FOUNDADDR_ZIP + ", EVENTADDR_STREET=" + EVENTADDR_STREET
				+ ", EVENTADDR_CITY=" + EVENTADDR_CITY + ", EVENTADDR_COUNTY=" + EVENTADDR_COUNTY + ", EVENTADDR_STATE="
				+ EVENTADDR_STATE + ", EVENTADDR_ZIP=" + EVENTADDR_ZIP + ", PRNPLACE=" + PRNPLACE + ", PRNSTREET="
				+ PRNSTREET + ", PRNCITY=" + PRNCITY + ", PRNCOUNTY=" + PRNCOUNTY + ", PRNSTATE=" + PRNSTATE
				+ ", PRNZIP=" + PRNZIP + ", CINJPLACE=" + CINJPLACE + ", CINJSTREET=" + CINJSTREET + ", CINJCITY="
				+ CINJCITY + ", CINJCOUNTY=" + CINJCOUNTY + ", CINJSTATE=" + CINJSTATE + ", CINJZIP=" + CINJZIP
				+ ", CDEATHDATE=" + CDEATHDATE + ", RESNAME=" + RESNAME + ", LKAWHERE=" + LKAWHERE + ", HOSPNAME="
				+ HOSPNAME + ", SCENEADDR_STREET=" + SCENEADDR_STREET + ", SCENEADDR_CITY=" + SCENEADDR_CITY
				+ ", SCENEADDR_COUNTY=" + SCENEADDR_COUNTY + ", SCENEADDR_STATE=" + SCENEADDR_STATE + ", SCENEADDR_ZIP="
				+ SCENEADDR_ZIP + ", SURGERY=" + SURGERY + ", SURGDATE=" + SURGDATE + ", HCPROVIDER=" + HCPROVIDER
				+ ", CAUTOPSY=" + CAUTOPSY + ", AUTOPUSED=" + AUTOPUSED + ", CUSTODY=" + CUSTODY + ", success="
				+ success + "]";
	}
}