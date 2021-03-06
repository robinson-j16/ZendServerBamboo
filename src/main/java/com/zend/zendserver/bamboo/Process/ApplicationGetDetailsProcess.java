package com.zend.zendserver.bamboo.Process;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.zend.zendserver.bamboo.Env.BuildEnv;

public class ApplicationGetDetailsProcess implements Process {
	
	public static final String OUTPUT_FILE_PREFIX = "applicationGetDetails-";
	public static final String OUTPUT_FILE_SUFFIX = ".xml";
	
	private final ConfigurationMap configMap;
	private final ExecutableHelper executableHelper;
	private BuildEnv buildEnv;
	private String applicationId;
	
	private static int testIteration = 0;
	
	public ApplicationGetDetailsProcess(ConfigurationMap configMap, ExecutableHelper executableHelper)
    {
		this.configMap = configMap;
		this.executableHelper = executableHelper;
    }
	
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public List<String> getCommandList() throws Exception {
		List<String> commandList = new LinkedList<String>(Arrays.asList(
				executableHelper.getExecutable(),
				"applicationGetDetails",
				"--application=" + applicationId,
				"--zsurl=" + configMap.get("zs_url"),
				"--zskey=" + configMap.get("api_key"),
        		"--zssecret=" + configMap.get("api_secret"),
        		"--zsversion=" + configMap.get("zsversion")));
		
		if (!StringUtils.isEmpty(configMap.get("custom_options"))) {
			commandList.add(configMap.get("custom_options"));
		}
		
		return commandList;
	}
	
	public void incTestIteration() {
		testIteration++;
	}

	public String getOutputFilePrefix() {
		return OUTPUT_FILE_PREFIX;
	}

	public String getOutputFileSuffix() {
		return "-" + String.valueOf(testIteration) + OUTPUT_FILE_SUFFIX;
	}

	public void setBuildEnv(BuildEnv buildEnv) {
		this.buildEnv = buildEnv;
	}
	
	public BuildEnv getBuildEnv() {
		return buildEnv;
	}
}
