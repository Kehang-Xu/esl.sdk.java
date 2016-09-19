package com.silanis.esl.sdk.examples;

import com.silanis.esl.sdk.DocumentPackage;
import com.silanis.esl.sdk.DocumentType;

import static com.silanis.esl.sdk.builder.DocumentBuilder.newDocumentWithName;
import static com.silanis.esl.sdk.builder.PackageBuilder.newPackageNamed;
import static com.silanis.esl.sdk.builder.SignatureBuilder.acceptanceFor;
import static com.silanis.esl.sdk.builder.SignatureBuilder.signatureFor;
import static com.silanis.esl.sdk.builder.SignerBuilder.newSignerWithEmail;

public class ConsentExample extends SDKSample {

    public static void main( String... args ) {
        new ConsentExample().run();
    }

    public void execute() {
        DocumentPackage superDuperPackage = newPackageNamed(getPackageName())
                .describedAs( "This is a package created using the eSignLive SDK" )
                .withSigner( newSignerWithEmail( email1 )
                        .withFirstName( "John1" )
                        .withLastName( "Smith1" ) )
                .withSigner( newSignerWithEmail( email2 )
                        .withFirstName( "John2" )
                        .withLastName( "Smith2" ) )
                .withDocument( newDocumentWithName( "First Document" )
                        .fromStream( documentInputStream1, DocumentType.PDF )
                        .withSignature( acceptanceFor( email1 ) ) )
                .withDocument( newDocumentWithName( "Second Document" )
                        .fromStream( documentInputStream2, DocumentType.PDF )
                        .withSignature( signatureFor( email1 )
                                .onPage( 0 )
                                .atPosition( 100, 100 ) ) )
                .build();

        packageId = eslClient.createPackage( superDuperPackage );
        eslClient.sendPackage( packageId );
        retrievedPackage = eslClient.getPackage( packageId );
    }
}
