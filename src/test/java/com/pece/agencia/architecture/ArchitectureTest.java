package com.pece.agencia.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

class ArchitectureTest {

    private JavaClasses importMainClasses() {
        // Importa apenas classes do diretório de produção (target/classes)
        return new ClassFileImporter().importPath("target/classes");
    }

    @Test
    void servicesShouldResideInServicePackage() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().resideInAPackage("..service..")
                .check(importedClasses);
    }

    @Test
    void controllersShouldOnlyDependOnServiceAndDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAnyPackage("..repository..")
                .because("Controllers não devem depender de repositórios diretamente, apenas de services e domain").check(importedClasses);
    }

    @Test
    void servicesShouldOnlyDependOnRepositoryAndDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..")
                .because("Services não devem depender de controllers, apenas de repository e domain").check(importedClasses);
    }

    @Test
    void repositoriesShouldOnlyDependOnDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..", "..service..")
                .because("Repositories não devem depender de controllers ou services, apenas de domain").check(importedClasses);
    }

    @Test
    void domainShouldNotDependOnOtherLayers() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..", "..service..", "..repository..")
                .because("Domain não deve depender de nenhuma camada externa").check(importedClasses);
    }
}
