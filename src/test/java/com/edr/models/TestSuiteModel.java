package com.edr.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

public class TestSuiteModel {
	@JacksonXmlRootElement(localName = "suite")
	public class Suite {

	    @JacksonXmlProperty(isAttribute = true)
	    private String name;

	    @JacksonXmlProperty(localName = "listeners")
	    @JacksonXmlElementWrapper(useWrapping = false)
	    private List < Listeners > listeners;
	    
	    @JacksonXmlProperty(localName = "test")
	    @JacksonXmlElementWrapper(useWrapping = false)
	    private List < Test > tests;

	    public Suite(String name) {
	        this.name = name;
	        this.listeners = new ArrayList<Suite.Listeners>();
	        this.tests = new ArrayList < Suite.Test > ();
	    }
	    
	    public void addListeners(List<String> listenerList) {
	    	Listeners listeners = new Listeners();
	    	for(String l : listenerList) {
	    		listeners.addListeners(l);
	    	}	  
	    	this.listeners.add(listeners);
	    }

	    public void addTest(String testname, String paramName, String paramValue, String className,
	    		String methodName,String groupName,String dependsOn) {
	        Test test = new Test(testname);
	        
	        String [] paramNameArray = paramName.split(",");
	        String [] paramValueArray = paramValue.split(",");
	        for(int i=0;i<paramNameArray.length;i++) {
	        	test.addParam(paramNameArray[i], paramValueArray[i]);
	        }
	        
	        
	        String [] groupNameArray = groupName.split(",");
	        String [] dependsOnArray = dependsOn.split(",");
	        for(int i=0;i<groupNameArray.length;i++) {
	        	test.addGroups(groupNameArray[i], dependsOnArray[i]);
	        }
	        
	        String [] classNameArray = className.split(",");
	        String [] classLevelMethodNameArray = methodName.split(",");
	        for(int i=0;i<classNameArray.length;i++) {
	        	test.addClasses(classNameArray[i], classLevelMethodNameArray[i]);
	        }	        
	        this.tests.add(test);
	    }
	    class Listeners {
	    	@JacksonXmlProperty(localName = "listener")
	    	@JacksonXmlElementWrapper(useWrapping = false)
	        private List<Listener> classNameList;
	    	
	    	public Listeners() {
	    		this.classNameList = new ArrayList<Suite.Listener>();
	    	}

	        public void addListeners(String className) {
	            this.classNameList.add(new Listener(className));
	        }
	    }
	    class Listener {
	    	@JacksonXmlProperty(isAttribute = true,localName = "class-name")
	        private String className;
	    	
	    	public Listener(String className) {
	    		this.className  = className;
	    	}	    	
	    }
	    class Test {

	        @JacksonXmlProperty(isAttribute = true)
	        private String name;

	        @JacksonXmlProperty(localName = "parameter")
	        @JacksonXmlElementWrapper(useWrapping = false)
	        private List<Parameter> params;

	        @JacksonXmlProperty(localName = "groups")
	        private Groups groups;
	        
	        @JacksonXmlProperty(localName = "classes")
	        private Classes classes;
	        
	        public Test(String name) {
	            this.name = name;
	            classes = new Classes();
	            groups = new Groups();
	            params = new ArrayList<Suite.Parameter>();
	        }

	        public void addParam(String paramName,String paramValue) {	        	
	        	params.add(new Parameter(paramName,paramValue));
	        }
	        
	        public void addGroups(String groupName,String dependsOn) {	
	        	groups.addGroups(groupName,dependsOn);	        	
	        }

	        public void addClasses(String className,String methodName) {	
	        	classes.addClasses(className,methodName);	        	
	        }
	    }

//	    class Parameters {
//	    	@JacksonXmlProperty(localName = "parameter")
//	        @JacksonXmlElementWrapper(useWrapping = false)
//	        private List<Parameter> parameterList;
//
//	        public Parameters() {
//	            this.parameterList = new ArrayList<Suite.Parameter>();
//	        }
//	        
//	        public void addParameter(String name, String value) {
//	        	parameterList.add(new Parameter(name, value));
//	        }
//	    }
	    class Parameter {
	        @JacksonXmlProperty(isAttribute = true)
	        private String name;

	        @JacksonXmlProperty(isAttribute = true)
	        private String value;

	        public Parameter(String name, String value) {
	            this.name = name;
	            this.value = value;
	        }
	    }
	    class Groups {	 
	        @JacksonXmlProperty(localName = "dependencies")
	        @JacksonXmlElementWrapper(useWrapping = false)
	        private Dependencies dependencies;

	        public Groups() {
	        	dependencies = new Dependencies();	        	
	        }
	        
	        public void addGroups(String groupName,String dependsOn) {
	        	dependencies.addGroup(groupName, dependsOn);
	        }
	        
	    }
	    class Dependencies {

	        @JacksonXmlProperty(localName = "group")
	        @JacksonXmlElementWrapper(useWrapping = false)
	        private List<Group> groupList;

	        public Dependencies() {
	            groupList = new ArrayList<Group>();
	        }
	        
	        public void addGroup(String groupName,String dependsOn) {	        	
	            this.groupList.add(new Group(groupName,dependsOn));
	        }
	    }
	    
	    class Group {
	    	@JacksonXmlProperty(isAttribute = true)
	        private String name;
	    	@JacksonXmlProperty(isAttribute = true,localName = "depends-on")
	        private String dependsOn;
	    	
	    	Group(String name,String dependsOn) {
	            this.name = name;
	            this.dependsOn = dependsOn;
	        }
	    }

	    class Classes {

	        @JacksonXmlProperty(localName = "class")
	        @JacksonXmlElementWrapper(useWrapping = false)
	        private List < Class > classList;

	        public Classes() {
	            this.classList = new ArrayList < Suite.Class > ();
	        }

	        public void addClasses(String className,String methodName) {
	            this.classList.add(new Class(className,methodName));
	        }
	    }

	    class Class {

	        @JacksonXmlProperty(isAttribute = true)
	        private String name;
	        
	        @JacksonXmlProperty(localName = "methods")
	        @JacksonXmlElementWrapper(useWrapping = false)
	        private Methods methods;
	        
	        Class(String className,String methodName) {
	            this.name = className;
	            methods=new Methods();
	            String [] methodNameArray = methodName.split("#");
	        	for(int i=0;i<methodNameArray.length;i++) {
	        		this.methods.addMethods(methodNameArray[i]);
	        	}	            
	        }	 
	    }
	    
	    class Methods {
	    	@JacksonXmlProperty(localName = "include")
	        @JacksonXmlElementWrapper(useWrapping = false)
	        private List < Include > includeList;

	        public Methods() {
	            this.includeList = new ArrayList < Include > ();
	        }

	        public void addMethods(String methodName) {
				this.includeList.add(new Include(methodName));
				
			}			
	    }
	    class Include {
	    	@JacksonXmlProperty(isAttribute = true)
	        private String name;

	        Include(String name) {
	            this.name = name;
	        }
	    }

	}

}
