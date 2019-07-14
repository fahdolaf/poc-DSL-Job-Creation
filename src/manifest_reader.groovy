//read CONFIG_FILE from ENV
config_file = System.getenv('Manifest_FILE')

//then try to get it from Jenkins
if (config_file == null) {
	try {
		config_file = "${CONFIG_FILE}".toString()
	} catch (e) {
		println e
	}
}
println config_file
assert config_file?.trim() != null, "Environment variable CONFIG_FILE not set or empty!"

//def manifest = new XmlSlurper().parse('/var/lib/jenkins/workspace/poc-manifest-reader/manifest/default.xml')
def manifest = new XmlSlurper().parse(config_file)

//manifest.remote.each{ jobConfig ->
//    def fetch_URL = jobConfig.@fetch.text()
//    println fetch_URL
//}

    def fetch_URL = manifest.remote[1].@fetch.text()
    println fetch_URL

manifest.project.each{ jobConfig ->
    def project_Name = jobConfig.@name.text()
    println project_Name
    
    def project_type = jobConfig.@type.text()
    if (!project_type.isEmpty()) {
		println project_type
		
		
    	def reqiredPrj = jobConfig.@reqiredPrj.text()
            if (!reqiredPrj.isEmpty()) {
        		println reqiredPrj
        		
////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////

        	}
	}
    

}
