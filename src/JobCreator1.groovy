
def branches = ['release/poc2', 'release/poc1', 'master']
def project_Names = ['play_kernel', 'playProject']

for (String project_Name : project_Names) {

def fetch_URL = 'https://github.com/fahdolaf'
//def project_Name = 'playProject'
def reqiredPrj 
def reqiredTools  = 'play_kernel'
def Git_credentials = 'e764954a-6484-4515-91db-bdc151316cbb'

def productPrefix = "POC-DSL"

def specificNode = null
def jobTemplate = "Template--pkg--Build"
def defaultTimeout = "10"
def jobTimeoutString =  "60" 
def jobPxString = "false"
def jobKwString = "false"
def jobNotify = "bom@take.com"

def reqiredPrjtURL  = "${fetch_URL}${reqiredPrj}"
def reqiredToolsURL  = "${fetch_URL}${reqiredTools}"

def jobGitURL  = "${fetch_URL}${project_Name}"
def jobBranch  = 'master'
def jobName = "${productPrefix}--${project_Name}--${jobBranch}--Build".replaceAll('/','-')

def jobDescription = """\
Project Name :  ${project_Name} 
GIT URL: ${jobGitURL}
Branch: ${jobBranch}
THIS IS A GENERATED JOB, DO NOT MODIFY ITS CONFIGURATION HERE!!! 			
"""	

def envVars = [:]
    envVars['BRANCH'] = jobBranch
    envVars['REPO_NAME'] = project_Name

def jobPxEnabled
if (jobPxString.isEmpty()) {
  jobPxEnabled = false
} else {
  jobPxEnabled = jobPxString.toBoolean()
}

def jobTimeout
if (jobTimeoutString.isEmpty()) {
  jobTimeout = defaultTimeout as int
} else {
  jobTimeout = jobTimeoutString as int
}

if (!jobTemplate.isEmpty()) {
job(jobName) {
    using("${jobTemplate}")
CRA5  Q    disabled(false)
    description("${jobDescription}")
    if (specificNode != null) {
        label("${specificNode}")
    }
    
    wrappers {
			timeout {
				absolute(jobTimeout)
                                writeDescription("Build aborted due to timeout after {0} minutes")
			}
			timestamps()
		}
		
    multiscm {
        git {
            remote {
                name('origin')
                    url(jobGitURL)
                    credentials(Git_credentials)
            }
            branch(jobBranch)
            extensions {
                relativeTargetDirectory(project_Name)
            }
        }
        if (!reqiredPrj.isEmpty()) {
        git {
            remote {
                name('origin')
                    url(reqiredToolsURL)
                    credentials(Git_credentials)
            }
            branch('master')
            extensions {
                relativeTargetDirectory(reqiredTools)
            }
        }
        }
        git {
            remote {
                name('origin')
                    url(reqiredPrjtURL)
                    credentials(Git_credentials)
            }
            branch('master')
            extensions {
                relativeTargetDirectory(reqiredPrj)
            }
        }

    }
    triggers {
        scm('H/15 * * * *')
    }
    	
    environmentVariables(envVars)


	
    steps {
    shell('''

#!/bin/sh -x

cd ${WORKSPACE}

ls -la

          ''')    
    }
}	
	
}
}
