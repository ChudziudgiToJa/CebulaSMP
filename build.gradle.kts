plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
    kotlin("jvm")
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://maven.nostal.ink/repository/maven-snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.stellardrift.ca/repository/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://maven.enginehub.org/repo/")
    maven("https://jitpack.io")
    maven("https://maven.enginehub.org/repo/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://maven.citizensnpcs.co/repo")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

    val eternalcodeVersion = "1.1.3"
    implementation("com.eternalcode:multification-bukkit:${eternalcodeVersion}")
    implementation("com.eternalcode:multification-okaeri:${eternalcodeVersion}")
    implementation("com.eternalcode:eternalcode-commons-adventure:${eternalcodeVersion}")
    compileOnly("com.eternalcode:eternalcore-api:1.5.1")


    implementation("dev.rollczi:litecommands-bukkit:3.4.3")
    implementation("dev.rollczi:litecommands-adventure:3.4.3")

    val okaeriConfigsVersion = "5.0.3"
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:${okaeriConfigsVersion}")

    implementation("de.rapha149.signgui:signgui:2.5.0")

    // worldguard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.10")

    //NPC
    compileOnly("net.citizensnpcs:citizens-main:2.0.33-SNAPSHOT") {
        exclude(group = "*", module = "*")
    }

    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.projectlombok:lombok:1.18.30")

    // database
    implementation("org.mongodb:mongo-java-driver:3.12.14")

    //Placeholder api
    compileOnly("me.clip:placeholderapi:2.11.6")

    //Decenthologram
    compileOnly("com.github.decentsoftware-eu:decentholograms:2.8.11")

    //Vault
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")

    //Protocolib
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")

    implementation(kotlin("stdlib-jdk8"))
}

bukkit {
    main = "pl.chudziudgi.lifesteal.SurvivalPlugin"
    name = "lifesteal-core"
    version = "1.0-SNAPSHOT"
    apiVersion = "1.20"
    author = "Chudziudgi"
    softDepend = listOf("PlaceholderAPI", "Citizens", "DecentHolograms")
    depend = listOf("Vault", "ProtocolLib")
    prefix = "lifesteal-core"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    archiveFileName.set("lifesteal-core.jar")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}