<!---
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

maven-scm-provider-jgit
===

This scm provider implementation allows the usage of git with the release and scm plugin without having to install a nativ git client. 
This implementation uses username and password instead of a public/private keys to authenticate the requests to a remote repository like GitHub.

Configuration
---

Usage with the `maven-release-plugin`

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-release-plugin</artifactId>
				<version>2.3</version>
				<configuration>
					<providerImplementations>
						<git>jgit</git>
					</providerImplementations>
				</configuration>
				<dependencies>
					<dependency>
						<groupId>org.apache.maven.scm</groupId>
						<artifactId>maven-scm-provider-jgit</artifactId>
						<version>1.8.1</version>
					</dependency>
				</dependencies>
			</plugin>

Usage with the `maven-scm-plugin`

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-scm-plugin</artifactId>
				<version>1.8.1</version>
				<configuration>
					<providerImplementations>
						<git>jgit</git>
					</providerImplementations>
				</configuration>
				<dependencies>
					<dependency>
						<groupId>org.apache.maven.scm</groupId>
						<artifactId>maven-scm-provider-jgit</artifactId>
						<version>${jgit.provider.version}</version>
					</dependency>
				</dependencies>
			</plugin>
			
Examples
____
changelog

	mvn org.apache.maven.plugins:maven-scm-plugin:1.8.1:changelog
	
release

	mvn release:prepare release:perform -Dresume=false -Dusername=XXX -Dpassword=XXX

	

			
			
Features
---

this is a brief list of the supported maven-scm-provider-jgit's integration status with regard to some core maven plugins


maven-scm-plugin
---

| goal        | implemented?|
| ------------- |:-------------:|
| scm:list | yes | 
| scm:tag | yes | 
| scm:bootstrap | no |  
| scm:export | no |  	
| scm:update | no |  	
| scm:status | yes | 
| scm:edit | no |  	
| scm:changelog | yes |  	
| scm:add | yes |  	
| scm:unedit | no |  	
| scm:validate | yes |  	
| scm:branch | yes |  	
| scm:checkin | yes |  	
| scm:checkout | yes |  	
| scm:diff | yes | 
| scm:blame | yes | 
| scm:remoteinfo | yes | 

maven-release-plugin
---

| goal        | implemented?|
| ------------- |:-------------:|
| release:clean | yes |  	
| release:prepare | yes |  	
| release:rollback | no |  	
| release:perform | yes |  	
| release:stage | no |  	
| release:branch | yes |  	