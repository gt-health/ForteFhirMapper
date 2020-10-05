package edu.gatech.chai.Submission.Configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "submission.sources")
public class SubmissionSourcesConfiguration {
	private List<String> sourceurl;
	private List<String> sourcemode;
	public List<String> getSourceurl() {
		return sourceurl;
	}
	public void setSourceurl(List<String> sourceurl) {
		this.sourceurl = sourceurl;
	}
	public List<String> getSourcemode() {
		return sourcemode;
	}
	public void setSourcemode(List<String> sourcemode) {
		this.sourcemode = sourcemode;
	}
}
