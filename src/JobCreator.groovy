def giturl = 'https://github.com/fahdolaf/playProject.git'
def project = 'play_kernel'
def branches = ['release/poc2', 'release/poc1', 'master']

for (String branchName : branches) {
    System.out.println(branchName);
    def jobName = "DSL-${project}-${branchName}".replaceAll('/','-')
job(jobName) {
    scm {
        git {
            remote {
                name('origin')
                    url(giturl)
                    //credentials('xxx-xx-xx-xx-xxxx')
            }
            branch(branchName)
        }
    }
    triggers {
        scm('H/15 * * * *')
    }
    steps {
    shell('''

#!/bin/sh -x

cd ${WORKSPACE}

ls -la


          ''')    
    }
}
}
