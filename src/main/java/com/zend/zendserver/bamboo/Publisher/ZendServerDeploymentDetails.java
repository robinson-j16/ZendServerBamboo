package com.zend.zendserver.bamboo.Publisher;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.atlassian.bamboo.build.PlanResultsAction;
import com.zend.zendserver.bamboo.DeploymentCheckTask;
import com.zend.zendserver.bamboo.DeploymentTask;
import com.zend.zendserver.bamboo.TaskResult.ResultParserDeploymentCheck;

@SuppressWarnings("serial")
public class ZendServerDeploymentDetails extends PlanResultsAction {
    private boolean isJob;
    private String strTest;
    
    private String appId;
	private String appBaseUrl;
	private String appName;
	private String userAppName;
	private String installedLocation;
	private String status;
	private String isRollbackable;
	private String isRedeployable;
    
    public String doExecute() throws Exception {
        String result = super.doExecute();
            this.isJob = true;
            strTest = "+++";

            strTest += "<br>---------------------------------<br>";
            Map<String, String> data = this.getResultsSummary().getCustomBuildData();
            
            
            strTest += " working.dir: " + data.get("working.directory");
            strTest += " buildId: " + this.getBuildNumber();
            String revision = data.get("repository.revision.number").substring(0, 6);
            strTest += " revision: " + revision;
            strTest += "<br>---------------------------------<br>";
            
            strTest += data.get(DeploymentTask.OUTPUT_FILE_KEY);
            
strTest += "<br>---------------------------------<br>";
            
            //strTest += data.get(DeploymentCheckTask.OUTPUT_FILE_KEY);
            buildApplicationInfo();
            
        return result;

    }
    
    public boolean getIsJob() {
        return this.isJob;
    }
    
    public String getStrTest() {
        return this.strTest;
    }
    
    private void buildApplicationInfo() throws ParserConfigurationException, SAXException, IOException {
    	Map<String, String> metadata = this.getResultsSummary().getCustomBuildData();
    	
    	ResultParserDeploymentCheck parser = new ResultParserDeploymentCheck(
    		metadata.get(DeploymentCheckTask.OUTPUT_FILE_KEY)	
        );
            
    	Element applicationInfo = parser.getNodeApplicationInfo();
		
		setAppId(parser.getValue(applicationInfo, "id"));
		setAppBaseUrl(parser.getValue(applicationInfo, "baseUrl"));
		setAppName(parser.getValue(applicationInfo, "appName"));
		setUserAppName(parser.getValue(applicationInfo, "userAppName"));
		setInstalledLocation(parser.getValue(applicationInfo, "installedLocation"));
		setStatus(parser.getValue(applicationInfo, "status"));
		setIsRollbackable(parser.getValue(applicationInfo, "isRollbackable"));
		setIsRedeployable(parser.getValue(applicationInfo, "isRedeployable"));
    }

	public String getAppId() {
		return appId;
	}

	public void setAppId(String applicationId) {
		this.appId = applicationId;
	}

	public String getAppBaseUrl() {
		return appBaseUrl;
	}

	public void setAppBaseUrl(String appBaseUrl) {
		this.appBaseUrl = appBaseUrl;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserAppName() {
		return userAppName;
	}

	public void setUserAppName(String userAppName) {
		this.userAppName = userAppName;
	}

	public String getInstalledLocation() {
		return installedLocation;
	}

	public void setInstalledLocation(String installedLocation) {
		this.installedLocation = installedLocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsRollbackable() {
		return isRollbackable;
	}

	public void setIsRollbackable(String isRollbackable) {
		this.isRollbackable = isRollbackable;
	}

	public String getIsRedeployable() {
		return isRedeployable;
	}

	public void setIsRedeployable(String isRedeployable) {
		this.isRedeployable = isRedeployable;
	}
}