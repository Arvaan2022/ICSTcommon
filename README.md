dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

 
	dependencies {
	        implementation 'com.github.Arvaan2022:ICSTcommon:Tag'
	}

//below variable set in your app mandatory
App.language
App.url
App.token
App.appLogo
