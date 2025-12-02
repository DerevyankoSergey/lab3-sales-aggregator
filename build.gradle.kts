import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification

plugins {
    id("java")
    id("application")
    id("jacoco")
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    // Компилируем под Java 11, чтобы код собирался на JDK 11, 17 и 21
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


application {
    mainClass.set("org.example.sales.Main")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

tasks.test {
    useJUnitPlatform()
    // после тестов сразу генерируем отчёт
    finalizedBy(tasks.named("jacocoTestReport"))
}

jacoco {
    toolVersion = "0.8.12"
}

// отчёт JaCoCo (html + xml)
tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

// правило: покрытие строк >= 80% для классов бизнес-логики (service)
tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.test)

    violationRules {
        rule {
            element = "CLASS"
            // считаем только сервисы, а не Main/конфиг/вывод
            includes = listOf("org.example.sales.service.*")

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}



// чтобы check / build падали, если покрытие меньше порога
tasks.check {
    dependsOn(tasks.named("jacocoTestCoverageVerification"))
}
