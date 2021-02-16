pluginManagement {
	repositories {
		gradlePluginPortal()
		maven { url = uri("https://repo.nokee.dev/release") }
		maven { url = uri("https://repo.nokee.dev/snapshot") }
	}
	val nokeeVersion = "0.3.0"
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id.startsWith("dev.nokee.")) {
				useModule("${requested.id.id}:${requested.id.id}.gradle.plugin:${nokeeVersion}")
			}
		}
	}
}

rootProject.name = "publish-jni-library-with-target-machines"

includeBuild("gradle/plugins/jni-library-publish")
