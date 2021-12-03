plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}


dependencies {

}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}

val day: String by project
tasks.register("day") {
    doFirst {
        println("src/Day${day}.kt")
    }
    doLast {
        val template = File("src/utils/template.kt").readText().replace("XX", day)
        File("src/Day${day}.kt").writeText(template)
        File("src/input/${day}_test.txt").createNewFile()
        File("src/input/${day}.txt").createNewFile()
    }
}
